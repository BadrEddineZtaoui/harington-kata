package com.harington.kata.repository;

import com.harington.kata.constant.OperationType;
import com.harington.kata.model.Account;
import com.harington.kata.model.Operation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
class OperationRepositoryTest {

    @Autowired
    private OperationRepository operationRepository;

    @Test
    void shouldSaveOperation() {
        //GIVEN
        Account account = Account
                .builder()
                .id(1l)
                .balance(new BigDecimal("150.00"))
                .date(LocalDate.now())
                .build();
        Operation operation = Operation.builder()
                .date(LocalDateTime.now())
                .operationType(OperationType.DEPOSIT)
                .amount(new BigDecimal("150.00"))
                .account(account)
                .build();
        //WHEN
        Operation retrievedOperation = operationRepository.save(operation);

        //THEN
        Assertions.assertThat(retrievedOperation).usingRecursiveComparison().ignoringFields("account").isEqualTo(operation);

    }
}
