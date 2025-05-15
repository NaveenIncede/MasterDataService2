package com.incede.Controller.loanTransaction.loanTransactionEngine;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.loanTransaction.loanTransactionEngine.LoanTransactionDTO;
import com.incede.Response.response.responseBody.ResponseWrapper;
import com.incede.Service.loanTransaction.loanTransationengine.LoanTransactionService;


@RestController
@RequestMapping("/v1/loan-transactions")
public class LoanTransactionController {

    private final LoanTransactionService loanTransactionService;

    public LoanTransactionController(LoanTransactionService loanTransactionService) {
        this.loanTransactionService = loanTransactionService;
    }

    // API to handle loan disbursements or repayments
    @PostMapping("/")
    public ResponseEntity<ResponseWrapper<String>> postLoanTransaction(@RequestBody LoanTransactionDTO loanTransactionDTO) {
        return ResponseEntity.ok(loanTransactionService.postLoanTransaction(loanTransactionDTO));
    }
}
