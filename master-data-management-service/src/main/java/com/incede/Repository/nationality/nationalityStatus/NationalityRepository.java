package com.incede.Repository.nationality.nationalityStatus;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.incede.Model.nationality.nationalityStatus.Nationality;


@Repository
public interface NationalityRepository extends JpaRepository<Nationality, Integer> {

	List<Nationality> findByIsActiveTrue();

	boolean existsByTenantIdAndNationality(Integer tenantId, String nationality);

	List<Nationality> findByTenantIdAndIsActiveTrue(Integer tenantId);

    // You can add custom queries here if needed, for example:
    // List<Nationality> findByIsActive(Boolean isActive);

}
