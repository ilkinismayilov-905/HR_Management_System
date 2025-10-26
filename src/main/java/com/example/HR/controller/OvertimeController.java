package com.example.HR.controller;

import com.example.HR.dto.payroll.overtime.OvertimeRequestDTO;
import com.example.HR.dto.payroll.overtime.OvertimeResponseDTO;
import com.example.HR.service.OvertimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/overtime")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class OvertimeController {
    private final OvertimeService overtimeService;

    @Operation(
            summary = "Get all overtimes",
            description = "Retrieves all overtimes from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Overtimes retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No overtimes found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAll")
    public ResponseEntity<Map<String,Object>> getAll() {
        log.info("REST request to get all overtime data");
        Map<String,Object> response = new HashMap<>();
        try {
            List<OvertimeResponseDTO> items = overtimeService.getAllOvertimes();
            response.put("success",true);
            response.put("data",items);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalArgumentException e) {

            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }


    @Operation(
            summary = "Add a new overtime",
            description = "Creates a new overtime with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Overtime created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping()
    public ResponseEntity<Map<String,Object>> create(@RequestBody OvertimeRequestDTO overtimeDTO) {
        log.info("REST request to create overtime");
        Map<String,Object> response = new HashMap<>();

        try {
            OvertimeResponseDTO project = overtimeService.createOvertime(overtimeDTO);

            response.put("success",true);
            response.put("data",project);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {

            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }


    @Operation(
            summary = "Get overtime by ID",
            description = "Retrieves overtime by ID from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Overtime retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No overtime found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getOvertimeById(@PathVariable Long id){
        log.info("REST request to get overtime by id");
        Map<String,Object> response = new HashMap<>();

        try {
            OvertimeResponseDTO overtime = overtimeService.getOvertimeById(id);

            response.put("success",true);
            response.put("data",overtime);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {

            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @Operation(
            summary = "Update overtime",
            description = "Update an existing overtime by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Overtime updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Overtime not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long id, @RequestBody OvertimeRequestDTO overtimeDTO) {
        log.info("REST request to update overtime by id");
        Map<String,Object> response = new HashMap<>();

        try {
            OvertimeResponseDTO dto = overtimeService.updateOvertime(id, overtimeDTO);

            response.put("success",true);
            response.put("data",dto);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }  catch (IllegalArgumentException e) {

            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Delete overtime by ID",
            description = "Deletes overtime by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Overtime was deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No overtime found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable Long id){
        log.info("REST request to delete overtime by id");
        Map<String,Object> response = new HashMap<>();
        try {
            OvertimeResponseDTO overtime = overtimeService.getOvertimeById(id);

            overtimeService.deleteOvertime(id);
            response.put("success",true);
            response.put("data",overtime);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (IllegalArgumentException e) {

            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
