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
    @Inject EtudiantService service;

    @GET public Response lister() { return Response.ok(service.tous()).build(); }

    @GET @Path("/{id}")
    public Response trouver(@PathParam("id") long id) {
        Etudiant e = service.trouver(id);
        if (e == null) return Response.status(Response.Status.NOT_FOUND).entity(Map.of("erreur", "ETUDIANT_INTROUVABLE")).build();
        return Response.ok(e).build();
    }

    @POST @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouter(Etudiant entree, @Context UriInfo uriInfo) {
        if (entree == null || entree.getNom() == null || entree.getNom().isBlank() ||
            entree.getEmail() == null || !entree.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("erreur", "DONNEES_INVALIDES")).build();
        }
        Etudiant cree = service.creer(entree.getNom(), entree.getEmail(), entree.getDepartementId());
        if (entree.getModuleIds() != null) cree.setModuleIds(entree.getModuleIds()); // Lien N-N initial
        
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(cree.getId())).build();
        return Response.created(uri).entity(cree).build();
    }

    @PUT @Path("/{id}") @Consumes(MediaType.APPLICATION_JSON)
    public Response modifier(@PathParam("id") long id, Etudiant entree) {
        if (entree == null || entree.getNom() == null || entree.getNom().isBlank() ||
            entree.getEmail() == null || !entree.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("erreur", "DONNEES_INVALIDES")).build();
        }
        Etudiant maj = service.mettreAJour(id, entree.getNom(), entree.getEmail(), entree.getDepartementId(), entree.getModuleIds());
        if (maj == null) return Response.status(Response.Status.NOT_FOUND).entity(Map.of("erreur", "ETUDIANT_INTROUVABLE")).build();
        return Response.ok(maj).build();
    }

    @PATCH @Path("/{id}") @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierPartiellement(@PathParam("id") long id, Etudiant entree) {
        if (entree.getEmail() != null && !entree.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("erreur", "EMAIL_INVALIDE")).build();
        }
        Etudiant maj = service.modifierPartiellement(id, entree.getNom(), entree.getEmail(), entree.getDepartementId(), entree.getModuleIds());
        if (maj == null) return Response.status(Response.Status.NOT_FOUND).entity(Map.of("erreur", "ETUDIANT_INTROUVABLE")).build();
        return Response.ok(maj).build();
    }

    @DELETE @Path("/{id}")
    public Response supprimer(@PathParam("id") long id) {
        if (!service.supprimer(id)) return Response.status(Response.Status.NOT_FOUND).entity(Map.of("erreur", "ETUDIANT_INTROUVABLE")).build();
        return Response.noContent().build();
    }
}