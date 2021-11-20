package co.edu.javeriana.pica.front.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;

@Data
@MongoEntity(collection="user")
public class User extends PanacheMongoEntity {
    private String userId;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String status;
    private String rol;
}
