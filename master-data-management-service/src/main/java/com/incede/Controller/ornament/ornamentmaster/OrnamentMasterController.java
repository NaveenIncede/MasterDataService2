package com.incede.Controller.ornament.ornamentmaster;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.ornament.ornamentmaster.OrnamentMasterDto;
import com.incede.Model.ornament.ornamentmaster.OrnamentMaster;
import com.incede.Service.ornament.ornamentmaster.OrnamentMasterService;

@RestController
@RequestMapping("/v1/masterdata/ornament/ornamentmaster")
public class OrnamentMasterController {

    private final OrnamentMasterService ornamentMasterService;

    public OrnamentMasterController(OrnamentMasterService ornamentMasterService) {
        this.ornamentMasterService = ornamentMasterService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrnamentMasterDto>> getAllOrnaments() {
        List<OrnamentMasterDto> ornaments = ornamentMasterService.getAllOrnaments();
        return ResponseEntity.ok(ornaments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrnamentMasterDto> getOrnamentById(@PathVariable Integer id) {
        return ResponseEntity.ok(ornamentMasterService.getOrnamentById(id));
    }
    @GetMapping("/identity/{identity}")
    public ResponseEntity<OrnamentMasterDto> getOrnamentByIdentity(@PathVariable UUID identity) {
        return ResponseEntity.ok(ornamentMasterService.getOrnamentByIdentity(identity));
    }

    @PostMapping("/")
    public ResponseEntity<OrnamentMaster> createOrnament(@RequestBody OrnamentMasterDto dto) {
        OrnamentMaster ornament = ornamentMasterService.createOrnament(dto);
        return ResponseEntity.status(201).body(ornament);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrnamentMaster> updateOrnament(
            @PathVariable Integer id, @RequestBody OrnamentMasterDto dto) {
        OrnamentMaster updatedOrnament = ornamentMasterService.updateOrnament(id, dto);
        return ResponseEntity.ok(updatedOrnament);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrnament(@PathVariable Integer id) {
        ornamentMasterService.deleteOrnament(id);
        return ResponseEntity.ok("Ornament deleted successfully");
    }

}
