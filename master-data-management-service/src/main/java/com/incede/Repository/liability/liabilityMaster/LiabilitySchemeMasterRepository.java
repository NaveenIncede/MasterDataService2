package com.incede.Repository.liability.liabilityMaster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.incede.Model.liability.liabilityMaster.LiabilitySchemeMaster;

@Repository
public interface LiabilitySchemeMasterRepository extends JpaRepository<LiabilitySchemeMaster, Integer> {

	boolean existsBySchemeCode(String schemeCode);

	List<LiabilitySchemeMaster> findByProductIdAndIsActiveTrue(Integer productId);

    // Custom query methods can go here, e.g., find by scheme code
}
