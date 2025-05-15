package com.incede.Service.liability.liabilityMaster;

import com.incede.Dto.liability.liabilityMaster.LiabilitySchemeMasterDto;
import com.incede.Exception.ApiResponse;
import com.incede.Exception.BusinessException;
import com.incede.Model.liability.liabilityMaster.LiabilitySchemeMaster;
import com.incede.Repository.liability.liabilityMaster.LiabilitySchemeMasterRepository;
import com.incede.Response.response.responseBody.ResponseWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LiabilitySchemeMasterService {

    private final LiabilitySchemeMasterRepository repository;

    public LiabilitySchemeMasterService(LiabilitySchemeMasterRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ApiResponse<LiabilitySchemeMasterDto> saveOrUpdate(LiabilitySchemeMasterDto dto) {
        try {
            // Duplicate check for new entries
            if (dto.getSchemeId() == null && repository.existsBySchemeCode(dto.getSchemeCode())) {
                throw new BusinessException("This scheme code is already in use.");
            }

            // Fetch or create entity
            LiabilitySchemeMaster entity = (dto.getSchemeId() != null)
                    ? repository.findById(dto.getSchemeId())
                        .orElseThrow(() -> new BusinessException("Scheme not found for update"))
                    : new LiabilitySchemeMaster();

            mapToEntity(dto, entity);

            LiabilitySchemeMaster savedEntity = repository.save(entity);

            String message = (dto.getSchemeId() == null) ? "Created successfully" : "Updated successfully";
            return new ApiResponse<>("success", mapToDto(savedEntity), message);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Failed to save or update scheme: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<LiabilitySchemeMasterDto>> getAllActive() {
        List<LiabilitySchemeMaster> activeSchemes = repository.findAll().stream()
                .filter(LiabilitySchemeMaster::getIsActive)
                .collect(Collectors.toList());

        if (activeSchemes.isEmpty()) {
            throw new BusinessException("No active schemes found");
        }

        List<LiabilitySchemeMasterDto> dtos = activeSchemes.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new ApiResponse<>("success", dtos, "Active schemes fetched successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<LiabilitySchemeMasterDto> getById(Integer id) {
        LiabilitySchemeMaster scheme = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Scheme not found"));

        return new ApiResponse<>("success", mapToDto(scheme), "Scheme fetched successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<LiabilitySchemeMasterDto>> getByProductId(Integer productId) {
        List<LiabilitySchemeMaster> schemes = repository.findByProductIdAndIsActiveTrue(productId);

        if (schemes.isEmpty()) {
            throw new BusinessException("No active schemes found for product ID: " + productId);
        }

        List<LiabilitySchemeMasterDto> dtos = schemes.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new ApiResponse<>("success", dtos, "Schemes fetched by product ID");
    }

    @Transactional
    public ApiResponse<Boolean> softDelete(Integer id) {
        LiabilitySchemeMaster scheme = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Scheme not found"));

        scheme.setIsActive(false);
        repository.save(scheme);

        return new ApiResponse<>("success", true, "Scheme marked as inactive (soft deleted)");
    }

    // Mapping methods
    private void mapToEntity(LiabilitySchemeMasterDto dto, LiabilitySchemeMaster entity) {
        entity.setSchemeName(dto.getSchemeName());
        entity.setSchemeCode(dto.getSchemeCode());
        entity.setProductId(dto.getProductId());
        entity.setMinDepositAmount(dto.getMinDepositAmount());
        entity.setMaxDepositAmount(dto.getMaxDepositAmount());
        entity.setMaxInterestRate(dto.getMaxInterestRate());
        entity.setEffectiveFrom(dto.getEffectiveFrom());
        entity.setEffectiveTo(dto.getEffectiveTo());
        entity.setMultiplesOf(dto.getMultiplesOf());
        entity.setDepositPeriod(dto.getDepositPeriod());
        entity.setPeriodType(dto.getPeriodType());
        entity.setSpecialInterestRate(dto.getSpecialInterestRate());
        entity.setInterestAccrualPostingFrequency(dto.getInterestAccrualPostingFrequency());
        entity.setCompoundFrequency(dto.getCompoundFrequency());
        entity.setInterestPaymentFrequency(dto.getInterestPaymentFrequency());
        entity.setPutOptionAvailable(dto.getPutOptionAvailable());
        entity.setPutOptionAfter(dto.getPutOptionAfter());
        entity.setPutOptionPeriodType(dto.getPutOptionPeriodType());
        entity.setAuthorisedBy(dto.getAuthorisedBy());
        entity.setAuthorisedAt(dto.getAuthorisedAt());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        if (dto.getIdentity() != null) {
            entity.setIdentity(dto.getIdentity());
        } else if (entity.getIdentity() == null) {
            entity.setIdentity(UUID.randomUUID());
        }

        if (dto.getSchemeId() == null) {
            if (dto.getCreatedBy() == null) throw new BusinessException("CreatedBy cannot be null");
            entity.setCreatedBy(dto.getCreatedBy());
        } else {
            entity.setUpdatedBy(dto.getSchemeId());
        }
    }

    private LiabilitySchemeMasterDto mapToDto(LiabilitySchemeMaster entity) {
        return new LiabilitySchemeMasterDto(
                entity.getSchemeId(),
                entity.getSchemeName(),
                entity.getSchemeCode(),
                entity.getProductId(),
                entity.getMinDepositAmount(),
                entity.getMaxDepositAmount(),
                entity.getMaxInterestRate(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getMultiplesOf(),
                entity.getDepositPeriod(),
                entity.getPeriodType(),
                entity.getSpecialInterestRate(),
                entity.getInterestAccrualPostingFrequency(),
                entity.getCompoundFrequency(),
                entity.getInterestPaymentFrequency(),
                entity.getPutOptionAvailable(),
                entity.getPutOptionAfter(),
                entity.getPutOptionPeriodType(),
                entity.getAuthorisedBy(),
                entity.getAuthorisedAt(),
                entity.getIsActive(),
                entity.getIdentity(),
                entity.getCreatedBy()
        );
    }
}
