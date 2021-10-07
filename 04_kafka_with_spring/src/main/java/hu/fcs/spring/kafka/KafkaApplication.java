package hu.fcs.spring.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class KafkaApplication {

    public static final String REQUEST_TOPIC = "fibonacciRequests";
    public static final String RESULT_TOPIC = "fibonacciResults";

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }
}
