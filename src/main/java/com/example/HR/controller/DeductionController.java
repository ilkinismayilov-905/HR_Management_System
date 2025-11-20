package com.example.HR.controller;

import com.example.HR.dto.payroll.addiction.AdditionResponseDTO;
import com.example.HR.dto.payroll.deduction.DeductionRequestDTO;
import com.example.HR.dto.payroll.deduction.DeductionResponseDTO;
import com.example.HR.enums.payroll.DeductionRate;
import com.example.HR.service.DeductionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/deduction")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class DeductionController {

    private final DeductionService deductionService;

    @Operation(
            summary = "Get all deductions",
            description = "Retrieves all deductions from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deductions retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No deductions found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping()
    public ResponseEntity<Map<String,Object>> findAllDeductions() {
        log.info("REST request to find all deductions");
        Map<String,Object> response = new HashMap<>();
        try {
            List<DeductionResponseDTO> dto = deductionService.getAll();

            response.put("success", true);
            response.put("data", dto);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalArgumentException e){
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception e) {

            response.put("success", false);
            response.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Add a new deduction",
            description = "Creates a new deduction with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Deduction created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Map<String,Object>> createDeduction(@RequestBody DeductionRequestDTO dto) {
        log.info("REST request to create deduction {}", dto);
        Map<String,Object> response = new HashMap<>();

        try {
            DeductionResponseDTO deduction =  deductionService.createDeduction(dto);
            response.put("success", true);
            response.put("data", deduction);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e){
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }

    @Operation(
            summary = "Get deduction by ID",
            description = "Retrieves deduction by ID from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deduction retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No deduction found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> findDeductionById(@PathVariable Long id) {
        log.info("REST request to find deduction by id {}", id);
        Map<String,Object> response = new HashMap<>();
        try{
            DeductionResponseDTO deduction = deductionService.getDeductionById(id);

            response.put("success", true);
            response.put("data", deduction);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e){
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Update deduction",
            description = "Update an existing deduction by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deduction updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Deduction not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("{id}")
    public ResponseEntity<Map<String,Object>>  updateDeduction(@PathVariable Long id, @RequestBody DeductionRequestDTO dto) {
        log.info("REST request to update deduction {}", dto);
        Map<String,Object> response = new HashMap<>();

        try {
            DeductionResponseDTO deduction =  deductionService.updateDeduction(id,dto);

            response.put("success", true);
            response.put("data", deduction);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e){
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Delete deduction by ID",
            description = "Deletes deduction by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deduction was deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No deduction found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Map<String,Object>>  deleteDeduction(@PathVariable Long id) {
        log.info("REST request to delete deduction {}", id);
        Map<String,Object> response = new HashMap<>();

        try {
            DeductionResponseDTO deduction = deductionService.getDeductionById(id);
            deductionService.deleteDeduction(id);

            response.put("success", true);
            response.put("data", deduction);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e){
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @Operation(
            summary = "Get deductions between dates",
            description = "Retrieves deductions created between the specified start and end dates from the system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deductions retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date parameters"),
            @ApiResponse(responseCode = "404", description = "No deductions found in the given date range"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/between")
    public ResponseEntity<Map<String,Object>> getBetweenDates(@RequestParam("start") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate start,
                                                              @RequestParam("end") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate end){
        log.info("REST request to get deduction between: {} and {}", start, end);

        Map<String,Object> response = new HashMap<>();
        try {
            List<DeductionResponseDTO> events = deductionService.getBetweenDates(start, end);
            response.put("success", true);
            response.put("data", events);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            log.error("Error fetching events between dates: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Failed to fetch events: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String,Object>> getByCategory(@PathVariable String category){
        log.info("REST request to get deductions by category {}", category);
        Map<String,Object> response = new HashMap<>();

        try {
            DeductionRate enumRate;
            try {
                enumRate = DeductionRate.valueOf(category.toUpperCase());

            }catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Category: " + category);
            }

            List<DeductionResponseDTO> list = deductionService.getByRate(enumRate);
            response.put("success", true);
            response.put("data", list);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to fetch events: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
