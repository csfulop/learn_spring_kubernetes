package hu.fcs.spring.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {
    @Bean
    public FibonacciCalculator fibonacciCalculator() {
        return new SlowFibonacciCalculator();
    }

}
