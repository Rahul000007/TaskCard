## Task and SubTask Management API

## Prerequisites

1. Database: Set up a database (MySQL) and configure the database connection details in application.properties

## Access API Endpoints

Base URL: http://localhost:8080/api

## User Authentication

1. `Register User`
   **URL: POST /api/auth/register**
   **Request Body: SON payload with user details..**
   **Parameters:** username,email,password
   **Response: JSuccess message upon registration.**
   **Sample Data**

``` javascript
   {
   "username": "testuser",
   "email": "testuser@example.com",
   "password": "password123"
   }
   ```

2. Login User
   **URL: POST /api/auth/login**
   **Request Body: JSON payload with login credentials.**
   **Parameters: usernameOrEmail: Username,password: Password of the user.**
   **Response: JSON containing the JWT token.**

``` javascript
 {
  "usernameOrEmail": "testuser",
  "password": "password123"
}
   ```

## Task Management
1. Create Task
 **URL: POST /api/task**
 **Request Body: JSON payload with task details.**
**Parameters:title: Title of the task.
description: Description of the task.
dueDate: Due date for the task (format: yyyy-MM-dd).
priority: Priority of the task (e.g., HIGH, MEDIUM, LOW).
status: Status of the task (TODO,DONE).**

``` javascript
{
  "title": "Complete Documentation",
  "description": "Write API documentation for task management",
  "dueDate": "2024-12-01",
  "priority": "HIGH",
  "status": "TODO"
}
   ```
2. Get All Tasks
   **URL: GET /api/task**
   **Query Parameters:
   priority: Filter tasks by priority (optional).
   dueDate: Filter tasks by due date (optional, format: yyyy-MM-dd).
   page: Page number for pagination (default: 0).
   size: Page size for pagination (default: 10).**

3. Update Task
   **URL: PUT /api/task/{taskId}**
   **Parameters:
   dueDate: New due date for the task.
   status: New status for the task (optional).**
``` javascript
{
  "dueDate": "2024-12-15",
  "status": 1
}
   ```
4. Delete Task
   **URL: DELETE /api/task/{taskId}**

## SubTask Management
1. Create SubTask
  ** URL: POST /api/subTask**
   **Parameters:
   title: Title of the subtask.
   taskId: ID of the parent task.**
``` javascript
{
  "title": "Write examples",
  "taskId": 1
}
   ```
2. Get All SubTasks
   **URL: GET /api/subTask**
   **Query Parameters:
   taskId: Filter subtasks by task ID (optional).
   page: Page number for pagination (default: 0).
   size: Page size for pagination (default: 10).**

3. Update SubTask
   **URL: PUT /api/subTask/{subTaskId}**
   **Parameters:
   status: New status for the task.**

4. Delete SubTask
  **URL: DELETE /api/task/{subTaskId}**