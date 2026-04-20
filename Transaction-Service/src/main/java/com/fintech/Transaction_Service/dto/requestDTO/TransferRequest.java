package com.fintech.Transaction_Service.dto.requestDTO;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {

    @NotNull
    private Long senderId;

    @NotNull
    private Long receiverId;

    @Positive
    private BigDecimal amount;
}
