package com.fintech.Account_Service.service;

import com.fintech.Account_Service.entity.Account;

import java.math.BigDecimal;

public interface AccountService {

    Account createAccount(Long userId);

    Account getAccount(Long userId);

    void debit(Long accountId, BigDecimal amount, String idempotencyKey );

    void credit(Long accountId, BigDecimal amount , String idempotencyKey);

    void freezeAccount(Long accountId, String reason);

}
