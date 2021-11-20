package co.edu.javeriana.pica.front.service;

public interface MetricsService {

    final String TRAMITES_RADICADOS = "app.frontoffice.tramites.radicados";
    final String USUARIOS_REGISTRADOS = "app.frontoffice.usuarios.registrados";

    void incrementCounter(String metric);
}
