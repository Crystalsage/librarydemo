package org.example.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.repository.BookRepository;
import org.example.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<List<Long>> createBooks(List<Book> books) {
        var savedBooks =  bookRepository.saveAll(books).stream()
            .map(Book::getBookId)
            .collect(Collectors.toList());

        return Optional.of(savedBooks);
    }

    public boolean isBookAvailable(Long bookId) {
        return bookRepository.findById(String.valueOf(bookId)).isPresent();
    }

    public void createSubscription(Long bookId) throws Exception {
        var optionalBook = bookRepository.findById(String.valueOf(bookId));
        if (optionalBook.isEmpty()) {
            throw new Exception("Book not found!");
        } else {
            var book = optionalBook.get();
            var availableCopies = book.getAvailableCopies();
            synchronized (this) {
                book.setAvailableCopies(availableCopies - 1);
                bookRepository.save(book);
            }
        }
    }
}
