package com.example.HR.controller;

import com.example.HR.dto.calendar.CalendarRequestDTO;
import com.example.HR.dto.calendar.CalendarResponseDTO;
import com.example.HR.dto.tool.ToolResponseDTO;
import com.example.HR.service.CalendarService;
import com.example.HR.validation.Create;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calendar")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService service;

    @PostMapping("/addEvent")
    public ResponseEntity<Map<String,Object>> add(@Validated(Create.class) @RequestBody CalendarRequestDTO dto){
        log.info("REST request to add event: {}", dto.getEventName());

        try{
            CalendarResponseDTO calendarResponse = service.addEvent(dto);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",calendarResponse);
            response.put("message","Event added successfully");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message","Failed to add event: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Map<String,Object>> getAll(){

        try {
            List<CalendarResponseDTO> list = service.getAll();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", list);
            response.put("count", list.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching events: {}", e.getMessage());

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to fetch events: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
