package com.fintech.Transaction_Service.service.impl;

import com.fintech.Transaction_Service.dto.requestDTO.TransferRequest;
import com.fintech.Transaction_Service.dto.responseDTO.TransferResponse;
import com.fintech.Transaction_Service.enums.TransactionStatus;
import com.fintech.Transaction_Service.kafka.TransactionEvent;
import com.fintech.Transaction_Service.model.Transaction;
import com.fintech.Transaction_Service.producer.TransactionProducer;
import com.fintech.Transaction_Service.repository.TransactionRepo;
import com.fintech.Transaction_Service.service.AccountClient;
import com.fintech.Transaction_Service.service.TransactionService;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepository;

    @Autowired
    private TransactionProducer transactionProducer;



    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest request) {

        // STEP 1: Create transaction in PENDING state
        Transaction txn = new Transaction();
        txn.setSenderAccountId(request.getSenderId());
        txn.setReceiverAccountId(request.getReceiverId());
        txn.setAmount(request.getAmount());
        txn.setStatus(TransactionStatus.FRAUD_CHECK_PENDING);

        txn = transactionRepository.save(txn);

        // STEP 2: Publish event to Kafka (for Fraud Service)
        TransactionEvent event = new TransactionEvent(
                txn.getId(),
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount(),
                TransactionStatus.FRAUD_CHECK_PENDING.name()
        );

        transactionProducer.sendTransactionEvent(event);

        // STEP 3: DO NOT debit/credit here anymore
        return new TransferResponse(
                txn.getId(),
                TransactionStatus.FRAUD_CHECK_PENDING,
                "Transaction sent for fraud check"
        );
    }

    public Transaction getTransaction( Long id ){
        return transactionRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(" Transaction not Found!!")
                );
    }
}
