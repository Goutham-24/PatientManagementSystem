# 🏥 Patient Management System

A robust backend RESTful API application developed using **Spring Boot** to help doctors manage patients and their medical records. This project follows a clean layered architecture and includes secure authentication, MySQL integration, and full CI/CD automation using Jenkins and Docker.

---


## ✅ Features & Architecture

### 📁 Project Structure

- **Entity / Model Layer**  
  Defines `Doctor`, `Patient`, and `MedicalRecord` entities with JPA annotations and proper relationships.

- **Controller Layer**  
  - Exposes RESTful API endpoints.  
  - Manages and validates incoming HTTP requests.

 - **Service Layer**  
  - Contains all business logic.  
  - Handles data processing, validation, and authentication.
    
- **Repository Layer**  
  - Built using `JpaRepository` for CRUD operations.  
  - Includes custom queries using `@Query` for specific data fetches.

---

### 🔐 Security & Error Handling

- **Spring Security**  
  - Implements secured login for doctors.  
  - Restricts access to protected routes using authentication tokens.

- **Exception Handling**  
  - Custom exceptions like `PatientNotFoundException` etc.  
  - Global error handling using `@ControllerAdvice`.

---

### 🧪 Testing

- **JUnit & Mockito**  
  - Unit tests for service layer testing.  
  - Mocking to isolate and test components independently and provide test cases.

---

### 🔁 Entity Relationships

- `Doctor` ➝ `Patient` → One-to-Many  
- `Patient` ➝ `MedicalRecord` → One-to-Many  

---

### 🗄️ Database Connectivity

- Uses **MySQL** for persistent storage.  
- Database configured via `application.properties`.

---

### ⚙️ DevOps & CI/CD

- **Docker**  
  - Containerizes the Spring Boot application.  
  - Runs on port `8090`.

- **Jenkins**  
  - Automates pipeline:  
    - Clones repository  
    - Builds project  
    - Runs unit tests  
    - Builds Docker image

- **GitHub Webhooks**  
  - Triggers Jenkins build on every code push automatically.

---

## 🚀 Tech Stack

| Category         | Tools & Frameworks             |
|------------------|-------------------------------|
| Backend          | Spring Boot, Spring Security  |
| Database         | MySQL                         |
| Testing          | JUnit, Mockito                |
| API Testing      | Postman                       |
| DevOps           | Docker, Jenkins, Webhooks     |
| Version Control  | Git, GitHub                   |

---
