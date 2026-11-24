package mr.univ.soa.api;

import java.net.URI;
import java.util.Map;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import mr.univ.soa.metier.Departement;
import mr.univ.soa.service.DepartementService;

@Path("/departements")
@Produces(MediaType.APPLICATION_JSON)
public class DepartementResource {

    @Inject
    DepartementService service;

    @GET
    public Response lister() { return Response.ok(service.tous()).build(); }

    @GET
    @Path("/{id}")
    public Response trouver(@PathParam("id") long id) {
        Departement d = service.trouver(id);
        if (d == null) return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "DEPARTEMENT_INTROUVABLE")).build();
        return Response.ok(d).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouter(Departement entree, @Context UriInfo uriInfo) {
        if (entree == null || entree.getNom() == null || entree.getNom().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erreur", "DONNEES_INVALIDES", "message", "Le nom est requis")).build();
        }
        Departement cree = service.creer(entree.getNom());
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(cree.getId())).build();
        return Response.created(uri).entity(cree).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifier(@PathParam("id") long id, Departement entree) {
        if (entree == null || entree.getNom() == null || entree.getNom().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erreur", "DONNEES_INVALIDES")).build();
        }
        Departement maj = service.mettreAJour(id, entree.getNom());
        if (maj == null) return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "DEPARTEMENT_INTROUVABLE")).build();
        return Response.ok(maj).build();
    }

    @DELETE
    @Path("/{id}")
    public Response supprimer(@PathParam("id") long id) {
        if (!service.supprimer(id)) return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "DEPARTEMENT_INTROUVABLE")).build();
        return Response.noContent().build();
    }
}