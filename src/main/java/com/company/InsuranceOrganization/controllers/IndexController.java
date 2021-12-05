package com.company.InsuranceOrganization.controllers;

import com.company.InsuranceOrganization.repository.AcceptanceRepository;
import com.company.InsuranceOrganization.repository.EmployeeRepository;
import com.company.InsuranceOrganization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class IndexController {
    private final AcceptanceRepository acceptanceRepo;
    private final EmployeeRepository employeeRepo;
    private final OrganizationRepository organizationRepo;

    @Autowired
    public IndexController(AcceptanceRepository acceptanceRepo, EmployeeRepository employeeRepo,
                           OrganizationRepository organizationRepo) {
        this.acceptanceRepo = acceptanceRepo;
        this.employeeRepo = employeeRepo;
        this.organizationRepo = organizationRepo;
    }

    @GetMapping("/")
    public String indexPage(Model model, Principal principal) {
        model.addAttribute("acceptances",
                acceptanceRepo.findByUserUsername(principal.getName()));
        return "acceptancesMain";
    }

    @GetMapping("/acceptance/{id}/employee/organization/enterprise")
    public String enterprise(Model model, @PathVariable("id") Long id) {

        if (!acceptanceRepo.existsById(id)) {
            return "redirect:/";
        }
        if (!organizationRepo.existsOrganizationByAcceptanceId(id)) {

            return "redirect:/";

        }
        model.addAttribute("organizatoin",
                organizationRepo.findOrganizationByAcceptanceId(id));
        model.addAttribute("employees",
                employeeRepo.findAllByAcceptanceId(id));

        model.addAttribute("acceptance", acceptanceRepo.getById(id));

        return "enterprisePage";
    }
}
