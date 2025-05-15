package com.incede.Service.liabilityInterest.liabilityInterestRateMaster;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;


import com.incede.Dto.liabilityInterest.liabilityInterestRateMaster.LiabilityInterestRateDto;
import com.incede.Exception.BusinessException;
import com.incede.Exception.GlobalExceptionHandler;
import com.incede.Model.liabityInterest.liabilityInterestMaster.LiabilityInterestRateMaster;
import com.incede.Repository.liabilityInterest.liabilityInterestMaster.LiabilityInterestRateMasterRepository;

import jakarta.transaction.Transactional;

@Service
public class LiabilityInterestRateService {

    
	    private final GlobalExceptionHandler globalExceptionHandler;

		
			
			private final LiabilityInterestRateMasterRepository liabilityInterestRateMasterRepository;
			
			public LiabilityInterestRateService(LiabilityInterestRateMasterRepository liabilityInterestRateMasterRepository , GlobalExceptionHandler globalExceptionHandler)
			{
				
				this.liabilityInterestRateMasterRepository=liabilityInterestRateMasterRepository;
				
				this.globalExceptionHandler = globalExceptionHandler;
				
			}


			
			public Long createLiabilityInterest(LiabilityInterestRateDto interestDto) {
			    // Step 1: Basic validation
			    if (interestDto.getEffectiveFrom().compareTo(interestDto.getEffectiveUpto()) >= 0) {
			        throw new BusinessException("'effectiveFrom' must be less than 'effectiveUpto'");
			    }

			    // Step 2: Overlap check (based on user story)
			    boolean isOverlap = liabilityInterestRateMasterRepository.existsByOverlappingDatesAndSchemeId(
			        interestDto.getSchemeId(),
			        interestDto.getEffectiveFrom(),
			        interestDto.getEffectiveUpto(),
			        interestDto.getInterestRateId() != null && interestDto.getInterestRateId() != 0 ? interestDto.getInterestRateId() : null
			    );

			    if (isOverlap) {
			        throw new BusinessException("An interest rate period already exists for this scheme that overlaps with the provided date range.");
			    }

			    // Step 3: Create or Update entity
			    LiabilityInterestRateMaster liabilityInterestEntity;

			    if (interestDto.getInterestRateId() == null || interestDto.getInterestRateId() == 0) {
			        liabilityInterestEntity = new LiabilityInterestRateMaster(); // New entry
			        liabilityInterestEntity.setCreatedBy(interestDto.getCreatedBy());
			    } else {
			        liabilityInterestEntity = liabilityInterestRateMasterRepository.findByInterestRateId(interestDto.getInterestRateId());
			        if (liabilityInterestEntity == null) {
			        	
			            throw new BusinessException("No LiabilityInterest found with the provided ID");
			        }
			        liabilityInterestEntity.setUpdatedBy(interestDto.getInterestRateId());
			    }

			    // Step 4: Map DTO to Entity
			    dtoToEntity(interestDto, liabilityInterestEntity);

			    // Step 5: Save and return
			    LiabilityInterestRateMaster saved = liabilityInterestRateMasterRepository.save(liabilityInterestEntity);
			    return saved.getInterestRateId().longValue();
			}

			@Transactional
			public int archiveExpiredRates() {
			    return liabilityInterestRateMasterRepository.archiveExpiredInterestRates();
			}
			
	
		//getBYId
			    public LiabilityInterestRateDto getLiabilityInterestById(Integer id) {
			        LiabilityInterestRateMaster entity = liabilityInterestRateMasterRepository.findByInterestRateId(id);
			            //.orElseThrow(() -> 
			        if(entity==null) {
			        	throw  new BusinessException("Liability interest with ID: " + id+"is either deleted or not found");
			        }
			        return mapToDto(entity);
			    }

			    
		//getALL
			    public List<LiabilityInterestRateDto> getAllLiabilityInterests() {
			        List<LiabilityInterestRateMaster> entities = liabilityInterestRateMasterRepository.findAll();
			        return entities.stream()
			                       .map(this::mapToDto)
			                       .collect(Collectors.toList());
			    }
			    
			    
	  
//getByIs active
			    public List<LiabilityInterestRateDto> getLiabilityInterestByIsActive() {
			        List<LiabilityInterestRateMaster> entities = liabilityInterestRateMasterRepository.findByIsActive();
			        
			        if (entities == null || entities.isEmpty()) {
			            throw new BusinessException("No active Liability interest rates available");
			        }

			        return entities.stream()
			                       .map(this::mapToDto)
			                       .collect(Collectors.toList());
			    }
			    
