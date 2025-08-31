package com.example.HR.controller;

import com.example.HR.dto.calendar.CalendarRequestDTO;
import com.example.HR.dto.calendar.CalendarResponseDTO;
import com.example.HR.dto.tool.ToolResponseDTO;
import com.example.HR.service.CalendarService;
import com.example.HR.validation.Create;
import com.example.HR.validation.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.ProjectedPayload;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Calendar;
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

    @Operation(
            summary = "Add a new calendar event",
            description = "Creates a new calendar event with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(
            summary = "Get all calendar events",
            description = "Retrieves all calendar events from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No events found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

    @Operation(
            summary = "Update event",
            description = "Update an existing event by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event updated successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,Object>> update(@Validated(Update.class)
                                                     @PathVariable Long id,
                                                     @RequestBody CalendarRequestDTO dto){
        log.info("REST request to update event with ID: {}", id);

        try {
            CalendarResponseDTO updatedEvent = service.updateEvent(id,dto);

            Map<String, Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",updatedEvent);

            return ResponseEntity.ok(response);
        } catch (Exception e){
            log.error("Error updating event: {}", e.getMessage());

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message","Failed to update event: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(summary = "Get event by ID", description = "Retrieve a specific event by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getById(@PathVariable Long id){
        log.info("REST request to get event with ID: {}", id);

        try {
            CalendarResponseDTO dto = service.getById(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", dto);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching event: {}", e.getMessage());

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message", "Event not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(summary = "Get event by date", description = "Retrieve a specific event by its date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Event not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/date/{date}")
    public ResponseEntity<Map<String,Object>> getByDate(@PathVariable  @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date){
        log.info("REST request to get event with date: {}", date);

        try {

            CalendarResponseDTO calendar = service.getByDate(date);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", calendar);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching event: {}", e.getMessage());

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message", "Event not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }



    }

    @GetMapping("/between")
    public ResponseEntity<Map<String,Object>> getBetweenDates(@RequestParam("start") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate start,
                                                              @RequestParam("end") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate end){
        log.info("REST request to get events between: {} and {}", start, end);

        Map<String,Object> response = new HashMap<>();
        try {
            List<CalendarResponseDTO> events = service.getBetweenDates(start, end);
            response.put("success", true);
            response.put("data", events);
            response.put("count", events.size());
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

    @GetMapping("/upcoming")
    public ResponseEntity<Map<String,Object>> getUpcomingEvents(){

        try {

            List<CalendarResponseDTO> upcomingEvents = service.getUpcomingEvents();

            Map<String,Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", upcomingEvents);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching event: {}", e.getMessage());

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message", "Event not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }
}
