package com.incede.Service.contactType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.incede.Dto.contactType.ContactTypeDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.contactType.ContactType;
import com.incede.Repository.contactType.ContactTypeRepository;

@Service
public class ContactTypeService {

    private final ContactTypeRepository contactTypeRepository;

    public ContactTypeService(ContactTypeRepository contactTypeRepository) {
        this.contactTypeRepository = contactTypeRepository;
    }

    public Long createOrUpdateContactType(ContactTypeDto contactTypeDto) {
        ContactType contactTypeEntity;

        if (contactTypeDto.getContactTypeId() == null || contactTypeDto.getContactTypeId() == 0) {
            // Check for duplicate before creating
            boolean exists = contactTypeRepository.existsByTenantIdAndContactType(
                    contactTypeDto.getTenantId(), contactTypeDto.getContactType());
            if (exists) {
                throw new BusinessException("Contact Type already exists for this tenant.");
            }
            contactTypeEntity = new ContactType(); // Create new
        } else {
            contactTypeEntity = contactTypeRepository.findContactTypeById(contactTypeDto.getContactTypeId());
            if (contactTypeEntity == null) {
                throw new BusinessException("No ContactType found with the provided ID");
            }

            // Check if new tenantId + contactType would cause a conflict
            boolean isDuplicate = contactTypeRepository.existsByTenantIdAndContactTypeAndContactTypeIdNot(
                    contactTypeDto.getTenantId(), contactTypeDto.getContactType(), contactTypeDto.getContactTypeId());
            if (isDuplicate) {
                throw new BusinessException("Cant Update!Contact Type already exists for this tenant! Try New ContactType");
            }

            contactTypeEntity.setUpdatedBy(contactTypeDto.getContactTypeId()); // updatedBY will be userId
        }

        dtoToEntity(contactTypeDto, contactTypeEntity);
        ContactType saved = contactTypeRepository.save(contactTypeEntity);
        return saved.getContactTypeId().longValue();
    }

    // Get by ID
    public ContactTypeDto getContactTypeById(Long id) {
        ContactType entity = contactTypeRepository.findById(id.intValue())
                .orElseThrow(() -> new BusinessException("ContactType with ID: " + id + " is not found"));

        return mapToDto(entity);
    }

    // Get All
    public List<ContactTypeDto> getAllContactTypes() {
        List<ContactType> entities = contactTypeRepository.findAll();
        return entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Soft Delete by ID
    public void deleteContactType(Long id) {
        Optional<ContactType> optional = contactTypeRepository.findById(id.intValue());
        if (optional.isPresent()) {
            ContactType entity = optional.get();
            entity.setIsActive(false); // Soft delete
            contactTypeRepository.save(entity);
        }
    }

    // Map DTO to Entity
    private void dtoToEntity(ContactTypeDto dto, ContactType entity) {
    	
    	
    	validateNotNull(dto.getTenantId(),"Tenent ID cant be NULL");
    	validateNotNull(dto.getContactType(),"Contact type cant be NULL");
    	validateNotNull(dto.getCreatedBy(),"Created by cant Be NUll");
    	
        entity.setTenantId(dto.getTenantId());
        entity.setContactType(dto.getContactType());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setIsActive(true);;
    }

    private void validateNotNull(Object field, String message) {
    	if(field == null) {
    		throw new BusinessException(message);
    	}
	}

	// Map Entity to DTO
    private ContactTypeDto mapToDto(ContactType entity) {
        ContactTypeDto dto = new ContactTypeDto();
        dto.setContactTypeId(entity.getContactTypeId());
        dto.setTenantId(entity.getTenantId());
        dto.setContactType(entity.getContactType());
        dto.setActive(entity.getIsActive());
        return dto;
    }
}
