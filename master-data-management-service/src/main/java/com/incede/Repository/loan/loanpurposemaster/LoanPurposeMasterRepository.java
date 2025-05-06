package com.incede.Repository.loan.loanpurposemaster;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incede.Model.loan.loanpurposemaster.LoanPurposeMaster;

public interface LoanPurposeMasterRepository extends JpaRepository<LoanPurposeMaster, Integer> {
    boolean existsByTenantIdAndPurposeName(Integer tenantId, String purposeName);
    Optional<LoanPurposeMaster> findByIdentity(UUID identity);
}
