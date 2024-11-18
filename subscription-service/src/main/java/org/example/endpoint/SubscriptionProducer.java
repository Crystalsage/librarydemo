package org.example.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Constants;
import org.example.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionProducer {
    @Autowired
    static KafkaTemplate<String, SubscriptionCreatedEvent> kafkaTemplate;

    public static void produce(Long bookId) {
        SubscriptionCreatedEvent event = new SubscriptionCreatedEvent();
        event.setBookId(bookId);
        kafkaTemplate.send(Constants.BOOK_CREATION_TOPIC, event);
    }
}
