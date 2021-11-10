package co.edu.javeriana.pica.front.controller;

import co.edu.javeriana.pica.front.controller.dto.TramiteListResponse;
import co.edu.javeriana.pica.front.controller.dto.TramiteRequest;
import co.edu.javeriana.pica.front.entity.Tramite;
import co.edu.javeriana.pica.front.service.TramiteService;
import javax.inject.Inject;
import javax.ws.rs.*;
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
    public Response create(TramiteRequest tramiteRequest) {
        System.out.println("SAVE");
        Tramite form = tramiteService.save(tramiteRequest.toTramite());
        return Response.status(Response.Status.OK).entity(form).build();
    }

    @GET
    @Path("/usuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@PathParam("id") String id) {
        return Response.status(Response.Status.OK).entity(
            tramiteService.listByUserId(id).stream().map(it -> {
                TramiteListResponse tramite = new TramiteListResponse();
                tramite.setId(it.getId().toString());
                tramite.setEstado(it.getEstado());
                tramite.setTipo(it.getTipo());
                return tramite;
            }).collect(Collectors.toList())
        ).build();
    }

    @POST
    @Path("/radicar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response radicar(@PathParam("id") String id) {
        return Response.status(Response.Status.OK).entity(tramiteService.radicar(id)).build();
    }
}