package org.example.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.SubscriptionRepository;
import org.example.client.BookServiceClient;
import org.example.endpoint.SubscriptionProducer;
import org.example.model.Book;
import org.example.model.Subscription;
import org.example.repository.BookRepository;
import org.example.dto.SubscriptionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Optional<Long> createSubscription(SubscriptionRequest request) throws Exception {
        Optional<Book> optionalBook = bookRepository.findById(request.getBookId());
        if (optionalBook.isEmpty()) {
            return Optional.empty();
        }

        Book book = optionalBook.get();

        if (book.getAvailableCopies() < 1) {
            return Optional.empty();
        }

        Subscription subscription = new Subscription();
        subscription.setSubcriberName(request.getSubscriberName());
        subscription.setBookName(book.getBookName());
        subscription.setDateSubscribed(request.getDateSubscribed());
        subscription.setDateReturned(request.getDateReturned());

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        SubscriptionProducer.produce(book.getBookId());

        subscriptionRepository.save(subscription);
        return Optional.of(book.getBookId());
    }
}
