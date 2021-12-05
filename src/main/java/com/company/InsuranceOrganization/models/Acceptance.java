package com.company.InsuranceOrganization.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Acceptance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(insertable = true, updatable = false)
    private LocalDate creationDate;
    @NotNull
    @Column(nullable = false, updatable = false)
    private Date endingDate;
    @NotNull
    @Column(nullable = false)
    private String categoryPaymentSum;
    @NotNull
    @Column(nullable = false)
    private String insuranceTypePayment;

    private boolean completed = false;

    @OneToMany
    private List<Employees> employees;

    @ManyToOne
    private User user;

    @PrePersist
    void Create() {
        this.creationDate = LocalDateTime.now().toLocalDate();
    }
}
