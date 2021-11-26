package co.edu.javeriana.pica.front.core.services;

import co.edu.javeriana.pica.front.core.entities.AuthUser;
import co.edu.javeriana.pica.front.core.entities.Radicacion;
import co.edu.javeriana.pica.front.core.entities.Tramite;
import co.edu.javeriana.pica.front.core.entities.User;
import co.edu.javeriana.pica.front.core.interfaces.*;
import co.edu.javeriana.pica.front.infraestructure.adapters.ConfigPortImpl;
import co.edu.javeriana.pica.front.infraestructure.adapters.MetricsPortImpl;
import co.edu.javeriana.pica.front.infraestructure.adapters.NotificacionPortImpl;
import co.edu.javeriana.pica.front.infraestructure.adapters.TramitePortImpl;
import co.edu.javeriana.pica.front.infraestructure.respositories.AdjuntoRepositoryImpl;
import co.edu.javeriana.pica.front.infraestructure.respositories.TramiteRepositoryImpl;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TramiteServiceImplTest {

    MetricsPort metricsPort = mock(MetricsPortImpl.class);
    TramiteRepository tramiteRepository = mock(TramiteRepositoryImpl.class);
    AdjuntoRepository adjuntoRepository = mock(AdjuntoRepositoryImpl.class);
    NotificacionPort notificacionPort = mock(NotificacionPortImpl.class);
    TramitePort tramitePort = mock(TramitePortImpl.class);
    ConfigPort configPort = mock(ConfigPortImpl.class);
    UserService userService = mock(UserServiceImpl.class);
    ResolucionRepository resolucionRepository = mock(ResolucionRepository.class);

    private TramiteService getMockTramiteService() {
        return new TramiteServiceImpl(metricsPort, tramiteRepository, adjuntoRepository,
                notificacionPort, tramitePort, configPort, userService, resolucionRepository);
    }

    @Test
    public void saveReturnTramite() {
        TramiteService service = getMockTramiteService();
        AuthUser auth = new AuthUser("username", "rol");
        Optional<User> user = Optional.of(getUser());
        when(userService.getByUsername(auth.getUsername())).thenReturn(user);
        service.save(new Tramite(), auth);
        assertTrue(true);

    }

    @Test
    public void saveThrowException() {
        AuthUser auth = new AuthUser("username", "rol");
        when(userService.getByUsername(auth.getUsername())).thenReturn(Optional.empty());
        TramiteService service = getMockTramiteService();
        assertThrows(RuntimeException.class, () -> {
            service.save(new Tramite(), auth);
        });
    }

    @Test
    public void radicar() {
        AuthUser auth = new AuthUser("username", "rol");
        Optional<Tramite> mockTramite = Optional.of(getTramite());
        when(tramiteRepository.findById(mockTramite.get().getId())).thenReturn(mockTramite);
        when(configPort.isNotificationsEnable()).thenReturn(true);
        when(tramiteRepository.persistOrUpdate(mockTramite.get())).thenReturn(mockTramite.get());
        TramiteService service = getMockTramiteService();
        Radicacion res = service.radicar(mockTramite.get().getId(), auth);
        assertEquals(mockTramite.get().getId(), res.getCodigoRadicacion());
    }

    private User getUser() {
        User user = new User();
        user.setUserId("123456");
        user.setEmail("mail@gmail.com");
        user.setUserId("username");
        return user;
    }

    private Tramite getTramite() {
        Tramite tramite = new Tramite();
        tramite.setId("123456");
        tramite.setTipo("tipo");
        tramite.setEstado("estado");
        tramite.setCreador(getUser());
        return tramite;
    }
}
