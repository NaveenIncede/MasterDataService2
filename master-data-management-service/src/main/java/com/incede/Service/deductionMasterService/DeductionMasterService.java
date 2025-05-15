package com.incede.Service.deductionMasterService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.incede.Dto.deductionMaster.DeductionMasterDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.deductionMaster.DeductionMaster;
import com.incede.Repository.deductionMasterRepository.DeductionMasterRepository;
import com.incede.enums.deductionTypes.DeductionType;
@Service
public class DeductionMasterService {
	
	
	

    private final DeductionMasterRepository deductionMasterRepository;

    public DeductionMasterService(DeductionMasterRepository deductionMasterRepository) {
        this.deductionMasterRepository = deductionMasterRepository;
    }

    public Long createOrUpdateDeduction(DeductionMasterDto dto) {
        DeductionType deductionType = dto.getDeductionType();
        if (deductionType == null) {
            throw new BusinessException("Deduction type must be 'Fixed' or 'Percentage'");
        }

        if (deductionType == DeductionType.Fixed &&
                dto.getAmountOrPercent().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Amount must be >= 0 for Fixed type");
        }

        if (deductionType == DeductionType.Percentage &&
                (dto.getAmountOrPercent().compareTo(BigDecimal.ZERO) < 0 ||
                 dto.getAmountOrPercent().compareTo(new BigDecimal("100")) > 0)) {
            throw new BusinessException("Percentage must be between 0 and 100");
        }

        //  Check for duplicate 
        boolean isDuplicate = deductionMasterRepository.existsByTenantIdAndDeductionNameAndDeductionIdNot(
                dto.getTenantId(), dto.getDeductionName(), dto.getDeductionId() == null ? 0L : dto.getDeductionId());

        if (isDuplicate) {
            throw new BusinessException("Deduction name already exists for this tenant. Please choose a different name.");
        }

        DeductionMaster entity;
        if (dto.getDeductionId() == null || dto.getDeductionId() == 0) {
            entity = new DeductionMaster(); // Create
            entity.setCreatedBy(dto.getCreatedBy());
        } else {
            entity = deductionMasterRepository.findById(dto.getDeductionId())
                    .orElseThrow(() -> new BusinessException("No deduction found with given ID"));
            entity.setUpdatedBy(dto.getCreatedBy());  //  createdBy is UserId
        }

        dtoToEntity(dto, entity);

        DeductionMaster saved = deductionMasterRepository.save(entity);
        return saved.getDeductionId().longValue();
    }


    public DeductionMasterDto getDeductionById(Integer deductionId) {
        DeductionMaster entity = deductionMasterRepository.findById(deductionId)
                .orElseThrow(() -> new BusinessException("Deduction not found"));
        return mapToDto(entity);
    }

    public List<DeductionMasterDto> getActiveDeductions() {
        List<DeductionMaster> list = deductionMasterRepository.findByIsActiveTrue();
        if (list.isEmpty()) {
            throw new BusinessException("No active deductions found");
        }
        return list.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public void softDeleteDeduction(Integer id) {
        Optional<DeductionMaster> optional = deductionMasterRepository.findById(id);
        if (optional.isPresent()) {
            DeductionMaster entity = optional.get();
            entity.setIsActive(false);
            deductionMasterRepository.save(entity);
        } else {
            throw new BusinessException("Deduction not found for soft delete");
        }
    }
    private DeductionMaster dtoToEntity(DeductionMasterDto dto, DeductionMaster entity) {
        validateNotNull(dto.getTenantId(), "Tenant ID can't be null");
        validateNotNull(dto.getDeductionName(), "Deduction Name can't be null");
        validateNotNull(dto.getDeductionType(), "Deduction Type can't be null");
        validateNotNull(dto.getAmountOrPercent(), "Amount or Percent can't be null");
        validateNotNull(dto.getCreatedBy(), "Created By can't be null");

        entity.setTenantId(dto.getTenantId());
        entity.setDeductionName(dto.getDeductionName());
        entity.setDeductionType(dto.getDeductionType());
        entity.setAmountOrPercent(dto.getAmountOrPercent());
        entity.setIsActive(true);
        entity.setCreatedBy(dto.getCreatedBy());
        return entity;
    }

    private void validateNotNull(Object field, String message) {
        if (field == null) {
            throw new BusinessException(message);
        }
    }


    private DeductionMasterDto mapToDto(DeductionMaster entity) {
        DeductionMasterDto dto = new DeductionMasterDto();
        dto.setDeductionId(entity.getDeductionId());
        dto.setTenantId(entity.getTenantId());
        dto.setDeductionName(entity.getDeductionName());
        dto.setDeductionType(entity.getDeductionType());
        dto.setAmountOrPercent(entity.getAmountOrPercent());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setIdentity(entity.getIdentity());
        return dto;
    }
}
