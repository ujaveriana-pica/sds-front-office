package co.edu.javeriana.pica.front.service.impl;

import co.edu.javeriana.pica.front.controller.dto.RadicarResponse;
import co.edu.javeriana.pica.front.entity.Attachment;
import co.edu.javeriana.pica.front.entity.Form;
import co.edu.javeriana.pica.front.service.AttachmentService;
import co.edu.javeriana.pica.front.service.FormService;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class FormServiceImpl implements FormService {

    @Override
    public Form save(Form form) {
        form.setState(STATE_BORRADOR);
        form.persist();
        return form;
    }

    @Override
    public List<Form> listByUserId(String userId) {
        return Form.list("owner", userId);
    }

    @Override
    public RadicarResponse radicar(String id) {
        Form form = Form.findById(new ObjectId(id));
        if(form != null) {
            form.setState(STATE_RADICADO);
            form.persistOrUpdate();
            RadicarResponse response = new RadicarResponse();
            response.setCodigoRadicacion(form.getId().toString());
            return response;
        } else {
            throw new IllegalArgumentException("No existe formulario con el id");
        }
    }

    @Override
    public void addAttachment(String id, Attachment attachment) {
        Form form = Form.findById(new ObjectId(id));
        if(form != null) {
            form.getAttachments().add(attachment);
            form.persistOrUpdate();
        } else {
            throw new IllegalArgumentException("No existe formulario con el id");
        }
    }
}
