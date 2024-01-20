package io.confluent.developer.springccloud;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class JsonConsumer {
    // @KafkaListener(topics = "quotes-json", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ConsumerRecord<Integer, Quote> record) {
        System.out.println("received json = " + record.value() + " with key " + record.key());
      }
}
