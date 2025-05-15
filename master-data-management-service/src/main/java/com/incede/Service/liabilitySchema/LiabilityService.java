package com.incede.Service.liabilitySchema;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.liabilitySchema.LiabilityDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.liabilityScheme.LiabilityModel;
import com.incede.Repository.liabilitySchema.LiabilityRepository;


//--------------------------- created by Glodin joseph----------------------------//
//----------------------------08/05/2025------------------------------------------//

 
@Service
public class LiabilityService {
	
	public final LiabilityRepository liabilityRepository;
	
	public LiabilityService(LiabilityRepository liabilityRepository) {
		this.liabilityRepository = liabilityRepository;
	}
	
	//-------------------create and update the fields using post methode--------------------------------------//
	@Transactional
	public LiabilityDto createOrUpdateLiabilitySchemeSpecificMasterDetails(LiabilityDto liabilityDto) {
	    if (liabilityDto.getCreatedBy() == null) {
	        throw new BusinessException("Created by should not be null.");
	    }
	    try {
	        if (liabilityDto.getSchemeSpecificId() != null && liabilityDto.getSchemeSpecificId() != 0) {
	            LiabilityModel existingLiabilityModel = liabilityRepository
	                    .findExistingLiabilitySchemeSpecificMasterDetails(liabilityDto.getSchemeSpecificId());

	            LiabilityModel duplicate = liabilityRepository
	                    .findByConfigKeyAndSchemeId(liabilityDto.getConfigKey(), liabilityDto.getSchemeId());

	            if (duplicate != null && !duplicate.getSchemeSpecificId().equals(liabilityDto.getSchemeSpecificId())) {
	                throw new BusinessException("Config_key and Scheme_id already exist for another record.");
	            }

	            if (existingLiabilityModel != null) {
//	                if (liabilityDto.getCreatedBy() == null) {
//	                    throw new BusinessException("Created by should not be null.");
//	                }

	                existingLiabilityModel.setConfigKey(liabilityDto.getConfigKey());
	                existingLiabilityModel.setConfigValue(liabilityDto.getConfigValue());
	                existingLiabilityModel.setConfigValueType(liabilityDto.getConfigValueType());
	                existingLiabilityModel.setSchemeId(liabilityDto.getSchemeId());
	                existingLiabilityModel.setCreatedBy(liabilityDto.getCreatedBy());
	                existingLiabilityModel.setUpdatedBy(liabilityDto.getUpdatedBy());

	                liabilityRepository.save(existingLiabilityModel);
	            }
	        } else {
	            LiabilityModel duplicate = liabilityRepository
	                    .findByConfigKeyAndSchemeId(liabilityDto.getConfigKey(), liabilityDto.getSchemeId());

	            if (duplicate != null) {
	                throw new BusinessException("Config_key and Scheme_id already exist.");
	            }

	            LiabilityModel newLiabilityModel = new LiabilityModel();
	            newLiabilityModel.setConfigKey(liabilityDto.getConfigKey());
	            newLiabilityModel.setConfigValue(liabilityDto.getConfigValue());
	            newLiabilityModel.setConfigValueType(liabilityDto.getConfigValueType());
	            newLiabilityModel.setSchemeId(liabilityDto.getSchemeId());
	            newLiabilityModel.setCreatedBy(liabilityDto.getCreatedBy());
	            newLiabilityModel.setUpdatedBy(liabilityDto.getUpdatedBy());
//	            newLiabilityModel.setUpdatedAt(liabilityDto.getUpdatedAt());

	            liabilityRepository.save(newLiabilityModel);
	        }

	        return liabilityDto;

	    } catch (DataIntegrityViolationException e) {
	        if (e.getMessage().contains("created_by")) {
	            throw new BusinessException("Created by should not be null.");
	        }
	        throw e; // rethrow the exception if it's not related to 'created_by'
	    }
	}

	        
	        	
	        

	//-------------------------soft deleting the fields using Post methode-------------------//
	 
