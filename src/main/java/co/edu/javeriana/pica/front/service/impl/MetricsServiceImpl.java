package co.edu.javeriana.pica.front.service.impl;

import co.edu.javeriana.pica.front.service.MetricsService;
import io.micrometer.core.instrument.MeterRegistry;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MetricsServiceImpl implements MetricsService {

    private final MeterRegistry registry;

    public MetricsServiceImpl(MeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void incrementCounter(String metric) {
        registry.counter(metric).increment();
    }
}
