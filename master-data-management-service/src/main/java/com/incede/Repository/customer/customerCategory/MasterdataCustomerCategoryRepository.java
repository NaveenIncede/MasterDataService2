package com.incede.Repository.customer.customerCategory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.incede.Model.customer.customerCategory.MasterdataCustomerCategory;

@Repository
public interface MasterdataCustomerCategoryRepository extends JpaRepository<MasterdataCustomerCategory, Integer> {
	
	List<MasterdataCustomerCategory> findByIsActiveTrue();

   List<MasterdataCustomerCategory> findByTenantId(Integer tenantId);
   
   MasterdataCustomerCategory findByCategoryIdAndTenantId(Integer categoryId, Integer tenantId);
   
   }
