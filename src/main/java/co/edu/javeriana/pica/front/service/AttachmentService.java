package co.edu.javeriana.pica.front.service;

import java.io.IOException;

public interface AttachmentService {
    void save(String folder, String filename, byte[] filedata) throws IOException;
}
