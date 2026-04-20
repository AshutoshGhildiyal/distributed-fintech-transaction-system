package com.fintech.Fraud_Service.consumer;

import com.fintech.Fraud_Service.engine.FraudDecision;
import com.fintech.Fraud_Service.engine.FraudEngine;
import com.fintech.Fraud_Service.kafka.FraudAlertEvent;
import com.fintech.Fraud_Service.kafka.TransactionEvent;
import com.fintech.Fraud_Service.producer.FraudProducer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {


    private final FraudEngine fraudEngine;
    private final FraudProducer fraudProducer;

    public TransactionConsumer(FraudEngine fraudEngine, FraudProducer fraudProducer) {
        this.fraudEngine = fraudEngine;
        this.fraudProducer = fraudProducer;
    }


    @KafkaListener(topics = "transaction.events", groupId = "fraud-group")
    public void consume(TransactionEvent event){

        System.out.println("Received transaction: " + event.getTransactionId());
        System.out.println("Amount: " + event.getAmount());

        FraudDecision decision = fraudEngine.evaluate(event);

        System.out.println("Fraud Decision: " + decision.getAction());

        FraudAlertEvent alertEvent = new FraudAlertEvent();
        alertEvent.setTransactionId(event.getTransactionId());
        alertEvent.setSenderId(event.getSenderId());
        alertEvent.setReceiverId(event.getReceiverId());
        alertEvent.setAmount(event.getAmount());
        alertEvent.setSeverity(decision.getSeverity());
        alertEvent.setAction(decision.getAction());
        alertEvent.setReason(decision.getReason());

        fraudProducer.sendFraud(alertEvent);
    }

//    @KafkaListener(topics = "transaction.events", groupId = "fraud-group")
//    public void consume(TransactionEvent event){
//
//        System.out.println("Received transaction: " + event.getTransactionId());
//        System.out.println("Amount: " + event.getAmount());
//
//
//        FraudDecision decision = fraudEngine.evaluate(event);
//
//        System.out.println("Fraud Decision: " + decision.getAction());
//
//        if (!decision.getAction().equals("PROCEED")) {
//
//            FraudAlertEvent alertEvent = new FraudAlertEvent();
//            alertEvent.setTransactionId(event.getTransactionId());
//            alertEvent.setSenderId(event.getSenderId());
//            alertEvent.setSeverity(decision.getSeverity());
//            alertEvent.setAction(decision.getAction());
//            alertEvent.setReason(decision.getReason());
//            alertEvent.setReceiverId(event.getReceiverId());
//            alertEvent.setAmount(event.getAmount());
//
//            fraudProducer.sendFraud(alertEvent);
//        }
////        fraudService.evaluateTransaction(event);
//    }

}
