package co.edu.javeriana.pica.front.service;

import co.edu.javeriana.pica.front.entity.User;
import java.util.Optional;

public interface UserService {
    void save(User user);
    Optional<User> getByUsername(String username);
}
