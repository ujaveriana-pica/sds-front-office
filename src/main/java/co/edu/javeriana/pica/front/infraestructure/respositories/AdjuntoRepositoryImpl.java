package co.edu.javeriana.pica.front.infraestructure.respositories;

import co.edu.javeriana.pica.front.core.interfaces.AdjuntoRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ApplicationScoped
public class AdjuntoRepositoryImpl implements AdjuntoRepository {

    @ConfigProperty(name = "app.data.adjuntos")
    String data;

    @Override
    public void save(String folder, String filename, byte[] filedata) throws IOException {
        String dir = String.format("%s/%s", data, folder);
        Files.createDirectories(Paths.get(dir));
        String fileName = String.format("%s.png", filename);
        Files.write(Paths.get(dir, fileName), filedata);
    }
}
