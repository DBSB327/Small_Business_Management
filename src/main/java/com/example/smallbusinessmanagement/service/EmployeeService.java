package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.EmployeeRequest;
import com.example.smallbusinessmanagement.model.Employee;
import com.example.smallbusinessmanagement.model.User;
import com.example.smallbusinessmanagement.repository.EmployeeRepository;
import com.example.smallbusinessmanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Employee createEmployee(EmployeeRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        Employee employee = new Employee();
        employee.setFullName(request.getFullName());
        employee.setPhone(request.getPhone());
        employee.setUser(user);
        return employeeRepository.save(employee);
    }
}