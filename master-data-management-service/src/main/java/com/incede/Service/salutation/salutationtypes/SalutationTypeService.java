
package com.incede.Service.salutation.salutationtypes;

import com.incede.Dto.salutation.salutationtypes.SalutationTypeDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.salutation.salutationtypes.SalutationType;
import com.incede.Repository.salutation.salutationtypes.SalutationTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalutationTypeService {

    private final SalutationTypeRepository repository;

    public SalutationTypeService(SalutationTypeRepository repository) {
        this.repository = repository;
    }

    public List<SalutationTypeDto> getAllSalutations() {
        return repository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<SalutationTypeDto> getActiveSalutations() {
        return repository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SalutationTypeDto getSalutationById(Integer id) {
        SalutationType entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Salutation not found"));
        return convertToDto(entity);
    }

    public SalutationTypeDto createSalutation(SalutationTypeDto dto) {
    	 validateSalutation(dto, true); 
        SalutationType entity = convertToEntity(dto);
        SalutationType saved = repository.save(entity);
        return convertToDto(saved);
    }

    public SalutationTypeDto updateSalutation(Integer id, SalutationTypeDto dto) {
        SalutationType entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Salutation not found"));
      //  validateSalutation(dto, true); 
        entity.setSalutation(dto.getSalutation());  
        entity.setUpdatedBy(dto.getUpdatedBy());
        SalutationType updated = repository.save(entity);
        return convertToDto(updated);
    }

    public void deleteSalutation(Integer id) {
        SalutationType entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Salutation not found"));
        entity.setIsActive(false); // soft delete
        repository.save(entity);
    }

    private SalutationTypeDto convertToDto(SalutationType entity) {
        SalutationTypeDto dto = new SalutationTypeDto();
        dto.setSalutationId(entity.getSalutationId());
        dto.setSalutation(entity.getSalutation());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        return dto;
    }

    private SalutationType convertToEntity(SalutationTypeDto dto) {
        SalutationType entity = new SalutationType();
        entity.setSalutationId(dto.getSalutationId());
        entity.setSalutation(dto.getSalutation());
        entity.setIsActive( true);
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        return entity;
    }
    private void validateSalutation(SalutationTypeDto dto, boolean isCreate) {
        if (dto.getSalutation() == null || dto.getSalutation().trim().isEmpty()) {
            throw new BusinessException("Salutation cannot be null or empty");
        }

        if (isCreate) {
            if (dto.getCreatedBy() == null) {
                throw new BusinessException("CreatedBy user ID cannot be null during creation");
            }
        } else {
            if (dto.getUpdatedBy() == null) {
                throw new BusinessException("UpdatedBy user ID cannot be null during update");
            }
        }
    }

   
    
}


