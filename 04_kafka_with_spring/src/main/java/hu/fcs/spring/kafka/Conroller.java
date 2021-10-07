package hu.fcs.spring.kafka;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class Conroller {

    private RequestProducer requestProducer;

    public Conroller(RequestProducer producer) {
        this.requestProducer = producer;
    }

    @PostMapping("/fibonacci")
    public void fibonacci(Integer n) {
        requestProducer.sendMessage(n.toString());
    }
}
