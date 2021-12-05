package com.company.InsuranceOrganization.repository;

import com.company.InsuranceOrganization.models.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, String> {

    List<Employees> findAllByAcceptanceId(Long id);

    void deleteAllByAcceptanceId(Long id);

}
