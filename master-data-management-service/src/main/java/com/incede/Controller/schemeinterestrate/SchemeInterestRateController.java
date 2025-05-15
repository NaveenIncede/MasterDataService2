package com.incede.Controller.schemeinterestrate;

import com.incede.Dto.schemeinterestrate.SchemeInterestRateDto;
import com.incede.Model.schemeinterestrate.SchemeInterestRate;
import com.incede.Service.schemeinterestrate.SchemeInterestRateService;
import com.incede.Exception.ApiResponse;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/masterdata/schemeinterestrate")
@CrossOrigin(origins = "*")
public class SchemeInterestRateController {

    private final SchemeInterestRateService service;

    public SchemeInterestRateController(SchemeInterestRateService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SchemeInterestRateDto>>> getAllInterestRates() {
        List<SchemeInterestRateDto> rates = service.getAllInterestRates();
        return ResponseEntity.ok(new ApiResponse<>("success",rates,"All interest rates fetched successfully"));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<SchemeInterestRateDto>>> getAllActiveInterestRates() {
        List<SchemeInterestRateDto> activeRates = service.getAllActiveInterestRates();
        return ResponseEntity.ok(new ApiResponse<>("success", activeRates, "Active interest rates fetched successfully"));
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<ApiResponse<List<SchemeInterestRateDto>>> getInterestRatesByTenantId(@PathVariable Integer tenantId) {
        List<SchemeInterestRateDto> dtoList = service.getInterestRatesByTenantId(tenantId);
        return ResponseEntity.ok(new ApiResponse<>("success",  dtoList, "Interest rates fetched for tenant"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SchemeInterestRate>> getInterestRateById(@PathVariable Integer id) {
        SchemeInterestRate rate = service.getInterestRateById(id);
        return ResponseEntity.ok(new ApiResponse<>("success", rate, "Interest rate fetched successfully"));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<SchemeInterestRate>> createInterestRate(@RequestBody SchemeInterestRateDto dto) {
        SchemeInterestRate created = service.createInterestRate(dto);
        return ResponseEntity.ok(new ApiResponse<>("success", created, "Interest rate created successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<SchemeInterestRate>> updateInterestRate(@PathVariable Integer id, @RequestBody SchemeInterestRateDto dto) {
        SchemeInterestRate updated = service.updateInterestRate(id, dto);
        return ResponseEntity.ok(new ApiResponse<>("success", updated, "Interest rate updated"));
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteInterestRate(@PathVariable Integer id) {
        service.deleteInterestRate(id);
        return ResponseEntity.ok(new ApiResponse<>("success", "Interest rate soft-deleted successfully", "Deleted"));
    }
    
  

    @PostMapping("/match-slab")
    public ResponseEntity<ApiResponse<SchemeInterestRate>> getMatchingSlab(@RequestBody Map<String, Object> request) {
        Integer schemeId = (Integer) request.get("schemeId");
        BigDecimal loanAmount = new BigDecimal(request.get("loanAmount").toString());
        BigDecimal period = new BigDecimal(request.get("period").toString());
        String periodType = (String) request.get("periodType");
        Boolean loanPastDue = request.get("loanPastDue") != null ? Boolean.parseBoolean(request.get("loanPastDue").toString()) : null;
        Boolean recalcInterest = request.get("recalcInterest") != null ? Boolean.parseBoolean(request.get("recalcInterest").toString()) : null;
        LocalDate asOnDate = LocalDate.parse(request.get("asOnDate").toString());

        SchemeInterestRate slab = service.getMatchingInterestRateSlab(schemeId, loanAmount, period, periodType, loanPastDue, recalcInterest, asOnDate);

        return ResponseEntity.ok(new ApiResponse<>("success", slab, "Matching interest rate slab found"));
    }

   


    
    
}
