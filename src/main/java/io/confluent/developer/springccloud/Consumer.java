package io.confluent.developer.springccloud;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Consumer {

    @Value("${topic.wordcount}")
    private String topicWordCount;

    @KafkaListener(topics = { "${topic.wordcount}" }, groupId = "spring-boot-kafka")
    @Transactional
    public void consume(ConsumerRecord<String, Long> record) {
        System.out.println("received from word count topic = " + record.value() + " with key " + record.key());
    }
}
