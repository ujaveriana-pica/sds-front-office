package co.edu.javeriana.pica.front.infraestructure.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Path("/front-office/esquema")
@Tag(name = "esquema", description = "Gestión de esquemas de trámites.")
public class EsquemaController {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Permite obtener el esquema de captura para un trámite específico")
    public Response get(@PathParam("id") String id) {
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(
                String.format("esquemas/%s.json", id))) {
            if (is == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            JsonNode actualObj = mapper.readTree(is);
            return Response.status(Response.Status.OK).entity(actualObj).build();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
