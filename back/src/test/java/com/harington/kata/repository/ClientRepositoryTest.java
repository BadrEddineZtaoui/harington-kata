package com.harington.kata.repository;

import com.harington.kata.model.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void shouldGetClientByIdTestOk() {
        //GIVEN
        Long clientId = 1l;
        Client client = Client
                .builder()
                .id(clientId)
                .name("badr")
                .build();

        //WHEN
        Client retrievedClient = clientRepository.findById(clientId).orElse(null);

        //THEN
        assertThat(retrievedClient).usingRecursiveComparison().ignoringFields("accounts").isEqualTo(client);

    }

    @Test
    void shouldGetClientByIdTestNull() {
        //GIVEN
        Long accountId = 2l;

        //WHEN
        Optional<Client> retrievedClient = clientRepository.findById(accountId);

        //THEN
        assertThat(retrievedClient).isNotPresent();
    }
}
