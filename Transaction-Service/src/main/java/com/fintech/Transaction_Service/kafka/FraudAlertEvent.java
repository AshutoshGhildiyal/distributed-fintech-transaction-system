package com.fintech.Transaction_Service.kafka;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FraudAlertEvent {

    private Long transactionId;
    private Long senderId;
    private Long receiverId;
    private BigDecimal amount;
    private String severity;
    private String action;
    private String reason;

}
