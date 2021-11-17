package co.edu.javeriana.pica.front.service.impl;

import co.edu.javeriana.pica.front.controller.dto.RadicarResponse;
import co.edu.javeriana.pica.front.entity.Adjunto;
import co.edu.javeriana.pica.front.entity.Tramite;
import co.edu.javeriana.pica.front.messages.Notificacion;
import co.edu.javeriana.pica.front.service.MetricsService;
import co.edu.javeriana.pica.front.service.TramiteService;
import co.edu.javeriana.pica.front.util.DateTimeUtil;
import io.micrometer.core.instrument.MeterRegistry;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class TramiteServiceImpl implements TramiteService {

    private final MetricsService metricsService;
    @Inject
    @Channel("notifications")
    Emitter<Notificacion> notificacionEmitter;

    public TramiteServiceImpl(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

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
            metricsService.incrementCounter(MetricsService.TRAMITES_RADICADOS);

            Notificacion notificacion = new Notificacion();
            notificacion.setTemplate("tramite-radicado");
            notificacion.setTo("juancastellanosm@gmail.com");
            Map<String, String> vars = new HashMap<>();
            vars.put("nombre", "Juan Carlos Castellanos");
            vars.put("tramiteId", form.getId().toString());
            notificacion.setVars(vars);
            CompletionStage<Void> ack = notificacionEmitter.send(notificacion);

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
