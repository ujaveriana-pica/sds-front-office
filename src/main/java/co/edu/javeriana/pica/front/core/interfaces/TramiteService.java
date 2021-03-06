package co.edu.javeriana.pica.front.core.interfaces;

import co.edu.javeriana.pica.front.core.entities.Radicacion;
import co.edu.javeriana.pica.front.core.entities.Tramite;
import co.edu.javeriana.pica.front.core.entities.AuthUser;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface TramiteService {

    String ESTADO_BORRADOR = "borrador";
    String ESTADO_RADICADO = "radicado";

    Tramite save(Tramite tramite, AuthUser authUser);
    List<Tramite> listByUsername(String username);
    Radicacion radicar(String id, AuthUser authUser);
    void addAttachment(String id, String uploadType, byte[] filedata);
    Optional<File> resolucionDownload(String id);
    void updateEstado(String id, String estado);
}
