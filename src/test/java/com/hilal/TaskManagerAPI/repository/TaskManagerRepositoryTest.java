package com.hilal.TaskManagerAPI.repository;

import com.hilal.TaskManagerAPI.entity.Task;
import com.hilal.TaskManagerAPI.enums.Priority;
import com.hilal.TaskManagerAPI.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskManagerRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    private Task createTask(String title, Priority priority, TaskStatus status, LocalDateTime dueDate) {
        return taskRepository.save(Task.builder()
                .title(title)
                .description("Sample description")
                .priority(priority)
                .status(status)
                .dueDate(dueDate)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Test
    void findByStatus_shouldReturnCorrectTasks() {
        createTask("Pending Task", Priority.MEDIUM, TaskStatus.PENDING, LocalDateTime.now());
        createTask("Completed Task", Priority.HIGH, TaskStatus.COMPLETED, LocalDateTime.now());

        List<Task> pendingTasks = taskRepository.findByStatus(TaskStatus.PENDING);
        assertEquals(1, pendingTasks.size());
        assertEquals(TaskStatus.PENDING, pendingTasks.get(0).getStatus());
    }

    @Test
    void findByPriority_shouldReturnCorrectTasks() {
        createTask("Low Priority", Priority.LOW, TaskStatus.PENDING, LocalDateTime.now());
        createTask("High Priority", Priority.HIGH, TaskStatus.IN_PROGRESS, LocalDateTime.now());

        List<Task> highPriorityTasks = taskRepository.findByPriority(Priority.HIGH);
        assertEquals(1, highPriorityTasks.size());
        assertEquals(Priority.HIGH, highPriorityTasks.get(0).getPriority());
    }

    @Test
    void findByDueDateBetween_shouldReturnTasksInRange() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusDays(1);
        LocalDateTime end = now.plusDays(2);

        createTask("Today Task", Priority.MEDIUM, TaskStatus.PENDING, now);
        createTask("Tomorrow Task", Priority.HIGH, TaskStatus.PENDING, now.plusDays(1));
        createTask("Out of Range Task", Priority.LOW, TaskStatus.PENDING, now.plusDays(5));

        List<Task> tasksInRange = taskRepository.findByDueDateBetween(start, end);
        assertEquals(2, tasksInRange.size());
    }
}
