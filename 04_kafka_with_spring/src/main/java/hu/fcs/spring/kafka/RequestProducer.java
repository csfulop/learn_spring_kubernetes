package hu.fcs.spring.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static hu.fcs.spring.kafka.KafkaApplication.REQUEST_TOPIC;


@Service
public class RequestProducer {

    private static final Logger logger = LoggerFactory.getLogger(RequestProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info(String.format("#### -> Producing message -> %s", message));
        this.kafkaTemplate.send(REQUEST_TOPIC, message);
    }
}