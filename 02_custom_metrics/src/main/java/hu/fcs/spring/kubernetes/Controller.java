package hu.fcs.spring.kubernetes;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNullElse;


@RestController
@RequestMapping("/api")
public class Controller {

    private MeterRegistry meterRegistry;
    private Map<String, Counter> counters = new HashMap<>();

    public Controller(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @GetMapping({"/hello", "/hello/{name}"})
    public String hello(
        @PathVariable(required = false)
            String name
    ) {
        name = requireNonNullElse(name, "World");
        counters.putIfAbsent(name, this.meterRegistry.counter("hello", "name", name));
        counters.get(name).increment();
        return String.format("Hello %s!", name);
    }
}
