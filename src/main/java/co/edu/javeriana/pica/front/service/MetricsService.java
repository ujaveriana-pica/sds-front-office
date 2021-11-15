package co.edu.javeriana.pica.front.service;

public interface MetricsService {

    final String TRAMITES_RADICADOS = "app.frontoffice.tramites.radicados";

    void incrementCounter(String metric);
}
