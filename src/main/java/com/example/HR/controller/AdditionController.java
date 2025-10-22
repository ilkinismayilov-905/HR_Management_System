package com.example.HR.controller;

import com.example.HR.dto.calendar.CalendarResponseDTO;
import com.example.HR.dto.payroll.AdditionRequestDTO;
import com.example.HR.dto.payroll.AdditionResponseDTO;
import com.example.HR.enums.TaskStatus;
import com.example.HR.enums.payroll.AdditionCategory;
import com.example.HR.service.AdditionService;
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

    @GetMapping
    public ResponseEntity<List<AdditionResponseDTO>> listAll() {
        List<AdditionResponseDTO> list = additionService.getAllAdditions();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdditionResponseDTO> getOne(@PathVariable Long id) {
        AdditionResponseDTO dto = additionService.getAdditionById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AdditionResponseDTO> create(@RequestBody @Validated AdditionRequestDTO request) {
        AdditionResponseDTO created = additionService.createAddition(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdditionResponseDTO> update(@PathVariable Long id,
                                                      @RequestBody @Validated AdditionRequestDTO request) {
        AdditionResponseDTO updated = additionService.updateAddition(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        additionService.deleteAddition(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{category}")
        public ResponseEntity<Map<String,Object>> getByCategory(@PathVariable String category){
        try {
            AdditionCategory enumCategory;
            try {
                enumCategory= AdditionCategory.valueOf(category.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Category: " + category);
            }

            List<AdditionResponseDTO> list = additionService.getAdditionsByCategory(enumCategory);

            Map<String,Object> response = new HashMap<>();
            response.put("data",list);
            response.put("success",true);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (IllegalArgumentException e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

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
