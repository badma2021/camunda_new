package com.camunda.camunda_new.model;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class BankAccount {

    private long accountNumber;
    private int balance;
    private OffsetDateTime dateOfMoneyTransfer;
}