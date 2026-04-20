package com.fintech.Transaction_Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionServiceApplication.class, args);
	}

}


/*

Client → Transaction Service
   ↓
Validate request
   ↓
Debit Sender (Account Service)
   ↓
Credit Receiver (Account Service)
   ↓
Mark transaction SUCCESS
   ❌ if credit fails → compensate (refund sender)

 */