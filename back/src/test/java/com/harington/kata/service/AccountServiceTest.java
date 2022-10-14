package com.harington.kata.service;

import com.harington.kata.constant.OperationType;
import com.harington.kata.dto.OperationDTO;
import com.harington.kata.exception.AccountNotFoundException;
import com.harington.kata.exception.ClientNotFoundException;
import com.harington.kata.exception.InvalidArgumentException;
import com.harington.kata.model.Account;
import com.harington.kata.model.Client;
import com.harington.kata.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    void shouldGetAccountByIdTestOk() {
        //GIVEN
        Long accountId = 1l;
        Account account = Account
                .builder()
                .id(accountId)
                .balance(new BigDecimal("150.00"))
                .date(LocalDate.now())
                .build();
        //WHEN
        Mockito.when(accountRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(account));
        Account retrievedAccount = accountService.getAccountById(accountId);
        //THEN
        assertThat(retrievedAccount).usingRecursiveComparison().isEqualTo(account);

    }

    @Test
    void shouldDepositAmountTestOk() {
        //GIVEN
        Account account = Account
                .builder()
                .id(1l)
                .balance(new BigDecimal("150.00"))
                .date(LocalDate.now())
                .build();
        OperationDTO operationDTO = new OperationDTO(1l, 1l, OperationType.DEPOSIT, new BigDecimal("100.00"));

        Account resultAccount = Account
                .builder()
                .id(1l)
                .balance(new BigDecimal("250.00"))
                .date(LocalDate.now())
                .build();

        //WHEN
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(resultAccount);
        Account retrievedAccount = accountService.depositWithdrawalAccount(account, operationDTO);

        //THEN
        assertThat(retrievedAccount).usingRecursiveComparison().ignoringFields("client").isEqualTo(resultAccount);

    }

    @Test
    void shouldWithdrawalAmountTestOk() {
        //GIVEN
        Account account = Account
                .builder()
                .id(1l)
                .balance(new BigDecimal("150.00"))
                .date(LocalDate.now())
                .build();
        OperationDTO operationDTO = new OperationDTO(1l, 1l, OperationType.WITHDRAWAL, new BigDecimal("100.00"));

        Account resultAccount = Account
                .builder()
                .id(1l)
                .balance(new BigDecimal("50.00"))
                .date(LocalDate.now())
                .build();

        //WHEN
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(resultAccount);
        Account retrievedAccount = accountService.depositWithdrawalAccount(account, operationDTO);

        //THEN
        assertThat(retrievedAccount).usingRecursiveComparison().ignoringFields("client").isEqualTo(resultAccount);

    }

    @Test
    void shouldNotWithdrawalAmountTestThrowInvalidArgumentException() {
        //GIVEN
        Account account = Account
                .builder()
                .id(1l)
                .balance(new BigDecimal("150.00"))
                .date(LocalDate.now())
                .build();
        OperationDTO operationDTO = new OperationDTO(1l, 1l, OperationType.WITHDRAWAL, new BigDecimal("500.00"));

        //THEN
        Assertions.assertThatThrownBy(() -> accountService.depositWithdrawalAccount(account, operationDTO))
                .isInstanceOf(RuntimeException.class)
                .isExactlyInstanceOf(InvalidArgumentException.class)
                .hasMessage("Insufficient Amount!");

    }

    @Test
    void shouldNotDepositAmountTestThrowInvalidArgumentException() {
        //GIVEN
        Account account = Account
                .builder()
                .id(1l)
                .balance(new BigDecimal("150.00"))
                .date(LocalDate.now())
                .build();
        OperationDTO operationDTO = new OperationDTO(1l, 1l, OperationType.DEPOSIT, new BigDecimal("-100.00"));

        //THEN
        Assertions.assertThatThrownBy(() -> accountService.depositWithdrawalAccount(account, operationDTO))
                .isInstanceOf(RuntimeException.class)
                .isExactlyInstanceOf(InvalidArgumentException.class)
                .hasMessage("Error Negative Amount!");

    }


    @Test
    void shouldNotDepositAmountTestAccountNull() {
        //GIVEN
        Account account = null;
        OperationDTO operationDTO = new OperationDTO(1l, 1l, OperationType.DEPOSIT, new BigDecimal("100.00"));

        //THEN
        Assertions.assertThatThrownBy(() -> accountService.depositWithdrawalAccount(account, operationDTO))
                .isInstanceOf(RuntimeException.class)
                .isExactlyInstanceOf(InvalidArgumentException.class);
    }

    @Test
    void shouldNotDepositAmountTestOperationDTONull() {
        //GIVEN
        Account account = Account
                .builder()
                .id(1l)
                .balance(new BigDecimal("150.00"))
                .date(LocalDate.now())
                .build();;
        OperationDTO operationDTO = null;

        //THEN
        Assertions.assertThatThrownBy(() -> accountService.depositWithdrawalAccount(account, operationDTO))
                .isInstanceOf(RuntimeException.class)
                .isExactlyInstanceOf(InvalidArgumentException.class);
    }

    @Test
    void shouldNotDepositAmountTestAccountNullAndOperationDTONull() {
        //GIVEN
        Account account = null;
        OperationDTO operationDTO = null;

        //THEN
        Assertions.assertThatThrownBy(() -> accountService.depositWithdrawalAccount(account, operationDTO))
                .isInstanceOf(RuntimeException.class)
                .isExactlyInstanceOf(InvalidArgumentException.class);
    }



    @Test
    void shouldGetAccountByIdTestThrowNotFoundException() {
        //GIVEN
        Long accountId = 2l;

        //THEN
        Assertions.assertThatThrownBy(() -> accountService.getAccountById(accountId))
                .isInstanceOf(RuntimeException.class)
                .isExactlyInstanceOf(AccountNotFoundException.class)
                .hasMessage("Account Not Found!");
    }

    @Test
    void shouldGetAccountByIdTestThrowInvalidArgumentException() {
        Assertions.assertThatThrownBy(() -> accountService.getAccountById(null))
                .isInstanceOf(RuntimeException.class)
                .isExactlyInstanceOf(InvalidArgumentException.class);
    }
}
