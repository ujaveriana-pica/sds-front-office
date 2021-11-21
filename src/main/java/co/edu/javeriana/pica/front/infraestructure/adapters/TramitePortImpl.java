package co.edu.javeriana.pica.front.infraestructure.adapters;

import co.edu.javeriana.pica.front.core.entities.Tramite;
import co.edu.javeriana.pica.front.core.interfaces.TramitePort;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class TramitePortImpl implements TramitePort {

    @Inject
    @Channel("tramites")
    Emitter<Tramite> emitterr;

    @Override
    public void send(Tramite tramite) {
        CompletionStage<Void> ack = emitterr.send(tramite);
    }
}
