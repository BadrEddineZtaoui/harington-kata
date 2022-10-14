package com.harington.kata.controller;

import com.harington.kata.dto.OperationDTO;
import com.harington.kata.model.Operation;
import com.harington.kata.service.OperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operation")
public class OperationController {

    Logger logger = LoggerFactory.getLogger(OperationController.class);
    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping(value = "/deposit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Operation> depositOperation(@RequestBody OperationDTO operationDTO) {
        logger.info("Starting deposit operation");
        Operation operation = operationService.withdrawalOrDepositOperations(operationDTO);
        return new ResponseEntity<>(operation, HttpStatus.CREATED);
    }

    @PostMapping(value ="/withdrawal", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Operation> withdrawalOperation(@RequestBody OperationDTO operationDTO) {
        logger.info("Starting withdrawal operation");
        Operation operation = operationService.withdrawalOrDepositOperations(operationDTO);
        return new ResponseEntity<>(operation, HttpStatus.OK);
    }

    @GetMapping(value ="/{accountId}/history", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Operation>> getOperationsHistory(@PathVariable Long accountId) {
        logger.info("Starting getting operations history of accountId: {} ", accountId);
        List<Operation> operations = operationService.operationsHistory(accountId);
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }

}
