# EDU-BRIDGE: Student Enrollment Management System

**Project Type:** Web-Based Student Enrollment Management System

**Technology Stack:** Java Spring Boot, MySQL, HTML, CSS, and JavaScript

## Project Description

EDU-BRIDGE is a web-based student enrollment management system designed to simplify and organize the enrollment process. It allows students to create accounts, submit enrollment applications, and track their application status. It also provides staff with tools to manage student records, review applications, and approve or reject enrollment requests.

## Key Features

### Student Features
- Student account registration and login
- Online enrollment application
- Submission of personal and academic information
- Application reference number generation
- Real-time enrollment status tracking
- View application remarks and feedback
- Resubmit rejected applications

### Staff Features
- Secure staff login
- Staff dashboard
- View and manage student records
- View and review enrollment applications
- Approve or reject enrollment applications
- Provide remarks and feedback on applications

### Administrator Features
- Administrator account access
- Create new staff accounts
- Role-based access control
- Manage staff account creation

### System Features
- MySQL database integration
- Spring Boot backend
- Input validation
- Password encryption using BCrypt
- Session-based authentication
- Organized student and staff records
- Enrollment application status management

## Database Setup

EDU-BRIDGE uses MySQL as its database management system.

### Requirements
- XAMPP Control Panel
- MySQL
- phpMyAdmin

### Installation Steps

1. Open the XAMPP Control Panel.
2. Start Apache and MySQL.
3. Open phpMyAdmin at `http://localhost/phpmyadmin`.
4. Create a new database named `edubridge_db`.
5. Select the `edubridge_db` database.
6. Click the **Import** tab.
7. Click **Choose File** and select `database/edubridge_db.sql`.
8. Click **Import** or **Go** to execute the SQL file.

### Database Configuration

Configure the database connection in `src/main/resources/application.properties`.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/edubridge_db
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

**Note:** Update the database username and password according to your local MySQL configuration.