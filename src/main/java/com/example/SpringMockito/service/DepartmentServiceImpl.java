package com.example.SpringMockito.service;

import com.example.SpringMockito.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final EmployeeService employeeService;

    @Autowired
    public DepartmentServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Collection<Employee> getEmployeesByDepartment(int id) {
        return employeeService.printAll().stream()
                .filter(employee -> employee.getDepartment() == id)
                .collect(Collectors.toList());
    }

    @Override
    public double getSalarySumByDepartment(int id) {
        return employeeService.printAll().stream()
                .filter(employee -> employee.getDepartment() == id)
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    @Override
    public double getMaxSalaryByDepartment(int id) {
        return employeeService.printAll().stream()
                .filter(employee -> employee.getDepartment() == id)
                .mapToDouble(Employee::getSalary)
                .max()
                .orElse(0.0);
    }

    @Override
    public double getMinSalaryByDepartment(int id) {
        return employeeService.printAll().stream()
                .filter(employee -> employee.getDepartment() == id)
                .mapToDouble(Employee::getSalary)
                .min()
                .orElse(0.0);
    }

    @Override
    public Map<Integer, List<Employee>> getEmployeesGroupedByDepartment() {
        return employeeService.printAll().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
}