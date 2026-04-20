package com.fintech.Account_Service.controller;


import com.fintech.Account_Service.entity.Account;
import com.fintech.Account_Service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public Account createAccount(@RequestParam Long userId){
        return accountService.createAccount(userId);
    }

    @GetMapping("/{userId}")
    public Account getAccount(@PathVariable Long userId){
        return accountService.getAccount(userId);
    }

    @PostMapping("/debit")
    public String debit(
            @RequestParam Long accountId,
            @RequestParam BigDecimal amount,
            @RequestHeader("Idempotency-Key") String idempotencyKey ){
        accountService.debit(accountId, amount, idempotencyKey);
        return "Debited successfully";
    }

    @PostMapping("/credit")
    public String credit(
            @RequestParam Long accountId,
            @RequestParam BigDecimal amount,
            @RequestHeader("Idempotency-Key") String idempotencyKey ){
        accountService.credit(accountId,amount,idempotencyKey);
        return "Credited Successfully";
    }

    @PostMapping("/freeze")
    public String freeze(
            @RequestParam Long accountId,
            @RequestParam String reason
    ){
        accountService.freezeAccount(accountId,reason);
        return "Account Frozen";
    }

}
