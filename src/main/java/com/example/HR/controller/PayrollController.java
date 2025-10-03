package com.example.HR.controller;

import com.example.HR.dto.payroll.EmployeeSalaryRequestDTO;
import com.example.HR.dto.payroll.EmployeeSalaryResponseDTO;
import com.example.HR.dto.payroll.PayrollItemDTO;
import com.example.HR.entity.payroll.PayrollItem;
import com.example.HR.enums.PayrollCategory;
import com.example.HR.service.PayrollService;
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

    @PostMapping("/employee-salary")
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

    @GetMapping("/employee-salary/{employeeId}")
    public ResponseEntity<Map<String,Object>> getEmployeeSalary(@PathVariable Long employeeId) {

        try {
            EmployeeSalaryResponseDTO  salary = service.getEmployeeSalaryByEmployeeId(employeeId);
            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",salary);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


    }

    @GetMapping("/employee-salary/{employeeId}/history")
    public ResponseEntity<List<EmployeeSalaryResponseDTO>> getEmployeeSalaryHistory(@PathVariable Long employeeId) {
        List<EmployeeSalaryResponseDTO> salaries = service.getEmployeeSalaryHistory(employeeId);
        return ResponseEntity.ok(salaries);
    }

    // Update employee salary
    @PutMapping("/employee-salary/{salaryId}")
    public ResponseEntity<EmployeeSalaryResponseDTO > updateEmployeeSalary(
            @PathVariable Long salaryId,
            @Valid @RequestBody EmployeeSalaryRequestDTO requestDTO) {
        EmployeeSalaryResponseDTO  updatedSalary = service.updateEmployeeSalary(salaryId, requestDTO);
        return ResponseEntity.ok(updatedSalary);
    }

    // Delete employee salary
    @DeleteMapping("/employee-salary/{salaryId}")
    public ResponseEntity<Void> deleteEmployeeSalary(@PathVariable Long salaryId) {
        service.deleteEmployeeSalary(salaryId);
        return ResponseEntity.noContent().build();
    }

    // Create payroll item
    @PostMapping("/items")
    public ResponseEntity<PayrollItem> createPayrollItem(@RequestBody PayrollItem item) {
        PayrollItem createdItem = service.createPayrollItem(item);
        return ResponseEntity.ok(createdItem);
    }

    // Update payroll item
    @PutMapping("/items/{itemId}")
    public ResponseEntity<PayrollItem> updatePayrollItem(
            @PathVariable Long itemId,
            @RequestBody PayrollItem item) {
        PayrollItem updatedItem = service.updatePayrollItem(itemId, item);
        return ResponseEntity.ok(updatedItem);
    }

    // Delete payroll item
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deletePayrollItem(@PathVariable Long itemId) {
        service.deletePayrollItem(itemId);
        return ResponseEntity.noContent().build();
    }

    // Get payroll items by category
    @GetMapping("/items/category/{category}")
    public ResponseEntity<List<PayrollItemDTO>> getPayrollItemsByCategory(@PathVariable PayrollCategory category) {
        List<PayrollItemDTO> items = service.getPayrollItemsByCategory(category);
        return ResponseEntity.ok(items);
    }
}
