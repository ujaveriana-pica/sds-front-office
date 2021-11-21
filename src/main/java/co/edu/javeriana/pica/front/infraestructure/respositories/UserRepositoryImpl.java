package co.edu.javeriana.pica.front.infraestructure.respositories;

import co.edu.javeriana.pica.front.core.entities.User;
import co.edu.javeriana.pica.front.core.interfaces.UserRepository;
import co.edu.javeriana.pica.front.infraestructure.data.UserData;
import co.edu.javeriana.pica.front.infraestructure.mapper.UserMapper;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {
    @Override
    public void persistOrUpdate(User user) {
        UserData data = UserMapper.entityToData(user);
        data.persistOrUpdate();
    }

    @Override
    public Optional<User> getByUsername(String username) {
        List<UserData> users = UserData.list("username", username);
        if (!users.isEmpty()) {
            return Optional.of(UserMapper.dataToEntity(users.get(0)));
        }
        return Optional.empty();
    }
}
