package co.edu.javeriana.pica.front.core.services;

import co.edu.javeriana.pica.front.core.entities.User;
import co.edu.javeriana.pica.front.core.interfaces.UserRepository;
import co.edu.javeriana.pica.front.core.interfaces.UserService;
import co.edu.javeriana.pica.front.infraestructure.respositories.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Test
    public void saveDontThrowException() {
        UserRepository userRepository = mock(UserRepositoryImpl.class);
        UserService userService = new UserServiceImpl(userRepository);
        User user = getUser();
        userService.save(user);
        assertTrue(true);
    }

    @Test
    public void getByUsername() {
        String username = "username";
        Optional<User> user = Optional.of(getUser());
        UserRepository userRepository = mock(UserRepositoryImpl.class);
        when(userRepository.getByUsername(username)).thenReturn(user);
        UserService userService = new UserServiceImpl(userRepository);
        Optional<User> res = userService.getByUsername(username);
        assertEquals(user.get().getUserId(), res.get().getUserId());
    }

    private User getUser() {
        User user = new User();
        user.setUserId("123456");
        user.setEmail("mail@gmail.com");
        user.setUserId("username");
        return user;
    }
}
