package com.example.HR.converter;

import com.example.HR.dto.EmployeeInformationDTO;
import com.example.HR.dto.employee.EmployeeAttachmentDTO;
import com.example.HR.dto.employee.EmployeeRequestDTO;
import com.example.HR.dto.employee.EmployeeResponseDTO;
import com.example.HR.dto.employee.EmployeeTaskResponseDTO;
import com.example.HR.dto.ticket.TicketAttachmentDTO;
import com.example.HR.entity.employee.Employee;
import com.example.HR.entity.employee.EmployeeAttachment;
import com.example.HR.entity.task.Task;
import com.example.HR.entity.task.TaskAssignment;
import com.example.HR.entity.task.TaskAttachment;
import com.example.HR.entity.ticket.TicketAttachment;
import com.example.HR.enums.Status;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class EmployeeConverter extends Convert<EmployeeRequestDTO, Employee> {
    @Override
    public Employee dtoToEntity(EmployeeRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setJoinDate(dto.getJoinDate());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setCompany(dto.getCompany());
        employee.setDepartament(dto.getDepartament());
        employee.setJobTitle(dto.getJobTitle());
        employee.setAbout(dto.getAbout());
        employee.setEmploymentType(dto.getEmploymentType());
        employee.setStatus(dto.getStatus() != null ? dto.getStatus() : Status.ACTIVE);// default as ACTIVE
//        employee.setImageType(dto.getImageType());
//        employee.setImageName(dto.getImageName());
//        employee.setImageDate(dto.getImageDate());
        // Note: User relationships should be set in the service layer
        return employee;
    }

    @Override
    public EmployeeRequestDTO entityToDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeRequestDTO dto = new EmployeeRequestDTO();
        dto.setId(employee.getId());
        dto.setJoinDate(employee.getJoinDate());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setCompany(employee.getCompany());
        dto.setDepartament(employee.getDepartament());
        dto.setJobTitle(employee.getJobTitle());
        dto.setAbout(employee.getAbout());
        dto.setEmploymentType(employee.getEmploymentType());
        dto.setStatus(employee.getStatus());
        // User relationships — convert to String
        if (employee.getFullname() != null) {
            dto.setFullname(employee.getFullname().getFullname());
        }
        if (employee.getEmail() != null) {
            dto.setEmail(employee.getEmail().getEmail());
        }
        if (employee.getPassword() != null) {
            dto.setPassword(employee.getPassword().getPassword());
        }
        return dto;
    }

    /**
     * Updates the given Employee entity with non-null values from the EmployeeRequestDTO.
     * User relationships are updated only if the User object is not null.
     * This does not create new User objects.
     */
    public void updateEntityFromDto(EmployeeRequestDTO dto, Employee entity) {
        if (dto == null || entity == null) {
            return;
        }
        if(dto.getId() != null) entity.setId(dto.getId());
        if (dto.getJoinDate() != null) entity.setJoinDate(dto.getJoinDate());
        if (dto.getPhoneNumber() != null) entity.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getCompany() != null) entity.setCompany(dto.getCompany());
        if (dto.getDepartament() != null) entity.setDepartament(dto.getDepartament());
        if (dto.getJobTitle() != null) entity.setJobTitle(dto.getJobTitle());
        if (dto.getAbout() != null) entity.setAbout(dto.getAbout());
        if (dto.getEmploymentType() != null) entity.setEmploymentType(dto.getEmploymentType());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        // User relationships — update fields if User object exists
        if (dto.getFullname() != null && entity.getFullname() != null) {
            entity.getFullname().setFullname(dto.getFullname());
        }
        if (dto.getEmail() != null && entity.getEmail() != null) {
            entity.getEmail().setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null && entity.getPassword() != null) {
            entity.getPassword().setPassword(dto.getPassword());
        }
    }

    public EmployeeResponseDTO entityToResponseDTO(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        // Email və FullName User entity-sindən gəlir

        if (employee.getFullname() != null) {
            dto.setFullname(employee.getFullname().getFullname());
        }
        if (employee.getEmail() != null) {
            dto.setEmail(employee.getEmail().getEmail());
        }

        dto.setId(employee.getId());
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setJoinDate(employee.getJoinDate());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setCompany(employee.getCompany());
        dto.setDepartament(employee.getDepartament());
        dto.setJobTitle(employee.getJobTitle());
        dto.setAbout(employee.getAbout());
        dto.setEmploymentType(employee.getEmploymentType());
        dto.setStatus(employee.getStatus());
        dto.setAttachments(employee.getAttachments().stream()
                .map(this::mapAttachmentToDTO)
                .collect(Collectors.toList()));
        dto.setTasks(employee.getTaskAssignments().stream()
                .map(TaskAssignment::getTask)
                .map(this::mapTaskToDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        return dto;
    }

    public List<EmployeeResponseDTO> entityListToResponseDTOList(List<Employee> employees){
        return employees.stream()
                .map(this::entityToResponseDTO)
                .collect(Collectors.toList());
    }


    public EmployeeInformationDTO entityToEmployeeInfo(Employee employee) {
        EmployeeInformationDTO dto = new EmployeeInformationDTO();

        dto.setEmployeeId(employee.getEmployeeId());
        dto.setJoinDate(employee.getJoinDate());
        dto.setDepartament(employee.getDepartament());
        dto.setJobTitle(employee.getJobTitle());
        dto.setEmploymentType(employee.getEmploymentType());
        dto.setStatus(employee.getStatus());

        return dto;
    }

    private EmployeeAttachmentDTO mapAttachmentToDTO(EmployeeAttachment attachment) {
        return EmployeeAttachmentDTO.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .originalFileName(attachment.getOriginalFileName())
                .contentType(attachment.getContentType())
                .fileSize(attachment.getFileSize())
                .uploadedDate(attachment.getUploadedDate())
                .build();
    }

    private EmployeeTaskResponseDTO mapTaskToDTO(Task task){
        return EmployeeTaskResponseDTO.builder()
                .id(task.getId())
                .taskName(task.getTaskName())
                .build();
    }

}