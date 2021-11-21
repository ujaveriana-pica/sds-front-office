package co.edu.javeriana.pica.front.service.impl;

import co.edu.javeriana.pica.front.entity.User;
import co.edu.javeriana.pica.front.service.UserService;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserServiceImpl implements UserService {
    @Override
    public void save(User user) {
        user.persistOrUpdate();
    }

    @Override
    public Optional<User> getByUsername(String username) {
        List<User> users = User.list("username", username);
        if(!users.isEmpty()) {
            return Optional.of(users.get(0));
        }
        return Optional.empty();
    }
}
