package org.example.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SubscriptionRequest {
    String subscriberName;
    String bookId;
    Date dateSubscribed;
    Date dateReturned;
}
