package com.company.InsuranceOrganization.controllers;

import com.company.InsuranceOrganization.models.Acceptance;
import com.company.InsuranceOrganization.models.User;
import com.company.InsuranceOrganization.repository.AcceptanceRepository;
import com.company.InsuranceOrganization.repository.EmployeeRepository;
import com.company.InsuranceOrganization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class AcceptanceController {

    private final AcceptanceRepository acceptanceRepo;
    private final EmployeeRepository employeeRepo;
    private final OrganizationRepository organizationRepo;


    @Autowired
    public AcceptanceController(AcceptanceRepository acceptanceRepo, EmployeeRepository employeeRepo, OrganizationRepository organizationRepo) {
        this.acceptanceRepo = acceptanceRepo;
        this.employeeRepo = employeeRepo;
        this.organizationRepo = organizationRepo;
    }

    @GetMapping("/acceptance")
    public String acceptancePage(Model model) {
        model.addAttribute("acceptance", new Acceptance());
        return "acceptancePage";
    }

    @PostMapping("/acceptance")
    public String newAcceptance(
            @ModelAttribute("acceptance") @Valid Acceptance acceptance,
            BindingResult bindingResult,
            @AuthenticationPrincipal User user) {

        if(bindingResult.hasErrors()){
            return "redirect:/acceptance";
        }

        acceptance.setUser(user);
        acceptanceRepo.save(acceptance);
        return "redirect:/acceptance/" + acceptance.getId() + "/employee";
    }

    @GetMapping("/acceptance/{id}/delete")
    @Transactional
    public String deleteAcceptance(@PathVariable("id") Long id) {
        if (!acceptanceRepo.existsById(id)) {
            return "redirect:/";
        }

        employeeRepo.deleteAllByAcceptanceId(id);
        organizationRepo.deleteByAcceptanceId(id);
        acceptanceRepo.deleteById(id);

        return "redirect:/";
    }

    @GetMapping("acceptance/{id}/edit")
    public String acceptanceEdit(@PathVariable("id") Long id, Model model) {
        if (!acceptanceRepo.existsById(id)) {
            return "redirect:/";
        }

        model.addAttribute("acceptance", acceptanceRepo.getById(id));
        model.addAttribute("newAcceptance", new Acceptance());

        return "acceptanceEdit";
    }

    @PostMapping("acceptance/{id}/edit")
    public String acceptanceEdit(@PathVariable("id") Long id,
                                 @ModelAttribute("newAcceptance") @Valid Acceptance editAcceptance,
                                 BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "/acceptance/" + id + "/edit";
        }

        Acceptance acceptance = acceptanceRepo.getById(id);
        acceptance.setEndingDate(editAcceptance.getEndingDate());
        acceptance.setCategoryPaymentSum(editAcceptance.getCategoryPaymentSum());
        acceptance.setInsuranceTypePayment(editAcceptance.getInsuranceTypePayment());

        acceptanceRepo.save(acceptance);

        return "redirect:/acceptance/" + acceptance.getId() + "/employee/organization/enterprise";

    }

}
