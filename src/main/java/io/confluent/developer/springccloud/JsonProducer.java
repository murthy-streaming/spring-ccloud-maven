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

import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class JsonProducer {

    private final KafkaTemplate<Integer, Quote> template;

    Faker faker;

    @Bean
	NewTopic hobbitAvro() {
		return TopicBuilder.name("quotes-json").partitions(6).replicas(3).build();
	}

    // @EventListener(ApplicationStartedEvent.class)
    public void generate() {

        faker = Faker.instance();
        final Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));

        final Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.hobbit().quote()));

        Flux.zip(interval, quotes)
                .map(it -> {
                    System.out.println("Sending message: " + it.getT2());
                    return template.send("quotes-json", faker.random().nextInt(42), new Quote(it.getT2()));
                })
                .blockLast();
    }
}
