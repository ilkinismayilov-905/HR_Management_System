package com.example.HR.controller;

import com.example.HR.dto.EducationInfoDTO;
import com.example.HR.dto.EmployeeInformationDTO;
import com.example.HR.dto.tool.ToolRequestDTO;
import com.example.HR.dto.tool.ToolResponseDTO;
import com.example.HR.service.EducationInfoService;
import com.example.HR.service.EmployeeService;
import com.example.HR.service.ToolService;
import com.example.HR.validation.Create;
import com.example.HR.validation.Update;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/employeeDetails")
@CrossOrigin(origins = "*")
public class EmployeeDetailsController {

    private final EmployeeService employeeService;
    private final EducationInfoService infoService;
    private final ToolService toolService;

    @Autowired
    public EmployeeDetailsController(EmployeeService employeeService, EducationInfoService infoService, ToolService toolService) {
        this.employeeService = employeeService;
        this.infoService = infoService;
        this.toolService = toolService;
    }

    @Operation(summary = "Get employee information",
            description = "Returns a list of employee informations"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employee information retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employee found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/employeeInfo/{employeeID}")
    public ResponseEntity<Optional<EmployeeInformationDTO>> viewEmployeesByJoinDate(@PathVariable String employeeID) throws MalformedURLException {
        Optional<EmployeeInformationDTO> employee = employeeService.getByEmployeeID(employeeID);


        return ResponseEntity.ok(employee);
    }


    @Operation(
            summary = "Add education information",
            description = "Adds information about education"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Information added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/addInfo")
    public ResponseEntity<EducationInfoDTO> addInformatoin(@RequestBody @Validated(Create.class) EducationInfoDTO dto) throws IOException {
        EducationInfoDTO information = infoService.save(dto);
        return  ResponseEntity.status(HttpStatus.OK).body(information);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EducationInfoDTO>> getAll() throws MalformedURLException {
        List<EducationInfoDTO> list = infoService.getAll();
        return ResponseEntity.ok(list);
    }


    @Operation(summary = "Delete information",
            description = "Deletes an informatoin by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Information deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Information not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        infoService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update informatoin",
            description = "Updates an existing info"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Information updated successfully"),
            @ApiResponse(responseCode = "404", description = "Information not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Optional<EducationInfoDTO>> update(@PathVariable Long id, @RequestBody @Validated(Update.class) EducationInfoDTO dto){
        infoService.update(id, dto);

        return ResponseEntity.ok(infoService.getById(id));

    }

    @PostMapping("/addExperience")
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ToolRequestDTO dto){
        log.info("REST request to create tool: {}", dto.getName());

        try {
            ToolResponseDTO createdTool = toolService.createTool(dto);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Tool created successfully");
            response.put("data", createdTool);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error creating tool: {}", e.getMessage());

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to create tool: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @GetMapping("/getAllExperiences")
    public ResponseEntity<Map<String, Object>> getAllToolsWithSkills() {
        log.info("REST request to get all tools with skills");

        try {
            List<ToolResponseDTO> tools = toolService.getToolsWithAllSkills();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", tools);
            response.put("count", tools.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching all tools with skills: {}", e.getMessage());

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Failed to fetch tools: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



}
