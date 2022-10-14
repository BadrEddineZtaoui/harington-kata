package com.harington.kata.service;

import com.harington.kata.constant.OperationType;
import com.harington.kata.dto.OperationDTO;
import com.harington.kata.exception.AccountNotFoundException;
import com.harington.kata.exception.InvalidArgumentException;
import com.harington.kata.model.Account;
import com.harington.kata.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class AccountService {

    Logger logger = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account depositWithdrawalAccount(Account account, OperationDTO operationDTO) {
        if (Objects.nonNull(account) && Objects.nonNull(operationDTO)) {
            if (operationDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) throw new InvalidArgumentException("Error Negative Amount!");
            if (OperationType.DEPOSIT.equals(operationDTO.getOperationType())) {
                account.setBalance(account.getBalance().add(operationDTO.getAmount()));
                return accountRepository.save(account);
            } else {
                BigDecimal currentBalance = account.getBalance().subtract(operationDTO.getAmount());
                if (currentBalance.compareTo(BigDecimal.ZERO) >= 0) {
                    account.setBalance(currentBalance);
                    return accountRepository.save(account);
                } else {
                    throw new InvalidArgumentException("Insufficient Balance!");
                }
            }
        } else {
            throw new InvalidArgumentException("Something Went Wrong");
        }
    }

    public Account getAccountById(Long id) throws AccountNotFoundException {
        logger.info("looking for account with id - {} ", id);
        if (Objects.nonNull(id)) {
            return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        } else {
            logger.info("Error looking for client with id - {} ", id);
            throw new InvalidArgumentException("Error Account not Set!");
        }
    }
}
