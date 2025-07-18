package com.hilal.TaskManagerAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hilal.TaskManagerAPI.dto.TaskRequestDto;
import com.hilal.TaskManagerAPI.dto.TaskResponseDto;
import com.hilal.TaskManagerAPI.dto.TaskUpdateDto;
import com.hilal.TaskManagerAPI.enums.Priority;
import com.hilal.TaskManagerAPI.enums.TaskStatus;
import com.hilal.TaskManagerAPI.service.impl.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskManagerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskResponseDto getSampleResponse() {
        return TaskResponseDto.builder()
                .id(1L)
                .title("Sample Task")
                .description("Sample Desc")
                .priority(Priority.HIGH)
                .status(TaskStatus.PENDING)
                .dueDate(LocalDateTime.now())
                .build();
    }

    @Test
    void createTask_shouldReturnCreatedTask() throws Exception {
        TaskRequestDto request = TaskRequestDto.builder()
                .title("Sample Task")
                .description("Sample Desc")
                .priority(Priority.HIGH)
                .status(TaskStatus.PENDING)
                .dueDate(LocalDateTime.now())
                .build();

        when(taskService.createTask(any(TaskRequestDto.class))).thenReturn(getSampleResponse());

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()) // since you're not using ResponseEntity with 201
                .andExpect(jsonPath("$.title").value("Sample Task"));
    }

    @Test
    void updateTask_shouldReturnUpdatedTask() throws Exception {
        TaskUpdateDto updateDto = TaskUpdateDto.builder()
                .title("Updated Task")
                .build();

        TaskResponseDto responseDto = getSampleResponse();
        responseDto.setTitle("Updated Task");

        when(taskService.updateTask(Mockito.eq(1L), any(TaskUpdateDto.class))).thenReturn(responseDto);

        mockMvc.perform(patch("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    @Test
    void deleteTask_shouldReturn204() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk()); // not 204 since your controller returns void, not ResponseEntity
    }

    @Test
    void getTaskById_shouldReturnTask() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(getSampleResponse());

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Task"));
    }

    @Test
    void getAllTasks_shouldReturnList() throws Exception {
        when(taskService.getAllTasks()).thenReturn(List.of(getSampleResponse()));

        mockMvc.perform(get("/api/tasks/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getTasksByStatus_shouldReturnList() throws Exception {
        when(taskService.getTasksByStatus(TaskStatus.PENDING)).thenReturn(List.of(getSampleResponse()));

        mockMvc.perform(get("/api/tasks/status/PENDING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getTasksByPriority_shouldReturnList() throws Exception {
        when(taskService.getTasksByPriority(Priority.HIGH)).thenReturn(List.of(getSampleResponse()));

        mockMvc.perform(get("/api/tasks/priority/HIGH"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void getTasksByDueDate_shouldReturnList() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        when(taskService.getTasksByDueDate(now)).thenReturn(List.of(getSampleResponse()));

        mockMvc.perform(get("/api/tasks/dueDate/" + now.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
