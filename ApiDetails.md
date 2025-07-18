# 📘 Task Management API – Swagger Endpoint Summary

This document provides a list of all available API endpoints for the Task Management application.

---

## Base URL
```
http://localhost:8080/api
```

---

## 📌 Endpoints

### 🟢 Create Task
- **Method**: POST `/tasks`
- **Description**: Creates a new task with the provided details.
- **Request Body**: `TaskRequestDto`
- **Response**: `201 Created`

---

### 🟡 Update Task
- **Method**: PATCH `/tasks/{taskId}`
- **Description**: Updates an existing task.
- **Path Variable**: `taskId` – Task ID to update
- **Request Body**: `TaskUpdateDto`
- **Response**: `200 OK`

---

### 🔴 Delete Task
- **Method**: DELETE `/tasks/{taskId}`
- **Description**: Deletes a task by its ID.
- **Path Variable**: `taskId`
- **Response**: `204 No Content`

---

### 🔍 Get Task by ID
- **Method**: GET `/tasks/{taskId}`
- **Description**: Retrieves a task by its ID.
- **Response**: `200 OK`

---

### 📋 Get All Tasks
- **Method**: GET `/tasks/all`
- **Description**: Retrieves all tasks.
- **Response**: `200 OK`

---

### 📊 Get Tasks by Status
- **Method**: GET `/tasks/status/{status}`
- **Description**: Retrieves tasks filtered by status.
- **Path Variable**: `status` (e.g., `PENDING`, `COMPLETED`, etc.)
- **Response**: `200 OK`

---

### 🏷 Get Tasks by Priority
- **Method**: GET `/tasks/priority/{priority}`
- **Description**: Retrieves tasks filtered by priority.
- **Path Variable**: `priority` (e.g., `HIGH`, `MEDIUM`, `LOW`)
- **Response**: `200 OK`

---

### 📆 Get Tasks by Due Date
- **Method**: GET `/tasks/dueDate/{dueDate}`
- **Description**: Retrieves tasks filtered by due date.
- **Path Variable**: `dueDate` (ISO 8601 format, e.g., `2024-02-15T10:00:00`)
- **Response**: `200 OK`

---

## 🛠 DTOs
- `TaskRequestDto`: Used for creating a new task
- `TaskUpdateDto`: Used for updating existing task fields
- `TaskResponseDto`: Returned for all retrieval requests

---

For complete request/response details, refer to the [Swagger UI](http://localhost:8080/swagger-ui/index.html).
