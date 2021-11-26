package co.edu.javeriana.pica.front.infraestructure.consumer;

import co.edu.javeriana.pica.front.core.entities.Tramite;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class TramiteDeserializer extends ObjectMapperDeserializer<Tramite> {
    public TramiteDeserializer() {
        super(Tramite.class);
    }
}
