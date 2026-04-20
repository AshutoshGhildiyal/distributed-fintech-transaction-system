package com.fintech.Account_Service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "idempotency_keys")
@Getter
@Setter
public class IdempotencyKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String idempotencyKey;

    private String operationType; // DEBIT/CREDIT

    private Long accountId;
    private Long transactionId;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();
}
