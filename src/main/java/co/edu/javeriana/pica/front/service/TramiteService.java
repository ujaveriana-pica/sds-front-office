package co.edu.javeriana.pica.front.service;

import co.edu.javeriana.pica.front.controller.dto.RadicarResponse;
import co.edu.javeriana.pica.front.entity.Adjunto;
import co.edu.javeriana.pica.front.entity.Tramite;

import java.util.List;

public interface TramiteService {

    static final String ESTADO_BORRADOR = "borrador";
    static final String ESTADO_RADICADO = "radicado";

    Tramite save(Tramite form);
    List<Tramite> listByUserId(String userId);
    RadicarResponse radicar(String id);
    void addAttachment(String id, Adjunto attachment);
}
