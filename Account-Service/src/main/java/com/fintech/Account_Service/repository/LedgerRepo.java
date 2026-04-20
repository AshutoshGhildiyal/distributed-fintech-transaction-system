package com.fintech.Account_Service.repository;

import com.fintech.Account_Service.entity.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LedgerRepo extends JpaRepository<LedgerEntry, Long> {
}
