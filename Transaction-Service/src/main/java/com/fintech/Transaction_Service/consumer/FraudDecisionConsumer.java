package com.fintech.Transaction_Service.consumer;

import com.fintech.Transaction_Service.enums.TransactionStatus;
import com.fintech.Transaction_Service.kafka.FraudAlertEvent;
import com.fintech.Transaction_Service.model.Transaction;
import com.fintech.Transaction_Service.repository.TransactionRepo;
import com.fintech.Transaction_Service.service.AccountClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FraudDecisionConsumer {

    private final TransactionRepo transactionRepo;
    private final AccountClient accountClient;

    public FraudDecisionConsumer(TransactionRepo transactionRepo,
                                 AccountClient accountClient) {
        this.transactionRepo = transactionRepo;
        this.accountClient = accountClient;
    }

    @KafkaListener(topics = "fraud.alerts", groupId = "transaction-group")
    public void consume(FraudAlertEvent event) {

        Transaction txn = transactionRepo.findById(event.getTransactionId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if ("PROCEED".equalsIgnoreCase(event.getAction())) {

            String debitKey = "DEBIT-" + event.getTransactionId();
            String creditKey = "CREDIT-" + event.getTransactionId();

            accountClient.debit(event.getSenderId(), event.getAmount(), debitKey);
            accountClient.credit(event.getReceiverId(), event.getAmount(), creditKey);

            txn.setStatus(TransactionStatus.COMPLETED);

            System.out.println("✅ Transaction completed");

        } else {

            txn.setStatus(TransactionStatus.REJECTED);

            System.out.println("❌ Transaction failed due to fraud");
        }

        transactionRepo.save(txn);
    }
}