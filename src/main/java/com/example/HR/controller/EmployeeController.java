package com.example.HR.controller;

import com.example.HR.dto.employee.EmployeeAttachmentDTO;
import com.example.HR.dto.employee.EmployeeRequestDTO;
import com.example.HR.dto.employee.EmployeeResponseDTO;
import com.example.HR.entity.employee.EmployeeAttachment;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import com.example.HR.service.EmployeeService;
//import com.example.HR.service.implement.FileUploadService;
import com.example.HR.service.implement.fileStorage.EmployeeImagesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeImagesService imagesService;

    @Operation(
            summary = "Create a new employee",
            description = "Creates a new employee with the provided information"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/add")
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeRequestDTO employeeRequestDTO) throws IOException {
        log.info("=== Employee Creation Request ===");
        log.info("Creating new employee: {}", employeeRequestDTO.getFullname());
        log.info("Email received: '{}'", employeeRequestDTO.getEmail());
        log.info("Username received: '{}'", employeeRequestDTO.getFullname());
        log.info("Password received: '{}'", employeeRequestDTO.getPassword());
        log.info("ConfirmPassword received: '{}'", employeeRequestDTO.getConfirmPassword());
        log.info("PhoneNumber received: '{}'", employeeRequestDTO.getPhoneNumber());
        log.info("Company received: '{}'", employeeRequestDTO.getCompany());
        log.info("Departament received: '{}'", employeeRequestDTO.getDepartament());
        log.info("JobTitle received: '{}'", employeeRequestDTO.getJobTitle());
        log.info("EmploymentType received: '{}'", employeeRequestDTO.getEmploymentType());
        log.info("Status received: '{}'", employeeRequestDTO.getStatus());
        log.info("About received: '{}'", employeeRequestDTO.getAbout());
        log.info("JoinDate received: '{}'", employeeRequestDTO.getJoinDate());
        log.info("=================================");
        
        EmployeeRequestDTO employee = employeeService.save(employeeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Get all employees",
            description = "Returns a list of all employees"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<EmployeeResponseDTO>> viewAllEmployees() throws MalformedURLException {
        List<EmployeeResponseDTO> employeeResponseDTOList = employeeService.findAll();
        log.info("All Employee list returned");

        return ResponseEntity.ok(employeeResponseDTOList);
    }


    @Operation(summary = "Get employee by ID",
            description = "Returns a single employee by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        return employeeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get employees by status",
            description = "Returns a list of all employees by status"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getByStatus/{status}")
    public ResponseEntity<List<EmployeeResponseDTO>> viewEmployeesByStatus(@PathVariable String status) throws MalformedURLException {

        Status enumStatus;
        try {
            enumStatus= Status.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Status: " + status);
        }
        List<EmployeeResponseDTO> employeeRequestDTOList = employeeService.getByStatus(enumStatus);

        log.info("Employee list returned by status: {}", status);

        return ResponseEntity.ok(employeeRequestDTOList);
    }

    @Operation(summary = "Get employees by fullname",
            description = "Returns a list of all employees by fullname"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getByFullname/{fullname}")
    public ResponseEntity<Optional<EmployeeResponseDTO>> viewEmployeesByFullname(@PathVariable String fullname) throws MalformedURLException {
        Optional<EmployeeResponseDTO> employeeDTOList = employeeService.getByFullname(fullname);

        log.info("Employee list returned by fullname: {}" ,fullname);
        return ResponseEntity.ok(employeeDTOList);
    }

    @Operation(summary = "Get employees by jobTitle",
            description = "Returns a list of all employees by JobTitle"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getByJob/{jobTitle}")
    public ResponseEntity<List<EmployeeResponseDTO>> viewEmployeesByJobTitle(@PathVariable String jobTitle) throws MalformedURLException {

        JobTitle enumJobTitle;
        try {
            enumJobTitle= JobTitle.valueOf(jobTitle.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid JobTitle: " + jobTitle);
        }
        List<EmployeeResponseDTO> employeeRequestDTOList = employeeService.getByJobPosition(enumJobTitle);

        log.info("Employee list returned by jobTitle: {}", jobTitle);

        return ResponseEntity.ok(employeeRequestDTOList);
    }

    @Operation(summary = "Get employees by employment type",
            description = "Returns a list of all employees by employment type"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getByEmploymentType/{employmentType}")
    public ResponseEntity<List<EmployeeResponseDTO>> viewEmployeesByEmploymentType(@PathVariable String employmentType) throws MalformedURLException {

        EmploymentType enumStatus;
        try {
            enumStatus= EmploymentType.valueOf(employmentType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid EmploymentType: " + employmentType);
        }

        List<EmployeeResponseDTO> employeeRequestDTOList = employeeService.getByEmploymentType(enumStatus);

        log.info("Employee list returned by employment type: {}", employmentType);

        return ResponseEntity.ok(employeeRequestDTOList);
    }

    @Operation(summary = "Get employees by joinDate",
            description = "Returns a list of all employees by joinDate"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No employees found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/getByDate/{localDate}")
    public ResponseEntity<List<EmployeeResponseDTO>> viewEmployeesByJoinDate(@PathVariable LocalDate localDate) throws MalformedURLException {
        List<EmployeeResponseDTO> employeeRequestDTOList = employeeService.getByDate(localDate);

        log.info("Employee list returned by joinDate: {}", localDate);

        return ResponseEntity.ok(employeeRequestDTOList);
    }

    //UPDATE EMPLOYEE

    @Operation(summary = "Update employee",
            description = "Updates an existing employee"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeRequestDTO> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeRequestDTO employeeRequestDTO) throws IOException {
        employeeService.update(id, employeeRequestDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete employee",
            description = "Deletes an employee by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Add a new employee attachment",
            description = "Creates a new attachment with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee attachment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{employeeId}/attachments")
    public ResponseEntity<Map<String, Object>> uploadFile(@PathVariable String employeeId,
                                                          @RequestParam("file") MultipartFile file) {
        EmployeeAttachment attachment = employeeService.uploadAttachment(employeeId, file);

        try {


            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "File uploaded successfully");
            response.put("attachment", EmployeeAttachmentDTO.builder()
                    .id(attachment.getId())
                    .fileName(attachment.getFileName())
                    .originalFileName(attachment.getOriginalFileName())
                    .contentType(attachment.getContentType())
                    .fileSize(attachment.getFileSize())
                    .uploadedDate(attachment.getUploadedDate())
                    .build());

            return ResponseEntity.ok(response);
        }catch (Exception e) {
            Map<String,Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message",e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(
            summary = "Download employee attachments",
            description = "Downloads all task attachments from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee attachments downloaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No employee attachments found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/attachments/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName){
        Resource resource = imagesService.loadFileAsResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +resource.getFilename() +"\"")
                .body(resource);
    }

    @Operation(
            summary = "Get employee attachments",
            description = "Retrieves all employee attachments from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee attachments retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "No employee attachments found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/attachments/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName){
        Resource resource = imagesService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }






}
