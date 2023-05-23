package com.greenbone.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.greenbone.admin.dto.ComputerDto;
import com.greenbone.admin.exceptions.AppException;
import com.greenbone.admin.model.Employee;
import com.greenbone.admin.notification.NotificationService;
import com.greenbone.admin.repository.EmployeeRepo;
import com.greenbone.admin.service.ComputerService;
import com.greenbone.admin.service.EmployeeServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private ComputerService computerService;

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void testAssignComputerToEmployee_WithUnassignedComputer() {
        //given
        UUID employeeId = UUID.randomUUID();
        UUID computerId = UUID.randomUUID();

        // Mock the behavior of computerService.getComputerInfoById
        ComputerDto computer = new ComputerDto();
        computer.setId(computerId);
        computer.setEmployeeId(null);

        List<ComputerDto> employeeComputers = new ArrayList<>();
        employeeComputers.add(computer);

        //when
        when(computerService.getComputerInfoById(computerId)).thenReturn(computer);

        // Mock the behavior of employeeRepo.findById
        Employee employee = new Employee();
        employee.setId(employeeId);
        when(employeeRepo.findById(employeeId)).thenReturn(Optional.of(employee));

        // Mock the behavior of computerService.getEmployeeComputersCount
        when(computerService.getEmployeeComputersCount(employeeId)).thenReturn(0);
        when(computerService.getAllEmployeeComputers(employeeId)).thenReturn(employeeComputers);

        // Call the method under test
        List<ComputerDto> result = employeeService.assignComputerToEmployee(employeeId, computerId);

        // Verify the interactions and assertions
        verify(computerService).saveComputer(any(ComputerDto.class));
        verify(notificationService, never()).sendWarningNotification(anyString());
        assertEquals(1, result.size());
        assertEquals(computerId, result.get(0).getId());
        assertEquals(employeeId, result.get(0).getEmployeeId());
    }

    @Test
    public void testAssignComputerToEmployee_WithAlreadyAssignedComputer() {
        UUID employeeId = UUID.randomUUID();
        UUID computerId = UUID.randomUUID();

        // Mock the behavior of computerService.getComputerInfoById
        ComputerDto computer = new ComputerDto();
        computer.setEmployeeId(UUID.randomUUID());
        when(computerService.getComputerInfoById(computerId)).thenReturn(computer);

        // Call the method under test and assert that it throws the expected exception
        assertThrows(AppException.class,
            () -> employeeService.assignComputerToEmployee(employeeId, computerId));

        // Verify that no further interactions occurred
        verifyNoMoreInteractions(computerService, employeeRepo, notificationService);
    }

    @Test
    public void testUnAssignComputerFromEmployee_WithAssignedComputer() {
        UUID employeeId = UUID.randomUUID();
        UUID computerId = UUID.randomUUID();

        // Mock the behavior of computerService.getComputerInfoById
        ComputerDto computer = new ComputerDto();
        computer.setEmployeeId(employeeId);
        when(computerService.getComputerInfoById(computerId)).thenReturn(computer);

        // Call the method under test
        List<ComputerDto> result = employeeService.unAssignComputerFromEmployee(employeeId,
            computerId);

        // Verify the interactions and assertions
        verify(computerService).saveComputer(any(ComputerDto.class));
        assertEquals(0, result.size());
    }

    @Test
    public void testUnAssignComputerFromEmployee_WithMismatchedEmployeeId() {
        UUID employeeId = UUID.randomUUID();
        UUID computerId = UUID.randomUUID();

        // Mock the behavior of computerService.getComputerInfoById
        ComputerDto computer = new ComputerDto();
        computer.setEmployeeId(UUID.randomUUID());
        when(computerService.getComputerInfoById(computerId)).thenReturn(computer);

        // Call the method under test and assert that it throws the expected exception
        assertThrows(AppException.class,
            () -> employeeService.unAssignComputerFromEmployee(employeeId, computerId));

        // Verify that no further interactions occurred
        verifyNoMoreInteractions(computerService);
    }
}


