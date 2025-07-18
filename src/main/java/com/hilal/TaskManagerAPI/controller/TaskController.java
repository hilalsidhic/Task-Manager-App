package com.hilal.TaskManagerAPI.controller;

import com.hilal.TaskManagerAPI.dto.TaskRequestDto;
import com.hilal.TaskManagerAPI.dto.TaskResponseDto;
import com.hilal.TaskManagerAPI.dto.TaskUpdateDto;
import com.hilal.TaskManagerAPI.enums.Priority;
import com.hilal.TaskManagerAPI.enums.TaskStatus;
import com.hilal.TaskManagerAPI.service.impl.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Task Management", description = "APIs for managing tasks")
@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "Create a new task", description = "Creates a new task with the provided details")
    @ApiResponse(responseCode = "201", description = "Task created successfully")
    @PostMapping("/tasks")
    public TaskResponseDto createTask(@RequestBody @Valid TaskRequestDto taskRequestDto) {
        return taskService.createTask(taskRequestDto);
    }

    @Operation(summary = "Update an existing task", description = "Updates the details of an existing task")
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @PatchMapping("/tasks/{taskId}")
    public TaskResponseDto updateTask(@PathVariable Long taskId, @RequestBody TaskUpdateDto taskUpdateDto) {
        return taskService.updateTask(taskId, taskUpdateDto);
    }

    @Operation(summary = "Delete a task", description = "Deletes a task by its ID")
    @ApiResponse(responseCode = "204", description = "Task deleted successfully")
    @DeleteMapping("/tasks/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    @Operation(summary = "Get task by ID", description = "Retrieves a task by its ID")
    @ApiResponse(responseCode = "200", description = "Task retrieved successfully")
    @GetMapping("/tasks/{taskId}")
    public TaskResponseDto getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }

    @Operation(summary = "Get all tasks", description = "Retrieves all tasks")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @GetMapping("/tasks/all")
    public List<TaskResponseDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @Operation(summary = "Get tasks by status", description = "Retrieves tasks filtered by their status")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @GetMapping("/tasks/status/{status}")
    public List<TaskResponseDto> getTasksByStatus(@PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }

    @Operation(summary = "Get tasks by priority", description = "Retrieves tasks filtered by their priority")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @GetMapping("/tasks/priority/{priority}")
    public List<TaskResponseDto> getTasksByPriority(@PathVariable Priority priority) {
        return taskService.getTasksByPriority(priority);
    }

    @Operation(summary = "Get tasks by due date", description = "Retrieves tasks filtered by their due date")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @GetMapping("/tasks/dueDate/{dueDate}")
    public List<TaskResponseDto> getTasksByDueDate(@PathVariable LocalDateTime dueDate) {
        return taskService.getTasksByDueDate(dueDate);
    }
}
