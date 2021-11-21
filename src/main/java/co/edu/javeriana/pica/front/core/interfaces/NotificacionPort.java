package co.edu.javeriana.pica.front.core.interfaces;

import co.edu.javeriana.pica.front.core.entities.Notificacion;

public interface NotificacionPort {
    void send(Notificacion notificacion);
}

