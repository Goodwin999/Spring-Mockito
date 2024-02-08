package com.example.SpringMockito.service;


import com.example.SpringMockito.dto.Employee;
import com.example.SpringMockito.exception.EmployeeAlreadyAddedException;
import com.example.SpringMockito.exception.EmployeeNotFoundException;
import com.example.SpringMockito.exception.EmployeeStorageIsFullException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private final Map<String, Employee> employeeMap;
    private static final int MAX_EMPLOYEES = 100;

    private String generateKey(String firstName, String lastName) {
        return firstName + lastName;
    }


    public EmployeeServiceImpl() {
        employeeMap = new HashMap<>();

    }

    @Override
    public Employee addEmployee
            (String firstName, String lastName, int department, double salary) {
        Employee newEmployee = new Employee(
                StringUtils.capitalize(firstName),
                StringUtils.capitalize(lastName),
                department,
                salary);

        if (employeeMap.size() == MAX_EMPLOYEES) {
            throw new EmployeeStorageIsFullException
                    ("превышен лимит количества сотрудников в фирме");
        }
        String key = generateKey(firstName, lastName);

        if (employeeMap.containsKey(key)) {
            throw new EmployeeAlreadyAddedException
                    ("в коллекции уже есть такой сотрудник");
        }
        employeeMap.put(key, newEmployee);
        return newEmployee;
    }

    @Override
    public Employee removeEmployee(String firstName, String lastName) {
        String key = generateKey(firstName, lastName);

        if (!employeeMap.containsKey(key)) {

            throw new EmployeeNotFoundException("сотрудник не найден");
        }
        Employee employee = employeeMap.remove(key);

        return employee;

    }

    @Override
    public Employee findEmployee(String firstName, String lastName) {

        Employee employee = employeeMap.get(firstName + lastName);

        String key = generateKey(firstName, lastName);

        if (employeeMap.containsKey(key)) {
            return employee;
        }
        throw new EmployeeNotFoundException("сотрудник не найден");

    }

    @Override
    public Collection<Employee> printAll() {
        return employeeMap.values();
    }
}
