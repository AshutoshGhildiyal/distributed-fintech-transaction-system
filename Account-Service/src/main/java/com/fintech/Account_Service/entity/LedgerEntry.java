package com.fintech.Account_Service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ledger")
@Getter
@Setter
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

    private Long transactionId;

    private String entryType; // DEBIT or CREDIT

    private BigDecimal amount;
    private BigDecimal balanceAfter;

    private LocalDateTime createdAt = LocalDateTime.now();
}
