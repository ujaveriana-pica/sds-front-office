package co.edu.javeriana.pica.front.consumer;

import co.edu.javeriana.pica.front.messages.User;
import co.edu.javeriana.pica.front.service.MetricsService;
import co.edu.javeriana.pica.front.service.UserService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class UsersConsumer {

    private final UserService userService;
    private final MetricsService metricsService;
    Logger logger = LoggerFactory.getLogger(UsersConsumer.class);

    public UsersConsumer(UserService userService, MetricsService metricsService) {
        this.userService = userService;
        this.metricsService = metricsService;
    }

    @Incoming("users")
    public CompletionStage<Void> consume(Message<User> msg) {
        User user = msg.getPayload();
        if(user != null) {
            co.edu.javeriana.pica.front.entity.User userEntity = new co.edu.javeriana.pica.front.entity.User();
            userEntity.setUserId(user.getUserId());
            userEntity.setEmail(user.getEmail());
            userEntity.setUsername(user.getUsername());
            userEntity.setName(user.getName());
            userEntity.setLastName(user.getLastName());
            userEntity.setRol(user.getRol());
            userEntity.setStatus(user.getStatus());
            userService.save(userEntity);
            metricsService.incrementCounter(MetricsService.USUARIOS_REGISTRADOS);
            logger.info("Usuario con userId: {} username: {} creado satisfactoriamente");
        } else {
            logger.warn("Mensaje invalido, de descarta el mensaje de la cola");
        }
        return msg.ack();
    }
}
