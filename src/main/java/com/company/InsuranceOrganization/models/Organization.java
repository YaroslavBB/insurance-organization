package com.company.InsuranceOrganization.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(nullable = false)
    private String fullOrganizationName;
    @NotNull
    @Column(nullable = false)
    private String shortOrganizationName;
    @NotNull
    @Column(nullable = false)
    private String address;
    @NotNull
    @Column(nullable = false)
    private String bankInspection;
    @NotNull
    @Column(nullable = false)
    private String speciality;

    @OneToOne
    private Acceptance acceptance;

}
