package co.edu.javeriana.pica.front.core.interfaces;

import co.edu.javeriana.pica.front.core.entities.User;
import java.util.Optional;

public interface UserService {
    void save(User user);
    Optional<User> getByUsername(String username);
}
