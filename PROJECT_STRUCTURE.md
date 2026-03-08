# Event Management System - Project Structure

```
EventManagementSystem/
│
├── backend/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/
│           │       └── eventmanagement/
│           │           ├── EventManagementApplication.java
│           │           ├── controller/
│           │           │   └── EventController.java
│           │           ├── service/
│           │           │   ├── EventService.java
│           │           │   └── EventServiceImpl.java
│           │           ├── repository/
│           │           │   └── EventRepository.java
│           │           ├── entity/
│           │           │   └── Event.java
│           │           ├── dto/
│           │           │   ├── EventDTO.java
│           │           │   └── ApiResponse.java
│           │           ├── exception/
│           │           │   ├── ResourceNotFoundException.java
│           │           │   ├── InvalidInputException.java
│           │           │   └── GlobalExceptionHandler.java
│           │           └── config/
│           │               └── CorsConfig.java
│           └── resources/
│               ├── application.properties
│               └── schema.sql
│
├── frontend/
│   ├── index.html
│   ├── css/
│   │   └── style.css
│   ├── js/
│   │   └── app.js
│   └── assets/
│       └── images/
│
├── console-version/
│   └── EventManagementConsole.java
│
├── database/
│   └── schema.sql
│
├── pom.xml
└── README.md
```
