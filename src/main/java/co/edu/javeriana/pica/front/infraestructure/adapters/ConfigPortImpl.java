package co.edu.javeriana.pica.front.infraestructure.adapters;

import co.edu.javeriana.pica.front.core.interfaces.ConfigPort;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfigPortImpl implements ConfigPort {

    @ConfigProperty(name = "app.notifications.enable")
    boolean notificationsEnable;

    @Override
    public boolean isNotificationsEnable() {
        return notificationsEnable;
    }

}
