package co.edu.javeriana.pica.front.core.interfaces;

import java.io.IOException;

public interface AdjuntoRepository {
    void save(String folder, String filename, byte[] filedata) throws IOException;
}
