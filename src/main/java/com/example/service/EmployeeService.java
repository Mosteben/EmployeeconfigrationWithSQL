package com.example.service;

import com.example.dao.EmployeeRepository;
import com.example.model.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService() {
        this.employeeRepository = new EmployeeRepository();
    }

    public void addEmployee(Employee employee) throws SQLException {
        employeeRepository.addEmployee(employee);
    }

    public List<Employee> getAllEmployees() throws SQLException {
        return employeeRepository.getAllEmployees();
    }

    public Employee getEmployeeById(int id) throws SQLException {
        return employeeRepository.getEmployeeById(id);
    }

    public void updateEmployee(Employee employee) throws SQLException {
        employeeRepository.updateEmployee(employee);
    }

    public void deleteEmployee(int id) throws SQLException {
        employeeRepository.deleteEmployee(id);
    }
}