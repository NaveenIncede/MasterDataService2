package com.incede.Repository.loanTransaction.loanTransactionEngine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incede.Model.loanTransaction.loanTransactionEngine.LoanTransaction;

@Repository
public interface LoanTransactionRepository extends JpaRepository<LoanTransaction, Integer> {
    // CRUD operations for loan transactions (if needed)
}
