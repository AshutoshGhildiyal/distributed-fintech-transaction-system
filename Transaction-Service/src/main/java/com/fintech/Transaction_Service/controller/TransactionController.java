package com.fintech.Transaction_Service.controller;

import com.fintech.Transaction_Service.dto.requestDTO.TransferRequest;
import com.fintech.Transaction_Service.dto.responseDTO.TransferResponse;
import com.fintech.Transaction_Service.model.Transaction;
import com.fintech.Transaction_Service.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public TransferResponse transfer(
            @RequestBody @Valid TransferRequest request
            ){
        return transactionService.transfer(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }

    
}
