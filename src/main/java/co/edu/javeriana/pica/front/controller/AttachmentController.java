package co.edu.javeriana.pica.front.controller;

import co.edu.javeriana.pica.front.controller.dto.FormRequest;
import co.edu.javeriana.pica.front.entity.Attachment;
import co.edu.javeriana.pica.front.service.AttachmentService;
import co.edu.javeriana.pica.front.service.FormService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/attachments")
public class AttachmentController {

    private AttachmentService attachmentService;
    private FormService formService;

    public AttachmentController(AttachmentService attachmentService, FormService formService) {
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
            Attachment att = new Attachment();
            att.setType(uploadType);
            att.setUrl(uploadType);
            formService.addAttachment(formId, att);
            return Response.status(Response.Status.CREATED).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

