package com.company.thymeleafdemo.dao;

import com.company.thymeleafdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
  // add a method to sort by last name
  List<Employee> findAllByOrderByLastNameAsc();

  // search by name
  List<Employee> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(
      String name, String lName);
}
