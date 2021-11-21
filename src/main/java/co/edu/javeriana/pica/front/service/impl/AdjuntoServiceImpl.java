package co.edu.javeriana.pica.front.service.impl;

import co.edu.javeriana.pica.front.service.AdjuntoService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ApplicationScoped
public class AdjuntoServiceImpl implements AdjuntoService {

    @ConfigProperty(name = "app.data.adjuntos")
    private String data;

    @Override
    public void save(String folder, String filename, byte[] filedata) throws IOException {
        String dir = String.format("%s/%s", data, folder);
        Files.createDirectories(Paths.get(dir));
        String fileName = String.format("%s.png", filename);
        Files.write(Paths.get(dir, fileName), filedata);
    }
}
