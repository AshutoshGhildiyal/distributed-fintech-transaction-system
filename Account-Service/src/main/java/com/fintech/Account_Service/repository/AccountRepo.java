package com.fintech.Account_Service.repository;

import com.fintech.Account_Service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long > {
    Optional<Account> findByUserId(Long userId);

}
