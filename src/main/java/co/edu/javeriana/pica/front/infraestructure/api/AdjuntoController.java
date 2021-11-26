package co.edu.javeriana.pica.front.infraestructure.api;

import co.edu.javeriana.pica.front.infraestructure.api.dto.FileUploadForm;
import co.edu.javeriana.pica.front.core.interfaces.TramiteService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/front-office/adjunto")
public class AdjuntoController {

    private TramiteService tramiteService;

    public AdjuntoController(TramiteService tramiteService) {
        this.tramiteService = tramiteService;
    }

    @POST
    @Path("{formType}/{formId}/{uploadType}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@MultipartForm FileUploadForm uploadForm,
                           @PathParam("formType") String formType,
                           @PathParam("formId") String formId,
                           @PathParam("uploadType") String uploadType) {
        tramiteService.addAttachment(formId, uploadType, uploadForm.getFileData());
        return Response.status(Response.Status.CREATED).build();
    }
}

