package co.edu.javeriana.pica.front.controller.dto;

import co.edu.javeriana.pica.front.entity.Tramite;
import lombok.Data;

import java.util.Map;

@Data
public class TramiteRequest {
    private Map<String, String> data;
    private String type;

    public Tramite toTramite() {
        Tramite formEntity = new Tramite();
        formEntity.setData(data);
        formEntity.setTipo(type);
        return formEntity;
    }
}
