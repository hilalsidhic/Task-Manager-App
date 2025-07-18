package com.hilal.TaskManagerAPI.service;

import com.hilal.TaskManagerAPI.dto.TaskRequestDto;
import com.hilal.TaskManagerAPI.dto.TaskResponseDto;
import com.hilal.TaskManagerAPI.dto.TaskUpdateDto;
import com.hilal.TaskManagerAPI.enums.Priority;
import com.hilal.TaskManagerAPI.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskServiceInterface {

    TaskResponseDto createTask(TaskRequestDto taskRequestDto);

    TaskResponseDto updateTask(Long taskId, TaskUpdateDto taskUpdateDto);

    void deleteTask(Long taskId);

    TaskResponseDto getTaskById(Long taskId);

    List<TaskResponseDto> getAllTasks();

    List<TaskResponseDto> getTasksByStatus(TaskStatus status);

    List<TaskResponseDto> getTasksByPriority(Priority priority);

    List<TaskResponseDto> getTasksByDueDate(LocalDateTime dueDate);
}
