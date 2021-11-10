package co.edu.javeriana.pica.front.controller;

import co.edu.javeriana.pica.front.controller.dto.FileUploadForm;
import co.edu.javeriana.pica.front.entity.Adjunto;
import co.edu.javeriana.pica.front.service.AdjuntoService;
import co.edu.javeriana.pica.front.service.TramiteService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/front-office/adjunto")
public class AdjuntoController {

    private AdjuntoService attachmentService;
    private TramiteService formService;

    public AdjuntoController(AdjuntoService attachmentService, TramiteService formService) {
        this.attachmentService = attachmentService;
        this.formService = formService;
    }

    @POST
    @Path("{formType}/{formId}/{uploadType}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@MultipartForm FileUploadForm uploadForm,
                           @PathParam("formType") String formType,
                           @PathParam("formId") String formId,
                           @PathParam("uploadType") String uploadType) {
        try {
            attachmentService.save(formId, uploadType, uploadForm.getFileData());
            Adjunto att = new Adjunto();
            att.setTipo(uploadType);
            att.setUrl(uploadType);
            formService.addAttachment(formId, att);
            return Response.status(Response.Status.CREATED).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

