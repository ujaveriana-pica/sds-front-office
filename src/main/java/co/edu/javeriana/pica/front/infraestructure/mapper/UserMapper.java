package co.edu.javeriana.pica.front.infraestructure.mapper;

import co.edu.javeriana.pica.front.core.entities.User;
import co.edu.javeriana.pica.front.infraestructure.data.UserData;

public class UserMapper {

    private UserMapper() {

    }

    public static UserData entityToData(User user) {
        UserData data = new UserData();
        data.setUserId(user.getUserId());
        data.setUsername(user.getUsername());
        data.setName(user.getName());
        data.setLastName(user.getLastName());
        data.setEmail(user.getEmail());
        data.setStatus(user.getStatus());
        data.setRol(user.getRol());
        return data;
    }

    public static User dataToEntity(UserData data) {
        User user = new User();
        user.setUserId(data.getUserId());
        user.setUsername(data.getUsername());
        user.setName(data.getName());
        user.setLastName(data.getLastName());
        user.setEmail(data.getEmail());
        user.setStatus(data.getStatus());
        user.setRol(data.getRol());
        return user;
    }
}
