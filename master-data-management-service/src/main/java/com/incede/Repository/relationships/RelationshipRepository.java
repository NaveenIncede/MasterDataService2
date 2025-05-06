package com.incede.Repository.relationships;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incede.Model.relationships.Relationship;

public interface RelationshipRepository extends JpaRepository<Relationship, Integer> {

    // Case-insensitive check for duplicate relationships based on tenantId and relationship
    Optional<Relationship> findByTenantIdAndRelationshipIgnoreCase(Integer tenantId, String relationship);
    boolean existsByTenantIdAndRelationshipIgnoreCaseAndIsDeletedFalse(Integer tenantId, String relationship);

}
