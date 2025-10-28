package com.example.HR.entity.user;

import com.example.HR.entity.task.TaskAssignment;
import com.example.HR.enums.City;
import com.example.HR.enums.Country;
import com.example.HR.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_information")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false,unique = true,referencedColumnName = "id")
    private User user;

    private String fullname;

    @Column(nullable = false,unique = true)
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Phone number is invalid")
    private String phoneNumber;

    @Column(nullable = false)
    @Email
    private String email;

    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City city;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(nullable = false)
    private String postalCode;

    @OneToMany(mappedBy = "profile",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserAttachment> taskAssignments = new HashSet<>();

}
