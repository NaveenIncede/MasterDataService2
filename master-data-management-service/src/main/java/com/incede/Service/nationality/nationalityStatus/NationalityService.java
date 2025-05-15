package com.incede.Service.nationality.nationalityStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.nationality.nationalitystatus.NationalityDTO;
import com.incede.Exception.BusinessException;
import com.incede.Model.nationality.nationalityStatus.Nationality;
import com.incede.Repository.nationality.nationalityStatus.NationalityRepository;
import com.incede.Response.response.responseBody.ResponseWrapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NationalityService {

    private final NationalityRepository nationalityRepository;

    public NationalityService(NationalityRepository nationalityRepository) {
        this.nationalityRepository = nationalityRepository;
    }

    // Convert Nationality entity to DTO
    private NationalityDTO toDto(Nationality nationality) {
        NationalityDTO dto = new NationalityDTO();
        dto.setNationalityId(nationality.getNationalityId());
        dto.setNationality(nationality.getNationality());
        dto.setIsActive(nationality.getIsActive());
        dto.setTenantId(nationality.getTenantId());
        return dto;
    }

    // Convert NationalityDTO to entity
    private Nationality toEntity(NationalityDTO dto) {
        Nationality nationality;
        if (dto.getNationalityId() != null) {
            nationality = nationalityRepository.findById(dto.getNationalityId())
                    .orElseThrow(() -> new BusinessException("Nationality not found for update"));
            nationality.setUpdatedBy(dto.getNationalityId()); 
        } else {
            nationality = new Nationality();
            if (dto.getCreatedBy() == null) {
                throw new BusinessException("CreatedBy cannot be null for new Nationality.");
            }
            nationality.setCreatedBy(dto.getCreatedBy());
            nationality.setIsActive(true);
        }
        nationality.setTenantId(dto.getTenantId());
        nationality.setNationality(dto.getNationality());
        nationality.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        return nationality;
    }
    
    @Transactional(readOnly = true)
    public ResponseWrapper<List<NationalityDTO>> getAllNationalities() {
        List<NationalityDTO> nationalities = nationalityRepository.findByIsActiveTrue().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return new ResponseWrapper<>("success", nationalities, "Nationalities fetched successfully");
    }

    @Transactional(readOnly = true)
    public ResponseWrapper<NationalityDTO> getNationalityById(Integer id) {
        Nationality nationality = nationalityRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Nationality not found with ID: " + id));

        NationalityDTO nationalityDTO = toDto(nationality);

        return new ResponseWrapper<>("success", nationalityDTO, "Nationality fetched successfully");
    }

    @Transactional
    public ResponseWrapper<Nationality> createOrUpdateNationality(NationalityDTO dto) {
        if (dto.getNationalityId() != null) {
            Nationality existingNationality = nationalityRepository.findById(dto.getNationalityId())
                    .orElseThrow(() -> new BusinessException("Nationality not found with ID: " + dto.getNationalityId()));
            if (!existingNationality.getNationality().equalsIgnoreCase(dto.getNationality())) {
                boolean exists = nationalityRepository.existsByTenantIdAndNationality(dto.getTenantId(), dto.getNationality());
                if (exists) {
                    throw new BusinessException("Nationality name already exists for this tenant.");
                }
            }
            existingNationality = toEntity(dto);
            return new ResponseWrapper<>("success", nationalityRepository.save(existingNationality), "Nationality updated successfully");
        }
        boolean exists = nationalityRepository.existsByTenantIdAndNationality(dto.getTenantId(), dto.getNationality());
        if (exists) {
            throw new BusinessException("Nationality already exists for this tenant.");
        }

        Nationality nationality = toEntity(dto);
        return new ResponseWrapper<>("success", nationalityRepository.save(nationality), "Nationality created successfully");
    }

    @Transactional
    public ResponseWrapper<String> deleteNationality(Integer id) {
        Nationality nationality = nationalityRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cannot delete. Nationality not found with ID: " + id));
        nationality.setIsActive(false);
        nationalityRepository.save(nationality);
        return new ResponseWrapper<>("success", "Nationality deleted successfully", "Nationality deleted");
    }

    @Transactional
    public List<NationalityDTO> getByTenantId(Integer tenantId) {
        List<Nationality> nationalities = nationalityRepository.findByTenantIdAndIsActiveTrue(tenantId);
        return nationalities.stream().map(this::toDto).collect(Collectors.toList());
    }
}
