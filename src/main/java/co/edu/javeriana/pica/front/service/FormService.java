package co.edu.javeriana.pica.front.service;

import co.edu.javeriana.pica.front.controller.dto.RadicarResponse;
import co.edu.javeriana.pica.front.entity.Attachment;
import co.edu.javeriana.pica.front.entity.Form;

import java.util.List;

public interface FormService {
    static final String STATE_BORRADOR = "borrador";
    static final String STATE_RADICADO = "radicado";

    Form save(Form form);
    List<Form> listByUserId(String userId);
    RadicarResponse radicar(String id);
    void addAttachment(String id, Attachment attachment);
}
