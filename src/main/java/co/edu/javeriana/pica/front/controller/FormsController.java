package co.edu.javeriana.pica.front.controller;

import co.edu.javeriana.pica.front.controller.dto.FormRequest;
import co.edu.javeriana.pica.front.entity.Form;
import co.edu.javeriana.pica.front.service.FormService;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/forms")
public class FormsController {

    private final FormService formService;

    @Inject
    public FormsController(FormService formService) {
        this.formService = formService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(FormRequest formRequest) {
        System.out.println("SAVE");
        Form form = formService.save(formRequest.toForm());
        return Response.status(Response.Status.OK).entity(form).build();
    }

    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@PathParam("id") String id) {
        return Response.status(Response.Status.OK).entity(formService.listByUserId(id)).build();
    }

    @POST
    @Path("/radicar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response radicar(@PathParam("id") String id) {
        return Response.status(Response.Status.OK).entity(formService.radicar(id)).build();
    }
}