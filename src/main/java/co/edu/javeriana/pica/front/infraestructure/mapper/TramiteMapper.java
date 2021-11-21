package co.edu.javeriana.pica.front.infraestructure.mapper;

import co.edu.javeriana.pica.front.core.entities.Tramite;
import co.edu.javeriana.pica.front.infraestructure.data.TramiteData;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

public class TramiteMapper {

    private TramiteMapper() {

    }

    public static TramiteData entityToData(Tramite tramite) {
        TramiteData data = new TramiteData();
        if(tramite.getId() != null) {
            data.setId(new ObjectId(tramite.getId()));
        }
        data.setData(tramite.getData());
        data.setTipo(tramite.getTipo());
        data.setEstado(tramite.getEstado());
        data.setAdjuntos(tramite.getAdjuntos());
        data.setCreador(UserMapper.entityToData(tramite.getCreador()));
        data.setFechaCreacion(tramite.getFechaCreacion());
        data.setFechaRadicacion(tramite.getFechaRadicacion());
        return data;
    }

    public static Tramite dataToEntity(TramiteData data) {
        Tramite tramite = new Tramite();
        tramite.setId(data.getId().toString());
        tramite.setData(data.getData());
        tramite.setTipo(data.getTipo());
        tramite.setEstado(data.getEstado());
        tramite.setAdjuntos(data.getAdjuntos());
        tramite.setCreador(UserMapper.dataToEntity(data.getCreador()));
        tramite.setFechaCreacion(data.getFechaCreacion());
        tramite.setFechaRadicacion(data.getFechaRadicacion());
        return tramite;
    }

    public static List<Tramite> dataListToEntityList(List<TramiteData> list) {
        return list.stream().map(data -> dataToEntity(data)).collect(Collectors.toList());
    }
}
