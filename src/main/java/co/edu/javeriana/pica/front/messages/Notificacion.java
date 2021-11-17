package co.edu.javeriana.pica.front.messages;

import lombok.Data;
import java.util.Map;

@Data
public class Notificacion {
    private String to;
    private String template;
    private Map<String, String> vars;
}
