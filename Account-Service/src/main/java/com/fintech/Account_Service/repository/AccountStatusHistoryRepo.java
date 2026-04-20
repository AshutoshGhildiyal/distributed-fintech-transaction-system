package com.fintech.Account_Service.repository;

import com.fintech.Account_Service.entity.AccountStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatusHistoryRepo extends JpaRepository<AccountStatusHistory, Long> {
}
