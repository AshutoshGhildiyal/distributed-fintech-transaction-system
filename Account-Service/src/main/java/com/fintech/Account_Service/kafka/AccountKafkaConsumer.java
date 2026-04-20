package com.fintech.Account_Service.kafka;

import com.fintech.Account_Service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountKafkaConsumer {

    @Autowired
    private AccountService accountService;

    @KafkaListener(topics = "fraud.alerts", groupId = "account-fraud-group")
    public void consumeFraudAlert(FraudAlertEvent alert){

        System.out.println("Received Fraud Alert: " + alert);

        if(alert.getAction().equals("FREEZE")){

            accountService.freezeAccount(
                    alert.getSenderId(),
                    "Fraud detected for transaction " + alert.getTransactionId()
            );

        }
        else if(alert.getAction().equals("APPROVE")){

            // Now we actually move money
            String debitKey = "DEBIT-" + alert.getTransactionId();
            String creditKey = "CREDIT-" + alert.getTransactionId();

            accountService.debit(
                    alert.getSenderId(),
                    alert.getAmount(),
                    debitKey
            );

            accountService.credit(
                    alert.getReceiverId(),
                    alert.getAmount(),
                    creditKey
            );
        }
    }
}
