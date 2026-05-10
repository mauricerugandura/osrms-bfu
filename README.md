# Online Student Result Management System (OSRMS)

## Project Overview

The Online Student Result Management System (OSRMS) is a web-based academic records platform developed for Bright Future University (BFU). It supports the core university workflow for managing students, lecturers, courses, enrollments, marks, result approval, result publishing, and student transcript access.

The application is built with Java Spring Boot and follows the MVC pattern to keep responsibilities clean and maintainable. Administrators manage users and courses, lecturers submit and update student marks, registrars review and publish approved results, and students view their published results and transcripts through a secured portal.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring MVC
- Spring Security
- Spring Data JPA / Hibernate
- MySQL 8
- Thymeleaf
- Bootstrap 5
- Maven
- Docker and Docker Compose
- Git

## Design Pattern

This project follows the Model-View-Controller (MVC) architectural pattern.

- **Model Layer:** Represents the domain entities such as `User`, `Student`, `Lecturer`, `Course`, `Enrollment`, `Result`, and `Transcript`.
- **Repository Layer:** Uses Spring Data JPA repositories to communicate with the MySQL database.
- **Service Layer:** Contains business rules such as grade submission, approval, publishing, transcript generation, enrollment handling, and user registration.
- **Controller Layer:** Handles HTTP requests, coordinates services, prepares model data, and returns Thymeleaf views.
- **View Layer:** Uses Thymeleaf templates to render secured pages for Admin, Lecturer, Registrar, and Student users.

## Project Structure

```text
osrms-bfu/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── README.md
├── setup-git.sh
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── bfu/
│       │           └── osrms/
│       │               ├── controller/
│       │               ├── model/
│       │               ├── repository/
│       │               ├── security/
│       │               ├── service/
│       │               ├── DataSeeder.java
│       │               └── OsrmsApplication.java
│       └── resources/
│           ├── application.properties
│           └── templates/
│               ├── admin/
│               ├── lecturer/
│               ├── registrar/
│               ├── student/
│               ├── login.html
│               └── register.html
└── target/
```

## How to Run Locally

1. Make sure Java 17, Maven, and MySQL are installed.
2. Create a MySQL database named `osrms_db`.
3. Confirm the local database settings in `src/main/resources/application.properties`.
4. Run the application:

```bash
mvn spring-boot:run
```

5. Open the application:

```text
http://localhost:8080
```

## How to Run with Docker

Docker Compose starts both the MySQL database and the Spring Boot application.

```bash
docker-compose up --build
```

The application will be available at:

```text
http://localhost:8080
```

The MySQL container is exposed on the host at:

```text
localhost:3307
```

To stop the containers:

```bash
docker-compose down
```

## Default Login Credentials

Use these seeded accounts for testing after the database is initialized:

| Role | Email | Password |
| --- | --- | --- |
| Admin | `admin@bfu.ac.rw` | `admin123` |
| Lecturer | `lecturer1@bfu.ac.rw` | `pass123` |
| Lecturer | `lecturer2@bfu.ac.rw` | `pass123` |
| Student | `student1@bfu.ac.rw` | `pass123` |
| Registrar | `registrar@bfu.ac.rw` | `pass123` |

## Author

Student Name: Rugandura Maurice
Institution: Bright Future University (BFU)  
Program: Software Engineering
