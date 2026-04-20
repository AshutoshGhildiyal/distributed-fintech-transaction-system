package com.fintech.Transaction_Service.producer;

import com.fintech.Transaction_Service.kafka.TransactionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate ;

    public void sendTransactionEvent(TransactionEvent transactionEvent){
        kafkaTemplate.send("transaction.events", transactionEvent);
    }
}
