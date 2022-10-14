package com.harington.kata.service;

import com.harington.kata.exception.ClientNotFoundException;
import com.harington.kata.exception.InvalidArgumentException;
import com.harington.kata.model.Client;
import com.harington.kata.repository.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @MockBean
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
        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(client));
        Client retrievedClient = clientService.getClientById(clientId);

        //THEN
        assertThat(retrievedClient).usingRecursiveComparison().isEqualTo(client);

    }

    @Test
    void shouldGetClientByIdTestThrowNotFoundException() {
        //GIVEN
        Long clientId = 2l;

        //THEN
        Assertions.assertThatThrownBy(() -> clientService.getClientById(clientId))
                .isInstanceOf(RuntimeException.class)
                .isExactlyInstanceOf(ClientNotFoundException.class)
                .hasMessage("Client Not Found!");
    }

    @Test
    void shouldGetClientByIdTestThrowIllegalArgumentException() {
        Assertions.assertThatThrownBy(() -> clientService.getClientById(null))
                .isInstanceOf(RuntimeException.class)
                .isExactlyInstanceOf(InvalidArgumentException.class);
    }

}
