package hu.fcs.spring.kafka;

import hu.fcs.avro.User;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@CommonsLog(topic = "Consumer V1 Logger")
public class Consumer {

    @KafkaListener(topics = "${topic.name}", groupId = "group_id")
    public void consume(ConsumerRecord<String, User> record) {
        log.info(String.format("Consumed message -> %s", record.value()));
    }
}
