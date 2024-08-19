CREATE DATABASE EmployeeDB;

USE EmployeeDB;

CREATE TABLE employees (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) NOT NULL UNIQUE,
    position NVARCHAR(50) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL
);


INSERT INTO employees (name, email, position, salary) 
VALUES 
('mohamed Ali', 'mohamed.ali@gmail.com', 'Developer', 7000.00),
('Mona Hassan', 'mona.hassan@gmail.com', 'Manager', 12000.00),
('Youssef Ibrahim', 'youssef.ibrahim@gmail.com', 'Developer', 8000.00);

SELECT * FROM employees;

UPDATE employees 
SET name = 'Ali Ahmed', email = 'ali.ahmed@gmail.com', position = 'Senior Developer', salary = 9000.00 
WHERE id = 1;

DELETE FROM employees WHERE id = 2;

SELECT * FROM employees WHERE id = 1;

