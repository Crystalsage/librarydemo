package org.example.controller;

import org.example.endpoint.BookEventProducer;
import org.example.model.Book;
import org.example.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookEventProducer producer;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping
    public ResponseEntity saveBooks(List<Book> books) {
        BookEventProducer.produce(books);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity isBookAvailable(Long bookId) {
        if (bookService.isBookAvailable(bookId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}