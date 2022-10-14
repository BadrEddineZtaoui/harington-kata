package com.harington.kata.service;

import com.harington.kata.constant.OperationType;
import com.harington.kata.dto.OperationDTO;
import com.harington.kata.model.Account;
import com.harington.kata.model.Client;
import com.harington.kata.model.Operation;
import com.harington.kata.repository.ClientRepository;
import com.harington.kata.repository.OperationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class OperationServiceTest {

    @Autowired
    OperationService operationService;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientService clientService;


    @MockBean
    OperationRepository operationRepository;

    @Test
    void shouldSaveOperation() {
        //GIVEN
        Account account = Account
                .builder()
                .id(1l)
                .balance(new BigDecimal("250.00"))
                .date(LocalDate.now())
                .build();
        Client client = Client.builder()
                .id(1l)
                .name("badr")
                .accounts(List.of(account))
                .build();

        OperationDTO operationDTO = new OperationDTO(client.getId(), account.getId(), OperationType.DEPOSIT, new BigDecimal("100.00"));

        Operation operation = Operation
                .builder()
                .operationType(operationDTO.getOperationType())
                .amount(operationDTO.getAmount())
                .date(LocalDateTime.now())
                .account(account)
                .balance(new BigDecimal("350.00"))
                .build();
        //WHEN
        Mockito.when(operationRepository.save(Mockito.any())).thenReturn(operation);
        Operation retrievedAccountAfterOperation = operationService.withdrawalOrDepositOperations(operationDTO);

        //THEN
        Assertions.assertThat(retrievedAccountAfterOperation).usingRecursiveComparison().ignoringFields("account").isEqualTo(operation);
    }
}
