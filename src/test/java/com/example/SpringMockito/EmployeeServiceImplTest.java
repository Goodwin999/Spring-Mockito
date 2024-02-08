package com.example.SpringMockito;

import com.example.SpringMockito.dto.Employee;
import com.example.SpringMockito.exception.EmployeeAlreadyAddedException;
import com.example.SpringMockito.exception.EmployeeNotFoundException;
import com.example.SpringMockito.exception.EmployeeStorageIsFullException;
import com.example.SpringMockito.service.EmployeeService;
import com.example.SpringMockito.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


    class EmployeeServiceImplTest {

        private EmployeeService employeeService;
        private Employee testEmployee;

        @BeforeEach
        void setUp() {
            employeeService = new EmployeeServiceImpl();
            testEmployee = new Employee("John", "Doe", 1, 50000.0);
        }

        @Test
        void addEmployee_ShouldAddEmployee_WhenValidDataProvided() {
            assertEmployeeValues(testEmployee);
        }

        @Test
        void addEmployee_ShouldThrowException_WhenEmployeeAlreadyExists() {
            employeeService.addEmployee(testEmployee.getFirstName(), testEmployee.getLastName(),
                    testEmployee.getDepartment(), testEmployee.getSalary());

            assertThrows(EmployeeAlreadyAddedException.class,
                    () -> employeeService.addEmployee(testEmployee.getFirstName(), testEmployee.getLastName(),
                            testEmployee.getDepartment(), testEmployee.getSalary()));
        }

        @Test
        void removeEmployee_ShouldRemoveEmployee_WhenValidDataProvided() {
            // Arrange
            EmployeeService employeeService = new EmployeeServiceImpl();
            String firstName = "John";
            String lastName = "Doe";
            int department = 1;
            double salary = 50000.0;


            employeeService.addEmployee(firstName, lastName, department, salary);


            employeeService.removeEmployee(firstName, lastName);


            assertThrows(EmployeeNotFoundException.class,
                    () -> employeeService.findEmployee(firstName, lastName));
        }


        @Test
        void removeEmployee_ShouldThrowException_WhenEmployeeNotFound() {
            assertThrows(EmployeeNotFoundException.class,
                    () -> employeeService.removeEmployee("Non", "Existent"));
        }

        @Test
        void findEmployee_ShouldReturnEmployee_WhenValidDataProvided() {

            EmployeeService employeeService = new EmployeeServiceImpl();
            String firstName = "John";
            String lastName = "Doe";
            int department = 1;
            double salary = 50000.0;


            employeeService.addEmployee(firstName, lastName, department, salary);


            Employee foundEmployee = employeeService.findEmployee(firstName, lastName);


            assertEmployeeValues(foundEmployee);
        }


        @Test
        void findEmployee_ShouldThrowException_WhenEmployeeNotFound() {
            assertThrows(EmployeeNotFoundException.class,
                    () -> employeeService.findEmployee("Non", "Existent"));
        }

        private void assertEmployeeValues(Employee employee) {
            Employee addedEmployee = employeeService.addEmployee(employee.getFirstName(), employee.getLastName(),
                    employee.getDepartment(), employee.getSalary());

            assertNotNull(addedEmployee);
            assertEquals(employee, addedEmployee);

            Employee removedEmployee = employeeService.removeEmployee(employee.getFirstName(), employee.getLastName());

            assertNotNull(removedEmployee);
            assertEquals(employee, removedEmployee);

            assertThrows(EmployeeNotFoundException.class,
                    () -> employeeService.findEmployee(employee.getFirstName(), employee.getLastName()));
        }
    }
