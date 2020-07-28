package com.company.thymeleafdemo.service;

import com.company.thymeleafdemo.entity.Employee;

import java.util.List;

public interface IEmployeeSevice {
  List<Employee> findAll();

  Employee findById(int id);

  void save(Employee employee);

  void deleteById(int id);

  List<Employee> searchBy(String name);

}
