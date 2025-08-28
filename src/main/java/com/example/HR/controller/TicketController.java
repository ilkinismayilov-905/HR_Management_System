package com.example.HR.controller;

import com.example.HR.dto.ticket.TicketRequestDTO;
import com.example.HR.dto.ticket.TicketResponseDTO;
import com.example.HR.entity.Ticket;
import com.example.HR.service.TicketService;
import com.example.HR.validation.Create;
import com.example.HR.validation.Update;
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
@RequestMapping("/ticket")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> add(@RequestBody @Validated(Create.class) TicketRequestDTO dto){
        try {
            TicketResponseDTO ticket = ticketService.create(dto);

            Map<String,Object> response = new HashMap<>();
            response.put("success:", true);
            response.put("data:",ticket);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){

            Map<String,Object> response = new HashMap<>();
            response.put("success:", false);
            response.put("message:","Failed to add ticket: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Map<String,Object>> getAll(){
        try {
            List<TicketResponseDTO> list = ticketService.getAll();

            Map<String,Object> response = new HashMap<>();
            response.put("success:", true);
            response.put("data:",list);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){

            Map<String,Object> response = new HashMap<>();
            response.put("success:", false);
            response.put("message:","Failed to fetch tickets");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,Object>> update(@Validated(Update.class) @PathVariable Long id,
                                                     @RequestBody TicketRequestDTO dto){
        log.info("REST request to update ticket with ID: {}", id);

        try {
            TicketResponseDTO ticket = ticketService.update(id,dto);

            Map<String,Object> response = new HashMap<>();
            response.put("success:",true);
            response.put("data:",ticket);
            response.put("message:","Ticket updated successfully");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success:", false);
            response.put("message:","Failed to update ticket");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getByID(@PathVariable Long id) {
        try {
            TicketResponseDTO list = ticketService.getById(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success:", true);
            response.put("data:", list);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {

            Map<String, Object> response = new HashMap<>();
            response.put("success:", false);
            response.put("message:", "Failed to fetch ticket by id: " + id);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
