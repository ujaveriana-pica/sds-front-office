package co.edu.javeriana.pica.front.infraestructure.api.dto;

import co.edu.javeriana.pica.front.core.entities.Tramite;
import lombok.Data;

import java.util.Map;

@Data
public class TramiteRequest {
    private Map<String, String> data;
    private String type;

    public Tramite toTramite() {
        Tramite tramite = new Tramite();
        tramite.setData(data);
        tramite.setTipo(type);
        return tramite;
    }
}
