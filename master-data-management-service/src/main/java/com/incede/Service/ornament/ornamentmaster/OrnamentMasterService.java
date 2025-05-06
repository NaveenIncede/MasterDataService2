package com.incede.Service.ornament.ornamentmaster;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.ornament.ornamentmaster.OrnamentMasterDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.ornament.ornamentmaster.OrnamentMaster;
import com.incede.Repository.ornament.ornamentmaster.OrnamentMasterRepository;

@Service
public class OrnamentMasterService {

    private final OrnamentMasterRepository repository;

    public OrnamentMasterService(OrnamentMasterRepository repository) {
        this.repository = repository;
    }

    private OrnamentMasterDto toDto(OrnamentMaster entity) {
        OrnamentMasterDto dto = new OrnamentMasterDto();
        dto.setTenantId(entity.getTenantId());
        dto.setOrnamentName(entity.getOrnamentName());
        dto.setIsActive(entity.getIsActive());
        dto.setIsDeleted(entity.getIsDeleted());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setIdentity(entity.getIdentity());
        return dto;
    }

    private OrnamentMaster toEntity(OrnamentMasterDto dto) {
        OrnamentMaster entity = new OrnamentMaster();
        entity.setTenantId(dto.getTenantId());
        entity.setOrnamentName(dto.getOrnamentName());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        entity.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false);
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setIdentity(dto.getIdentity() != null ? dto.getIdentity() : UUID.randomUUID());
        return entity;
    }

    @Transactional(readOnly = true)
    public List<OrnamentMasterDto> getAllOrnaments() {
        return repository.findAll().stream()
                .filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrnamentMasterDto getOrnamentById(Integer id) {
        OrnamentMaster ornament = repository.findById(id)
                .filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()))
                .orElseThrow(() -> new BusinessException("Ornament not found with id: " + id));
        return toDto(ornament);
    }

    @Transactional(readOnly = true)
    public OrnamentMasterDto getOrnamentByIdentity(UUID identity) {
        OrnamentMaster ornament = repository.findByIdentity(identity)
                .filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()))
                .orElseThrow(() -> new BusinessException("Ornament not found with identity: " + identity));
        return toDto(ornament);
    }

    @Transactional
    public OrnamentMaster createOrnament(OrnamentMasterDto dto) {
        boolean exists = repository.existsByTenantIdAndOrnamentNameIgnoreCaseAndIsDeletedFalse(
                dto.getTenantId(), dto.getOrnamentName());

        if (exists) {
            throw new BusinessException("Duplicate ornament name for this tenant is not allowed.");
        }

        OrnamentMaster ornament = toEntity(dto);
        return repository.save(ornament);
    }

    @Transactional
    public OrnamentMaster updateOrnament(Integer id, OrnamentMasterDto dto) {
        OrnamentMaster existing = repository.findById(id)
                .filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()))
                .orElseThrow(() -> new BusinessException("Cannot update. Ornament not found with id: " + id));

        if (!existing.getOrnamentName().equalsIgnoreCase(dto.getOrnamentName())) {
            boolean exists = repository.existsByTenantIdAndOrnamentNameIgnoreCaseAndIsDeletedFalse(
                    dto.getTenantId(), dto.getOrnamentName());
            if (exists) {
                throw new BusinessException("Duplicate ornament name for this tenant is not allowed.");
            }
        }

        existing.setTenantId(dto.getTenantId());
        existing.setOrnamentName(dto.getOrnamentName());
        existing.setIsActive(dto.getIsActive());
        existing.setUpdatedBy(dto.getUpdatedBy());

        return repository.save(existing);
    }

    @Transactional
    public void deleteOrnament(Integer id) {
        OrnamentMaster ornament = repository.findById(id)
                .filter(o -> !Boolean.TRUE.equals(o.getIsDeleted()))
                .orElseThrow(() -> new BusinessException("Cannot delete. Ornament not found with id: " + id));
        ornament.setIsDeleted(true);
        repository.save(ornament);
    }
}
