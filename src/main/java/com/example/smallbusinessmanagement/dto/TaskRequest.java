package com.example.smallbusinessmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskRequest {
    private String title;
    private String description;
    private Long assigneeId;
    private LocalDateTime deadline;
}
