package hu.fcs.spring.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static hu.fcs.spring.kafka.KafkaApplication.REQUEST_TOPIC;


@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(RequestProducer.class);
    private FibonacciCalculator fibonacciCalculator;

    public Consumer(FibonacciCalculator fibonacciCalculator) {
        this.fibonacciCalculator = fibonacciCalculator;
    }

    @KafkaListener(topics = REQUEST_TOPIC, groupId = "group_id")
    public void processFibonacciRequest(String message) throws IOException {
        long n = Long.valueOf(message);
        logger.info(String.format("#### -> fib(%s) = %s", n, fibonacciCalculator.calc(n)));
    }
}