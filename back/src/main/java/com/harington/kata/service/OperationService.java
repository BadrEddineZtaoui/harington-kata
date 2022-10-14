package com.harington.kata.service;

import com.harington.kata.dto.OperationDTO;
import com.harington.kata.exception.AccountNotFoundException;
import com.harington.kata.model.Account;
import com.harington.kata.model.Client;
import com.harington.kata.model.Operation;
import com.harington.kata.repository.OperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class OperationService {

    Logger logger = LoggerFactory.getLogger(OperationService.class);
    private final OperationRepository operationRepository;
    private final ClientService clientService;
    private final AccountService accountService;

    public OperationService(OperationRepository operationRepository,
                            ClientService clientService, AccountService accountService) {
        this.operationRepository = operationRepository;
        this.clientService = clientService;
        this.accountService = accountService;
    }

    @Transactional
    public Operation withdrawalOrDepositOperations(OperationDTO operationDTO) {
        logger.info("start operation with data : {} ", operationDTO);
        Client client = clientService.getClientById(operationDTO.getClientId());
        Account account = client.getAccounts()
                .stream()
                .filter(acc -> operationDTO.getAccountId().equals(acc.getId()))
                .findFirst().orElse(null);
        if (Objects.nonNull(account)) {
            logger.info("account found - {}", account);
            Account accountAfterOperation = accountService.depositWithdrawalAccount(account, operationDTO);
            return saveOperation(operationDTO, accountAfterOperation);
        } else {
            throw new AccountNotFoundException();
        }
    }

    /**
     *
     * @param accountId
     * @return List<Operation>
     */
    public List<Operation> operationsHistory(Long accountId) {
        Account account = accountService.getAccountById(accountId);
        return operationRepository.findByAccountEqualsOrderByDateDesc(account);
    }

    /**
     * Save operation to database
     * @param operation
     * @param account
     */
    private Operation saveOperation(OperationDTO operation, Account account) {
        logger.info("saving operation for account - {} - with operation - {} ", account, operation);
        Operation operationToSave = Operation
                .builder()
                .date(LocalDateTime.now())
                .operationType(operation.getOperationType())
                .amount(operation.getAmount())
                .balance(account.getBalance())
                .account(account)
                .build();
        return operationRepository.save(operationToSave);
    }

}
