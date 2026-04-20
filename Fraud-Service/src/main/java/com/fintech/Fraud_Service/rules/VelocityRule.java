package com.fintech.Fraud_Service.rules;

import com.fintech.Fraud_Service.engine.FraudRule;
import com.fintech.Fraud_Service.kafka.TransactionEvent;
import com.fintech.Fraud_Service.engine.FraudDecision;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/*
If same sender makes 3+ transactions within 5 minutes -> suspicious

 */
@Component
public class VelocityRule implements FraudRule {


    @Override
    public FraudDecision evaluate(TransactionEvent event) {
        // TEMP LOGIC (later DB/Redis based)
//        if (event.getAmount().compareTo(new BigDecimal("3000")) > 0) {
//
//            return new FraudDecision(
//                    "LOW",
//                    "PROCEED",
//                    "Velocity check triggered (demo)"
//            );
//        }
        return FraudDecision.proceed();

    }
}
