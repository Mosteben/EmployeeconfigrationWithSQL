package com.example.factory;

import com.example.model.Developer;
import com.example.model.Employee;
import com.example.model.Manager;

public class EmployeeFactory {
    public static Employee createEmployee(String type, String name, String email, String position, double salary) {
        if ("Developer".equalsIgnoreCase(type)) {
            return new Developer(name, email, position, salary);
        } else if ("Manager".equalsIgnoreCase(type)) {
            return new Manager(name, email, position, salary);
        }
        throw new IllegalArgumentException("Unknown employee type: " + type);
    }
}
