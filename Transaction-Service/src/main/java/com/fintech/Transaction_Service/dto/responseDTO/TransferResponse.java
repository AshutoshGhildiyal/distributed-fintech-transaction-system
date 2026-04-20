package com.fintech.Transaction_Service.dto.responseDTO;

import com.fintech.Transaction_Service.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {

    private Long transactionId;
    private TransactionStatus status;
    private String message;
}
