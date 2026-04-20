package com.fintech.Account_Service.consumer;

import com.fintech.Account_Service.kafka.FraudAlertEvent;
import com.fintech.Account_Service.kafka.TransactionStatusEvent;
import com.fintech.Account_Service.producer.TransactionStatusProducer;
import com.fintech.Account_Service.service.AccountService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FraudAlertConsumer {

    private final AccountService accountService;
    private final TransactionStatusProducer transactionStatusProducer;

    public FraudAlertConsumer(AccountService accountService,
                              TransactionStatusProducer transactionStatusProducer){
        this.accountService = accountService;
        this.transactionStatusProducer = transactionStatusProducer;
    }

    @KafkaListener(topics = "fraud.alerts", groupId = "fraud-group")
    public void consume(FraudAlertEvent alertEvent ){

        System.out.println("🚨 Fraud alert received for txn: "
                + alertEvent.getTransactionId());

        TransactionStatusEvent statusEvent = new TransactionStatusEvent();
        statusEvent.setTransactionId(alertEvent.getTransactionId());

        if ("High".equalsIgnoreCase(alertEvent.getSeverity())) {

            String reason =
                    "Auto freeze due to suspected fraud on txn "
                            + alertEvent.getTransactionId()
                            + " | Reason: " + alertEvent.getReason();

            accountService.freezeAccount(alertEvent.getSenderId(), reason);

            System.out.println("✅ Account "
                    + alertEvent.getSenderId()
                    + " frozen automatically");

            statusEvent.setStatus("FAILED");
            statusEvent.setReason("Fraud detected");

        } else {

            System.out.println("🟢 Transaction SAFE");

            statusEvent.setStatus("SUCCESS");
            statusEvent.setReason("Transaction allowed");
        }

        // SEND RESULT BACK
        transactionStatusProducer.sendStatus(statusEvent);
    }
}