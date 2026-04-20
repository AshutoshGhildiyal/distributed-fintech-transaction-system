package com.fintech.Account_Service.service.impl;

import com.fintech.Account_Service.entity.Account;
import com.fintech.Account_Service.entity.AccountStatusHistory;
import com.fintech.Account_Service.entity.IdempotencyKey;
import com.fintech.Account_Service.entity.LedgerEntry;
import com.fintech.Account_Service.repository.AccountRepo;
import com.fintech.Account_Service.repository.AccountStatusHistoryRepo;
import com.fintech.Account_Service.repository.IdempotencyRepo;
import com.fintech.Account_Service.repository.LedgerRepo;
import com.fintech.Account_Service.service.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private LedgerRepo ledgerRepo;

    @Autowired
    private IdempotencyRepo idempotencyRepo;

    @Autowired
    private AccountStatusHistoryRepo accountStatusHistoryRepo;


    @Override
    public Account createAccount(Long userId) {
        Account account = new Account();

        account.setUserId(userId);
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setBalance(BigDecimal.ZERO);
        account.setStatus("ACTIVE");


        return accountRepo.save(account);
    }

    @Override
    public Account getAccount(Long userId) {

        return accountRepo.findByUserId(userId).orElseThrow(
                () -> new RuntimeException("Account not found")
        );

    }



    @Override
    @Transactional
    public void debit(Long accountId, BigDecimal amount, String idempotencyKey) {

        //1 Idempotency check
        if (idempotencyRepo.existsByIdempotencyKey(idempotencyKey)){
            throw new RuntimeException("Duplicate transaction detected");
        }

        Account account = accountRepo.findById(accountId).orElseThrow(
                ()-> new RuntimeException("Account not found!!")
        );

        if (!account.getStatus().equals("ACTIVE")){
            throw new RuntimeException("Account Not Active");
        }

        if ( account.getBalance().compareTo(amount) < 0 ){
            throw new RuntimeException("Insufficient Balance!!");
        }

        // 2 Update balance
        account.setBalance( account.getBalance().subtract(amount));
        accountRepo.save(account);

        // 3. Ledger Entry
        LedgerEntry entry = new LedgerEntry();
        entry.setAccountId(accountId);
        entry.setEntryType("DEBIT");
        entry.setAmount(amount);
        entry.setBalanceAfter(account.getBalance());
        ledgerRepo.save(entry);


        // 4. Idempotency Key
        IdempotencyKey key = new IdempotencyKey();
        key.setIdempotencyKey(idempotencyKey);
        key.setOperationType("DEBIT");;
        key.setAccountId(accountId);
        key.setStatus("SUCCESS");
//        key.setTransactionId(a);
        idempotencyRepo.save(key);

    }

    @Override
    @Transactional
    public void credit(Long accountId, BigDecimal amount, String idempotencyKey) {

        if(idempotencyRepo.existsByIdempotencyKey(idempotencyKey)){
            throw new RuntimeException("Duplicate transaction detected");
        }

        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found!!"));

        account.setBalance(account.getBalance().add(amount));
        accountRepo.save(account);

        LedgerEntry entry = new LedgerEntry();
        entry.setAccountId(accountId);
        entry.setEntryType("CREDIT");
        entry.setAmount(amount);
        entry.setBalanceAfter(account.getBalance());
        ledgerRepo.save(entry);

        IdempotencyKey key = new IdempotencyKey();
        key.setIdempotencyKey(idempotencyKey);
        key.setOperationType("CREDIT");
        key.setAccountId(accountId);
        key.setStatus("SUCCESS");
        idempotencyRepo.save(key);


    }

    @Override
    @Transactional
    public void freezeAccount(Long accountId, String reason) {

        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found!!"));

        String oldStatus = account.getStatus();
        account.setStatus("FROZEN");
        accountRepo.save(account);

        AccountStatusHistory history = new AccountStatusHistory();
        history.setAccountId(accountId);
        history.setOldStatus(oldStatus);
        history.setNewStatus("FROZEN");
        history.setReason(reason);

        accountStatusHistoryRepo.save(history);

    }
}
