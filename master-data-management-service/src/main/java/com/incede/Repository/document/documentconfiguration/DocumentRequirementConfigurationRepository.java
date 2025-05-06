package com.incede.Repository.document.documentconfiguration;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incede.Model.document.documentconfiguration.DocumentRequirementConfiguration;

@Repository
public interface DocumentRequirementConfigurationRepository 
        extends JpaRepository<DocumentRequirementConfiguration, Integer> {

    /**
     * Check if a document requirement exists for a given product and document type.
     */
    boolean existsByProductIdAndDocumentType(Integer productId, String documentType);

    /**
     * Find a configuration by its generated UUID identity.
     */
    Optional<DocumentRequirementConfiguration> findByIdentity(UUID identity);

    /**
     * Fetch all active configurations for a given product.
     */
    List<DocumentRequirementConfiguration> findByProductIdAndIsActiveTrue(Integer productId);

    /**
     * Fetch all mandatory documents for a given product.
     */
    List<DocumentRequirementConfiguration> findByProductIdAndIsMandatoryTrue(Integer productId);
}
