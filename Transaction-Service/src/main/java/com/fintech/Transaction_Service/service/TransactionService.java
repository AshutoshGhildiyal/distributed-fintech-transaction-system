package com.fintech.Transaction_Service.service;

import com.fintech.Transaction_Service.dto.requestDTO.TransferRequest;
import com.fintech.Transaction_Service.dto.responseDTO.TransferResponse;
import com.fintech.Transaction_Service.model.Transaction;

import java.math.BigDecimal;

public interface TransactionService {
    TransferResponse transfer(TransferRequest request);

    Transaction getTransaction(Long id);
}
