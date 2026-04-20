package com.fintech.Account_Service.repository;

import com.fintech.Account_Service.entity.IdempotencyKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotencyRepo extends JpaRepository<IdempotencyKey, Long> {
    boolean existsByIdempotencyKey(String idempotencyKey);
}
