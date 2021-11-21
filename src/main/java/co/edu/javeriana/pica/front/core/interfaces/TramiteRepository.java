package co.edu.javeriana.pica.front.core.interfaces;

import co.edu.javeriana.pica.front.core.entities.Tramite;
import java.util.List;
import java.util.Optional;

public interface TramiteRepository {

    Tramite persist(Tramite tramite);
    Optional<Tramite> findById(String id);
    Tramite persistOrUpdate(Tramite tramite);
    List<Tramite> findAllByCreadorUsername(String username);
}
