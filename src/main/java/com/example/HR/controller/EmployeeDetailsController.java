package com.example.HR.controller;

import com.example.HR.dto.EducationInfoDTO;
import com.example.HR.dto.EmployeeInformationDTO;
import com.example.HR.service.EducationInfoService;
import com.example.HR.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/employeeDetails")
public class EmployeeDetailsController {

    private final EmployeeService employeeService;
    private final EducationInfoService infoService;

    @Autowired
    public EmployeeDetailsController(EmployeeService employeeService, EducationInfoService infoService) {
        this.employeeService = employeeService;
        this.infoService = infoService;
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
    public ResponseEntity<EducationInfoDTO> addInformatoin(@RequestBody @Valid EducationInfoDTO dto) throws IOException {
        EducationInfoDTO information = infoService.save(dto);
        return  ResponseEntity.status(HttpStatus.OK).body(information);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<EducationInfoDTO>> getAll() throws MalformedURLException {
        List<EducationInfoDTO> list = infoService.getAll();
        return ResponseEntity.ok(list);
    }
}
