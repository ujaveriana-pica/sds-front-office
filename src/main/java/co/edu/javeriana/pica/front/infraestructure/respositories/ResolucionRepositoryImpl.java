package co.edu.javeriana.pica.front.infraestructure.respositories;

import co.edu.javeriana.pica.front.core.interfaces.ResolucionRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.util.Optional;

@ApplicationScoped
public class ResolucionRepositoryImpl implements ResolucionRepository {

    @ConfigProperty(name = "app.data.tramites")
    String data;

    @Override
    public Optional<File> download(String id) {
        String filename = String.format("Resolucion_%s.pdf", id.toUpperCase());
        File fileDownload = new File(data + File.separator + filename);
        if (fileDownload.exists()) {
            return Optional.of(fileDownload);
        }
        return Optional.empty();
    }
}
