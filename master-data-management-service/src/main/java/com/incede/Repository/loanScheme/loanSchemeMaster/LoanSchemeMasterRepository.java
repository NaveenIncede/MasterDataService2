package com.incede.Repository.loanScheme.loanSchemeMaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incede.Model.loanScheme.loanSchemeMaster.LoanSchemeMaster;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanSchemeMasterRepository extends JpaRepository<LoanSchemeMaster, Integer> {

    List<LoanSchemeMaster> findAllByIsActiveTrue();


	Optional<LoanSchemeMaster> findBySchemeIdAndIsActiveTrue(Integer id);


	List<LoanSchemeMaster> findByTenantIdAndIsActiveTrue(Integer tenantId);


	List<LoanSchemeMaster> findByProductIdAndIsActiveTrue(Integer productId);


	boolean existsBySchemeCode(String schemeCode);


}
