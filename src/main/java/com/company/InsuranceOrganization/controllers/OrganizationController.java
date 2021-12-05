package com.company.InsuranceOrganization.controllers;

import com.company.InsuranceOrganization.models.Acceptance;
import com.company.InsuranceOrganization.models.Organization;
import com.company.InsuranceOrganization.repository.AcceptanceRepository;
import com.company.InsuranceOrganization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrganizationController {

    private final AcceptanceRepository acceptanceRepo;
    private final OrganizationRepository organizationRepo;

    @Autowired
    public OrganizationController(AcceptanceRepository acceptanceRepo, OrganizationRepository organizationRepo) {
        this.acceptanceRepo = acceptanceRepo;
        this.organizationRepo = organizationRepo;
    }


    @GetMapping("/acceptance/{id}/employee/organization")
    public String organizationPage(Model model, @PathVariable("id") Long id) {

        if (!acceptanceRepo.existsById(id)) {
            return "redirect:/";
        }

        model.addAttribute("organization", new Organization());
        model.addAttribute("acceptance", acceptanceRepo.getById(id));
        return "organizationPage";
    }

    @PostMapping("/acceptance/{id}/employee/organization")
    public String newOrganization(@ModelAttribute("acceptance") Acceptance acceptance,
                                  @ModelAttribute("organization") @Valid Organization organization,
                                  BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "redirect:/acceptance/" + acceptance.getId() + "/employee/organization/";
        }

        organization.setAcceptance(acceptanceRepo.getById(acceptance.getId()));
        organizationRepo.save(organization);

        Acceptance acceptanceSave = acceptanceRepo.getById(acceptance.getId());
        acceptanceSave.setCompleted(true);
        acceptanceRepo.save(acceptanceSave);

        return "redirect:/acceptance/" + acceptance.getId() + "/employee/organization/enterprise";
    }

    @GetMapping("/organization/{id}/edit")
    public String organizationEdit(@PathVariable("id") Long id, Model model) {
        if (!organizationRepo.existsById(id)) {
            return "redirect:/";
        }
        model.addAttribute("organization", organizationRepo.getById(id));
        model.addAttribute("newOrganization", new Organization());
        return "organizationEdit";
    }

    @PostMapping("/organization/{id}/edit")
    public String organizationEdit(@PathVariable("id") Long id,
                                   @ModelAttribute("newOrganization") @Valid Organization editOrganization,
                                   BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "redirect:/organization/" + id + "/edit";
        }

        Organization organization = organizationRepo.getById(id);
        organization.setFullOrganizationName(editOrganization.getFullOrganizationName());
        organization.setShortOrganizationName(editOrganization.getShortOrganizationName());
        organization.setAddress(editOrganization.getAddress());
        organization.setBankInspection(editOrganization.getBankInspection());
        organization.setSpeciality(editOrganization.getSpeciality());

        Acceptance acceptance = acceptanceRepo.getById(organization.getAcceptance().getId());
        acceptance.setCompleted(true);
        acceptanceRepo.save(acceptance);

        return "redirect:/acceptance/" + acceptance.getId() + "/employee/organization/enterprise";

    }


    @GetMapping("acceptance/{id}/organization/delete")
    @Transactional
    public String deleteOrganization(@PathVariable("id") Long id) {
        if (!acceptanceRepo.existsById(id)) {
            return "redirect:/";
        }

        organizationRepo.deleteByAcceptanceId(id);
        Acceptance acceptance = acceptanceRepo.getById(id);
        acceptance.setCompleted(false);
        acceptanceRepo.save(acceptance);

        return "redirect:/";

    }


}
