# Role-Based Access Control (RBAC)

1. [Purpose of the Project](#purpose-of-the-project)  
2. [Key Features](#key-features)  
3. [Technologies Used](#technologies-used)  
4. [How to Run the Project Locally](#how-to-run-the-project-locally)  
   - [Method 1: Using Java and Maven](#method-1-using-java-and-maven)  
   - [Method 2: Using Docker](#method-2-using-docker)  
5. [Accessing the API Endpoints](#accessing-the-api-endpoints)  
6. [Accessing the Swagger UI](#swagger-ui)  

---

## Purpose of the Project

The **RBAC Authentication System** provides role-based access control (RBAC) for a web application using **Spring Boot**, **JWT (JSON Web Token)**, and **MongoDB**. The system allows users to register, log in, and access protected resources based on their assigned roles (e.g., **User**, **Moderator**, **Admin**).

### Key Features:
- **User Registration**: Users can register with details like name, email, password, and roles.
- **Login Authentication**: Users can log in to receive a **JWT token** for authentication.
- **Role-based Access**: Access to certain API endpoints is restricted based on user roles.
- **JWT-based Authentication**: All API requests are authenticated using JWT tokens.


### Technologies Used:
- **Spring Boot**: Backend framework.
- **Spring Security**: For managing authentication and authorization.
- **JWT**: For secure token-based authentication.
- **MongoDB**: For storing user data.
- **BCrypt**: For password hashing.

The project uses role-based access control to manage access to various endpoints:
- **User Role**: Can access basic resources.
- **Moderator Role**: Can access resources meant for moderators.
- **Admin Role**: Has full access to all resources.

## How to Run the Project Locally

### Method 1: Using Java and Maven
Ensure the following tools are installed on your local machine:

1. **JDK 17** (or higher)
2. **Maven** (for building and managing dependencies)
3. **MongoDB** (already it has connection with MongoDB Atlas)
4. **PORT** (bydefault configured with 8080)


### Steps to Run the Project

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Manish-Kumarrrr/vrv_assignment
   cd vrv_assignment
2. **Run it locally**:
   ```bash
   maven clean install
   mvn spring-boot:run
3. **The application will start on http://localhost:{PORT}**

### Method 2: Using Docker
Ensure the following tools are installed on your local machine:

1. **Install Docker and Ensure it is running** 

### Steps to Run the Project

1. **Pull the Image**:
   ```bash
   docker pull devmanish12/rbac
   ```
2. **Run it locally**:
   ```bash
   docker run -p {PORT}:8080 rbac
   ```
3. **The application will start on http://localhost:{PORT}**

### Accessing the API Endpoints
- **POST**:/v1/auth/register.
- **GET**: /v1/auth/login
- **GET**: /v1/resources/user (for USER,MODERATOR,ADMIN role)
- **GET**: /v1/resources/moderator (for MODERATOR,ADMIN role)
- **GET**: /v1/resources/admin (for ADMIN role)



## Swagger UI
- **The swagger ui will start on  http://localhost:{PORT}/swagger-ui/index.html**
![image](https://github.com/user-attachments/assets/75afe4e8-0591-4c89-b5d1-41d2bcd69a2f)

---
## Contact
- Author: Manish Kumar
- GitHub: [Manish-Kumarrrr](https://github.com/Manish-Kumarrrr)
