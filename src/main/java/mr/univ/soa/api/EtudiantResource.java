package mr.univ.soa.api;

import java.net.URI;
import java.util.Map;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import mr.univ.soa.metier.Etudiant;
import mr.univ.soa.service.EtudiantService;

@Path("/etudiants")
@Produces(MediaType.APPLICATION_JSON)
public class EtudiantResource {

    @Inject
    EtudiantService service;

    @GET
    public Response lister() {
        return Response.ok(service.tous()).build();
    }

    @GET
    @Path("/{id}")
    public Response trouver(@PathParam("id") long id) {
        Etudiant e = service.trouver(id);
        if (e == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("erreur", "ETUDIANT_INTROUVABLE", "message", "Identifiant inconnu"))
                    .build();
        }
        return Response.ok(e).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouter(Etudiant entree, @Context UriInfo uriInfo) {
        if (entree == null || entree.getNom() == null || entree.getNom().isBlank() ||
            entree.getEmail() == null || !entree.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erreur", "DONNEES_INVALIDES", "message", "Nom ou email invalide"))
                    .build();
        }
        
        Etudiant cree = service.creer(entree.getNom(), entree.getEmail());
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(cree.getId())).build();
        return Response.created(uri).entity(cree).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifier(@PathParam("id") long id, Etudiant entree) {
        // 1. On vérifie la validité des données (même règle que le POST)
        if (entree == null || entree.getNom() == null || entree.getNom().isBlank() ||
            entree.getEmail() == null || !entree.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erreur", "DONNEES_INVALIDES", "message", "Nom ou email invalide"))
                    .build();
        }

        // 2. On tente de modifier. Si null, c'est que l'ID n'existe pas (404).
        Etudiant maj = service.mettreAJour(id, entree.getNom(), entree.getEmail());
        if (maj == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("erreur", "ETUDIANT_INTROUVABLE", "message", "Identifiant inconnu"))
                    .build();
        }

        // 3. Succès : on renvoie 200 OK avec l'objet modifié
        return Response.ok(maj).build();
    }

    @DELETE
    @Path("/{id}")
    public Response supprimer(@PathParam("id") long id) {
        boolean supprime = service.supprimer(id);
        
        if (!supprime) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("erreur", "ETUDIANT_INTROUVABLE", "message", "Identifiant inconnu"))
                    .build();
        }

        // 204 No Content : Succès, mais on ne renvoie rien dans le corps de la réponse
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierPartiellement(@PathParam("id") long id, Etudiant entree) {
        // 1. On cherche l'étudiant existant
        Etudiant existant = service.trouver(id);
        if (existant == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("erreur", "ETUDIANT_INTROUVABLE", "message", "Identifiant inconnu"))
                    .build();
        }

        // 2. Validation partielle (seulement si les champs sont fournis)
        if (entree.getNom() != null && entree.getNom().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erreur", "DONNEES_INVALIDES", "message", "Le nom ne peut pas être vide"))
                    .build();
        }
        if (entree.getEmail() != null && !entree.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erreur", "DONNEES_INVALIDES", "message", "Email invalide"))
                    .build();
        }

        // 3. Fusion (Merge) : on garde l'ancienne valeur si la nouvelle est null
        String nouveauNom = (entree.getNom() != null) ? entree.getNom() : existant.getNom();
        String nouvelEmail = (entree.getEmail() != null) ? entree.getEmail() : existant.getEmail();

        // 4. Mise à jour
        Etudiant maj = service.mettreAJour(id, nouveauNom, nouvelEmail);
        
        return Response.ok(maj).build();
    }
}