			 //get currently active ( based on validUpTo < today )
			    public List<LiabilityInterestRateDto> getCurrentlyValidInterestRates() {
			        List<LiabilityInterestRateMaster> list = liabilityInterestRateMasterRepository.findCurrentlyValidInterestRates();
			      
			       System.out.println("list");

			        if (list.isEmpty()) {
			            throw new BusinessException("No currently valid interest rates found");
			        }
			        return list.stream().map(this::mapToDto).collect(Collectors.toList());
			    }

			    //get Expierd interest Rate
			    public List<LiabilityInterestRateDto> getExpiredInterestRates() {
			        List<LiabilityInterestRateMaster> list = liabilityInterestRateMasterRepository. findByIsactiveTrueAndIsArchivedTrue();
			    
			        if (list.isEmpty()) {
			            throw new BusinessException("No expired interest rates found");
			        }
			 
			        
			        return list.stream().map(this::mapToDto).collect(Collectors.toList());
			    }
			    
			 // Soft DELETE by ID 
			    public void deleteLiabilityInterest(Integer id) {
			        Optional<LiabilityInterestRateMaster> optional = liabilityInterestRateMasterRepository.findById(id);
			        if (optional.isPresent()) {
			            LiabilityInterestRateMaster entity = optional.get();
			            entity.setIsactive(false); // soft delete
			           liabilityInterestRateMasterRepository.save(entity);
			        } 
			    }
			    
			    //
			    
			    //mapping Dto -> entity
				private LiabilityInterestRateMaster dtoToEntity(LiabilityInterestRateDto dto, LiabilityInterestRateMaster entity) {
				    
					
				validateNotNull(dto.getSchemeId(),"Scheme ID cant be null");
				validateNotNull(dto.getInterestRate(),"InterestRate cant be null");
				validateNotNull(dto.getInterestDescription(),"InterestDescription  cant be null");
				validateNotNull(dto.getPeriodType(),"Period Type cant be null");
				validateNotNull(dto.getCreatedBy(),"createdBy  cant be null");
					
					
					entity.setSchemeId(dto.getSchemeId());
				    entity.setInterestRate(dto.getInterestRate());
				    entity.setInterestDescription(dto.getInterestDescription());
				    entity.setFromPeriod(dto.getFromPeriod());
				    entity.setUptoPeriod(dto.getUptoPeriod());
				    entity.setPeriodType(dto.getPeriodType());
				    entity.setEffectiveFrom(dto.getEffectiveFrom());
				    entity.setEffectiveUpto(dto.getEffectiveUpto());
				    entity.setCreatedBy(dto.getCreatedBy());
				    entity.setIsactive(true);
				    return entity;
				    }
			    
			    
			    
	   private void validateNotNull(Object field, String message) {
				if(field == null) {
					throw new BusinessException(message);
				}
				}

	//entity to Dto
			    private LiabilityInterestRateDto mapToDto(LiabilityInterestRateMaster entity) {
			        LiabilityInterestRateDto dto = new LiabilityInterestRateDto();
			        dto.setInterestRateId(entity.getInterestRateId());
			        dto.setSchemeId(entity.getSchemeId());
			        dto.setInterestRate(entity.getInterestRate());
			        dto.setInterestDescription(entity.getInterestDescription());
			        dto.setFromPeriod(entity.getFromPeriod());
			        dto.setUptoPeriod(entity.getUptoPeriod());
			        dto.setPeriodType(entity.getPeriodType());
			        dto.setEffectiveFrom(entity.getEffectiveFrom());
			        dto.setEffectiveUpto(entity.getEffectiveUpto());
			        dto.setIsactive(entity.isIsactive());
			        dto.setArchived(entity.getIsArchived());
			        dto.setCreatedBy(entity.getCreatedBy());
			        return dto;
			    }

}