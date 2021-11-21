package co.edu.javeriana.pica.front.core.services;

import co.edu.javeriana.pica.front.core.entities.User;
import co.edu.javeriana.pica.front.core.interfaces.UserRepository;
import co.edu.javeriana.pica.front.core.interfaces.UserService;
import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.persistOrUpdate(user);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.getByUsername(username);
    }
}
