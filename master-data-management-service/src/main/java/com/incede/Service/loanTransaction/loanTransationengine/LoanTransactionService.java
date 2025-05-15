package com.incede.Service.loanTransaction.loanTransationengine;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.loanTransaction.loanTransactionEngine.LoanTransactionDTO;
import com.incede.Model.loanTransaction.loanTransactionEngine.LoanTransaction;
import com.incede.Repository.loanTransaction.loanTransactionEngine.LoanTransactionRepository;
import com.incede.Response.response.responseBody.ResponseWrapper;
import com.incede.Service.journal.journalEntry.JournalEntryService;
import com.incede.Service.scheme.schemeGl.SchemeGlService;


@Service
public class LoanTransactionService {

    private final JournalEntryService journalEntryService;
    private final SchemeGlService schemeGlService;
    private final LoanTransactionRepository loanTransactionRepository;

    public LoanTransactionService(JournalEntryService journalEntryService, 
                                  SchemeGlService schemeGlService, 
                                  LoanTransactionRepository loanTransactionRepository) {
        this.journalEntryService = journalEntryService;
        this.schemeGlService = schemeGlService;
        this.loanTransactionRepository = loanTransactionRepository;
    }

    @Transactional
    public ResponseWrapper<String> postLoanTransaction(LoanTransactionDTO loanTransactionDTO) {
        // Fetch GL account IDs for each transaction type
        Integer principalGL = schemeGlService.getGlAccountId(loanTransactionDTO.getSchemeId(), "PRINCIPAL").getData();
        Integer interestGL = schemeGlService.getGlAccountId(loanTransactionDTO.getSchemeId(), "INTEREST").getData();
        Integer penalGL = schemeGlService.getGlAccountId(loanTransactionDTO.getSchemeId(), "PENAL").getData();
        Integer feesGL = schemeGlService.getGlAccountId(loanTransactionDTO.getSchemeId(), "FEES").getData();

        // Create and save Loan Transaction
        LoanTransaction loanTransaction = new LoanTransaction();
        loanTransaction.setSchemeId(loanTransactionDTO.getSchemeId());
        loanTransaction.setPrincipalAmount(loanTransactionDTO.getPrincipalAmount());
        loanTransaction.setInterestAmount(loanTransactionDTO.getInterestAmount());
        loanTransaction.setPenalAmount(loanTransactionDTO.getPenalAmount());
        loanTransaction.setFeesAmount(loanTransactionDTO.getFeesAmount());
        loanTransaction.setTenantId(loanTransactionDTO.getTenantId()); // Added tenantId
        loanTransaction.setIsActive(true); // Assuming default is active
        loanTransaction.setIdentity(UUID.randomUUID()); // Generating a unique identity
        loanTransaction.setCreatedBy(loanTransactionDTO.getCreatedBy()); // Generating a unique identity

        loanTransactionRepository.save(loanTransaction);

        // Post transactions to GL accounts
        journalEntryService.postTransaction(principalGL, loanTransactionDTO.getPrincipalAmount(),loanTransactionDTO.getTenantId(),loanTransactionDTO.getCreatedBy());
        journalEntryService.postTransaction(interestGL, loanTransactionDTO.getInterestAmount(), loanTransactionDTO.getTenantId(),loanTransactionDTO.getCreatedBy());
        journalEntryService.postTransaction(penalGL, loanTransactionDTO.getPenalAmount(), loanTransactionDTO.getTenantId(),loanTransactionDTO.getCreatedBy());
        journalEntryService.postTransaction(feesGL, loanTransactionDTO.getFeesAmount(), loanTransactionDTO.getTenantId(),loanTransactionDTO.getCreatedBy());

        return new ResponseWrapper<>("success", "Loan transaction posted successfully", "Transactions posted to GL accounts");
    }
}
