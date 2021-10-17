package hu.fcs.spring.kafka;

import hu.fcs.avro.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v2/user")
public class KafkaController {

    private final Producer producer;

    @Autowired
    KafkaController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(
            @RequestParam("name") String name,
            @RequestParam("age") Integer age,
            @RequestParam("nickName") String nickName
    ) {
        this.producer.sendMessage(new User(name, age, nickName));
    }
}