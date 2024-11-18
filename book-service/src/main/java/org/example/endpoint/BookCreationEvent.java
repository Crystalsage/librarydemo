package org.example.endpoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.example.model.Book;

import java.util.List;

@Data
public class BookCreationEvent {
    private List<Book> books;
}
