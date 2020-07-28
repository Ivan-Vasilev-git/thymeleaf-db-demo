package com.company.thymeleafdemo.controller;

import com.company.thymeleafdemo.entity.Employee;
import com.company.thymeleafdemo.service.IEmployeeSevice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

  private IEmployeeSevice employeeService;

  public EmployeeController(IEmployeeSevice employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/list")
  public String listEmployees(Model model) {
    List<Employee> employees = employeeService.findAll();

    model.addAttribute("employees", employees);
    return "/employees/list-employees";
  }

  @GetMapping("/showFormForAdd")
  public String showFormForAdd(Model model) {
    // create model attribute to bind form data
    Employee employee = new Employee();
    model.addAttribute("employee", employee);
    return "/employees/employee-form";
  }

  @GetMapping("/showFormForUpdate")
  public String showFormForUpdate(@RequestParam("employeeId") int id, Model model) {
    // get the employee from the service
    Employee employee = employeeService.findById(id);

    // set employee as a model attribute to pre-populate the form
    model.addAttribute("employee", employee);

    // send over to our form
    return "/employees/employee-form";
  }

  @PostMapping("/save")
  public String saveEmployee(@ModelAttribute("employee") Employee employee) {
    // save the employee
    employeeService.save(employee);

    // use a redirect to prevent duplicate submissions
    return "redirect:/employees/list";
  }

  @GetMapping("/delete")
  public String delete(@RequestParam("employeeId") int id) {
    // delete the employee
    employeeService.deleteById(id);

    // redirect to /employees/list
    return "redirect:/employees/list";
  }

  @GetMapping("/search")
  public String delete(@RequestParam("employeeName") String name,
                       Model theModel) {

    // delete the employee
    List<Employee> theEmployees = employeeService.searchBy(name);

    // add to the spring model
    theModel.addAttribute("employees", theEmployees);

    // send to /employees/list
    return "/employees/list-employees";

  }
}
