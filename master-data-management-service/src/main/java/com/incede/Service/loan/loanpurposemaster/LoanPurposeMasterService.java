package com.incede.Service.loan.loanpurposemaster;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.loan.loanpurposemaster.LoanPurposeMasterDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.loan.loanpurposemaster.LoanPurposeMaster;
import com.incede.Repository.loan.loanpurposemaster.LoanPurposeMasterRepository;

@Service
public class LoanPurposeMasterService {

    private final LoanPurposeMasterRepository repository;

    public LoanPurposeMasterService(LoanPurposeMasterRepository repository) {
        this.repository = repository;
    }

    private LoanPurposeMasterDto toDto(LoanPurposeMaster entity) {
        LoanPurposeMasterDto dto = new LoanPurposeMasterDto();
        dto.setTenantId(entity.getTenantId());
        dto.setPurposeName(entity.getPurposeName());
        dto.setPurposeCode(entity.getPurposeCode());
        dto.setPurposeDescription(entity.getPurposeDescription());
        dto.setIsActive(entity.getIsActive());
        dto.setIsDeleted(entity.getIsDeleted());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setIdentity(entity.getIdentity());
        return dto;
    }

    private LoanPurposeMaster toEntity(LoanPurposeMasterDto dto) {
        LoanPurposeMaster entity = new LoanPurposeMaster();
        entity.setTenantId(dto.getTenantId());
        entity.setPurposeName(dto.getPurposeName());
        entity.setPurposeCode(dto.getPurposeCode());
        entity.setPurposeDescription(dto.getPurposeDescription());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        entity.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false);
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setIdentity(dto.getIdentity() != null ? dto.getIdentity() : UUID.randomUUID());
        return entity;
    }

    @Transactional(readOnly = true)
    public List<LoanPurposeMasterDto> getAllPurposes() {
        return repository.findAll().stream()
                .filter(p -> !Boolean.TRUE.equals(p.getIsDeleted()))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LoanPurposeMasterDto getPurposeById(Integer id) {
        LoanPurposeMaster purpose = repository.findById(id)
                .filter(p -> !Boolean.TRUE.equals(p.getIsDeleted()))
                .orElseThrow(() -> new BusinessException("Purpose not found with id: " + id));
        return toDto(purpose);
    }

    @Transactional(readOnly = true)
    public LoanPurposeMasterDto getPurposeByIdentity(UUID identity) {
        LoanPurposeMaster purpose = repository.findByIdentity(identity)
                .filter(p -> !Boolean.TRUE.equals(p.getIsDeleted()))
                .orElseThrow(() -> new BusinessException("Purpose not found with identity: " + identity));
        return toDto(purpose);
    }

    @Transactional
    public LoanPurposeMaster createPurpose(LoanPurposeMasterDto dto) {
        boolean exists = repository.existsByTenantIdAndPurposeName(dto.getTenantId(), dto.getPurposeName());
        if (exists) {
            throw new BusinessException("Duplicate purpose name for this tenant is not allowed.");
        }

        LoanPurposeMaster purpose = toEntity(dto);
        return repository.save(purpose);
    }

    @Transactional
    public LoanPurposeMaster updatePurpose(Integer id, LoanPurposeMasterDto dto) {
        LoanPurposeMaster existing = repository.findById(id)
                .filter(p -> !Boolean.TRUE.equals(p.getIsDeleted()))
                .orElseThrow(() -> new BusinessException("Cannot update. Purpose not found with id: " + id));

        if (!existing.getPurposeName().equals(dto.getPurposeName())) {
            boolean exists = repository.existsByTenantIdAndPurposeName(dto.getTenantId(), dto.getPurposeName());
            if (exists) {
                throw new BusinessException("Duplicate purpose name for this tenant is not allowed.");
            }
        }

        existing.setTenantId(dto.getTenantId());
        existing.setPurposeName(dto.getPurposeName());
        existing.setPurposeCode(dto.getPurposeCode());
        existing.setPurposeDescription(dto.getPurposeDescription());
        existing.setIsActive(dto.getIsActive());
        existing.setUpdatedBy(dto.getUpdatedBy());

        return repository.save(existing);
    }

    @Transactional
    public void deletePurpose(Integer id) {
        LoanPurposeMaster purpose = repository.findById(id)
                .filter(p -> p.getIsDeleted() == null || !p.getIsDeleted())
                .orElseThrow(() -> new BusinessException("Cannot delete. Purpose not found with id: " + id));

        purpose.setIsDeleted(true);
        repository.save(purpose);
    }

    }

