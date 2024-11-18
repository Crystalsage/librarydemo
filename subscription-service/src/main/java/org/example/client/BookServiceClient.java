package org.example.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service", url = "<URL>")
public interface BookServiceClient {
    @GetMapping("/books/{bookId}")
    boolean isBookAvailable(@PathVariable("bookId") Long bookId);
}