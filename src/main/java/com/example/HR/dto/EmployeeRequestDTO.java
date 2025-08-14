package com.example.HR.dto;

import com.example.HR.enums.Departament;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

//@Setter
//@Getter
//@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDTO {

    @Schema(description = "Unique identifier", example = "1")
    private Long id;

    @Schema(description = "Full name of the employee", example = "John Doe", required = true)
    @NotBlank(message = "Full name is required")
    private String fullname;

    @Schema(description = "Unique employee ID", example = "EMP123456", required = true)
    @NotBlank(message = "Employee ID is required")
    private String employeeId;

    @Schema(description = "Joining date of the employee", example = "2023-01-15", required = true, type = "string", format = "date")
    @NotNull(message = "Join date is required")
    @PastOrPresent(message = "Join date cannot be in the future")
    private LocalDate joinDate;

    @Schema(description = "Email account of the employee", required = true, example = "johndoe@example.com")
    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Password for the user account", required = true)
    @NotNull(message = "Password is required")
    private String password;

    @Schema(description = "Password confirmation", required = true)
    @NotNull(message = "Confirm password is required")
    private String confirmPassword;

    @Schema(description = "Phone number of the employee", example = "+994501234567", required = true)
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number")
    private String phoneNumber;

    @Schema(description = "Company name", example = "TechCorp LLC", required = true)
    @NotBlank(message = "Company is required")
    private String company;

    @Schema(description = "Department the employee belongs to", required = true, example = "IT", allowableValues = {"IT", "HR", "SALES", "FINANCE", "MARKETING", "OPERATIONS", "DESIGN", "PROCUREMENT", "ENGINEERING", "CUSTOMER_SERVICE"})
    @NotNull(message = "Department is required")
    private Departament departament;

    @Schema(description = "Job title of the employee", required = true, example = "DEVELOPER", allowableValues = {"DEVELOPER", "CEO", "UX_DESIGNER", "PROJECT_MANAGER", "PRODUCTS_DESIGNER", "ILLUSTRATOR", "UI_DESIGNER"})
    @NotNull(message = "Job title is required")
    private JobTitle jobTitle;

    @Schema(description = "Short bio or description", example = "Hardworking and reliable employee")
    private String about;

    @Schema(description = "Employment type", example = "FULL_TIME", required = true, allowableValues = {"FULL_TIME", "PART_TIME", "INTERNSHIP" ,"REMOTE", "HYBRID", "CONTRACT"})
    @NotNull(message = "Employment type is required")
    private EmploymentType employmentType;

    @Schema(description = "Employee status", example = "ACTIVE", allowableValues = {"ACTIVE", "INACTIVE", "SUSPENDED"})
    private Status status;

//    @Schema(description = "Stored image name")
//    private String imageName;
//
//    @Schema(description = "Stored image MIME type")
//    private String imageType;
//
//    @Schema(description = "Stored image data as byte array")
//    private byte[] imageDate;

    //    public String getImageName() {
//        return imageName;
//    }

//    public void setImageName(String imageName) {
//        this.imageName = imageName;
//    }

//    public String getImageType() {
//        return imageType;
//    }
//
//    public void setImageType(String imageType) {
//        this.imageType = imageType;
//    }
//
//    public byte[] getImageDate() {
//        return imageDate;
//    }
//
//    public void setImageDate(byte[] imageDate) {
//        this.imageDate = imageDate;
//    }

    //    public String getPhoto() {
//        return photo;
//    }
//
//    public void setPhoto(String photo) {
//        this.photo = photo;
//    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
