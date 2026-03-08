# Event Management System - Full Stack Application

A complete Full Stack Event Management System built with Java Spring Boot, MySQL, HTML, CSS, JavaScript, and Bootstrap.

## 📋 Table of Contents
- [Features](#features)
- [Technology Stack](#technology-stack)
- [System Architecture](#system-architecture)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [API Documentation](#api-documentation)
- [Usage Guide](#usage-guide)
- [Validation Rules](#validation-rules)
- [Error Handling](#error-handling)
- [Optional Enhancements](#optional-enhancements)

## ✨ Features

### Core Features
- ✅ Complete CRUD operations for events
- ✅ Participant registration system
- ✅ Search events by name
- ✅ Filter events by status (Upcoming, Ongoing, Completed, Cancelled)
- ✅ Real-time statistics dashboard
- ✅ Responsive UI with Bootstrap
- ✅ Form validation (client & server side)
- ✅ RESTful API design
- ✅ Exception handling with proper HTTP status codes
- ✅ Visual indicators for event status

### User Interface Features
- 📊 Statistics cards showing event metrics
- 🔍 Real-time search functionality
- 🎨 Color-coded event status badges
- 📈 Progress bars for participant tracking
- 🎯 Visual highlighting for completed/cancelled events
- 📱 Fully responsive design
- 🔔 Toast notifications for user feedback

## 🛠 Technology Stack

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- **MySQL 8.0**
- **Maven** (Build tool)

### Frontend
- **HTML5**
- **CSS3**
- **JavaScript (ES6+)**
- **Bootstrap 5.3.0**
- **Font Awesome 6.4.0**

## 🏗 System Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    Frontend Layer                        │
│         (HTML, CSS, JavaScript, Bootstrap)               │
└────────────────────┬────────────────────────────────────┘
                     │ HTTP Requests (REST API)
                     ↓
┌─────────────────────────────────────────────────────────┐
│                  Controller Layer                        │
│              (@RestController)                           │
│         - EventController.java                           │
│         - Handles HTTP requests/responses                │
└────────────────────┬────────────────────────────────────┘
                     │ Business Logic Calls
                     ↓
┌─────────────────────────────────────────────────────────┐
│                   Service Layer                          │
│                  (@Service)                              │
│         - EventService.java (Interface)                  │
│         - EventServiceImpl.java                          │
│         - Business logic & validation                    │
└────────────────────┬────────────────────────────────────┘
                     │ Data Operations
                     ↓
┌─────────────────────────────────────────────────────────┐
│                 Repository Layer                         │
│              (JpaRepository)                             │
│         - EventRepository.java                           │
│         - Database operations                            │
└────────────────────┬────────────────────────────────────┘
                     │ SQL Queries
                     ↓
┌─────────────────────────────────────────────────────────┐
│                  Database Layer                          │
│                  MySQL Database                          │
│         - events table                                   │
└─────────────────────────────────────────────────────────┘
```

## 📁 Project Structure

```
EventManagementSystem/
│
├── backend/
│   └── src/
│       └── main/
│           ├── java/com/eventmanagement/
│           │   ├── EventManagementApplication.java
│           │   ├── controller/
│           │   │   └── EventController.java
│           │   ├── service/
│           │   │   ├── EventService.java
│           │   │   └── EventServiceImpl.java
│           │   ├── repository/
│           │   │   └── EventRepository.java
│           │   ├── entity/
│           │   │   └── Event.java
│           │   ├── dto/
│           │   │   ├── EventDTO.java
│           │   │   └── ApiResponse.java
│           │   ├── exception/
│           │   │   ├── ResourceNotFoundException.java
│           │   │   ├── InvalidInputException.java
│           │   │   └── GlobalExceptionHandler.java
│           │   └── config/
│           │       └── CorsConfig.java
│           └── resources/
│               └── application.properties
│
├── frontend/
│   ├── index.html
│   ├── css/
│   │   └── style.css
│   └── js/
│       └── app.js
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




