
package com.incede.Repository.scheme;

import com.incede.Model.scheme.SchemeSpecificMaster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchemeSpecificMasterRepository extends JpaRepository<SchemeSpecificMaster, Integer> {
	
	    boolean existsByTenantIdAndConfigKey(Integer tenantId, String configKey);
	    
	    boolean existsBySchemeIdAndConfigKey(Integer tenantId, String configKey);
	   
	    List<SchemeSpecificMaster> findByIsActiveTrue();
	    
	    List<SchemeSpecificMaster> findByTenantId(Integer tenantId);

        List <SchemeSpecificMaster> findBySchemeId(Integer schemeId);
	}



