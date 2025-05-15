package com.incede.Repository.Occupations;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incede.Model.occupations.OccupationsModel;

public interface OccupationsRepository extends JpaRepository<OccupationsModel, Long>{

 
	boolean existsByTenantIdAndOccupationName(Long tenantId, String occupationName);

//	OccupationsModel findByTenantIdAndOccupationName(Long tenantId, String occupationName);

//	Optional<OccupationsModel> findByOccupationIdAndIsActiveTrue(Long occupationId);

///	List<OccupationsModel> findAllByIsActiveTrue();

	Optional<OccupationsModel> findByOccupationId(Long occupationId);

	Optional<OccupationsModel> findByOccupationIdAndIsDeletedFalse(Long occupationId);

	List<OccupationsModel> findAllByIsDeletedFalse();

	List<OccupationsModel> findAllByTenantIdAndIsDeletedFalse(Long tenantId);

//	List<OccupationsModel> findAllByTenantIdAndIsActiveTrue(Long tenantId);
	



}
