package com.incede.Service.customer.customerCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incede.Dto.customer.customerCategory.MasterdataCustomerCategoryDTO;
import com.incede.Exception.BusinessException;
import com.incede.Model.customer.customerCategory.MasterdataCustomerCategory;
import com.incede.Repository.customer.customerCategory.MasterdataCustomerCategoryRepository;


@Service
public class MasterdataCustomerCategoryService {

    private final MasterdataCustomerCategoryRepository repository;

    public MasterdataCustomerCategoryService(MasterdataCustomerCategoryRepository repository) {
        this.repository = repository;
    }

    public List<MasterdataCustomerCategoryDTO> getAllCategories() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MasterdataCustomerCategoryDTO> getAllActiveCategories() {
        return repository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MasterdataCustomerCategoryDTO getCategoryById(Integer id) {
        MasterdataCustomerCategory entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found for ID: " + id));
        return convertToDTO(entity);
    }

    public MasterdataCustomerCategoryDTO getCategoryByIdAndTenant(Integer id, Integer tenantId) {
        MasterdataCustomerCategory entity = repository.findByCategoryIdAndTenantId(id, tenantId);
        if (entity == null) {
            throw new RuntimeException("Category not found for ID: " + id + " and Tenant ID: " + tenantId);
        }
        return convertToDTO(entity);
    }

 
    public MasterdataCustomerCategoryDTO createCategory(MasterdataCustomerCategoryDTO dto) {       
        if (dto.getCategoryId() != null) {
            throw new RuntimeException("Category ID should not be provided for creation.");
        }       
        validateCustomerCategory(dto, true);        
        MasterdataCustomerCategory entity = convertToEntity(dto);
        entity.setIsActive(true);  // Default active status      
        MasterdataCustomerCategory saved = repository.save(entity);
        saved.setCreatedBy(saved.getCategoryId());
        return convertToDTO(saved);
    }

 
    
    public MasterdataCustomerCategoryDTO updateCategory(Integer id, MasterdataCustomerCategoryDTO dto) {
        validateCustomerCategory(dto, false);

        // Enforce tenant isolation
        MasterdataCustomerCategory entity = repository.findByCategoryIdAndTenantId(id, dto.getTenantId());
        if (entity == null || !entity.getIsActive()) {
            throw new RuntimeException("Category not found for ID " + id + " and tenant ID " + dto.getTenantId());
        }

        entity.setCategoryName(dto.getCategoryName());
        entity.setDescription(dto.getDescription());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setCreatedAt(dto.getCreatedAt());

        MasterdataCustomerCategory updated = repository.save(entity);
        return convertToDTO(updated);
    }



    public void deleteCategoryById(Integer id) {
        MasterdataCustomerCategory entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found for ID: " + id));
        entity.setIsActive(false); 
        repository.save(entity);
    }
    
    public void deleteCategoryById(Integer categoryId, Integer tenantId) {
        MasterdataCustomerCategory entity = repository.findByCategoryIdAndTenantId(categoryId, tenantId);
        if (entity == null || !entity.getIsActive()) {
            throw new RuntimeException("Category not found for given ID and Tenant");
        }
        entity.setIsActive(false); // Soft delete
        repository.save(entity);
    }

    
    
    public List<MasterdataCustomerCategoryDTO> getAllCategoriesByTenantId(Integer tenantId) {
        return repository.findByTenantId(tenantId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MasterdataCustomerCategoryDTO convertToDTO(MasterdataCustomerCategory entity) {
        MasterdataCustomerCategoryDTO dto = new MasterdataCustomerCategoryDTO();
        dto.setCategoryId(entity.getCategoryId());
        dto.setCategoryName(entity.getCategoryName());
        dto.setDescription(entity.getDescription());
        dto.setTenantId(entity.getTenantId());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setIsDeleted(entity.getIsDeleted());
       
        return dto;
    }

    private MasterdataCustomerCategory convertToEntity(MasterdataCustomerCategoryDTO dto) {
        MasterdataCustomerCategory entity = new MasterdataCustomerCategory();
        entity.setCategoryId(dto.getCategoryId());
        entity.setCategoryName(dto.getCategoryName());
        entity.setDescription(dto.getDescription());
        entity.setTenantId(dto.getTenantId());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
     
       
        return entity;
    }



    private void validateCustomerCategory(MasterdataCustomerCategoryDTO dto, boolean isCreate) {
        if (dto.getTenantId() == null || dto.getTenantId() <= 0) {
            throw new BusinessException("Tenant ID cannot be null or zero");
        }

        if (dto.getCategoryName() == null || dto.getCategoryName().trim().isEmpty()) {
            throw new BusinessException("Category name cannot be null or empty");
        }

        if (dto.getCategoryName().length() > 2) {
            throw new BusinessException("Category name must be at most 4 characters");
        }

        if (dto.getDescription() != null && dto.getDescription().length() > 10) {
            throw new BusinessException("Description must be at most 100 characters");
        }

        if (isCreate) {
            if (dto.getCreatedBy() == null) {
                throw new BusinessException("CreatedBy user ID cannot be null during creation");
            }
            
            if (dto.getUpdatedBy() == null) {
                throw new BusinessException("UpdatedBy user ID cannot be null during update");
            }
        }
    }
}






















