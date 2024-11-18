package org.example.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CreateSubscriptionRequest {
    String subscriberName;
    Long bookId;
    Date dateSubscribed;
    Date dateReturned;
}
