package com.fintech.Fraud_Service.rules;

import com.fintech.Fraud_Service.engine.FraudDecision;
import com.fintech.Fraud_Service.engine.FraudRule;
import com.fintech.Fraud_Service.kafka.TransactionEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class HighAmountRule implements FraudRule {

    private static final BigDecimal THRESHOLD = new BigDecimal("10000");

    @Override
    public FraudDecision evaluate(TransactionEvent event) {

        if (event.getAmount().compareTo(THRESHOLD) > 0) {
            return new FraudDecision(
                    "HIGH",
                    "FREEZE",
                    "High value transaction detected"
            );
        }

        return FraudDecision.proceed();
    }
}