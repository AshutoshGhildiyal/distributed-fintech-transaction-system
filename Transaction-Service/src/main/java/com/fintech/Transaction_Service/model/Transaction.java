package com.fintech.Transaction_Service.model;

import com.fintech.Transaction_Service.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderAccountId;
    private Long receiverAccountId;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
//    FRAUD_CHECK_PENDING
//            APPROVED
//    REJECTED
//            COMPLETED
    @Column(nullable = false, updatable = false)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private LocalDateTime localDateTime = LocalDateTime.now();
}
