package co.edu.javeriana.pica.front.infraestructure.respositories;

import co.edu.javeriana.pica.front.core.entities.Tramite;
import co.edu.javeriana.pica.front.core.interfaces.TramiteRepository;
import co.edu.javeriana.pica.front.infraestructure.data.TramiteData;
import co.edu.javeriana.pica.front.infraestructure.mapper.TramiteMapper;
import org.bson.types.ObjectId;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TramiteRepositoryImpl implements TramiteRepository {

    public Tramite persist(Tramite tramite) {
        TramiteData data = TramiteMapper.entityToData(tramite);
        data.persist();
        return TramiteMapper.dataToEntity(data);
    }

    public Optional<Tramite> findById(String id) {
        return Optional.ofNullable(TramiteMapper.dataToEntity(TramiteData.findById(new ObjectId(id))));
    }

    public Tramite persistOrUpdate(Tramite tramite) {
        TramiteData data = TramiteMapper.entityToData(tramite);
        data.persistOrUpdate();
        return TramiteMapper.dataToEntity(data);
    }

    public List<Tramite> findAllByCreadorUsername(String username) {
        return TramiteMapper.dataListToEntityList(TramiteData.list("creador.username", username));
    }
}
