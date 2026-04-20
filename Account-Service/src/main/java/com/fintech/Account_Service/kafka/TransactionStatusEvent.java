package com.fintech.Account_Service.kafka;

import lombok.Data;

@Data
public class TransactionStatusEvent {

    private Long transactionId;
    private String status;
    private String reason;
}