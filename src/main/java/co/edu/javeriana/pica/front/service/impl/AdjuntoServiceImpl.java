package co.edu.javeriana.pica.front.service.impl;

import co.edu.javeriana.pica.front.service.AdjuntoService;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ApplicationScoped
public class AdjuntoServiceImpl implements AdjuntoService {

    @Override
    public void save(String folder, String filename, byte[] filedata) throws IOException {
        String dir = String.format("./%s", folder);
        Files.createDirectories(Paths.get(dir));
        String fileName = String.format("%s.png", filename);
        Files.write(Paths.get(dir, fileName), filedata);
    }
}
