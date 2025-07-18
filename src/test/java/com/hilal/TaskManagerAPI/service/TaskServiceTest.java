package com.hilal.TaskManagerAPI.service;

import com.hilal.TaskManagerAPI.dto.TaskRequestDto;
import com.hilal.TaskManagerAPI.dto.TaskResponseDto;
import com.hilal.TaskManagerAPI.dto.TaskUpdateDto;
import com.hilal.TaskManagerAPI.entity.Task;
import com.hilal.TaskManagerAPI.enums.Priority;
import com.hilal.TaskManagerAPI.enums.TaskStatus;
import com.hilal.TaskManagerAPI.exception.TaskNotFoundException;
import com.hilal.TaskManagerAPI.repository.TaskRepository;
import com.hilal.TaskManagerAPI.service.impl.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private TaskRequestDto taskRequestDto;
    private TaskUpdateDto taskUpdateDto;

    @BeforeEach
    void setup(){

        taskRequestDto = TaskRequestDto.builder()
                .title("New Task")
                .description("This is a new task")
                .priority(Priority.MEDIUM)
                .status(TaskStatus.PENDING)
                .dueDate(LocalDateTime.now().plusDays(3))
                .build();

        taskUpdateDto = TaskUpdateDto.builder()
                .title("Updated Task")
                .description("This is an updated task description")
                .priority(Priority.LOW)
                .status(TaskStatus.COMPLETED)
                .dueDate(LocalDateTime.now().plusDays(1))
                .build();
    }
    // Add test methods here to test the TaskService methods
    @Test
    void createTask_shouldReturnCreatedTaskResponseDto() {
        // Given
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {;
            Task savedTask = invocation.getArgument(0);
            savedTask.setId(1L); // Simulate ID generation
            return savedTask;
        });
        // When
        TaskResponseDto response = taskService.createTask(taskRequestDto);

        // Then
        assertNotNull(response);
        assertEquals("New Task", response.getTitle());
        assertEquals("This is a new task", response.getDescription());
        assertEquals(Priority.MEDIUM, response.getPriority());
        assertEquals(TaskStatus.PENDING, response.getStatus());
    }

    @Test
    void updateTask_shouldReturnUpdatedTaskResponseDto() {
        // Given
        Task existingTask = Task.builder()
                .id(1L)
                .title("Old Task")
                .description("This is an old task")
                .priority(Priority.HIGH)
                .status(TaskStatus.PENDING)
                .dueDate(LocalDateTime.now())
                .build();
        when(taskRepository.findById(1L)).thenReturn(java.util.Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        TaskResponseDto response = taskService.updateTask(1L, taskUpdateDto);

        // Then
        assertNotNull(response);
        assertEquals("Updated Task", response.getTitle());
        assertEquals("This is an updated task description", response.getDescription());
        assertEquals(Priority.LOW, response.getPriority());
        assertEquals(TaskStatus.COMPLETED, response.getStatus());
    }
    @Test
    void updateTask_shouldThrowExceptionWhenTaskNotFound() {
        // Given
        when(taskRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(1L, taskUpdateDto));
    }
    @Test
    void updateTask_shouldReturnSameTaskWhenInputIsNull(){
        // Given
        Task existingTask = Task.builder()
                .id(1L)
                .title("Old Task")
                .description("This is an old task")
                .priority(Priority.HIGH)
                .status(TaskStatus.PENDING)
                .dueDate(LocalDateTime.now())
                .build();
        when(taskRepository.findById(1L)).thenReturn(java.util.Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        TaskResponseDto response = taskService.updateTask(1L, new TaskUpdateDto());

        // Then
        assertNotNull(response);
        assertEquals("Old Task", response.getTitle());
        assertEquals("This is an old task", response.getDescription());
        assertEquals(Priority.HIGH, response.getPriority());
        assertEquals(TaskStatus.PENDING, response.getStatus());
    }

    @Test
    void deleteTask_shouldCallRepositoryDelete() {
        // Given
        Long taskId = 1L;
        Task existingTask = Task.builder()
                .id(taskId)
                .title("Task to be deleted")
                .description("This task will be deleted")
                .priority(Priority.MEDIUM)
                .status(TaskStatus.PENDING)
                .dueDate(LocalDateTime.now())
                .build();
        when(taskRepository.findById(taskId)).thenReturn(java.util.Optional.of(existingTask));

        // When
        taskService.deleteTask(taskId);

        // Then
        verify(taskRepository, times(1)).delete(existingTask);
    }

    @Test
    void deleteTask_shouldThrowExceptionWhenTaskNotFound() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(taskId));
    }

    @Test
    void getTaskById_shouldReturnTaskResponseDto() {
        // Given
        Long taskId = 1L;
        Task existingTask = Task.builder()
                .id(taskId)
                .title("Existing Task")
                .description("This is an existing task")
                .priority(Priority.HIGH)
                .status(TaskStatus.PENDING)
                .dueDate(LocalDateTime.now())
                .build();
        when(taskRepository.findById(taskId)).thenReturn(java.util.Optional.of(existingTask));

        // When
        TaskResponseDto response = taskService.getTaskById(taskId);

        // Then
        assertNotNull(response);
        assertEquals("Existing Task", response.getTitle());
        assertEquals("This is an existing task", response.getDescription());
        assertEquals(Priority.HIGH, response.getPriority());
        assertEquals(TaskStatus.PENDING, response.getStatus());
    }

    @Test
    void getTaskById_shouldThrowExceptionWhenTaskNotFound() {
        // Given
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));
    }

    @Test
    void getAllTasks_shouldReturnListOfTaskResponseDto() {
        // Given
        Task task1 = Task.builder()
                .id(1L)
                .title("Task 1")
                .description("Description 1")
                .priority(Priority.MEDIUM)
                .status(TaskStatus.PENDING)
                .dueDate(LocalDateTime.now())
                .build();
        Task task2 = Task.builder()
                .id(2L)
                .title("Task 2")
                .description("Description 2")
                .priority(Priority.HIGH)
                .status(TaskStatus.COMPLETED)
                .dueDate(LocalDateTime.now().plusDays(1))
                .build();
        when(taskRepository.findAll()).thenReturn(java.util.List.of(task1, task2));

        // When
        java.util.List<TaskResponseDto> response = taskService.getAllTasks();

        // Then
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Task 1", response.get(0).getTitle());
        assertEquals("Task 2", response.get(1).getTitle());
    }
    @Test
    void getAllTasks_shouldThrowExceptionWhenNoTasksFound() {
        // Given
        when(taskRepository.findAll()).thenReturn(java.util.List.of());

        // When & Then
        assertThrows(TaskNotFoundException.class, () -> taskService.getAllTasks());
    }

    @Test
    void getTasksByStatus_shouldReturnListOfTaskResponseDto() {
        // Given
        Task task1 = Task.builder()
                .id(1L)
                .title("Task 1")
                .description("Description 1")
                .priority(Priority.MEDIUM)
                .status(TaskStatus.PENDING)
                .dueDate(LocalDateTime.now())
                .build();
        Task task2 = Task.builder()
                .id(2L)
                .title("Task 2")
                .description("Description 2")
                .priority(Priority.HIGH)
                .status(TaskStatus.PENDING)
                .dueDate(LocalDateTime.now().plusDays(1))
                .build();
        when(taskRepository.findByStatus(TaskStatus.PENDING)).thenReturn(java.util.List.of(task1, task2));

        // When
        java.util.List<TaskResponseDto> response = taskService.getTasksByStatus(TaskStatus.PENDING);

        // Then
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Task 1", response.get(0).getTitle());
        assertEquals("Task 2", response.get(1).getTitle());
    }
    @Test
    void getTasksByStatus_shouldThrowExceptionWhenNoTasksFound() {
        // Given
        when(taskRepository.findByStatus(TaskStatus.COMPLETED)).thenReturn(java.util.List.of());

        // When & Then
        assertThrows(TaskNotFoundException.class, () -> taskService.getTasksByStatus(TaskStatus.COMPLETED));
    }

    @Test
    void getTasksByPriority_shouldReturnListOfTaskResponseDto() {
        // Given
        Task task1 = Task.builder()
                .id(1L)
                .title("Task 1")
                .description("Description 1")
                .priority(Priority.MEDIUM)
                .status(TaskStatus.PENDING)
                .dueDate(LocalDateTime.now())
                .build();
        Task task2 = Task.builder()
                .id(2L)
                .title("Task 2")
                .description("Description 2")
                .priority(Priority.MEDIUM)
                .status(TaskStatus.COMPLETED)
                .dueDate(LocalDateTime.now().plusDays(1))
                .build();
        when(taskRepository.findByPriority(Priority.MEDIUM)).thenReturn(java.util.List.of(task1, task2));

        // When
        java.util.List<TaskResponseDto> response = taskService.getTasksByPriority(Priority.MEDIUM);

        // Then
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Task 1", response.get(0).getTitle());
        assertEquals("Task 2", response.get(1).getTitle());
    }
    @Test
    void getTasksByPriority_shouldThrowExceptionWhenNoTasksFound() {
        // Given
        when(taskRepository.findByPriority(Priority.LOW)).thenReturn(java.util.List.of());

        // When & Then
        assertThrows(TaskNotFoundException.class, () -> taskService.getTasksByPriority(Priority.LOW));
    }

    @Test
    void getTasksByDueDate_shouldReturnListOfTaskResponseDto() {
        // Given
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        Task task1 = Task.builder()
                .id(1L)
                .title("Task 1")
                .description("Description 1")
                .priority(Priority.MEDIUM)
                .status(TaskStatus.PENDING)
                .dueDate(dueDate)
                .build();
        Task task2 = Task.builder()
                .id(2L)
                .title("Task 2")
                .description("Description 2")
                .priority(Priority.HIGH)
                .status(TaskStatus.COMPLETED)
                .dueDate(dueDate)
                .build();
        when(taskRepository.findByDueDateBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(java.util.List.of(task1, task2));

        // When
        java.util.List<TaskResponseDto> response = taskService.getTasksByDueDate( dueDate);

        // Then
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Task 1", response.get(0).getTitle());
        assertEquals("Task 2", response.get(1).getTitle());
    }

    @Test
    void getTasksByDueDate_shouldThrowExceptionWhenNoTasksFound() {
        // Given
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        when(taskRepository.findByDueDateBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(java.util.List.of());

        // When & Then
        assertThrows(TaskNotFoundException.class, () -> taskService.getTasksByDueDate(dueDate));
    }

}
