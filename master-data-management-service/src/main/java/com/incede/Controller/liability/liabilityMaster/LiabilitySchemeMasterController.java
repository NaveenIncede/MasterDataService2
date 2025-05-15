package com.incede.Controller.liability.liabilityMaster;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.incede.Dto.liability.liabilityMaster.LiabilitySchemeMasterDto;
import com.incede.Response.response.responseBody.ResponseWrapper;
import com.incede.Service.liability.liabilityMaster.LiabilitySchemeMasterService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/v1/liability-scheme-master")
public class LiabilitySchemeMasterController {

    private final LiabilitySchemeMasterService service;
    
    public LiabilitySchemeMasterController(LiabilitySchemeMasterService service){
    	this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseWrapper<LiabilitySchemeMasterDto>> saveOrUpdate(
            @Valid @RequestBody LiabilitySchemeMasterDto dto) {
        ResponseWrapper<LiabilitySchemeMasterDto> response = service.saveOrUpdate(dto);
        return ResponseEntity.status(201).body(response);  // 201 Created
    }


    @GetMapping("/")
    public ResponseEntity<ResponseWrapper<List<LiabilitySchemeMasterDto>>> getAllActive() {
        ResponseWrapper<List<LiabilitySchemeMasterDto>> response = service.getAllActive();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<LiabilitySchemeMasterDto>> getById(@PathVariable Integer id) {
        ResponseWrapper<LiabilitySchemeMasterDto> response = service.getById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseWrapper<List<LiabilitySchemeMasterDto>>> getByProductId(@PathVariable Integer productId) {
        ResponseWrapper<List<LiabilitySchemeMasterDto>> response = service.getByProductId(productId);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Boolean>> softDelete(@PathVariable Integer id) {
        ResponseWrapper<Boolean> response = service.softDelete(id);
        return ResponseEntity.ok(response);
    }
    
}