	@Transactional
	public LiabilityModel softDeletingLiabilitySchemeSpecificMasterDetails(Long schemeSpecificId) {
	    LiabilityModel findModelToDelete = liabilityRepository
	        .findLiabilityModelForSoftDeleting(schemeSpecificId);
	    if (findModelToDelete == null) {
	        throw new BusinessException("Cannot delete. Liability record not found with id: " + schemeSpecificId);
	    }
	    findModelToDelete.setIsDeleted(true);
	    return liabilityRepository.save(findModelToDelete);
	}


	
	//------------------------get by id--------------------------------------------------------//
	@Transactional
	public LiabilityDto getLiabilitySchemeSpecificMasterDetailById(Long schemeSpecificId) {
	    LiabilityModel findModelBySchemeSpecifiId = liabilityRepository.findLiabilityModelForSoftDeleting(schemeSpecificId);

	    if (findModelBySchemeSpecifiId == null) {
	        throw new BusinessException("No active liability details found with id: " + schemeSpecificId);
	    }

	    LiabilityDto newLiabilityDTO = new LiabilityDto();
	    newLiabilityDTO.setConfigKey(findModelBySchemeSpecifiId.getConfigKey());
	    newLiabilityDTO.setConfigValue(findModelBySchemeSpecifiId.getConfigValue());
	    newLiabilityDTO.setConfigValueType(findModelBySchemeSpecifiId.getConfigValueType());
	    newLiabilityDTO.setSchemeId(findModelBySchemeSpecifiId.getSchemeId());
	    // newLiabilityDTO.setDelete(findModelBySchemeSpecifiId.isDelete());

	    return newLiabilityDTO;
	}

	
	//---------------------------------get all liability details -----------------------------//
	@Transactional
	public List<LiabilityDto> getAllLiabilitySchemeSpecificMasterDetail() {
	    List<LiabilityDto> liabilityDTOList = new ArrayList<>();
	    
	    try {
 	        List<LiabilityModel> findAllModelBySchemeSpecifiId = liabilityRepository.findAllLiabilityModel();
	        System.out.println("-----------------"+findAllModelBySchemeSpecifiId);
 	        if (findAllModelBySchemeSpecifiId != null && !findAllModelBySchemeSpecifiId.isEmpty()) {
	            
 	            for (LiabilityModel l : findAllModelBySchemeSpecifiId) {
	                if (l != null) {
	                    LiabilityDto newLiabilityDTO = new LiabilityDto();
	                    newLiabilityDTO.setConfigKey(l.getConfigKey());
	                    newLiabilityDTO.setConfigValue(l.getConfigValue());
	                    newLiabilityDTO.setConfigValueType(l.getConfigValueType());
	                    newLiabilityDTO.setSchemeId(l.getSchemeId());
//	                    newLiabilityDTO.setDelete(l.isDelete());
	                    
 	                    liabilityDTOList.add(newLiabilityDTO);
	                }
	            }
	        }
	    } catch (Exception e) {
 	        e.printStackTrace();  
	    }
	    
 	    return liabilityDTOList;
	}


// deactivate specific configuration when Schema_specific_id(PK) is passed
	
	public String deactivateSpecificConfiguration(LiabilityDto liabilityDto) {
	    LiabilityModel existingLiabilityModel = liabilityRepository
	        .findExistingLiabilityToDeactivateSpecificConfigurationSchemeSpecificMasterDetails(liabilityDto.getSchemeSpecificId());

	    if (existingLiabilityModel == null) {
	        throw new BusinessException("Configuration not found for ID: " + liabilityDto.getSchemeSpecificId());
	    }

 	    existingLiabilityModel.setIsDeleted(liabilityDto.isDeactivateSpecificConfiguration());

 	    liabilityRepository.save(existingLiabilityModel);

 	    if (liabilityDto.isDeactivateSpecificConfiguration()) {
	        return "The configuration with id: " + liabilityDto.getSchemeSpecificId() + " has been deactivated successfully.";
	    } else {
	        return "The configuration with id: " + liabilityDto.getSchemeSpecificId() + " has been activated successfully.";
	    }
	}

	
	// get details by scheme_id (optional for future reference)

	public List<LiabilityDto> getLiabilitySchemeSpecificMasterDetailsBySchemeId(Long schemeId) {
	    List<LiabilityModel> existingLiabilityModels = liabilityRepository.findBySchemeId(schemeId);

	    if (existingLiabilityModels == null || existingLiabilityModels.isEmpty()) {
	        throw new BusinessException("Details not found for Scheme ID: " + schemeId);
	    }

	    List<LiabilityDto> dtoList = new ArrayList<>();
	    for (LiabilityModel model : existingLiabilityModels) {
	        if (model != null) {
	            LiabilityDto dto = new LiabilityDto();
	            dto.setConfigKey(model.getConfigKey());
	            dto.setConfigValue(model.getConfigValue());
	            dto.setConfigValueType(model.getConfigValueType());
	            dto.setSchemeId(model.getSchemeId());
	            dtoList.add(dto);
	        }
	    }

	    if (dtoList.isEmpty()) {
	        throw new BusinessException("No valid LiabilityModel entries found for Scheme ID: " + schemeId);
	    }

	    return dtoList;
	}

	
}




