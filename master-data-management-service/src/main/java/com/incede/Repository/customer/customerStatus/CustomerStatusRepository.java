package com.incede.Repository.customer.customerStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incede.Model.customer.customerStatus.CustomerStatus;

@Repository
public interface CustomerStatusRepository extends JpaRepository<CustomerStatus, Integer> {

    CustomerStatus findByTenantIdAndStatusName(Integer tenantId, String statusName);
    boolean existsByTenantIdAndStatusName(Integer tenantId, String statusName);



}