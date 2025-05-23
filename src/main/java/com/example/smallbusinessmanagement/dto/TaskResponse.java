package com.example.smallbusinessmanagement.dto;

import com.example.smallbusinessmanagement.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponse {
    private Long id;
    private String title;
    private TaskStatus status;
    private String assigneeName;
}
