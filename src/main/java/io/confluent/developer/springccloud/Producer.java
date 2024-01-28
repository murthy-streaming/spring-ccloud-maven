package io.confluent.developer.springccloud;

import java.time.Duration;
import java.util.stream.Stream;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@EnableTransactionManagement
public class Producer {

    private final KafkaTemplate<Integer, String> template;

    Faker faker;

    @Value("${input.topic}")
    private String inputTopic;


    @EventListener(ApplicationStartedEvent.class)
    // @Transactional
    public void generate() {

        String quote;
        faker = Faker.instance();

        for (int i = 0; i < 5; i++) {
            quote = faker.hobbit().quote();
            System.out.printf("Sending quote %d: %s %n", i, quote);
            template.send(inputTopic, faker.random().nextInt(42), quote);
        }

    }
}
