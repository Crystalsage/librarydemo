package org.example.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class BookCreationConsumer {
    KafkaTemplate<String, BookCreationEvent> kafkaTemplate;

    @Autowired
    BookService bookService;

    @KafkaListener(topics = "book-event", groupId = "book-event-consumer")
    public boolean consume(String event) throws JsonProcessingException {
        var bookCreationEvent = new ObjectMapper().readValue(event, BookCreationEvent.class);
        bookService.createBooks(bookCreationEvent.getBooks());
        return true;
    }
}
