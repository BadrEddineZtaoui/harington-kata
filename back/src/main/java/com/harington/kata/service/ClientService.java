package com.harington.kata.service;

import com.harington.kata.exception.ClientNotFoundException;
import com.harington.kata.exception.InvalidArgumentException;
import com.harington.kata.model.Client;
import com.harington.kata.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClientService {

    Logger logger = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getClientById(Long id) throws ClientNotFoundException {
        logger.info("looking for client with id - {} ", id);
        if(Objects.nonNull(id)) {
            return clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        } else {
            logger.info("Error looking for client with id - {} ", id);
            throw new InvalidArgumentException("Error Client Not Set!");
        }
    }
}
