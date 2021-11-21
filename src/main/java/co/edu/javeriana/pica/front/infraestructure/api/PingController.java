package co.edu.javeriana.pica.front.infraestructure.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ping")
public class PingController {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response get() {
        return Response.status(Response.Status.OK).entity("pong").build();
    }
}
