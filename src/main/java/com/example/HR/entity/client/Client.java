package com.example.HR.entity.client;

import com.example.HR.dto.client.ClientResponseDTO;
import com.example.HR.enums.ClientStatus;
import com.example.HR.validation.Create;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true,nullable = true)
    private String clientId;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String companyName;

    @Email(message = "Email should be valid")
    @NotBlank(groups = Create.class,message = "Email is required")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Invalid phone number")
    private String phoneNumber;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "company_members", columnDefinition = "TEXT")
    private String companyMembersJson;

    @Transient
    private List<String> companyMembers;

    public void setCompanyMembers(List<String> companyMembers){
        this.companyMembers = companyMembers;
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.companyMembersJson = mapper.writeValueAsString(companyMembers);
        } catch (Exception e) {
            this.companyMembersJson = "[]";
        }
    }

    public List<String> getCompanyMembers() {
        if (companyMembers == null && companyMembersJson != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                companyMembers = mapper.readValue(companyMembersJson,
                        new TypeReference<List<String>>() {});
            } catch (Exception e) {
                companyMembers = new ArrayList<>();
            }
        }
        return companyMembers != null ? companyMembers : new ArrayList<>();
    }

    @Enumerated(EnumType.STRING)
    private ClientStatus status;
}
