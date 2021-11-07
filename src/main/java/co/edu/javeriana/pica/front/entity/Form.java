package co.edu.javeriana.pica.front.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@MongoEntity(collection="form")
public class Form extends PanacheMongoEntity {
    private ObjectId id;
    private Map<String, String> data;
    private String type;
    private String state;
    private List<Attachment> attachments = new ArrayList<>();
    private String owner;

}
