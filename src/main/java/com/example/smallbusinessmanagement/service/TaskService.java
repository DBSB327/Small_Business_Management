package com.example.smallbusinessmanagement.service;

import com.example.smallbusinessmanagement.dto.TaskRequest;
import com.example.smallbusinessmanagement.enums.TaskStatus;
import com.example.smallbusinessmanagement.enums.UserRole;
import com.example.smallbusinessmanagement.model.Task;
import com.example.smallbusinessmanagement.model.User;
import com.example.smallbusinessmanagement.repository.TaskRepository;
import com.example.smallbusinessmanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Transactional
    public Task createTask(TaskRequest request) {
        User assignee = userRepository.findById(request.getAssigneeId())
                .orElseThrow(() -> new EntityNotFoundException("Сотрудник не найден"));

        if(assignee.getRole()!= UserRole.EMPLOYEE){
            throw new RuntimeException("Задачу можно назначать только сотрудникам");
        }
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