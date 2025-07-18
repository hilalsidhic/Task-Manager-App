package com.hilal.TaskManagerAPI.exception;

import com.hilal.TaskManagerAPI.controller.TaskController;
import com.hilal.TaskManagerAPI.service.impl.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void taskNotFoundExceptionHandledCorrectly() throws Exception {
        // Arrange
        when(taskService.getTaskById(1L)).thenThrow(new TaskNotFoundException("Task not found"));

        // Act + Assert
        mockMvc.perform(get("/api/tasks/1")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Task not found"));
    }
}
