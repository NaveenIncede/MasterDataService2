package com.incede.Service.scheme;

import com.incede.Dto.scheme.SchemeSpecificMasterDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.configvaluetype.ConfigValueType;
import com.incede.Model.scheme.SchemeSpecificMaster;
import com.incede.Repository.scheme.SchemeSpecificMasterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

	@Service
	public class SchemeSpecificMasterService {

	    private final SchemeSpecificMasterRepository repository;

	    public SchemeSpecificMasterService(SchemeSpecificMasterRepository repository) {
	        this.repository = repository;
	    }

	    private SchemeSpecificMasterDto toDto(SchemeSpecificMaster entity) {
	        SchemeSpecificMasterDto dto = new SchemeSpecificMasterDto();
	        dto.setSchemeSpecificId(entity.getSchemeSpecificId());
	        dto.setTenantId(entity.getTenantId());
	        dto.setSchemeId(entity.getSchemeId());
	        dto.setConfigKey(entity.getConfigKey());
	        dto.setConfigValue(entity.getConfigValue());
	        dto.setConfigValueType(entity.getConfigValueType());
	        dto.setIsActive(entity.getIsActive());
	        dto.setIdentity(entity.getIdentity());
	        dto.setCreatedBy(entity.getCreatedBy());
	        dto.setCreatedAt(entity.getCreatedAt());
	        dto.setUpdatedBy(entity.getUpdatedBy());
	        dto.setUpdatedAt(entity.getUpdatedAt());
	        return dto;
	    }

	    private SchemeSpecificMaster toEntity(SchemeSpecificMasterDto dto) {
	        SchemeSpecificMaster entity = new SchemeSpecificMaster();
	        entity.setTenantId(dto.getTenantId());
	        entity.setSchemeId(dto.getSchemeId());
	        entity.setConfigKey(dto.getConfigKey());
	        entity.setConfigValue(dto.getConfigValue());
	        entity.setConfigValueType(dto.getConfigValueType());
	        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
	        entity.setCreatedBy(dto.getCreatedBy());
	        entity.setIdentity(dto.getIdentity() != null ? dto.getIdentity() : UUID.randomUUID());
	        entity.setCreatedAt(LocalDateTime.now());
	        return entity;
	    }

	    @Transactional(readOnly = true)
	    public List<SchemeSpecificMasterDto> getAllSchemeConfigs() {
	        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
	    }

	    @Transactional(readOnly = true)
	    public SchemeSpecificMaster getSchemeConfigById(Integer id) {
	        return repository.findById(id)
	                .orElseThrow(() -> new BusinessException("Scheme configuration not found with ID: " + id));
	    }

	    //Create
	    @Transactional
	    public SchemeSpecificMaster createSchemeConfig(SchemeSpecificMasterDto dto) {
	    	validateMandatoryFields(dto);
	        boolean exists = repository.existsBySchemeIdAndConfigKey(dto.getSchemeId(), dto.getConfigKey());
	        if (exists) {
	            throw new BusinessException("Configuration key already exists for this scheme.");
	        }
	        
	        validateConfigValue(dto.getConfigValue(), dto.getConfigValueType());

	        SchemeSpecificMaster entity = toEntity(dto);
	        return repository.save(entity);
	    }

	    //Update 
	    @Transactional
	    public SchemeSpecificMaster updateSchemeConfig(Integer id, SchemeSpecificMasterDto dto) {
	        SchemeSpecificMaster existing = repository.findById(id)
	                .orElseThrow(() -> new BusinessException("Cannot update. Configuration not found with ID: " + id));

	        if (!existing.getConfigKey().equalsIgnoreCase(dto.getConfigKey())) {
	            boolean exists = repository.existsBySchemeIdAndConfigKey(dto.getSchemeId(), dto.getConfigKey());
	            if (exists) {
	                throw new BusinessException("Configuration key already exists for this scheme.");
	            }
	        }
	        
	        validateConfigValue(dto.getConfigValue(), dto.getConfigValueType());

	        existing.setConfigKey(dto.getConfigKey());
	        existing.setConfigValue(dto.getConfigValue());
	        existing.setConfigValueType(dto.getConfigValueType());
	        existing.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
	        existing.setUpdatedBy(dto.getUpdatedBy());
	        existing.setUpdatedAt(LocalDateTime.now());

	        return repository.save(existing);
	    }
	    
	    //get Active Scheme
	    public List<SchemeSpecificMasterDto> getAllActiveSchemeConfigs() {
	        List<SchemeSpecificMaster> activeConfigs = repository.findByIsActiveTrue();
	        List<SchemeSpecificMasterDto> dtoList = new ArrayList<>();

	        for (SchemeSpecificMaster entity : activeConfigs) {
	            dtoList.add(toDto(entity));
	        }

	        return dtoList;
	    }
	    
	    //getbytenantId
	    @Transactional(readOnly = true)
	    public List<SchemeSpecificMasterDto> getSchemeConfigsByTenantId(Integer tenantId) {
	        List<SchemeSpecificMaster> configs = repository.findByTenantId(tenantId);
	        return configs.stream().map(this::toDto).collect(Collectors.toList());
	    }
	    	   
	    //get by SchemeId
	    @Transactional(readOnly = true)
	    public List<SchemeSpecificMasterDto> getSchemeConfigsBySchemeId(Integer schemeId) {
	        List<SchemeSpecificMaster> configs = repository.findBySchemeId(schemeId);
	        List<SchemeSpecificMasterDto> result = new ArrayList<>();

	        for (SchemeSpecificMaster config : configs) {
	            SchemeSpecificMasterDto dto = new SchemeSpecificMasterDto();
	            dto.setConfigKey(config.getConfigKey());
	            dto.setConfigValue(config.getConfigValue());
	            dto.setConfigValueType(config.getConfigValueType());
	            result.add(dto);
	        }

	        return result;
	    }

	    
	    
	    
	    

	    //delete by id 
	    @Transactional
	    public void deleteSchemeConfig(Integer id) {
	        SchemeSpecificMaster entity = repository.findById(id)
	                .orElseThrow(() -> new BusinessException("Cannot delete. Configuration not found with ID: " + id));

	        entity.setIsActive(false);
	        entity.setUpdatedAt(LocalDateTime.now());

	        repository.save(entity);
	    }
	    
	    
	    
	  //VALIDATION  OF CONFIG VALUES
	    private void validateConfigValue(String value, String valueType) {
	        if (value == null || valueType == null) {
	            throw new BusinessException("Config value and type must not be null");
	        }

	        switch (valueType.toLowerCase()) {
	            case ConfigValueType.STRING:
	             
	                break;

	            case ConfigValueType.DECIMAL:
	                try {
	                    new BigDecimal(value);
	                } catch (NumberFormatException e) {
	                    throw new BusinessException("Invalid decimal value: " + value);
	                }
	                break;

	            case ConfigValueType.DATE:
	                try {
	                    LocalDate.parse(value, DateTimeFormatter.ISO_DATE); // Format: YYYY-MM-DD
	                } catch (DateTimeParseException e) {
	                    throw new BusinessException("Invalid date format. Expected yyyy-MM-dd: " + value);
	                }
	                break;

	            case ConfigValueType.BOOLEAN:
	                if (!(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
	                    throw new BusinessException("Invalid boolean value. Must be TRUE or FALSE: " + value);
	                }
	                break;

	            default:
	                throw new BusinessException("Unsupported config value type: " + valueType);
	        }
	    }
	    
	    //NULL CHECK EXCEPTIONS---------
	    private void validateMandatoryFields(SchemeSpecificMasterDto dto) {
	        StringBuilder errorMessages = new StringBuilder();

	        if (dto.getTenantId() == null) {
	            errorMessages.append("Tenant ID is mandatory. ");
	        }
	        if (dto.getSchemeId() == null) {
	            errorMessages.append("Scheme ID is mandatory. ");
	        }
	        if (dto.getConfigKey() == null || dto.getConfigKey().trim().isEmpty()) {
	            errorMessages.append("Config Key is mandatory. ");
	        }
	        if (dto.getConfigValue() == null || dto.getConfigValue().trim().isEmpty()) {
	            errorMessages.append("Config Value is mandatory. ");
	        }
	        if (dto.getConfigValueType() == null || dto.getConfigValueType().trim().isEmpty()) {
	            errorMessages.append("Config Value Type is mandatory. ");
	        }
	        if (dto.getCreatedBy() == null) {
	            errorMessages.append("Created By is mandatory. ");
	        }

	        if (errorMessages.length() > 0) {
	            throw new BusinessException(errorMessages.toString().trim());
	        }
	    }

	    
	    

	}



