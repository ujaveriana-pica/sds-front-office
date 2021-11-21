package co.edu.javeriana.pica.front.infraestructure.consumer;

import co.edu.javeriana.pica.front.core.entities.User;
import co.edu.javeriana.pica.front.core.interfaces.MetricsPort;
import co.edu.javeriana.pica.front.core.interfaces.UserService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class UsersConsumer {

    private final UserService userService;
    private final MetricsPort metricsService;
    private static final Logger LOG = LoggerFactory.getLogger(UsersConsumer.class);

    public UsersConsumer(UserService userService, MetricsPort metricsService) {
        this.userService = userService;
        this.metricsService = metricsService;
    }

    @Incoming("users")
    public CompletionStage<Void> consume(Message<User> msg) {
        User user = msg.getPayload();
        if (user != null) {
            userService.save(user);
            metricsService.incrementCounter(MetricsPort.USUARIOS_REGISTRADOS);
            LOG.info("Usuario con userId: {} username: {} creado satisfactoriamente");
        } else {
            LOG.warn("Mensaje invalido, de descarta el mensaje de la cola");
        }
        return msg.ack();
    }
}
