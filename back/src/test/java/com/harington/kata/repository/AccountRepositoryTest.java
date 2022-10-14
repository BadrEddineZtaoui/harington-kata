package com.harington.kata.repository;

import com.harington.kata.model.Account;
import com.harington.kata.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
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
        Account retrievedClient = accountRepository.findById(accountId).orElse(null);

        //THEN
        assertThat(retrievedClient).usingRecursiveComparison().ignoringFields("client").isEqualTo(account);

    }

    @Test
    void shouldGetAccountByIdTestNull() {
        //GIVEN
        Long accountId = 2l;

        //WHEN
        Optional<Account> retrievedClient = accountRepository.findById(accountId);

        //THEN
        assertThat(retrievedClient).isNotPresent();
    }

    @Test
    void shouldDepositBalanceTestOk() {
        //GIVEN
        Client client = Client.builder().id(1l).name("badr").build();
        Account account = Account
                .builder()
                .id(1l)
                .balance(new BigDecimal("250.00"))
                .date(LocalDate.now())
                .client(client)
                .build();
        //WHEN
        Account retrievedAccount = accountRepository.save(account);

        //THEN
        assertThat(retrievedAccount.getBalance()).isEqualTo(account.getBalance());
    }

    @BeforeEach
    void initData() {
        Client client = Client.builder().id(1l).name("badr").build();
        Account account = Account
                .builder()
                .id(1l)
                .balance(new BigDecimal("150.00"))
                .date(LocalDate.now())
                .client(client)
                .build();
        //WHEN
        accountRepository.save(account);
    }
}
