package com.incede.Service.loanScheme.loanSchemeMaster;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.loanScheme.loanSchemeMaster.LoanSchemeMasterDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.loanScheme.loanSchemeMaster.LoanSchemeMaster;
import com.incede.Repository.loanScheme.loanSchemeMaster.LoanSchemeMasterRepository;
import com.incede.Response.response.responseBody.ResponseWrapper;

@Service
public class LoanSchemeMasterService {

    private final LoanSchemeMasterRepository repository;

    public LoanSchemeMasterService(LoanSchemeMasterRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ResponseWrapper<LoanSchemeMasterDto> createOrUpdate(LoanSchemeMasterDto dto) {
        try {
            if (dto.getSchemeId() == null && repository.existsBySchemeCode(dto.getSchemeCode())) {
                throw new BusinessException("Duplicate scheme code: '" + dto.getSchemeCode() + "' already exists.");
            }

            // Capitalize schemeName and convert schemeCode to uppercase
            dto.setSchemeName(dto.getSchemeName());
            dto.setSchemeCode(dto.getSchemeCode().toUpperCase());

            LoanSchemeMaster entity = mapToEntity(dto);

            if (dto.getSchemeId() != null) {
                entity.setSchemeId(dto.getSchemeId());
            }

            LoanSchemeMaster savedEntity = repository.save(entity);
            LoanSchemeMasterDto responseDto = mapToDto(savedEntity);
            String message = (dto.getSchemeId() == null) ? "Loan scheme created successfully" : "Loan scheme updated successfully";

            return new ResponseWrapper<>("success", responseDto, message);

        } catch (Exception e) {
            throw new BusinessException("Failed to save or update loan scheme: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public ResponseWrapper<List<LoanSchemeMasterDto>> getAllActive() {
        List<LoanSchemeMaster> activeSchemes = repository.findAllByIsActiveTrue();

        if (activeSchemes.isEmpty()) {
            throw new BusinessException("No active loan schemes found");
        }

        List<LoanSchemeMasterDto> dtoList = activeSchemes.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new ResponseWrapper<>("success", dtoList, "Loan schemes retrieved successfully");
    }

    @Transactional(readOnly = true)
    public ResponseWrapper<LoanSchemeMasterDto> getByIdAndIsActive(Integer id) {
        LoanSchemeMaster entity = repository.findBySchemeIdAndIsActiveTrue(id)
                .orElseThrow(() -> new BusinessException("Loan scheme not found"));
        return new ResponseWrapper<>("success", mapToDto(entity), "Loan scheme retrieved successfully");
    }

    @Transactional
    public ResponseWrapper<Boolean> softDelete(Integer id) {
        LoanSchemeMaster entity = repository.findBySchemeIdAndIsActiveTrue(id)
                .orElseThrow(() -> new BusinessException("Loan scheme not found"));

        entity.setIsActive(false);
        repository.save(entity);

        return new ResponseWrapper<>("success", true, "Loan scheme marked as inactive (soft deleted)");
    }

    @Transactional
    public List<LoanSchemeMasterDto> getActiveSchemesByTenant(Integer tenantId) {
        List<LoanSchemeMaster> schemes = repository.findByTenantIdAndIsActiveTrue(tenantId);
        if (schemes.isEmpty()) {
            throw new BusinessException("No active loan schemes found for tenant with ID: " + tenantId);
        }
        return schemes.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<LoanSchemeMasterDto> getActiveSchemesByProduct(Integer productId) {
        List<LoanSchemeMaster> schemes = repository.findByProductIdAndIsActiveTrue(productId);
        if (schemes.isEmpty()) {
            throw new BusinessException("No active loan schemes found for product with ID: " + productId);
        }
        return schemes.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private LoanSchemeMaster mapToEntity(LoanSchemeMasterDto dto) {
        LoanSchemeMaster entity = new LoanSchemeMaster();

        entity.setSchemeId(dto.getSchemeId());
        entity.setTenantId(dto.getTenantId());
        entity.setSchemeName(dto.getSchemeName());
        entity.setSchemeCode(dto.getSchemeCode());
        entity.setProductId(dto.getProductId());
        entity.setIsEpi(dto.getIsEpi() != null ? dto.getIsEpi() : false);
        entity.setMinLoanAmount(dto.getMinLoanAmount());
        entity.setMaxLoanAmount(dto.getMaxLoanAmount());
        entity.setMaxInterestRate(dto.getMaxInterestRate());
        entity.setEffectiveFrom(dto.getEffectiveFrom());
        entity.setEffectiveTo(dto.getEffectiveTo());
        entity.setLtvRate(dto.getLtvRate());
        entity.setMinInterestDays(dto.getMinInterestDays());
        entity.setSlabDownAllowed(dto.getSlabDownAllowed() != null ? dto.getSlabDownAllowed() : false);
        entity.setAuthorisedBy(dto.getAuthorisedBy());
        entity.setAuthorisedAt(LocalDateTime.now());
        entity.setPenalInterestRate(dto.getPenalInterestRate());
        entity.setPenalCalculateOn(dto.getPenalCalculateOn());
        entity.setIdentity(dto.getIdentity() != null ? dto.getIdentity() : UUID.randomUUID());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getSchemeId());

        return entity;
    }

    private LoanSchemeMasterDto mapToDto(LoanSchemeMaster entity) {
        return new LoanSchemeMasterDto(
                entity.getSchemeId(),
                entity.getTenantId(),
                entity.getSchemeName(),
                entity.getSchemeCode(),
                entity.getProductId(),
                entity.getIsEpi(),
                entity.getMinLoanAmount(),
                entity.getMaxLoanAmount(),
                entity.getMaxInterestRate(),
                entity.getEffectiveFrom(),
                entity.getEffectiveTo(),
                entity.getLtvRate(),
                entity.getMinInterestDays(),
                entity.getSlabDownAllowed(),
                entity.getAuthorisedBy(),
                entity.getPenalInterestRate(),
                entity.getPenalCalculateOn(),
                entity.getAuthorisedAt(),
                entity.getIsActive(),
                entity.getIdentity(),
                entity.getCreatedBy()
        );
    }

}
