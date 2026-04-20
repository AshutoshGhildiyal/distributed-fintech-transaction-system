package com.fintech.Fraud_Service.kafka;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionEvent{

    private Long transactionId;
    private Long senderId;
    private Long receiverId;
    private BigDecimal amount;
    private String status;



    // getters & setters
}
