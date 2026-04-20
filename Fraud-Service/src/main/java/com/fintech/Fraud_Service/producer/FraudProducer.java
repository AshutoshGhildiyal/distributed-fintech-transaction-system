package com.fintech.Fraud_Service.producer;

import com.fintech.Fraud_Service.kafka.FraudAlertEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FraudProducer {

    private final KafkaTemplate<String, FraudAlertEvent> kafkaTemplate;

    public FraudProducer(KafkaTemplate<String, FraudAlertEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFraud(FraudAlertEvent alertEvent) {
        kafkaTemplate.send("fraud.alerts", alertEvent);
        System.out.println("Fraud alert sent for transaction: " + alertEvent.getTransactionId());
    }
}