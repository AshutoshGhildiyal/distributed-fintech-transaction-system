package com.fintech.Account_Service.kafka;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FraudAlertEvent {

    private Long transactionId;
    private Long senderId;
    private Long receiverId;
    private BigDecimal amount;
    private String severity;   // HIGH / LOW
    private String reason;
    private String action;     // 👉 THIS IS WHAT YOU ASKED
}
