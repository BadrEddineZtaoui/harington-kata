package com.harington.kata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harington.kata.constant.OperationType;
import com.harington.kata.dto.OperationDTO;
import com.harington.kata.exception.AccountNotFoundException;
import com.harington.kata.exception.ClientNotFoundException;
import com.harington.kata.exception.InvalidArgumentException;
import com.harington.kata.model.Operation;
import com.harington.kata.service.OperationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Objects;

@SpringBootTest
class OperationControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private OperationController operationController;

    @MockBean
    private OperationService operationService;

    private MockMvc mockMvc;

    @PostConstruct
    void setup() { this.mockMvc = MockMvcBuilders.standaloneSetup(this.operationController).build(); }

    @Test
    void postDepositOperationCreated() throws Exception {
       // GIVEN
       String route = "/api/operation/deposit";
        OperationDTO operationDTO = new OperationDTO(1l, 1l, OperationType.DEPOSIT, new BigDecimal("100"));

        //WHEN
        Mockito.when(operationService.withdrawalOrDepositOperations(Mockito.any())).thenReturn(new Operation());

        //THEN
        mockMvc.perform(
                MockMvcRequestBuilders.post(route)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(operationDTO))
        ).andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    void postWithdrawalOperationCreated() throws Exception {
        // GIVEN
        String route = "/api/operation/withdrawal";
        OperationDTO operationDTO = new OperationDTO(1l, 1l, OperationType.WITHDRAWAL, new BigDecimal("100"));

        //WHEN
        Mockito.when(operationService.withdrawalOrDepositOperations(Mockito.any())).thenReturn(new Operation());

        //THEN
        mockMvc.perform(
                MockMvcRequestBuilders.post(route)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(operationDTO))
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void postDepositOperationThrowException404ClientNotFound() throws Exception {
        // GIVEN
        String route = "/api/operation/deposit";
        OperationDTO operationDTO = new OperationDTO(2l, 1l, OperationType.DEPOSIT, new BigDecimal("100"));

        //WHEN
        Mockito.when(operationService.withdrawalOrDepositOperations(Mockito.any())).thenThrow(ClientNotFoundException.class);

        //THEN
        mockMvc.perform(
                MockMvcRequestBuilders.post(route)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(operationDTO))
        ).andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ClientNotFoundException));

    }

    @Test
    void postDepositOperationThrowException404AccountNotFound() throws Exception {
        // GIVEN
        String route = "/api/operation/withdrawal";
        OperationDTO operationDTO = new OperationDTO(1l, 2l, OperationType.WITHDRAWAL, new BigDecimal("100"));

        //WHEN
        Mockito.when(operationService.withdrawalOrDepositOperations(Mockito.any())).thenThrow(AccountNotFoundException.class);

        //THEN
        mockMvc.perform(
                MockMvcRequestBuilders.post(route)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(operationDTO))
        ).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof AccountNotFoundException));
    }

    @Test
    void postDepositOperationThrowExceptionIllegalArgumentException() throws Exception {
        // GIVEN
        String route = "/api/operation/withdrawal";
        OperationDTO operationDTO = new OperationDTO(1l, 2l, OperationType.WITHDRAWAL, new BigDecimal("-100.00"));

        //WHEN
        Mockito.when(operationService.withdrawalOrDepositOperations(Mockito.any())).thenThrow(InvalidArgumentException.class);

        //THEN
        mockMvc.perform(
                MockMvcRequestBuilders.post(route)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(operationDTO))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof InvalidArgumentException));
    }
}
