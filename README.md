# Role-Based Access Control (RBAC)

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

### Prerequisites
Ensure the following tools are installed on your local machine:

1. **JDK 17** (or higher)
2. **Maven** (for building and managing dependencies)
3. **MongoDB** (already it has connection with MongoDB Atlas)


### Steps to Run the Project

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Manish-Kumarrrr/vrv_assignment
   cd vrv_assignment
2. **Run it locally**:
   ```bash
   maven clean install
   mvn spring-boot:run
3. **The application will start on http://localhost:8080**

### Accessing the API Endpoints
- **POST**:/v1/auth/register.
- **GET**: /v1/auth/login
- **GET**: /v1/resources/user (for USER,MODERATOR,ADMIN role)
- **GET**: /v1/resources/moderator (for MODERATOR,ADMIN role)
- **GET**: /v1/resources/admin (for ADMIN role)


---

This README provides clear instructions on how to run the project locally with MongoDB.
