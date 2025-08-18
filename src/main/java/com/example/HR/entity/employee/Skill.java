package com.example.HR.entity.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "skill")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Skill {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name; // e.g. "UI Design", "Web Design"

    @ManyToMany(mappedBy = "skills")
//    @JsonIgnore
    private Set<Tool> tool = new HashSet<>();


}
