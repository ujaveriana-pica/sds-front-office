package co.edu.javeriana.pica.front.infraestructure.adapters;

import co.edu.javeriana.pica.front.core.entities.Notificacion;
import co.edu.javeriana.pica.front.core.interfaces.NotificacionPort;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class NotificacionPortImpl implements NotificacionPort {

    @Inject
    @Channel("notifications")
    Emitter<Notificacion> notificacionEmitter;

    @Override
    public void send(Notificacion notificacion) {
        notificacionEmitter.send(notificacion);
    }
}
