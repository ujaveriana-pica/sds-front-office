package co.edu.javeriana.pica.front.consumer;

import co.edu.javeriana.pica.front.messages.User;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class UserDeserializer extends ObjectMapperDeserializer<User> {
    public UserDeserializer() {
        super(User.class);
    }
}
