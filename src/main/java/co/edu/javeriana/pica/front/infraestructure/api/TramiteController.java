package co.edu.javeriana.pica.front.infraestructure.api;

import co.edu.javeriana.pica.front.infraestructure.api.dto.TramiteListResponse;
import co.edu.javeriana.pica.front.infraestructure.api.dto.TramiteRequest;
import co.edu.javeriana.pica.front.core.entities.AuthUser;
import co.edu.javeriana.pica.front.core.entities.Tramite;
import co.edu.javeriana.pica.front.core.interfaces.TramiteService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.stream.Collectors;

@Path("/front-office/tramite")
public class TramiteController {

    private final TramiteService tramiteService;

    @Inject
    public TramiteController(TramiteService tramiteService) {
        this.tramiteService = tramiteService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(TramiteRequest tramiteRequest, @Context HttpHeaders headers) {
        AuthUser authUser = SecurityInterceptor.getAuthUser(headers);
        Tramite tramite = tramiteRequest.toTramite();
        tramite = tramiteService.save(tramite, authUser);
        return Response.status(Response.Status.OK).entity(tramite).build();
    }

    @GET
    @Path("/usuario/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@PathParam("username") String username, @Context HttpHeaders headers) {
        AuthUser authUser = SecurityInterceptor.getAuthUser(headers);
        return Response.status(Response.Status.OK).entity(
            tramiteService.listByUsername(authUser.getUsername()).stream().map(it -> {
                TramiteListResponse tramite = new TramiteListResponse();
                tramite.setId(it.getId().toString());
                tramite.setEstado(it.getEstado());
                tramite.setTipo(it.getTipo());
                if (it.getFechaCreacion() != null) {
                    tramite.setFechaCreacion(it.getFechaCreacion().toString());
                }
                if (it.getFechaRadicacion() != null) {
                    tramite.setFechaRadicacion(it.getFechaRadicacion().toString());
                }
                return tramite;
            }).collect(Collectors.toList())
        ).build();
    }

    @POST
    @Path("/radicar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response radicar(@PathParam("id") String id, @Context HttpHeaders headers) {
        AuthUser authUser = SecurityInterceptor.getAuthUser(headers);
        return Response.status(Response.Status.OK).entity(tramiteService.radicar(id, authUser)).build();
    }

    @GET
    @Path("/resolucion/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("id") String id) {
        return tramiteService.resolucionDownload(id).map(file -> {
            Response.ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition", "attachment;filename=" + file);
            return response.build();
        }).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}