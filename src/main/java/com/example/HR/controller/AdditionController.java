package com.example.HR.controller;

import com.example.HR.dto.payroll.addiction.AdditionRequestDTO;
import com.example.HR.dto.payroll.addiction.AdditionResponseDTO;
import com.example.HR.enums.payroll.AdditionCategory;
import com.example.HR.service.AdditionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/additions")
@RequiredArgsConstructor
@Slf4j
public class AdditionController {

    private final AdditionService additionService;

    @Operation(
            summary = "Get all addictions",
            description = "Retrieves all addictions from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addictions retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No addictions found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Map<String,Object>> listAll() {
        log.info("REST request to get all list");

        try {
            List<AdditionResponseDTO> list = additionService.getAllAdditions();
            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("list",list);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


    }

    @Operation(
            summary = "Get addiction by ID",
            description = "Retrieves addiction by ID from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addiction retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No addiction found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getById(@PathVariable Long id) {
        log.info("REST request to get an addition by ID");

        try {
            AdditionResponseDTO dto = additionService.getAdditionById(id);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("addition",dto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


    }

    @Operation(
            summary = "Add a new addiction",
            description = "Creates a new addiction with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Addiction created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<Map<String,Object>> create(@RequestBody @Validated AdditionRequestDTO request) {
        log.info("REST request to create addition");

        try {
            AdditionResponseDTO created = additionService.createAddition(request);
            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("addition",created);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


    }

    @Operation(
            summary = "Update addiction",
            description = "Update an existing addiction by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addiction updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Addiction not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long id,
                                                      @RequestBody @Validated AdditionRequestDTO request) {
        log.info("REST request to update addition");
        try {
            AdditionResponseDTO updated = additionService.updateAddition(id, request);
            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("addition",updated);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (Exception e){
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @Operation(
            summary = "Delete addiction by ID",
            description = "Deletes addiction by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addiction was deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No addiction found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable Long id) {
        log.info("REST request to delete addition");

        try {
            AdditionResponseDTO dto = additionService.getAdditionById(id);
            additionService.deleteAddition(id);
            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",dto);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @Operation(
            summary = "Get addiction by category",
            description = "Retrieves addiction by category from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addiction retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No addiction found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{category}")
        public ResponseEntity<Map<String,Object>> getByCategory(@PathVariable String category){
        log.info("REST request to get all additions by category");

        Map<String,Object> response = new HashMap<>();

        try {
            AdditionCategory enumCategory;
            try {
                enumCategory= AdditionCategory.valueOf(category.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Category: " + category);
            }

            List<AdditionResponseDTO> list = additionService.getAdditionsByCategory(enumCategory);
            response.put("data",list);
            response.put("success",true);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (IllegalArgumentException e) {

            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            log.error("Error fetching addiction by category: {}", e.getMessage());
            response.put("success", false);
            response.put("message", "Failed to fetch events: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @Operation(
            summary = "Get addictions between dates",
            description = "Retrieves addictions created between the specified start and end dates from the system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addictions retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date parameters"),
            @ApiResponse(responseCode = "404", description = "No addictions found in the given date range"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/between")
    public ResponseEntity<Map<String,Object>> getBetweenDates(@RequestParam("start") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate start,
                                                              @RequestParam("end") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate end){
        log.info("REST request to get events between: {} and {}", start, end);

        Map<String,Object> response = new HashMap<>();
        try {
            List<AdditionResponseDTO> events = additionService.getBetweenDates(start, end);
            response.put("success", true);
            response.put("data", events);
            return ResponseEntity.ok(response);
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

}
