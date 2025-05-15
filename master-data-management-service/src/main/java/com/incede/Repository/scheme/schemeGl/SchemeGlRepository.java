package com.incede.Repository.scheme.schemeGl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.incede.Model.scheme.schemeGl.SchemeGl;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchemeGlRepository extends JpaRepository<SchemeGl, Integer> {

    // Method to fetch all active (non-deleted) projects
    List<SchemeGl> findByIsActiveTrue();

    List<SchemeGl> findByTenantIdAndIsActiveTrue(Integer tenantId);

    boolean existsBySchemeIdAndGlAccountTypeAndGlConfigIdNot(Integer schemeId, String glAccountType, Integer glConfigId);

	Optional<SchemeGl> findBySchemeIdAndGlAccountTypeAndIsActiveTrue(Integer schemeId, String glAccountType);

	List<SchemeGl> findByGlAccountIdAndIsActiveTrue(Integer glAccountId);


    // You can add other query methods as needed for additional functionality.
}
