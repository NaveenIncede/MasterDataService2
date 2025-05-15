
package com.incede.Repository.deductionMasterRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incede.Model.deductionMaster.DeductionMaster;

public interface DeductionMasterRepository extends JpaRepository<DeductionMaster, Integer> {

	
	List<DeductionMaster> findByIsActiveTrue();

	boolean existsByTenantIdAndDeductionNameAndDeductionIdNot(Integer tenantId, String deductionName, long l);
}
