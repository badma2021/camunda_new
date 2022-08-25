package com.camunda.camunda_new.model.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class BankAccountProcessDto {
    private long accountNumber;
    private int balance;
    private OffsetDateTime dateOfMoneyTransfer;
    private String processInstanceId;
    private boolean isDelay;
}
