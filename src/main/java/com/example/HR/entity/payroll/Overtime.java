package com.example.HR.entity.payroll;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "overtime")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Overtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ot_number", unique = true)
    private String otNumber;

    @Column(name = "salary", precision = 10, scale = 2)
    private String salary;

    @Column(name = "rate", length = 20)
    private String rate;

    @CreationTimestamp
    private LocalDate createdDate;

    @Transient
    public String getOtNumber() {
        return otNumber + "x";
    }

    @Transient
    public String getRate() {
        return "Hourly " + rate;
    }

    @Transient
    public String getSalary() {
        return "$" + salary;
    }

}
