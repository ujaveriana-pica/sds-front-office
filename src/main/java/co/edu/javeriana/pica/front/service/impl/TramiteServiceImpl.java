package co.edu.javeriana.pica.front.service.impl;

import co.edu.javeriana.pica.front.controller.dto.RadicarResponse;
import co.edu.javeriana.pica.front.entity.Adjunto;
import co.edu.javeriana.pica.front.entity.Tramite;
import co.edu.javeriana.pica.front.service.TramiteService;
import co.edu.javeriana.pica.front.util.DateTimeUtil;
import org.bson.types.ObjectId;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TramiteServiceImpl implements TramiteService {

    @Override
    public Tramite save(Tramite form) {
        form.setEstado(ESTADO_BORRADOR);
        form.setCreador("1");
        form.setFechaCreacion(DateTimeUtil.now());
        form.persist();
        return form;
    }

    @Override
    public List<Tramite> listByUserId(String userId) {
        return Tramite.list("creador", userId);
    }

    @Override
    public RadicarResponse radicar(String id) {
        Tramite form = Tramite.findById(new ObjectId(id));
        if(form != null) {
            form.setEstado(ESTADO_RADICADO);
            form.setFechaRadicacion(DateTimeUtil.now());
            form.persistOrUpdate();
            RadicarResponse response = new RadicarResponse();
            response.setCodigoRadicacion(form.getId().toString());
            return response;
        } else {
            throw new IllegalArgumentException("No existe formulario con el id");
        }
    }

    @Override
    public void addAttachment(String id, Adjunto attachment) {
        Tramite form = Tramite.findById(new ObjectId(id));
        if(form != null) {
            form.getAdjuntos().add(attachment);
            form.persistOrUpdate();
        } else {
            throw new IllegalArgumentException("No existe formulario con el id");
        }
    }
}
