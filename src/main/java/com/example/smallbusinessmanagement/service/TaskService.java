package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.TaskRequest;
import com.example.smallbusinessmanagement.enums.TaskStatus;
import com.example.smallbusinessmanagement.model.Employee;
import com.example.smallbusinessmanagement.model.Task;
import com.example.smallbusinessmanagement.repository.EmployeeRepository;
import com.example.smallbusinessmanagement.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Task createTask(TaskRequest request) {
        Employee assignee = employeeRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new EntityNotFoundException("Сотрудник не найден"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.NEW);
        task.setAssignee(assignee);
        task.setDeadline(request.getDeadline());

        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTaskStatus(Long taskId, TaskStatus newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена"));
        task.setStatus(newStatus);
        return taskRepository.save(task);
    }

    public List<Task> getEmployeeTasks(Long employeeId) {
        return taskRepository.findByAssigneeId(employeeId);
    }
}