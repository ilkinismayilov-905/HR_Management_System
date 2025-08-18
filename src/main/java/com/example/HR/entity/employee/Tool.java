package com.example.HR.entity.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "tool")
@AllArgsConstructor
@NoArgsConstructor
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tool_skills",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();

//    public Tool() {};
//
//    public Tool(Long id, String name, Set<Skill> skills) {
//        this.id = id;
//        this.name = name;
//        this.skills = skills;
//    }
//
//    // getter-setter
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public Set<Skill> getSkills() { return skills; }
//    public void setSkills(Set<Skill> skills) { this.skills = skills; }
}
