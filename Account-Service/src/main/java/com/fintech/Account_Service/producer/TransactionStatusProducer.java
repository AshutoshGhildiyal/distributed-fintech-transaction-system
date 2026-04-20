package com.fintech.Account_Service.producer;

import com.fintech.Account_Service.kafka.TransactionEvent;
import com.fintech.Account_Service.kafka.TransactionStatusEvent;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionStatusProducer {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public TransactionStatusProducer(KafkaTemplate<String, Object> kafkaTemplate ){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendStatus(TransactionStatusEvent event){
        kafkaTemplate.send("transaction.status", event);
    }
}
