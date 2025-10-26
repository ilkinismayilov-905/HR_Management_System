package com.example.HR.entity.payroll;

import com.example.HR.enums.payroll.DeductionRate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "deduction")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Deduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = false, scale = 2, precision = 19)
    private String amount;

    @Column(name = "salary", precision = 10, scale = 2)
    private String salary;

    @Column(name = "rate", length = 20)
    private DeductionRate rate;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate createdDate;

    public String getSalary() {
        return "$" + salary;
    }

    public String getAmount() {
        return "$" + amount;
    }
}
