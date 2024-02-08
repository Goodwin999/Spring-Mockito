package com.example.SpringMockito;

import com.example.SpringMockito.dto.Employee;
import com.example.SpringMockito.exception.EmployeeAlreadyAddedException;
import com.example.SpringMockito.exception.EmployeeNotFoundException;
import com.example.SpringMockito.exception.EmployeeStorageIsFullException;
import com.example.SpringMockito.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceImplTest {

    EmployeeServiceImpl underTest = new EmployeeServiceImpl();
    static final int MAX_EMPLOYEES = 100;

    Employee employeeTest = new Employee
            ("Ivan", "Ivanov", 1, 100);

    @Test
    void addEmployee_mapIsFull_thrownEmployeeStorageIsFullException() {

        for (int i = 0; i < MAX_EMPLOYEES; i++) {
            underTest.addEmployee("Ivan", String.valueOf(i), 1, 100);
        }

        EmployeeStorageIsFullException ex = assertThrows(EmployeeStorageIsFullException.class,
                () -> underTest.addEmployee("Ivan", "Ivanov", 1, 100));
        assertEquals("превышен лимит количества сотрудников в фирме", ex.getMessage());
    }

    @Test
    void addEmployee_employeeAlreadyExists_throwsEmployeeAlreadyAddedException() {

        underTest.addEmployee("Ivan", "Ivanov", 1, 100);

        EmployeeAlreadyAddedException ex = assertThrows(EmployeeAlreadyAddedException.class,
                () -> underTest.addEmployee("Ivan", "Ivanov", 1, 100));
        assertEquals("в коллекции уже есть такой сотрудник", ex.getMessage());
    }

    @Test
    void addEmployee_employeeIsNotInMap_addedAndReturnedNewEmployee() {

        Employee result = underTest.
                addEmployee(employeeTest.
                        getFirstName(), employeeTest.
                        getLastName(), employeeTest.
                        getDepartment(), employeeTest.
                        getSalary());

        assertEquals(employeeTest, result);

    }

    @Test
    void removeEmployee_employeeIsNotInMap_thrownException() {

        EmployeeNotFoundException ex = assertThrows
                (EmployeeNotFoundException.class,
                        () -> underTest.removeEmployee("Ivan", "Ivanov"));
        assertEquals("сотрудник не найден", ex.getMessage());
    }

    @Test
    void removeEmployee_employeeIsInMap_employeeRemovedAndReturned() {

        underTest.addEmployee(employeeTest.
                getFirstName(), employeeTest.
                getLastName(), employeeTest.
                getDepartment(), employeeTest.
                getSalary());

        Employee result = underTest.removeEmployee(
                employeeTest.getFirstName(),
                employeeTest.getLastName());

        assertEquals(employeeTest, result);
        assertFalse(underTest.printAll().contains(employeeTest));

    }

    @Test
    void findEmployee_firstNameAndLastName_returnedEmployee() {

        Employee addedEmployee = underTest.addEmployee("Ivan", "Ivanov", 1, 100);

        Employee foundEmployee = underTest.findEmployee("Ivan", "Ivanov");

        assertNotNull(foundEmployee);
        assertEquals(addedEmployee, foundEmployee);
    }

    @Test
    void findEmployee_nonExistingEmployee_throwsEmployeeNotFoundException() {

        EmployeeNotFoundException ex = assertThrows(EmployeeNotFoundException.class,
                () -> underTest.findEmployee("Без", "Имянный"));
        assertEquals("сотрудник не найден", ex.getMessage());
    }

    @Test
    void printAll_returnedAllEmployeesInCollection() {

        Employee employee1 = underTest.addEmployee
                ("Ivan", "Ivanov", 1, 100);
        Employee employee2 = underTest.addEmployee
                ("Divan", "Ivanov", 2, 200);

        Collection<Employee> employees = underTest.printAll();

        assertEquals(2, employees.size());
        assertTrue(employees.contains(employee1));
        assertTrue(employees.contains(employee2));

    }
}