package com.company.InsuranceOrganization.controllers;


import com.company.InsuranceOrganization.models.Acceptance;
import com.company.InsuranceOrganization.models.Employees;
import com.company.InsuranceOrganization.repository.AcceptanceRepository;
import com.company.InsuranceOrganization.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;

@Controller
public class EmployeeController {

    private final AcceptanceRepository acceptanceRepo;
    private final EmployeeRepository employeeRepo;


    @Autowired
    public EmployeeController(AcceptanceRepository acceptanceRepo, EmployeeRepository employeeRepo) {
        this.acceptanceRepo = acceptanceRepo;
        this.employeeRepo = employeeRepo;
    }

    @GetMapping("/acceptance/{id}/employee")
    public String addEmployeeStandart(Model model, @PathVariable("id") Long id) {

        if (!acceptanceRepo.existsById(id)) {
            return "redirect:/";
        }

        model.addAttribute("employee", new Employees());
        model.addAttribute("acceptance", acceptanceRepo.getById(id));

        return "employeePage";
    }

    @PostMapping("/acceptance/{id}/employee")
    public String addEmployeeStandart(@ModelAttribute("employee") Employees employees,
                                      @ModelAttribute("acceptance") Acceptance acceptance) {

        employees.setAcceptance(acceptanceRepo.getById(acceptance.getId()));
        employeeRepo.save(employees);
        return "redirect:/acceptance/" + acceptance.getId() + "/employee";
    }

    @GetMapping("/acceptance/{id}/addEmployee")
    public String addNewEmployee(Model model, @PathVariable("id") Long id) {

        if (!acceptanceRepo.existsById(id)) {
            return "redirect:/";
        }

        model.addAttribute("employee", new Employees());
        model.addAttribute("acceptance", acceptanceRepo.getById(id));

        return "addEmployeePage";
    }

    @PostMapping("/acceptance/{id}/addEmployee")
    public String addNewEmployee(@ModelAttribute("acceptance") Acceptance acceptance,
                                 @ModelAttribute("employee") Employees employees) {


        employees.setAcceptance(acceptanceRepo.getById(acceptance.getId()));
        employeeRepo.save(employees);
        return "redirect:/acceptance/" + acceptance.getId() + "/addEmployee";
    }

    @GetMapping("/employee/{id}/edit")
    public String editEmployee(@PathVariable("id") String id, Model model) {
        if (!employeeRepo.existsById(id)) {
            return "redirect:/";
        }

        model.addAttribute("employee", employeeRepo.getById(id));
        model.addAttribute("newEmployee", new Employees());

        return "employeeEdit";
    }

    @PostMapping("/employee/{id}/edit")
    public String editEmployee(@PathVariable("id") String id,
                               @ModelAttribute("newEmployee") Employees editEmployee) {

        Employees employee = employeeRepo.getById(id);
        employee.setFullName(editEmployee.getFullName());
        employee.setAge(editEmployee.getAge());
        employee.setCategoryRisk(editEmployee.getCategoryRisk());

        employeeRepo.save(employee);

        return "redirect:/acceptance/" + employee.getAcceptance().getId() + "/employee/organization/enterprise";

    }

    @GetMapping("/employee/{id}/delete")
    @Transactional
    public String deleteEmployee(@PathVariable("id") String id) {

        if (!employeeRepo.existsById(id)) {
            return "redirect:/";
        }

        Long acceptanceId = employeeRepo.getById(id).getAcceptance().getId();

        employeeRepo.deleteById(id);
        return "redirect:/acceptance/" + acceptanceId + "/employee/organization/enterprise";
    }

    @GetMapping("/acceptance/{id}/employees/delete-all")
    @Transactional
    public String deleteAllEmployee(@PathVariable("id") Long id) {
        if (!acceptanceRepo.existsById(id)) {
            return "redirect:/";
        }

        employeeRepo.deleteAllByAcceptanceId(id);

        return "redirect:/acceptance/" + id + "/employee/organization/enterprise";

    }
}
