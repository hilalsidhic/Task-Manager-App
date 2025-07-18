package com.hilal.TaskManagerAPI.dto;

import com.hilal.TaskManagerAPI.enums.Priority;
import com.hilal.TaskManagerAPI.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {
    Long id;
    String title;
    String description;
    Priority priority;
    TaskStatus status;
    LocalDateTime dueDate;
}
