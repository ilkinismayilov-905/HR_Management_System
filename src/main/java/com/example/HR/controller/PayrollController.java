package com.example.HR.controller;

import com.example.HR.dto.payroll.addictionSalary.EmployeeSalaryRequestDTO;
import com.example.HR.dto.payroll.addictionSalary.EmployeeSalaryResponseDTO;
import com.example.HR.dto.payroll.PayrollItemDTO;
import com.example.HR.entity.payroll.PayrollItem;
import com.example.HR.enums.payroll.PayrollCategory;
import com.example.HR.service.PayrollService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payroll")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class PayrollController {
    private final PayrollService service;

    @Operation(
            summary = "Get all payroll",
            description = "Retrieves all payroll from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payroll retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No payrolls found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAll")
    public ResponseEntity<Map<String,Object>> getPayrollItems() {
        log.info("REST request to get all payroll items");
        try {
            List<PayrollItemDTO> items = service.getPayrollItems();

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",items);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PostMapping("/employeeSalary")
    public ResponseEntity<Map<String,Object>> createEmployeeSalary( @Valid @RequestBody EmployeeSalaryRequestDTO requestDTO) {
        log.info("REST request to get create new employee salary");
        try {
            EmployeeSalaryResponseDTO salary = service.createEmployeeSalary(requestDTO);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",salary);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @Operation(
            summary = "Get payroll by ID",
            description = "Retrieves payroll by ID from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payroll retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No payroll found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getByID(@PathVariable Long id) {
        log.info("REST request to get payroll by ID");
        Map<String,Object> response = new HashMap<>();

        try {
            EmployeeSalaryResponseDTO  salary = service.getEmployeeSalaryByEmployeeId(id);
            response.put("success",true);
            response.put("data",salary);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalArgumentException e) {

            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }

    @GetMapping("/{id}/history")
    public ResponseEntity<Map<String,Object>> getEmployeeSalaryHistory(@PathVariable Long id) {
        log.info("REST request to get employee salary history");
        Map<String,Object> response = new HashMap<>();

        try{
            List<EmployeeSalaryResponseDTO> salaries = service.getEmployeeSalaryHistory(id);
            response.put("success",true);
            response.put("data",salaries);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalArgumentException e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Update payroll",
            description = "Update an existing payroll by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payroll updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Payroll not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{salaryId}")
    public ResponseEntity<Map<String,Object>> updateEmployeeSalary(
            @PathVariable Long salaryId,
            @Valid @RequestBody EmployeeSalaryRequestDTO requestDTO) {
        log.info("REST request to update employee salary");
        Map<String,Object> response = new HashMap<>();

        try {
            EmployeeSalaryResponseDTO  updatedSalary = service.updateEmployeeSalary(salaryId, requestDTO);
            response.put("success",true);
            response.put("data",updatedSalary);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @Operation(
            summary = "Delete payroll by ID",
            description = "Deletes payroll by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payroll was deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No payroll found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> deleteEmployeeSalary(@PathVariable Long id) {
        log.info("REST request to delete payroll by ID");
        Map<String,Object> response = new HashMap<>();

        try {
            EmployeeSalaryResponseDTO  salary = service.getEmployeeSalaryByEmployeeId(id);
            service.deleteEmployeeSalary(id);
            response.put("success",true);
            response.put("data",salary);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Add a new payroll",
            description = "Creates a new payroll with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payroll created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/payrollItem")
    public ResponseEntity<Map<String,Object>> createPayrollItem(@RequestBody PayrollItem item) {
        log.info("REST request to create payroll item");
        Map<String,Object> response = new HashMap<>();

        try {
            PayrollItem createdItem = service.createPayrollItem(item);
            response.put("success",true);
            response.put("data",createdItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Update payroll",
            description = "Update an existing payroll by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payroll updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Payroll not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> updatePayrollItem(
            @PathVariable Long id,
            @RequestBody PayrollItem item) {
        log.info("REST request to update payroll item");
        Map<String,Object> response = new HashMap<>();

        try {
            PayrollItem updatedItem = service.updatePayrollItem(id, item);
            response.put("success",true);
            response.put("data",updatedItem);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalArgumentException e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Delete payrollItem by ID",
            description = "Deletes payrollItem by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PayrollItem was deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No payrollItem found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/item/{id}")
    public ResponseEntity<Map<String,Object>> deletePayrollItem(@PathVariable Long id) {
        log.info("REST request to delete payroll item");
        Map<String,Object> response = new HashMap<>();

        try {
            service.deletePayrollItem(id);
            response.put("success",true);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalArgumentException e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(
            summary = "Get payrollItem by category",
            description = "Retrieves payrollItem by category from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PayrollItem retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No payrollItem found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/items/{category}")
    public ResponseEntity<Map<String,Object>> getPayrollItemsByCategory(@PathVariable PayrollCategory category) {
        log.info("REST request to get payroll items by category");
        Map<String,Object> response = new HashMap<>();

        try {
            List<PayrollItemDTO> items = service.getPayrollItemsByCategory(category);
            response.put("success",true);
            response.put("data",items);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalArgumentException e) {
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
}
