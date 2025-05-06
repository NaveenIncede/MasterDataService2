package com.incede.Repository.ornament.ornamentmaster;


import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incede.Model.ornament.ornamentmaster.OrnamentMaster;

public interface OrnamentMasterRepository extends JpaRepository<OrnamentMaster, Integer> {


    boolean existsByTenantIdAndOrnamentNameIgnoreCase(Integer tenantId, String ornamentName);

    boolean existsByTenantIdAndOrnamentNameIgnoreCaseAndIsDeletedFalse(Integer tenantId, String ornamentName);

    Optional<OrnamentMaster> findByIdentity(UUID identity);
}
