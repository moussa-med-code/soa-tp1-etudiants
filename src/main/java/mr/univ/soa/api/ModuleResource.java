package mr.univ.soa.api;

import java.net.URI;
import java.util.Map;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import mr.univ.soa.metier.Module;
import mr.univ.soa.service.ModuleService;

@Path("/modules")
@Produces(MediaType.APPLICATION_JSON)
public class ModuleResource {

    @Inject
    ModuleService service;

    @GET
    public Response lister() { return Response.ok(service.tous()).build(); }

    @GET
    @Path("/{id}")
    public Response trouver(@PathParam("id") long id) {
        Module m = service.trouver(id);
        if (m == null) return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "MODULE_INTROUVABLE")).build();
        return Response.ok(m).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouter(Module entree, @Context UriInfo uriInfo) {
        if (entree == null || entree.getNom() == null || entree.getNom().isBlank() || entree.getDepartementId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erreur", "DONNEES_INVALIDES", "message", "Nom et departementId requis")).build();
        }
        Module cree = service.creer(entree.getNom(), entree.getDepartementId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(cree.getId())).build();
        return Response.created(uri).entity(cree).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifier(@PathParam("id") long id, Module entree) {
        if (entree == null || entree.getNom() == null || entree.getNom().isBlank() || entree.getDepartementId() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erreur", "DONNEES_INVALIDES")).build();
        }
        Module maj = service.mettreAJour(id, entree.getNom(), entree.getDepartementId());
        if (maj == null) return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "MODULE_INTROUVABLE")).build();
        return Response.ok(maj).build();
    }

    @DELETE
    @Path("/{id}")
    public Response supprimer(@PathParam("id") long id) {
        if (!service.supprimer(id)) return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("erreur", "MODULE_INTROUVABLE")).build();
        return Response.noContent().build();
    }
}