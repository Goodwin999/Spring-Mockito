package com.example.SpringMockito;

import com.example.SpringMockito.dto.Employee;
import com.example.SpringMockito.service.DepartmentServiceImpl;
import com.example.SpringMockito.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    void getEmployeesByDepartment_ShouldReturnEmployees_WhenValidDepartmentIdProvided() {

        int departmentId = 1;
        List<Employee> expectedEmployees = Arrays.asList(
                new Employee("John", "Doe", departmentId, 50000.0),
                new Employee("Jane", "Smith", departmentId, 60000.0)
        );

        when(employeeService.printAll()).thenReturn(expectedEmployees);


        Collection<Employee> actualEmployees = departmentService.getEmployeesByDepartment(departmentId);

        assertEquals(expectedEmployees, actualEmployees);
    }

    @Test
    void getSalarySumByDepartment_ShouldReturnSum_WhenValidDepartmentIdProvided() {
        // Arrange
        int departmentId = 1;
        List<Employee> employees = Arrays.asList(
                new Employee("John", "Doe", departmentId, 50000.0),
                new Employee("Jane", "Smith", departmentId, 60000.0)
        );


        when(employeeService.printAll()).thenReturn(employees);


        double actualSum = departmentService.getSalarySumByDepartment(departmentId);


        assertEquals(110000.0, actualSum);
    }

    @Test
    void getMaxSalaryByDepartment_ShouldReturnMaxSalary_WhenValidDepartmentIdProvided() {

        int departmentId = 1;
        List<Employee> employees = Arrays.asList(
                new Employee("John", "Doe", departmentId, 50000.0),
                new Employee("Jane", "Smith", departmentId, 60000.0)
        );


        when(employeeService.printAll()).thenReturn(employees);

        double actualMaxSalary = departmentService.getMaxSalaryByDepartment(departmentId);

        assertEquals(60000.0, actualMaxSalary);
    }

    @Test
    void getMinSalaryByDepartment_ShouldReturnMinSalary_WhenValidDepartmentIdProvided() {

        int departmentId = 1;
        List<Employee> employees = Arrays.asList(
                new Employee("John", "Doe", departmentId, 50000.0),
                new Employee("Jane", "Smith", departmentId, 60000.0)
        );


        when(employeeService.printAll()).thenReturn(employees);


        double actualMinSalary = departmentService.getMinSalaryByDepartment(departmentId);


        assertEquals(50000.0, actualMinSalary);
    }

    @Test
    void getEmployeesGroupedByDepartment_ShouldReturnMap_WhenEmployeesExist() {

        List<Employee> employees = Arrays.asList(
                new Employee("John", "Doe", 1, 50000.0),
                new Employee("Jane", "Smith", 2, 60000.0)
        );


        when(employeeService.printAll()).thenReturn(employees);


        Map<Integer, List<Employee>> actualMap = departmentService.getEmployeesGroupedByDepartment();


        assertEquals(2, actualMap.size());
        assertEquals(Arrays.asList(50000.0), actualMap.get(1).stream().map(Employee::getSalary).collect(Collectors.toList()));
        assertEquals(Arrays.asList(60000.0), actualMap.get(2).stream().map(Employee::getSalary).collect(Collectors.toList()));
    }
}
