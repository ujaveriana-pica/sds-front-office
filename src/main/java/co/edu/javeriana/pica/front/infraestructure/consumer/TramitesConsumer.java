package co.edu.javeriana.pica.front.infraestructure.consumer;

import co.edu.javeriana.pica.front.core.entities.Tramite;
import co.edu.javeriana.pica.front.core.interfaces.TramiteService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class TramitesConsumer {

    private final TramiteService tramiteService;
    private static final Logger LOG = LoggerFactory.getLogger(TramitesConsumer.class);

    public TramitesConsumer(TramiteService tramiteService) {
        this.tramiteService = tramiteService;
    }

    @Incoming("tramites-in")
    public CompletionStage<Void> consume(Message<Tramite> msg) {
        Tramite tramite = msg.getPayload();
        if (tramite != null) {
            tramiteService.updateEstado(tramite.getId(), tramite.getEstado());
        }
        return msg.ack();
    }
}
