package com.company.InsuranceOrganization.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Employees {
    @Id
        @GeneratedValue(generator = "system-uuid")
        @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @NotNull
    @Column(nullable = false)
    private String fullName;
    @NotNull
    @Column(nullable = false)
    private Integer age;
    @NotNull
    @Column(nullable = false)
    private Integer categoryRisk;
    @ManyToOne
    private Acceptance acceptance;

}


