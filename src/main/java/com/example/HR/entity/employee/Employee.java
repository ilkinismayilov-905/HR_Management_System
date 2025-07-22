package com.example.HR.entity.employee;

import com.example.HR.entity.User;
import com.example.HR.enums.Departament;
import com.example.HR.enums.EmploymentType;
import com.example.HR.enums.JobTitle;
import com.example.HR.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false,unique = true)
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Emergency contact number is invalid")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobTitle jobTitle;

    @Column(nullable = false)
    private LocalDate joinDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private String company;

    @Column(columnDefinition = "TEXT")
    private String about;

    @Column(nullable = false)
    private String EmployeeId;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_name_id", nullable = false,unique = true)
    private User username;

    @OneToOne(optional = false)
    @JoinColumn(name = "password_id", nullable = false,unique = true)
    private User password;

    @Transient
    private User confirmPassword;

    @OneToOne(optional = false)
    @JoinColumn(name = "email_id", nullable = false,unique = true)
    private User email;

    @Enumerated(EnumType.STRING)
    @Column(name = "departament",nullable = false)
    private Departament departament;

    @Column(name = "photo")
    private String photo;

    @Transient
    private String photosPath;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFullname() {
//        return fullname;
//    }

//    public Employee(String fullname, String employeeId, LocalDate joinDate,
//                    User username, User email, User password, User confirmPassword,
//                    String phoneNumber, String company, Departament departament,
//                    JobTitle jobTitle, String about, String photo) {
//        this.fullname = fullname;
//        EmployeeId = employeeId;
//        this.joinDate = joinDate;
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.confirmPassword = confirmPassword;
//        this.phoneNumber = phoneNumber;
//        this.company = company;
//        this.departament = departament;
//        this.jobTitle = jobTitle;
//        this.about = about;
//        this.photo=photo;
//    }
//
//    public void setFullname(String fullname) {
//        this.fullname = fullname;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public JobTitle getJobTitle() {
//        return jobTitle;
//    }
//
//    public void setJobTitle(JobTitle jobTitle) {
//        this.jobTitle = jobTitle;
//    }
//
//    public LocalDate getJoinDate() {
//        return joinDate;
//    }
//
//    public void setJoinDate(LocalDate joinDate) {
//        this.joinDate = joinDate;
//    }
//
//    public EmploymentType getEmploymentType() {
//        return employmentType;
//    }
//
//    public void setEmploymentType(EmploymentType employmentType) {
//        this.employmentType = employmentType;
//    }
//
//    public Status getStatus() {
//        return status;
//    }
//
//    public void setStatus(Status status) {
//        this.status = status;
//    }
//
//    public String getCompany() {
//        return company;
//    }
//
//    public void setCompany(String company) {
//        this.company = company;
//    }
//
//    public String getAbout() {
//        return about;
//    }
//
//    public void setAbout(String about) {
//        this.about = about;
//    }
//
//    public String getEmployeeId() {
//        return EmployeeId;
//    }
//
//    public void setEmployeeId(String employeeId) {
//        EmployeeId = employeeId;
//    }
//
//    public User getUsername() {
//        return username;
//    }
//
//    public void setUsername(User username) {
//        this.username = username;
//    }
//
//    public User getPassword() {
//        return password;
//    }
//
//    public void setPassword(User password) {
//        this.password = password;
//    }
//
//    public User getConfirmPassword() {
//        return confirmPassword;
//    }
//
//    public void setConfirmPassword(User confirmPassword) {
//        this.confirmPassword = confirmPassword;
//    }
//
//    public User getEmail() {
//        return email;
//    }
//
//    public void setEmail(User email) {
//        this.email = email;
//    }
//
//    public Departament getDepartament() {
//        return departament;
//    }
//
//    public void setDepartament(Departament departament) {
//        this.departament = departament;
//    }
//
//    public String getPhoto() {
//        return photo;
//    }
//
//    public void setPhoto(String photo) {
//        this.photo = photo;
//    }

    public String getPhotosPath(){
        if(id == null || photo == null){
            return "image/user.png";
        }

        return "employee-photos/"+this.id+"/"+this.photo;
    }

    public void setPhotosPath(String photosPath) {
        this.photosPath = photosPath;
    }
}
