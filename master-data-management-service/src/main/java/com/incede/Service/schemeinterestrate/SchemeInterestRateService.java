
	package com.incede.Service.schemeinterestrate;

	import com.incede.Dto.schemeinterestrate.SchemeInterestRateDto;
	import com.incede.Model.schemeinterestrate.SchemeInterestRate;
	import com.incede.Exception.BusinessException;
	import com.incede.Repository.schemeinterestrate.SchemeInterestRateRepository;

	import jakarta.transaction.Transactional;

	import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
	import java.util.*;
	import java.util.stream.Collectors;


	@Service
	public class SchemeInterestRateService {

	    private final SchemeInterestRateRepository repository;

	    public SchemeInterestRateService(SchemeInterestRateRepository repository) {
	        this.repository = repository;
	    }

	    // Convert Entity to DTO
	    private SchemeInterestRateDto toDto(SchemeInterestRate entity) {
	        SchemeInterestRateDto dto = new SchemeInterestRateDto();
	        dto.setInterestRateId(entity.getInterestRateId());
	        dto.setTenantId(entity.getTenantId());
	        dto.setSchemeId(entity.getSchemeId());
	        dto.setFromAmount(entity.getFromAmount());
	        dto.setUptoAmount(entity.getUptoAmount());
	        dto.setFromPeriod(entity.getFromPeriod());
	        dto.setUptoPeriod(entity.getUptoPeriod());
	        dto.setPeriodType(entity.getPeriodType());
	        dto.setInterestRate(entity.getInterestRate());
	        dto.setEffectiveFrom(entity.getEffectiveFrom());
	        dto.setEffectiveUpto(entity.getEffectiveUpto());
	        dto.setRecalcInterest(entity.getRecalcInterest());
	        dto.setLoanPastDue(entity.getLoanPastDue());
	        dto.setIsActive(entity.getIsActive());
	//       dto.setIdentity(entity.getIdentity());
	        dto.setCreatedBy(entity.getCreatedBy());
	 //       dto.setCreatedAt(entity.getCreatedAt());
	        dto.setUpdatedBy(entity.getUpdatedBy());
	 //       dto.setUpdatedAt(entity.getUpdatedAt());
	        return dto;
	    }

	    // Convert DTO to Entity
	    private SchemeInterestRate toEntity(SchemeInterestRateDto dto) {
	        SchemeInterestRate entity = new SchemeInterestRate();
	        entity.setTenantId(dto.getTenantId());
	        entity.setSchemeId(dto.getSchemeId());
	        entity.setFromAmount(dto.getFromAmount());
	        entity.setUptoAmount(dto.getUptoAmount());
	        entity.setFromPeriod(dto.getFromPeriod());
	        entity.setUptoPeriod(dto.getUptoPeriod());
	        entity.setPeriodType(dto.getPeriodType());
	        entity.setInterestRate(dto.getInterestRate());
	        entity.setEffectiveFrom(dto.getEffectiveFrom());
	        entity.setEffectiveUpto(dto.getEffectiveUpto());
	        entity.setRecalcInterest(dto.getRecalcInterest() != null ? dto.getRecalcInterest() : false);
	        entity.setLoanPastDue(dto.getLoanPastDue() != null ? dto.getLoanPastDue() : false);
	        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
	        entity.setIdentity(dto.getIdentity() != null ? dto.getIdentity() : UUID.randomUUID());
	        entity.setCreatedBy(dto.getCreatedBy());
	        entity.setCreatedAt(LocalDateTime.now());
	        return entity;
	    }

	    
	    //Check overlapping of slab 
	    private boolean isOverlapping(SchemeInterestRateDto dto) {
	        List<SchemeInterestRate> existingSlabs = repository.findBySchemeIdAndPeriodTypeAndIsActiveTrue(dto.getSchemeId(), dto.getPeriodType());

	        for (SchemeInterestRate slab : existingSlabs) {	           
	            if (dto.getFromAmount().compareTo(slab.getUptoAmount()) <= 0 && dto.getUptoAmount().compareTo(slab.getFromAmount()) >= 0) {
	                return true;  
	            }	           
	            if (dto.getFromPeriod().compareTo(slab.getUptoPeriod()) <= 0 && dto.getUptoPeriod().compareTo(slab.getFromPeriod()) >= 0) {
	                return true;  
	            }
	        }
	        return false;
	    }
	        
	        
	    
	    // Get all
	    @Transactional
	    public List<SchemeInterestRateDto> getAllInterestRates() {
	        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
	    }

	    // Get by ID
	    @Transactional
	    public SchemeInterestRate getInterestRateById(Integer id) {
	        return repository.findById(id)
	                .orElseThrow(() -> new BusinessException("Interest rate not found with ID: " + id));
	    }

	    // Create
	    @Transactional
	    public SchemeInterestRate createInterestRate(SchemeInterestRateDto dto) {
	    	
	    	validateFields(dto);
	    	if (isOverlapping(dto)) {
	            throw new BusinessException("The amount or period range overlaps with an existing slab.");
	        }
	        boolean exists = repository.existsBySchemeIdAndEffectiveFrom(dto.getSchemeId(), dto.getEffectiveFrom());
	        if (exists) {
	            throw new BusinessException("Interest rate already exists for this scheme and date.");
	        }
	        return repository.save(toEntity(dto));
	    }

	    // Update
	    @Transactional
	    public SchemeInterestRate updateInterestRate(Integer id, SchemeInterestRateDto dto) {
	        SchemeInterestRate existing = repository.findById(id)
	                .orElseThrow(() -> new BusinessException("Cannot update. Interest rate not found with ID: " + id));

	        existing.setFromAmount(dto.getFromAmount());
	        existing.setUptoAmount(dto.getUptoAmount());
	        existing.setFromPeriod(dto.getFromPeriod());
	        existing.setUptoPeriod(dto.getUptoPeriod());
	        existing.setPeriodType(dto.getPeriodType());
	        existing.setInterestRate(dto.getInterestRate());
	        existing.setEffectiveFrom(dto.getEffectiveFrom());
	        existing.setEffectiveUpto(dto.getEffectiveUpto());
	        existing.setRecalcInterest(dto.getRecalcInterest() != null ? dto.getRecalcInterest() : false);
	        existing.setLoanPastDue(dto.getLoanPastDue() != null ? dto.getLoanPastDue() : false);
	        existing.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
	        existing.setUpdatedBy(dto.getUpdatedBy());
	        existing.setUpdatedAt(LocalDateTime.now());

	        return repository.save(existing);
	    }

	    // Get all active
	    public List<SchemeInterestRateDto> getAllActiveInterestRates() {
	        return repository.findByIsActiveTrue().stream().map(this::toDto).collect(Collectors.toList());
	    }

	    // Get by tenant ID
	    @Transactional
	    public List<SchemeInterestRateDto> getInterestRatesByTenantId(Integer tenantId) {
	        return repository.findByTenantId(tenantId).stream().map(this::toDto).collect(Collectors.toList());
	    }

	    // delete by ID
	    @Transactional
	    public void deleteInterestRate(Integer id) {
	        SchemeInterestRate entity = repository.findById(id)
	                .orElseThrow(() -> new BusinessException("Cannot delete. Interest rate not found with ID: " + id));
	        entity.setIsActive(false);
	        entity.setUpdatedAt(LocalDateTime.now());
	        repository.save(entity);
	    }
	    
	    
	    @Transactional
	    public SchemeInterestRate getMatchingInterestRateSlab(
	            Integer schemeId,
	            BigDecimal loanAmount,
	            BigDecimal period,
	            String periodType,
	            Boolean loanPastDue,
	            Boolean recalcInterest,
	            LocalDate asOnDate) {

	        // Fetch active slabs for scheme + period type
	        List<SchemeInterestRate> slabs = repository.findBySchemeIdAndPeriodTypeAndIsActiveTrue(schemeId, periodType);

	        for (SchemeInterestRate slab : slabs) {
	            boolean amountMatches = loanAmount.compareTo(slab.getFromAmount()) >= 0 &&
	                                    loanAmount.compareTo(slab.getUptoAmount()) <= 0;

	            boolean periodMatches = period.compareTo(slab.getFromPeriod()) >= 0 &&
	                                    period.compareTo(slab.getUptoPeriod()) <= 0;

	            boolean dateMatches = (slab.getEffectiveFrom() == null || !asOnDate.isBefore(slab.getEffectiveFrom())) &&
	                                  (slab.getEffectiveUpto() == null || !asOnDate.isAfter(slab.getEffectiveUpto()));

	            boolean pastDueMatches = loanPastDue == null || loanPastDue.equals(slab.getLoanPastDue());
	            boolean recalcMatches = recalcInterest == null || recalcInterest.equals(slab.getRecalcInterest());

	            if (amountMatches && periodMatches && dateMatches && pastDueMatches && recalcMatches) {
	                return slab;
	            }
	        }

	        throw new BusinessException("No matching interest rate slab found for the given criteria.");
	    }
	    

	    // Validate null or invalid fields
	    private void validateFields(SchemeInterestRateDto dto) {
	        if (dto.getTenantId() == null) {
	            throw new BusinessException("Tenant ID cannot be null.");
	        }
	        if (dto.getSchemeId() == null) {
	            throw new BusinessException("Scheme ID cannot be null.");
	        }
	        if (dto.getFromAmount() == null || dto.getUptoAmount() == null) {
	            throw new BusinessException("Amount range cannot be null.");
	        }
	        if (dto.getFromPeriod() == null || dto.getUptoPeriod() == null) {
	            throw new BusinessException("Period range cannot be null.");
	        }
	        if (dto.getPeriodType() == null || dto.getPeriodType().isEmpty()) {
	            throw new BusinessException("Period type cannot be null or empty.");
	        }
	        if (dto.getInterestRate() == null) {
	            throw new BusinessException("Interest rate cannot be null.");
	        }
	        if (dto.getEffectiveFrom() == null) {
	            throw new BusinessException("Effective from date cannot be null.");
	        }if(dto.getCreatedBy()==null) {
	        	throw new BusinessException("Created By should not be null.");
	        }
	        
	    }
	}
