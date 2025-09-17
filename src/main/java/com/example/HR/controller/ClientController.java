package com.example.HR.controller;

import com.example.HR.dto.client.ClientRequestDTO;
import com.example.HR.dto.client.ClientResponseDTO;
import com.example.HR.enums.ClientStatus;
import com.example.HR.enums.TaskStatus;
import com.example.HR.service.ClientService;
import com.example.HR.validation.Create;
import com.example.HR.validation.Update;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
@Slf4j
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> addClient(@Validated(Create.class) @RequestBody ClientRequestDTO dto){
        try {
            ClientResponseDTO client = service.save(dto);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",client);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Map<String,Object>> getAll(){
        try {
            List<ClientResponseDTO> list = service.getAll();

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",list);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getById(@PathVariable Long id){
        try {
            ClientResponseDTO client = service.getById(id);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",client);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Map<String,Object>> getByStatus(@PathVariable String status){
        try {
            ClientStatus enumStatus;
            try {
                enumStatus= ClientStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status: " + status);
            }
            List<ClientResponseDTO> client = service.getByStatus(enumStatus);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",client);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,Object>> update(@Validated(Update.class) @RequestBody ClientRequestDTO dto,
                                                     @PathVariable Long id){
        try {
            ClientResponseDTO client = service.update(id, dto);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",client);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,Object>> deleteById(@PathVariable Long id){
        try {
            ClientResponseDTO client = service.getById(id);

            service.deleteById(id);

            Map<String,Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",client);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
