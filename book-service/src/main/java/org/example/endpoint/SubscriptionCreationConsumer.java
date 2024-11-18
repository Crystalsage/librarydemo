package org.example.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Constants;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SubscriptionCreationConsumer {
    KafkaTemplate<String, BookCreationEvent> kafkaTemplate;

    @Autowired
    BookService bookService;

    @KafkaListener(topics = Constants.SUBSCRIPTION_CREATION_TOPIC, groupId = "subscription-event-consumer")
    public boolean consume(String event) throws Exception {
        var subscriptionCreationEvent = new ObjectMapper().readValue(event, SubscriptionCreatedEvent.class);
        bookService.createSubscription(subscriptionCreationEvent.getBookId());
        return true;
    }
}
