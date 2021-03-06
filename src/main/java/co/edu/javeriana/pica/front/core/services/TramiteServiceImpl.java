package co.edu.javeriana.pica.front.core.services;

import co.edu.javeriana.pica.front.core.entities.Radicacion;
import co.edu.javeriana.pica.front.core.entities.Tramite;
import co.edu.javeriana.pica.front.core.entities.User;
import co.edu.javeriana.pica.front.core.entities.Adjunto;
import co.edu.javeriana.pica.front.core.entities.AuthUser;
import co.edu.javeriana.pica.front.core.entities.Notificacion;
import co.edu.javeriana.pica.front.core.interfaces.ResolucionRepository;
import co.edu.javeriana.pica.front.core.interfaces.AdjuntoRepository;
import co.edu.javeriana.pica.front.core.interfaces.MetricsPort;
import co.edu.javeriana.pica.front.core.interfaces.TramiteRepository;
import co.edu.javeriana.pica.front.core.interfaces.TramiteService;
import co.edu.javeriana.pica.front.core.interfaces.NotificacionPort;
import co.edu.javeriana.pica.front.core.interfaces.TramitePort;
import co.edu.javeriana.pica.front.core.interfaces.ConfigPort;
import co.edu.javeriana.pica.front.core.interfaces.UserService;
import co.edu.javeriana.pica.front.core.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class TramiteServiceImpl implements TramiteService {

    private final MetricsPort metricsService;
    private final TramiteRepository tramiteRepository;
    private final AdjuntoRepository adjuntoRepository;
    private final NotificacionPort notificacionPort;
    private final TramitePort tramitePort;
    private final ConfigPort configPort;
    private final UserService userService;
    private final ResolucionRepository resolucionRepository;
    private static final Logger LOG = LoggerFactory.getLogger(TramiteServiceImpl.class);

    @Override
    public Tramite save(Tramite tramite, AuthUser authUser) {
        Optional<User> user = userService.getByUsername(authUser.getUsername());
        if (user.isPresent()) {
            tramite.setEstado(ESTADO_BORRADOR);
            tramite.setFechaCreacion(DateTimeUtil.now());
            tramite.setCreador(user.get());
            return tramiteRepository.persist(tramite);
        } else {
            throw new RuntimeException(
                    String.format("El tramite no pudo crearse, el usuario %s no existe en el micro.",
                            authUser.getUsername()));
        }
    }

    @Override
    public List<Tramite> listByUsername(String username) {
        return tramiteRepository.findAllByCreadorUsername(username);
    }

    @Override
    public Radicacion radicar(String id, AuthUser authUser) {
        return tramiteRepository.findById(id).map(tramite -> {
            tramite.setEstado(ESTADO_RADICADO);
            tramite.setFechaRadicacion(DateTimeUtil.now());
            Tramite savedTramite = tramiteRepository.persistOrUpdate(tramite);
            tramitePort.send(savedTramite);
            if (configPort.isNotificationsEnable()) {
                User user = tramite.getCreador();
                if (user != null) {
                    Notificacion notificacion = new Notificacion();
                    notificacion.setTemplate("tramite-radicado");
                    notificacion.setTo(user.getEmail());
                    Map<String, String> vars = new HashMap<>();
                    String name = user.getName() != null ? user.getName() : "";
                    String lastName = user.getLastName() != null ? user.getLastName() : "";
                    vars.put("nombre", name + " " + lastName);
                    vars.put("tramiteId", savedTramite.getId().toString());
                    notificacion.setVars(vars);
                    notificacionPort.send(notificacion);
                } else {
                    LOG.warn("No se ha enviado notificacion, el tramite no tiene usuario creador.");
                }
            }
            metricsService.incrementCounter(MetricsPort.TRAMITES_RADICADOS);
            Radicacion response = new Radicacion();
            response.setCodigoRadicacion(tramite.getId().toString());
            return response;
        }).orElseThrow(() -> new IllegalArgumentException("No existe formulario con el id"));
    }

    @Override
    public void addAttachment(String id, String uploadType, byte[] filedata) {
        try {
            adjuntoRepository.save(id, uploadType, filedata);
            tramiteRepository.findById(id).map(tramite -> {
                Adjunto adj = new Adjunto();
                adj.setTipo(uploadType);
                adj.setUrl(uploadType);
                tramite.getAdjuntos().add(adj);
                tramiteRepository.persistOrUpdate(tramite);
                return tramite;
            }).orElseThrow(() -> new IllegalArgumentException("No existe formulario con el id"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<File> resolucionDownload(String id) {
        return resolucionRepository.download(id);
    }

    @Override
    public void updateEstado(String id, String estado) {
        if (estado != null) {
            tramiteRepository.findById(id).map(tramite -> {
                LOG.info("Estado de tramite con id {} actualizado", tramite.getId());
                tramite.setEstado(estado.toLowerCase());
                return tramiteRepository.persistOrUpdate(tramite);
            });
        }
        LOG.warn("Tramite con estado nulo");
    }
}
