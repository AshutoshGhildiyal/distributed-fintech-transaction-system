package com.fintech.Fraud_Service.engine;

import com.fintech.Fraud_Service.kafka.TransactionEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FraudEngine {

    private final List<FraudRule> rules;
    public FraudEngine(List<FraudRule> rules ){
        this.rules = rules;
    }

    public FraudDecision evaluate(TransactionEvent event ){

        FraudDecision finalDecision = FraudDecision.proceed();

        for (FraudRule rule : rules) {

            FraudDecision decision = rule.evaluate(event);

            if("FREEZE".equalsIgnoreCase(decision.getAction())){
                return decision; //highest priority
            }

            if (decision.priority() > finalDecision.priority() ){
                finalDecision = decision;
            }
        }

        return finalDecision;
    }
}
