package com.incede.Repository.liabilityInterest.liabilityInterestMaster;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.incede.Model.liabityInterest.liabilityInterestMaster.LiabilityInterestRateMaster;


public interface LiabilityInterestRateMasterRepository extends JpaRepository<LiabilityInterestRateMaster, Integer>{
	@Query("SELECT l FROM LiabilityInterestRateMaster l WHERE l.interestRateId = :interestRateId AND l.isactive = true")
	LiabilityInterestRateMaster findByInterestRateId(Integer interestRateId);
	
	@Query("SELECT ls FROM LiabilityInterestRateMaster ls WHERE ls.isactive = true")
	List<LiabilityInterestRateMaster> findByIsActive();
	
	
	Optional<LiabilityInterestRateMaster> findById(Integer id);


    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END " +
            "FROM LiabilityInterestRateMaster l " +
            "WHERE l.schemeId = :schemeId " +
            "AND (:interestRateId IS NULL OR l.interestRateId <> :interestRateId) " +
            "AND l.effectiveFrom <= :effectiveUpto " +
            "AND (l.effectiveUpto IS NULL OR l.effectiveUpto >= :effectiveFrom)")
     boolean existsByOverlappingDatesAndSchemeId(
         @Param("schemeId") Integer schemeId,
         @Param("effectiveFrom") LocalDate effectiveFrom,
         @Param("effectiveUpto") LocalDate effectiveUpto,
         @Param("interestRateId") Integer interestRateId
     );
    
    @Query("SELECT l FROM LiabilityInterestRateMaster l WHERE l.isactive = true AND (l.effectiveUpto IS NULL OR l.effectiveUpto >= CURRENT_DATE)")
    List<LiabilityInterestRateMaster> findCurrentlyValidInterestRates();

    
    
    @Modifying
    @Query("UPDATE LiabilityInterestRateMaster a SET a.isArchived = true WHERE a.isactive = true AND a.effectiveUpto < CURRENT_DATE")
    int archiveExpiredInterestRates();

	List<LiabilityInterestRateMaster> findByIsactiveTrueAndIsArchivedTrue();
     

}
