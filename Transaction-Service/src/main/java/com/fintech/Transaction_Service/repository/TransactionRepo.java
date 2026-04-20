package com.fintech.Transaction_Service.repository;

import com.fintech.Transaction_Service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
