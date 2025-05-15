package com.incede.Service.scheme.schemeGl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.scheme.schemGl.SchemeGlDTO;
import com.incede.Exception.ApiResponse;
import com.incede.Exception.BusinessException;
import com.incede.Model.scheme.schemeGl.SchemeGl;
import com.incede.Repository.scheme.schemeGl.SchemeGlRepository;
import com.incede.Response.response.responseBody.ResponseWrapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchemeGlService {

    private final SchemeGlRepository schemeGlRepository;

    public SchemeGlService(SchemeGlRepository schemeGlRepository) {
        this.schemeGlRepository = schemeGlRepository;
    }

    @Transactional
    public ApiResponse<SchemeGlDTO> saveOrUpdate(SchemeGlDTO dto) {
        try {
            // Check uniqueness: prevent duplicate gl_account_type per scheme_id
            boolean isDuplicate = schemeGlRepository.existsBySchemeIdAndGlAccountTypeAndGlConfigIdNot(
                dto.getSchemeId(), dto.getGlAccountType(), dto.getGlConfigId() == null ? -1 : dto.getGlConfigId()
            );

            if (isDuplicate) {
                throw new BusinessException("This account type is already mapped for this scheme.");
            }

            // Map DTO to Entity
            SchemeGl entity = mapToEntity(dto);

            // Save the entity
            SchemeGl saved = schemeGlRepository.save(entity);

            // Map saved entity back to DTO
            SchemeGlDTO responseDto = mapToDto(saved);

            String message = (dto.getGlConfigId() == null)
                    ? "GL Config created successfully"
                    : "GL Config updated successfully";

            return new ApiResponse<>("success", responseDto, message);

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Failed to save or update GL Config: " + e.getMessage());
        }
    }

    @Transactional
    public ApiResponse<Boolean> softDelete(Integer id) {
        SchemeGl schemeGl = schemeGlRepository.findById(id)
                .orElseThrow(() -> new BusinessException("GL Config with ID " + id + " not found"));

        schemeGl.setIsActive(false);
        schemeGlRepository.save(schemeGl);

        return new ApiResponse<>("success", true, "GL Config deleted successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<SchemeGlDTO>> getAllActive() {
        List<SchemeGl> glList = schemeGlRepository.findByIsActiveTrue();

        if (glList.isEmpty()) {
            throw new BusinessException("No active GL Configs found");
        }

        List<SchemeGlDTO> dtos = glList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new ApiResponse<>("success", dtos, "Active GL Configs fetched successfully");
    }

    @Transactional(readOnly = true)
    public ApiResponse<SchemeGlDTO> getById(Integer id) {
        SchemeGl schemeGl = schemeGlRepository.findById(id)
                .orElseThrow(() -> new BusinessException("GL Config with ID " + id + " not found"));

        return new ApiResponse<>("success", mapToDto(schemeGl), "GL Config fetched successfully");
    }

    public ResponseWrapper<List<SchemeGlDTO>> getByTenantId(Integer tenantId) {
        try {
            List<SchemeGl> entities = schemeGlRepository.findByTenantIdAndIsActiveTrue(tenantId);

            if (entities.isEmpty()) {
                throw new BusinessException("No active GL configurations found for tenant with ID " + tenantId);
            }

            List<SchemeGlDTO> dtos = entities.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

            return new ResponseWrapper<>("success", dtos, "Active GL configurations fetched successfully");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException("Failed to fetch GL configurations for tenant: " + e.getMessage());
        }
    }

    public ApiResponse<Integer> getGlAccountId(Integer schemeId, String glAccountType) {
        Integer glAccountId = schemeGlRepository.findBySchemeIdAndGlAccountTypeAndIsActiveTrue(schemeId, glAccountType)
                .map(SchemeGl::getGlAccountId)
                .orElseThrow(() -> new BusinessException("No GL mapping found for " + glAccountType));

        return new ApiResponse<>("success", glAccountId, glAccountType + " GL account ID fetched successfully");
    }

    // ==========================
    // DTO <-> Entity Mapping
    // ==========================

    private SchemeGl mapToEntity(SchemeGlDTO dto) {
        SchemeGl entity;

        if (dto.getGlConfigId() != null) {
            entity = schemeGlRepository.findById(dto.getGlConfigId())
                    .orElseThrow(() -> new BusinessException("GL Config not found for update"));

            entity.setUpdatedBy(dto.getGlConfigId());
        } else {
            entity = new SchemeGl();
            entity.setCreatedBy(dto.getCreatedBy());
            entity.setIsDeleted(false); // Optional default
        }

        entity.setTenantId(dto.getTenantId());
        entity.setSchemeId(dto.getSchemeId());
        entity.setGlAccountType(dto.getGlAccountType());
        entity.setGlAccountId(dto.getGlAccountId());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        entity.setIdentity(dto.getIdentity());

        return entity;
    }

    private SchemeGlDTO mapToDto(SchemeGl entity) {
        return new SchemeGlDTO(
                entity.getGlConfigId(),
                entity.getTenantId(),
                entity.getSchemeId(),
                entity.getGlAccountType(),
                entity.getGlAccountId(),
                entity.getIdentity(),
                entity.getIsActive(),
                entity.getCreatedBy()
        );
    }


    @Transactional(readOnly = true)
    public ApiResponse<List<SchemeGlDTO>> getByGlAccountId(Integer glAccountId) {
        List<SchemeGl> list = schemeGlRepository.findByGlAccountIdAndIsActiveTrue(glAccountId);

        if (list.isEmpty()) {
            throw new BusinessException("No GL Config found for glAccountId: " + glAccountId);
        }

        List<SchemeGlDTO> dtos = list.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new ApiResponse<>("success", dtos, "GL Configs fetched successfully by glAccountId");
    }

}
