package com.harington.kata.repository;

import com.harington.kata.model.Account;
import com.harington.kata.model.Operation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Long> {
    List<Operation> findByAccountEqualsOrderByDateDesc(Account account);
}
