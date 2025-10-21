package com.example.HR.controller;

import com.example.HR.dto.payroll.AdditionRequestDTO;
import com.example.HR.dto.payroll.AdditionResponseDTO;
import com.example.HR.service.AdditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/additions")
@RequiredArgsConstructor
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
}
