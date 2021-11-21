package co.edu.javeriana.pica.front.infraestructure.api;

import co.edu.javeriana.pica.front.infraestructure.api.dto.FileUploadForm;
import co.edu.javeriana.pica.front.core.interfaces.AdjuntoRepository;
import co.edu.javeriana.pica.front.core.interfaces.TramiteService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/front-office/adjunto")
public class AdjuntoController {

    private AdjuntoRepository attachmentService;
    private TramiteService tramiteService;

    public AdjuntoController(AdjuntoRepository attachmentService, TramiteService tramiteService) {
        this.attachmentService = attachmentService;
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

