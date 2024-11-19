# Task Management System API

## Description
This is a task management API implemented using Spring Boot. It allows users to create, update, delete, and list tasks. Each task has a title, description, due date, and status (pending, in-progress, completed).

## Features
- Create a new task
- Get a list of all tasks
- Get a task by ID
- Update an existing task
- Delete a task
- Mark a task as complete

## Technologies Used
- Spring Boot 3.0
- Spring Security (for Basic Authentication)
- JPA/Hibernate (for database interaction)
- MySQL Database (for store, manage and maniputed data)
- JUnit 5 (for unit testing)

## Setup and Installation

### Prerequisites
- Java 17 (or higher)
- Maven
- Eclipse IDE
- Git (for version control)

# How to Run the Application
- Clone the Repository
Clone this repository to your local machine:

git clone <repository-url>
cd <repository-directory>

- Set Up MySQL Database
Before running the application, we need to set up a MySQL database. Create the taskdb database:

CREATE DATABASE taskdb;

- Configure the Database Connection
In the src/main/resources/application.properties file, configure MySQL connection settings.

# application.properties file

- MySQL Database Configuration, Hibernate settings, Enable JPA repositories &  Enable date-time formatting globally

spring.datasource.url=jdbc:mysql://localhost:3306/taskdb?useSSL=false&serverTimezone=UTC

spring.datasource.username=root

spring.datasource.password=root

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true 

spring.data.jpa.repositories.enabled=true

spring.jackson.date-format=yyyy-MM-dd

spring.jackson.time-zone=UTC

Note: Change root and root to match your MySQL username and password.
______________________________________________________________________________

- Build the Application
Install Maven and then build the project:

 command: mvn clean install

- Run the Application
we can now run the Spring Boot application with:

 command: mvn spring-boot:run

- The application will run by on port 9090. we can access the API at http://localhost:9090.

# How to Test the Application
- we can use tool like Postman or any HTTP client to interact with the API. Here are the available endpoints:

- Basic Authentication
All endpoints require Basic Authentication. Use the following credentials:

for AdminUSer
User "admin" with password "admin123"

for NormalUser
User "user" with password "user123"

- The authentication is passed in the Authorization header in Basic Auth.

Username: admin
Password: admin123

- Api Requests: 

- For get access of every below api's we have need pass username and password in header: 
  Authorization: Basic Auth: Username "user" with password "user123"

Get all tasks:
GET http://localhost:9090/tasks


Get a task by ID:
GET http://localhost:9090/tasks/1


Create a new task:
POST http://localhost:9090/tasks

Content-Type: application/json

{
  "title": "My New Task thirteen",
  "description": "This is a my new task thirteen",
  "dueDate": "2024-12-15"
}

Update an existing task:
PUT http://localhost:9090/tasks/13

Content-Type: application/json

{
  "title": "Updated Task thirteen Title",
  "description": "Updated task thirteen description",
  "dueDate": "2024-12-15",
  "status": "IN_PROGRESS"
}

Mark a task as complete:
PATCH http://localhost:9090/tasks/9/complete


Delete a task:
DELETE http://localhost:9090/tasks/10


# Unit Testing the Application
The application includes unit tests for core functionality. To run the tests:

 command: mvn test

The tests use JUnit 5 and verify the task creation

# Brief documentation of design decisions and assumptions made

# Design Decisions
Incorporating MySQL:
The application uses MySQL for task persistence. We use Spring Data JPA to manage the entity-to-database mappings, simplifying CRUD operations.

Task Entity:
The Task class is annotated with JPA annotations (@Entity, @Id, etc.), making it a persistent entity. It includes fields such as id, title, description, dueDate, status, createdAt, and updatedAt. The status is an enum (PENDING, IN_PROGRESS, COMPLETED).

Repository Layer:
The TaskRepository interface extends JpaRepository, enabling basic CRUD operations. We do not need to write custom queries for most operations, bacause Spring Data JPA.

Service Layer:
The TaskService layer handles business logic such as task creation, retrival, updating, marking as complete, and deletion. It interacts with the repository to manage data.

Controller Layer:
The TaskController provides endpoints to interact with tasks. It delegates to the service layer for task management and handles HTTP requests and responses.

Basic Authentication:
Basic Authentication is used to secure the API. It is simple to implement but should be replaced with more secure methods (like JWT or OAuth) in a production environment.

Error Handling:
Custom exceptions, like TaskNotFoundException, InvalidDateFormatException etc, are used to handle errors. These are mapped to appropriate HTTP status codes:
400 Bad Request: Invalid request (e.g., validation errors or bad input).
404 Not Found: Task not found for the specified ID.
500 Internal Server Error: For any unexpected errors (handled generically).


# Assumptions Made
Database Configuration:
MySQL is assumed to be running locally with the default database port (3306). The taskdb database must exist before starting the application.

Table Generation:
Hibernate's ddl-auto=update setting will automatically create or update the schema. This is useful for development but should be set to none or validate in production.

Authentication:
Basic Authentication is implemented for simplicity. In a production environment, it is recommended to switch to JWT or OAuth2 for improved security.

Data Validation:
The application assumes that all required fields (like title, description, and dueDate) are provided when creating or updating tasks. Input validation could be enhanced further for production.


