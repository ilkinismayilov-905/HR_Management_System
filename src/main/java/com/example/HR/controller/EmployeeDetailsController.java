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
@CrossOrigin
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


    @GetMapping("/employeeInfo/{employeeID}")
    public ResponseEntity<EmployeeInformationDTO> viewEmployeesByJoinDate(@PathVariable String employeeID) throws MalformedURLException {
        EmployeeInformationDTO employee = employeeService.getByEmployeeID(employeeID);


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
    public ResponseEntity<EducationInfoDTO> addInformation(@RequestBody @Validated(Create.class) EducationInfoDTO dto) throws IOException {
        EducationInfoDTO information = infoService.save(dto);
        return  ResponseEntity.status(HttpStatus.OK).body(information);
    }

    @Operation(summary = "Get employee information",
            description = "Returns a list of employee information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employee information retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employee found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<EducationInfoDTO>> getAll() throws MalformedURLException {
        List<EducationInfoDTO> list = infoService.getAll();
        return ResponseEntity.ok(list);
    }


    @Operation(summary = "Delete information",
            description = "Deletes an information by ID"
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

    @Operation(summary = "Update information",
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

    //------------------------------------------------------------------------------------------------------------------
    //EXPERIENCE

    @Operation(
            summary = "Add experience",
            description = "Adds information about experience"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Information added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/experience/add")
    public ResponseEntity<Map<String, Object>> create(@Validated(Create.class) @RequestBody ToolRequestDTO dto){
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

    @Operation(
            summary = "Get all experiences",
            description = "Views all experiences"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of experiences retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/experience/getAll")
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


    @Operation(
            summary = "Get tools by skill",
            description = "Verilən skill əsasında hansı tool-ların istifadə olunduğunu qaytarır"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of tools retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid skill parameter"),
            @ApiResponse(responseCode = "404", description = "No tools found for given skill"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/experience/skill/{skill}")
    public ResponseEntity<Map<String,Object>> getToolsBySkill(@PathVariable String skill){
        log.info("REST request to get tools by skill: {}", skill);

        try {
            List<ToolResponseDTO> tools = toolService.getToolsBySkill(skill);

            Map<String,Object> response = new HashMap<>();
            response.put("succes",true);
            response.put("data",tools);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching tools by skill: {}", e.getMessage());

            Map<String,Object> response = new HashMap<>();
            response.put("succes",false);
            response.put("message", "Failed to fetch tools: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Update tool", description = "Update an existing tool by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tool updated successfully"),
            @ApiResponse(responseCode = "404", description = "Tool not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTool(@PathVariable Long id,
                                                          @Validated(Update.class) @RequestBody ToolRequestDTO dto) {
        log.info("REST request to update tool with ID: {}", id);

        try{
            ToolResponseDTO updatedTool = toolService.updateTool(id,dto);

            Map<String, Object> response = new HashMap<>();
            response.put("success",true);
            response.put("data",updatedTool);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error updating tool: {}", e.getMessage());

            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message","Failed to update tool: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }


    }

    @Operation(summary = "Get tool by ID", description = "Retrieve a specific tool by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tool retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Tool not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/experience/{id}")
    public ResponseEntity<Map<String,Object>> getByID(@PathVariable Long id){
        log.info("REST request to get tool with ID: {}", id);

        try {
            ToolResponseDTO tool = toolService.getToolById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", tool);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error fetching tool: {}", e.getMessage());

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Tool not found: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


}