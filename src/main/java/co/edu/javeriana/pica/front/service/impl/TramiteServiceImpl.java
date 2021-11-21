package co.edu.javeriana.pica.front.service.impl;

import co.edu.javeriana.pica.front.controller.dto.RadicarResponse;
import co.edu.javeriana.pica.front.entity.Adjunto;
import co.edu.javeriana.pica.front.entity.AuthUser;
import co.edu.javeriana.pica.front.entity.Tramite;
import co.edu.javeriana.pica.front.entity.User;
import co.edu.javeriana.pica.front.messages.Notificacion;
import co.edu.javeriana.pica.front.service.MetricsService;
import co.edu.javeriana.pica.front.service.TramiteService;
import co.edu.javeriana.pica.front.service.UserService;
import co.edu.javeriana.pica.front.util.DateTimeUtil;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class TramiteServiceImpl implements TramiteService {

    private final MetricsService metricsService;
    @Inject
    @Channel("notifications")
    Emitter<Notificacion> notificacionEmitter;
    @ConfigProperty(name = "app.notifications.enable")
    boolean notificationsEnable;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(TramiteServiceImpl.class);

    public TramiteServiceImpl(MetricsService metricsService, UserService userService) {
        this.metricsService = metricsService;
        this.userService = userService;
    }

    @Override
    public Tramite save(Tramite form) {
        form.setEstado(ESTADO_BORRADOR);
        form.setFechaCreacion(DateTimeUtil.now());
        form.persist();
        return form;
    }

    @Override
    public List<Tramite> listByUsername(String username) {
        return Tramite.list("creador", username);
    }

    @Override
    public RadicarResponse radicar(String id, AuthUser authUser) {
        Tramite form = Tramite.findById(new ObjectId(id));
        if(form != null) {
            form.setEstado(ESTADO_RADICADO);
            form.setFechaRadicacion(DateTimeUtil.now());
            form.persistOrUpdate();
            metricsService.incrementCounter(MetricsService.TRAMITES_RADICADOS);
            if(notificationsEnable) {
                Optional<User> user = userService.getByUsername(authUser.getUsername());
                if(user.isPresent()) {
                    Notificacion notificacion = new Notificacion();
                    notificacion.setTemplate("tramite-radicado");
                    notificacion.setTo(user.get().getEmail());
                    Map<String, String> vars = new HashMap<>();
                    String name = user.get().getName() != null? user.get().getName(): "";
                    String lastName = user.get().getLastName() != null? user.get().getLastName(): "";
                    vars.put("nombre", name + " " + lastName);
                    vars.put("tramiteId", form.getId().toString());
                    notificacion.setVars(vars);
                    CompletionStage<Void> ack = notificacionEmitter.send(notificacion);
                }
                else {
                    logger.warn("No se ha enviado notificacion, el usuario no existe en el micro.");
                }
            }
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
