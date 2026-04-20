package com.fintech.Fraud_Service.engine;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FraudDecision {

    private String severity;
    private String action;
    private String reason;
    public static FraudDecision proceed() {
        return new FraudDecision("LOW", "PROCEED", "No risk detected");
    }

    public int priority(){
        return switch (action.toUpperCase()){
            case "FREEZE" -> 3;
            case "REVIEW"-> 2;
            default -> 1;
        };
    }
}
