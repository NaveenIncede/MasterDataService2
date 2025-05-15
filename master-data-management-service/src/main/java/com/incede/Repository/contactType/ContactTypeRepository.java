package com.incede.Repository.contactType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.incede.Model.contactType.ContactType;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Integer>{
	
	
	@Query("SELECT l FROM ContactType l WHERE l.contactTypeId = :contactTypeId AND l.isActive = true")
	ContactType findContactTypeById(Integer contactTypeId);

	boolean existsByTenantIdAndContactTypeAndContactTypeIdNot(Integer tenantId, String contactType,
			Integer contactTypeId);

	boolean existsByTenantIdAndContactType(Integer tenantId, String contactType);
	
	
	
}
