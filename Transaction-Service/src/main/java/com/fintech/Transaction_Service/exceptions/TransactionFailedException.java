package com.fintech.Transaction_Service.exceptions;

public class TransactionFailedException  extends RuntimeException{
    public TransactionFailedException(String message){
        super(message);
    }
}
