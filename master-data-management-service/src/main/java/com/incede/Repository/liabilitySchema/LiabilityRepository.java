package com.incede.Repository.liabilitySchema;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.incede.Model.liabilityScheme.LiabilityModel;


//--------------------------- created by Glodin joseph----------------------------//
//----------------------------08/05/2025------------------------------------------//


public interface LiabilityRepository extends JpaRepository<LiabilityModel, Long> {

    @Query("SELECT l FROM LiabilityModel l WHERE l.schemeSpecificId = :schemeSpecificId")
	LiabilityModel findExistingLiabilitySchemeSpecificMasterDetails(@Param("schemeSpecificId") Long schemeSpecificId);

    @Query("SELECT l FROM LiabilityModel l WHERE l.schemeSpecificId = :schemeSpecificId AND l.isDeleted = false")
    LiabilityModel findLiabilityModelForSoftDeleting(@Param("schemeSpecificId") Long schemeSpecificId);

    @Query("SELECT l FROM LiabilityModel l WHERE l.isDeleted = false")
	List<LiabilityModel> findAllLiabilityModel();

	LiabilityModel findByConfigKeyAndSchemeId(String configKey, Long schemeId);

	 @Query("SELECT l FROM LiabilityModel l WHERE l.schemeSpecificId = :schemeSpecificId ")
	LiabilityModel findExistingLiabilityToDeactivateSpecificConfigurationSchemeSpecificMasterDetails(
			@Param("schemeSpecificId") Long schemeSpecificId);

	List<LiabilityModel> findBySchemeId(Long schemeId);

	 


}
