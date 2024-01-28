package io.confluent.developer.springccloud;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JsonProducer {

    private final KafkaTemplate<Integer, Quote> template;

    Faker faker;

    @Value("${json.topic}")
    private String inputJsonTopic;

    // @EventListener(ApplicationStartedEvent.class)
    public void generate() {

        faker = Faker.instance();
        String quote;

        for (int i = 0; i < 5; i++) {
            quote = faker.hobbit().quote();
            System.out.printf("Sending json quote %d: %s %n", i, quote);
            template.send(inputJsonTopic, faker.random().nextInt(42), new Quote(quote));
        }
    }
}
