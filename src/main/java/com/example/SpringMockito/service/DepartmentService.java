package com.example.SpringMockito.service;

import com.example.SpringMockito.dto.Employee;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DepartmentService {

    Collection<Employee> getEmployeesByDepartment(int id);

    double getSalarySumByDepartment(int id);

    double getMaxSalaryByDepartment(int id);

    double getMinSalaryByDepartment(int id);

    Map<Integer, List<Employee>> getEmployeesGroupedByDepartment();
}


