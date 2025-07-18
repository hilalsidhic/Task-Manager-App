package com.hilal.TaskManagerAPI.service.impl;

import com.hilal.TaskManagerAPI.dto.TaskRequestDto;
import com.hilal.TaskManagerAPI.dto.TaskResponseDto;
import com.hilal.TaskManagerAPI.dto.TaskUpdateDto;
import com.hilal.TaskManagerAPI.entity.Task;
import com.hilal.TaskManagerAPI.enums.Priority;
import com.hilal.TaskManagerAPI.enums.TaskStatus;
import com.hilal.TaskManagerAPI.exception.TaskNotFoundException;
import com.hilal.TaskManagerAPI.repository.TaskRepository;
import com.hilal.TaskManagerAPI.service.TaskServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService implements TaskServiceInterface {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        Task task = Task.builder()
                .title(taskRequestDto.getTitle())
                .description(taskRequestDto.getDescription())
                .priority(taskRequestDto.getPriority())
                .status(taskRequestDto.getStatus())
                .createdAt(LocalDateTime.now()) // Assuming createdAt is set to now
                .updatedAt(LocalDateTime.now()) // Assuming updatedAt is set to now
                .dueDate(taskRequestDto.getDueDate()) // Assuming due date is set to now for simplicity
                .build();
        Task savedTask = taskRepository.save(task);
        return TaskResponseDto.builder()
                .id(savedTask.getId())
                .title(savedTask.getTitle())
                .description(savedTask.getDescription())
                .priority(savedTask.getPriority())
                .status(savedTask.getStatus())
                .dueDate(savedTask.getDueDate())
                .build();
    }

    @Override
    public TaskResponseDto updateTask(Long taskId, TaskUpdateDto taskUpdateDto) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        if (taskUpdateDto.getTitle() != null) {
            existingTask.setTitle(taskUpdateDto.getTitle());
        }
        if (taskUpdateDto.getDescription() != null) {
            existingTask.setDescription(taskUpdateDto.getDescription());
        }
        if (taskUpdateDto.getPriority() != null) {
            existingTask.setPriority(taskUpdateDto.getPriority());
        }
        if (taskUpdateDto.getStatus() != null) {
            existingTask.setStatus(taskUpdateDto.getStatus());
        }
        if (taskUpdateDto.getDueDate() != null) {
            existingTask.setDueDate(taskUpdateDto.getDueDate());
        }
        existingTask.setUpdatedAt(LocalDateTime.now()); // Update the updatedAt field
        Task updatedTask = taskRepository.save(existingTask);
        return TaskResponseDto.builder()
                .id(updatedTask.getId())
                .title(updatedTask.getTitle())
                .description(updatedTask.getDescription())
                .priority(updatedTask.getPriority())
                .status(updatedTask.getStatus())
                .dueDate(updatedTask.getDueDate())
                .build();
    }

    @Override
    public void deleteTask(Long taskId) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        taskRepository.delete(existingTask);
    }

    @Override
    public TaskResponseDto getTaskById(Long taskId) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));
        return TaskResponseDto.builder()
                .id(existingTask.getId())
                .title(existingTask.getTitle())
                .description(existingTask.getDescription())
                .priority(existingTask.getPriority())
                .status(existingTask.getStatus())
                .dueDate(existingTask.getDueDate())
                .build();
    }

    @Override
    public List<TaskResponseDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks found");
        }
        return tasks.stream()
                .map(task -> TaskResponseDto.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .priority(task.getPriority())
                        .status(task.getStatus())
                        .dueDate(task.getDueDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDto> getTasksByStatus(TaskStatus status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks found with status: " + status);
        }
        return tasks.stream()
                .map(task -> TaskResponseDto.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .priority(task.getPriority())
                        .status(task.getStatus())
                        .dueDate(task.getDueDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDto> getTasksByPriority(Priority priority) {
        List<Task> tasks = taskRepository.findByPriority(priority);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks found with priority: " + priority);
        }
        return tasks.stream()
                .map(task -> TaskResponseDto.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .priority(task.getPriority())
                        .status(task.getStatus())
                        .dueDate(task.getDueDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDto> getTasksByDueDate(LocalDateTime dueDate) {
        List<Task> tasks = taskRepository.findByDueDateBetween(LocalDateTime.now(),dueDate);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks found with due date before: " + dueDate);
        }
        return tasks.stream()
                .map(task -> TaskResponseDto.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .priority(task.getPriority())
                        .status(task.getStatus())
                        .dueDate(task.getDueDate())
                        .build())
                .collect(Collectors.toList());
    }
}
