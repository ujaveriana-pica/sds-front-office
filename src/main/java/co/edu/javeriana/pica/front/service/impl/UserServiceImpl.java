package co.edu.javeriana.pica.front.service.impl;

import co.edu.javeriana.pica.front.entity.User;
import co.edu.javeriana.pica.front.service.UserService;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserServiceImpl implements UserService {
    @Override
    public void save(User user) {
        user.persistOrUpdate();
    }
}
