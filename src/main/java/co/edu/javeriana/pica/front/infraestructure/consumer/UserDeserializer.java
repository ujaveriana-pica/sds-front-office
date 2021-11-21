package co.edu.javeriana.pica.front.infraestructure.consumer;

import co.edu.javeriana.pica.front.core.entities.User;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class UserDeserializer extends ObjectMapperDeserializer<User> {
    public UserDeserializer() {
        super(User.class);
    }
}
