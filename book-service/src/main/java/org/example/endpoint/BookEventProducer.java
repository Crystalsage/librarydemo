package org.example.endpoint;

import lombok.Lombok;
import org.example.Constants;
import org.example.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookEventProducer {

    @Autowired
    static KafkaTemplate<String, BookCreationEvent> kafkaTemplate;

    public static void produce(List<Book> books) {
        BookCreationEvent event = new BookCreationEvent();
        event.setBooks(books);
        kafkaTemplate.send(Constants.BOOK_CREATION_TOPIC, event);
    }
}
