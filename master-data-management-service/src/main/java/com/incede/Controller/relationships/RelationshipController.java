package com.incede.Controller.relationships;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.incede.Dto.relationships.RelationshipDto;
import com.incede.Model.relationships.Relationship;
import com.incede.Service.relationships.RelationshipService;

@RestController
@RequestMapping("/v1/masterdata/relationships")
public class RelationshipController {

    private final RelationshipService relationshipService;

    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    // Get all relationships
    @GetMapping("/getAll")
    public ResponseEntity<List<RelationshipDto>> getAllRelationships() {
        List<RelationshipDto> relationships = relationshipService.getAllRelationships();
        return ResponseEntity.ok(relationships);
    }

    // Get a relationship by ID
    @GetMapping("/{relationshipId}")
    public ResponseEntity<RelationshipDto> getRelationshipById(@PathVariable Integer relationshipId) {
        RelationshipDto relationship = relationshipService.getRelationship(relationshipId);
        return ResponseEntity.ok(relationship);
    }
    // Create a new relationship
    @PostMapping("/")
    public ResponseEntity<Relationship> createRelationship(@RequestBody RelationshipDto relationshipDto) {
        return ResponseEntity.status(201).body(relationshipService.createRelationship(relationshipDto));
    }

    // Update an existing relationship
    @PutMapping("/{relationshipId}")
    public ResponseEntity<Relationship> updateRelationship(@PathVariable Integer relationshipId,
                                                           @RequestBody RelationshipDto relationshipDto) {
        return ResponseEntity.ok(relationshipService.updateRelationship(relationshipId, relationshipDto));
    }

    // Delete a relationship
    @DeleteMapping("/{relationshipId}")
    public ResponseEntity<String> deleteRelationship(@PathVariable Integer relationshipId) {
        relationshipService.deleteRelationship(relationshipId);
        return ResponseEntity.ok("Relationship deleted successfully");
    }

}
