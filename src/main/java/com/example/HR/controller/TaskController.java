package com.example.HR.controller;

import com.example.HR.dto.task.TaskRequestDTO;
import com.example.HR.dto.task.TaskResponseDTO;
import com.example.HR.entity.task.Task;
import com.example.HR.service.TaskService;
import com.example.HR.validation.Create;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> createTask(@Validated(Create.class) @RequestBody TaskRequestDTO dto){

        try {

            TaskResponseDTO task = taskService.create(dto);

            Map<String,Object> response = new HashMap<>();
            response.put("success:",true);
            response.put("data:",task);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {

            Map<String,Object> response = new HashMap<>();
            response.put("success:",false);
            response.put("message:",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);ot
        }

    }
}
