# Employee Configuration with SQL

A Java application for managing employee data using an SQL database. Demonstrates basic CRUD (Create, Read, Update, Delete) operations via JDBC.

## Features

- **CRUD Operations:** Add, view, update, and delete employee records.
- **JDBC Integration:** Connects to SQL database for data manipulation.
- **Factory Pattern:** Creates employee objects with modular design.
- **Console Interface:** Basic user interaction through command line.

## Project Structure

- **`com.example.model`**: `Employee` class.
- **`com.example.dao`**: `EmployeeRepository` for DB operations.
- **`com.example.service`**: `EmployeeService` for business logic.
- **`com.example.factory`**: `EmployeeFactory` for creating employees.
- **`com.example`**: Main class (`Main`) for application execution.
