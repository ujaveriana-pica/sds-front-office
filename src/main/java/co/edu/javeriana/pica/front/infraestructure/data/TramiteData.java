package co.edu.javeriana.pica.front.infraestructure.data;

import co.edu.javeriana.pica.front.core.entities.Adjunto;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@MongoEntity(collection = "tramite")
public class TramiteData extends PanacheMongoEntity {
    private ObjectId id;
    private Map<String, String> data;
    private String tipo;
    private String estado;
    private List<Adjunto> adjuntos = new ArrayList<>();
    private UserData creador;
    private Date fechaCreacion;
    private Date fechaRadicacion;

}
