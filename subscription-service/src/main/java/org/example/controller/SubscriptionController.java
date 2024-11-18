package org.example.controller;

import org.example.model.Subscription;
import org.example.dto.SubscriptionRequest;
import org.example.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<Subscription>> getAllBooks() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    // ## Create sub API
    // - URL: POST /subscriptions
    // - request body json: subscriberName, bookdId, dateSubscribed, dateReturned
    // - repsonse http status code: 201 if successfully crated, 422 if book copies not
    //     available for subscription
    // - create a record in subscription table. for given bookid chekc if one or more
    //     copies are available in the book table, if not then fail the operation
    // - on success/fail subscription both book and subs database should be consistent
    //     using choreography saga pattern.

    @PostMapping
    public ResponseEntity<String> createSubscription(SubscriptionRequest request) throws Exception {
        if (subscriptionService.createSubscription(request).isPresent()) {
            return ResponseEntity.created(null).build();
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}

