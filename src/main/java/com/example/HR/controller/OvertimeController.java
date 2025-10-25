package com.example.HR.controller;

import com.example.HR.dto.payroll.OvertimeRequestDTO;
import com.example.HR.dto.payroll.OvertimeResponseDTO;
import com.example.HR.dto.payroll.PayrollItemDTO;
import com.example.HR.dto.project.ProjectResponseDTO;
import com.example.HR.service.OvertimeService;
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

    @GetMapping("/getAll")
    public ResponseEntity<Map<String,Object>> getAll() {
        log.info("REST request to get all overtime data");
        try {
            List<OvertimeResponseDTO> items = overtimeService.getAllOvertimes();

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

    @PostMapping()
    public ResponseEntity<Map<String,Object>> create(@RequestBody OvertimeRequestDTO overtimeDTO) {
        log.info("REST request to create overtime");
        try {

            OvertimeResponseDTO project = overtimeService.createOvertime(overtimeDTO);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",project);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getOvertimeById(@PathVariable Long id){
        log.info("REST request to get overtime by id");
        try {
            OvertimeResponseDTO overtime = overtimeService.getOvertimeById(id);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",overtime);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,Object>> update(@PathVariable Long id, @RequestBody OvertimeRequestDTO overtimeDTO) {
        log.info("REST request to update overtime by id");

        try {
            OvertimeResponseDTO dto = overtimeService.updateOvertime(id, overtimeDTO);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",dto);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }  catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> delete(@PathVariable Long id){
        log.info("REST request to delete overtime by id");
        try {
            OvertimeResponseDTO overtime = overtimeService.getOvertimeById(id);

            overtimeService.deleteOvertime(id);
            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",overtime);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
