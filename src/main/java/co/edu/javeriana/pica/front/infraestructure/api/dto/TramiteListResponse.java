package co.edu.javeriana.pica.front.infraestructure.api.dto;

import lombok.Data;

@Data
public class TramiteListResponse {
    private String id;
    private String tipo;
    private String tipoDesc;
    private String fechaCreacion;
    private String fechaRadicacion;
    private String estado;
}
