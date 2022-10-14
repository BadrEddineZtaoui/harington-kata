package com.harington.kata.dto;

import com.harington.kata.constant.OperationType;

import java.io.Serializable;
import java.math.BigDecimal;

public class OperationDTO implements Serializable {

    private Long clientId;

    private Long accountId;

    private OperationType operationType;

    private BigDecimal amount;

    public OperationDTO() {
    }

    public OperationDTO(Long clientId, Long accountId, OperationType operationType, BigDecimal amount) {
        this.clientId = clientId;
        this.accountId = accountId;
        this.operationType = operationType;
        this.amount = amount;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OperationDTO{" +
                "clientId=" + clientId +
                ", accountId=" + accountId +
                ", operationType=" + operationType +
                ", amount=" + amount +
                '}';
    }
}
