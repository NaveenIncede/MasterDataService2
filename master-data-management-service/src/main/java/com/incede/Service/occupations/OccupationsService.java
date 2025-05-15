package com.incede.Service.occupations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.occupations.OccupationsDto;
import com.incede.Exception.BusinessException;
import com.incede.Repository.Occupations.OccupationsRepository;
import com.incede.Model.occupations.OccupationsModel;


//----------------------------------created by glodin joseph-------------------------------------//
//----------------------------------created date 8/8/2025 ---------------------------------------//
@Service
public class OccupationsService {
	
	public final OccupationsRepository occupationsRepository;
	
	public OccupationsService(OccupationsRepository occupationsRepository) {
		this.occupationsRepository = occupationsRepository;
	}
	
	// create occupation by post methode 
	@Transactional
 	public OccupationsDto createMasterDataOccupations(OccupationsDto occupationsDto) {
		
		
		  if (occupationsDto.getOccupationId() != null) {
		        throw new BusinessException("Occupation ID should not be present when creating a new record.");
		    }
		  
		 if (occupationsDto.getCreatedBy() == null) {
		        throw new BusinessException("Created by should not be null.");
		    }
		 
 	    boolean exists = occupationsRepository.existsByTenantIdAndOccupationName(
	        occupationsDto.getTenantId(), 
	        occupationsDto.getOccupationName()
	    );

	    if (exists) {
	        throw new BusinessException("Job title already exists for this tenant.");
	    }

 	    OccupationsModel occupation = new OccupationsModel();
	    occupation.setOccupationName(occupationsDto.getOccupationName());
	    occupation.setTenantId(occupationsDto.getTenantId());
	    occupation.setCreatedBy(occupationsDto.getCreatedBy());
	    

//	    occupation.setActive(true);

	    OccupationsModel savedEntity = occupationsRepository.save(occupation);

 	    OccupationsDto result = new OccupationsDto();
	    result.setOccupationName(savedEntity.getOccupationName());
	    result.setTenantId(savedEntity.getTenantId());
	    return result;
	}
 	
 	// update the occupation by put methode 
	@Transactional
	public OccupationsDto updateMasterDataOccupations(OccupationsDto occupationDto) {
	    OccupationsModel existingOccupation = occupationsRepository
	        .findByOccupationId(occupationDto.getOccupationId())
	        .orElseThrow(() -> new BusinessException("Occupation not found with id: " + occupationDto.getOccupationId()));

//	    existingOccupation.setActive(occupationDto.isActive());
	    existingOccupation.setOccupationName(occupationDto.getOccupationName());
	    existingOccupation.setTenantId(occupationDto.getTenantId());
	    existingOccupation.setUpdatedBy(occupationDto.getUpdatedBy());

	    OccupationsModel updatedOccupation = occupationsRepository.save(existingOccupation);

	    OccupationsDto result = new OccupationsDto();
	    result.setOccupationId(updatedOccupation.getOccupationId());
	    result.setTenantId(updatedOccupation.getTenantId());
	    result.setOccupationName(updatedOccupation.getOccupationName());
	    
//	    result.setActive(updatedOccupation.isActive());

	    return result;
	}


  // delete the occupation based on id
	@Transactional
	public void deleteOccupationalStatus(Long  occupationId) {
	    OccupationsModel existingOccupation = occupationsRepository.findById(occupationId)
	        .orElseThrow(() -> new BusinessException("Cannot delete. Occupational status not found with id: " + occupationId));

//	    existingOccupation.setActive(false);
	    existingOccupation.setIsDeleted(true);
	    occupationsRepository.save(existingOccupation);  
	}
	
   // get occupational details by id
	@Transactional
	public OccupationsDto getOccupationalDetailsById(Long occupationId) {
	    OccupationsModel existingOccupation = occupationsRepository.findByOccupationIdAndIsDeletedFalse(occupationId)
	        .orElseThrow(() -> new BusinessException("Occupational details not found with id: " + occupationId));

	    OccupationsDto occupationDto = new OccupationsDto();
	    occupationDto.setOccupationName(existingOccupation.getOccupationName());
	    occupationDto.setTenantId(existingOccupation.getTenantId());
	    
	    return occupationDto;
	}
	
	//get all details
	@Transactional
	public List<OccupationsDto> getAllActiveOccupationalDetails() {
	    List<OccupationsModel> activeOccupations = occupationsRepository.findAllByIsDeletedFalse();

	    if (activeOccupations.isEmpty()) {
	        throw new BusinessException("No active occupational details found.");
	    }

 	    List<OccupationsDto> occupationDtoList = activeOccupations.stream().map(occupation -> {
	        OccupationsDto dto = new OccupationsDto();
	        dto.setOccupationId(occupation.getOccupationId());
	        dto.setTenantId(occupation.getTenantId());
	        dto.setOccupationName(occupation.getOccupationName());
//	        dto.setActive(occupation.isActive());
	        return dto;
	    }).collect(Collectors.toList());

	    return occupationDtoList;
	}
	
	// get all details of occupational details using Tenant Id,
	
	@Transactional
	public List<OccupationsDto> getAllActiveOccupationalDetailsUsingTenant(Long TenantId) {
	    List<OccupationsModel> activeOccupations = occupationsRepository.findAllByTenantIdAndIsDeletedFalse(TenantId);

	    if (activeOccupations.isEmpty()) {
	        throw new BusinessException("No active occupational details found aganist tenentId." +TenantId);
	    }

 	    List<OccupationsDto> occupationDtoList = activeOccupations.stream().map(occupation -> {
	        OccupationsDto dto = new OccupationsDto();
	        dto.setOccupationId(occupation.getOccupationId());
	        dto.setTenantId(occupation.getTenantId());
	        dto.setOccupationName(occupation.getOccupationName());
//	        dto.setActive(occupation.isActive());
	        return dto;
	    }).collect(Collectors.toList());

	    return occupationDtoList;
	}



}
