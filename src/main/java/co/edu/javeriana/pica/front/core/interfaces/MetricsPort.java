package co.edu.javeriana.pica.front.core.interfaces;

public interface MetricsPort {

    final String TRAMITES_RADICADOS = "app.frontoffice.tramites.radicados";
    final String USUARIOS_REGISTRADOS = "app.frontoffice.usuarios.registrados";

    void incrementCounter(String metric);
}
