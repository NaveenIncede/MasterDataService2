
package com.incede.Repository.schemeinterestrate;

import com.incede.Model.schemeinterestrate.SchemeInterestRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SchemeInterestRateRepository extends JpaRepository<SchemeInterestRate, Integer> {

    // Check existence of entry by schemeId and effectiveFrom date
    boolean existsBySchemeIdAndEffectiveFrom(Integer schemeId, LocalDate effectiveFrom);

    // Get all entries where isActive is true
    List<SchemeInterestRate> findByIsActiveTrue();

    // Get all interest rates by tenantId
    List<SchemeInterestRate> findByTenantId(Integer tenantId);
    
    //Get scheme interest slab
    List<SchemeInterestRate> findBySchemeIdAndPeriodTypeAndIsActiveTrue(Integer schemeId, String periodType);
    
    
}

