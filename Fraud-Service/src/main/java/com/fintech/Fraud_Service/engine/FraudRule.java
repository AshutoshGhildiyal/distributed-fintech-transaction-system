package com.fintech.Fraud_Service.engine;

import com.fintech.Fraud_Service.kafka.TransactionEvent;

public interface FraudRule {

    FraudDecision evaluate(TransactionEvent event);
}
