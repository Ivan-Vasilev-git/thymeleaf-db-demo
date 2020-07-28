package com.company.thymeleafdemo.service;

import com.company.thymeleafdemo.dao.IEmployeeRepository;
import com.company.thymeleafdemo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeSevice {

  private IEmployeeRepository employeeRepository;

  @Autowired
  public EmployeeService(IEmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public List<Employee> findAll() {
    return employeeRepository.findAllByOrderByLastNameAsc();
  }

  @Override
  public Employee findById(int id) {
    Optional<Employee> employee = employeeRepository.findById(id);
    if (!employee.isPresent()) {
      throw new RuntimeException("Can't find Employee id - " + id);
    }
    return employee.get();
  }

  @Override
  public void save(Employee employee) {
    employeeRepository.save(employee);
  }

  @Override
  public void deleteById(int id) {
    employeeRepository.deleteById(id);
  }

  @Override
  public List<Employee> searchBy(String name) {
    List<Employee> results =
        name != null && (name.trim().length() > 0) ?
            employeeRepository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(name, name) :
            findAll();
    return results;
  }
}
