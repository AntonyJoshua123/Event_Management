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

## 📋 Prerequisites

Before running this application, ensure you have:

1. **Java Development Kit (JDK) 17 or higher**
   - Download from: https://www.oracle.com/java/technologies/downloads/

2. **MySQL Server 8.0 or higher**
   - Download from: https://dev.mysql.com/downloads/mysql/

3. **Maven 3.6 or higher**
   - Download from: https://maven.apache.org/download.cgi

4. **IDE (Optional but recommended)**
   - IntelliJ IDEA, Eclipse, or VS Code

5. **Web Browser**
   - Chrome, Firefox, Edge, or Safari

## 🚀 Installation & Setup

### Step 1: Install MySQL

1. Install MySQL Server
2. Start MySQL service
3. Login to MySQL:
   ```bash
   mysql -u root -p
   ```

### Step 2: Create Database

Run the SQL script to create database and tables:

```bash
mysql -u root -p < database/schema.sql
```

Or manually execute:

```sql
CREATE DATABASE IF NOT EXISTS event_management_db;
USE event_management_db;

CREATE TABLE events (
    event_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(255) NOT NULL,
    event_description TEXT,
    event_date DATE NOT NULL,
    event_location VARCHAR(255) NOT NULL,
    max_participants INT NOT NULL DEFAULT 0,
    registered_count INT NOT NULL DEFAULT 0,
    event_status VARCHAR(50) NOT NULL DEFAULT 'UPCOMING',
    created_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Step 3: Configure Application Properties

Edit `backend/src/main/resources/application.properties`:

```properties
# Update these values according to your MySQL configuration
spring.datasource.url=jdbc:mysql://localhost:3306/event_management_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### Step 4: Build and Run Spring Boot Application

Navigate to project root directory:

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

Or run from IDE:
- Open project in your IDE
- Run `EventManagementApplication.java`

The backend will start on: **http://localhost:8080**

### Step 5: Open Frontend

1. Navigate to `frontend` directory
2. Open `index.html` in your web browser
3. Or use a local server:
   ```bash
   # Using Python
   python -m http.server 8000
   
   # Using Node.js
   npx http-server
   ```

Access the application at: **http://localhost:8000** (or directly open index.html)

## 📡 API Documentation

### Base URL
```
http://localhost:8080/api/events
```

### Endpoints

#### 1. Create Event
```http
POST /api/events
Content-Type: application/json

{
  "eventName": "Tech Conference 2026",
  "eventDescription": "Annual technology conference",
  "eventDate": "2026-04-15",
  "eventLocation": "Convention Center, New York",
  "maxParticipants": 500
}

Response: 201 Created
```

#### 2. Get All Events
```http
GET /api/events

Response: 200 OK
```

#### 3. Get Event by ID
```http
GET /api/events/{id}

Response: 200 OK
```

#### 4. Update Event
```http
PUT /api/events/{id}
Content-Type: application/json

{
  "eventName": "Updated Event Name",
  "eventStatus": "ONGOING"
}

Response: 200 OK
```

#### 5. Delete Event
```http
DELETE /api/events/{id}

Response: 200 OK
```

#### 6. Search Events
```http
GET /api/events/search?name=conference

Response: 200 OK
```

#### 7. Filter by Status
```http
GET /api/events/status/UPCOMING

Response: 200 OK
```

#### 8. Register Participant
```http
POST /api/events/{id}/register

Response: 200 OK
```

#### 9. Get Available Events
```http
GET /api/events/available

Response: 200 OK
```

#### 10. Get Statistics
```http
GET /api/events/statistics

Response: 200 OK
```

### Response Format

All API responses follow this structure:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... },
  "timestamp": "2026-03-08T10:30:00"
}
```

### HTTP Status Codes

- `200 OK` - Successful operation
- `201 Created` - Resource created successfully
- `400 Bad Request` - Invalid input or validation error
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server error

## 📖 Usage Guide

### Adding a New Event

1. Click "Add New Event" button
2. Fill in the form:
   - Event Name (required, min 3 characters)
   - Description (optional)
   - Event Date (required, must be future date)
   - Location (required)
   - Max Participants (required, min 1)
3. Click "Save Event"

### Viewing Event Details

- Click the eye icon (👁️) in the Actions column
- View complete event information in modal

### Editing an Event

- Click the edit icon (✏️) in the Actions column
- Modify event details
- Click "Save Event"

### Registering for an Event

- Click the user-plus icon (👤+) in the Actions column
- Confirm registration
- Registration count will increment

### Searching Events

- Type event name in search box
- Press Enter or click Search button
- Results will be filtered

### Filtering by Status

- Click status filter buttons:
  - All - Show all events
  - Upcoming - Future events
  - Ongoing - Currently happening
  - Completed - Past events
  - Cancelled - Cancelled events

### Deleting an Event

- Click the trash icon (🗑️) in the Actions column
- Confirm deletion

## ✅ Validation Rules

### Client-Side Validation

- Event name: Required, minimum 3 characters
- Event date: Required, must be future date
- Location: Required
- Max participants: Required, must be positive number

### Server-Side Validation

- Event name cannot be empty
- Event date must be valid and in future
- Max participants must be at least 1
- Registered count cannot exceed max participants
- Cannot register for full events
- Cannot register for cancelled events

## 🚨 Error Handling

### Frontend Error Handling

- Network errors display toast notifications
- Form validation errors show inline messages
- API errors display user-friendly messages

### Backend Error Handling

- `ResourceNotFoundException` - 404 Not Found
- `InvalidInputException` - 400 Bad Request
- `MethodArgumentNotValidException` - 400 Bad Request (Validation)
- `Exception` - 500 Internal Server Error

All errors return consistent JSON response:

```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": "2026-03-08T10:30:00"
}
```

## 🎯 Optional Enhancements

### 1. User Authentication & Authorization
- Implement Spring Security
- Add login/registration
- Role-based access control (Admin/User)

### 2. Pagination
- Add pagination to event list
- Implement page size selection
- Add sorting options

### 3. Dashboard Statistics
- Add charts (Chart.js)
- Event trends over time
- Participant analytics

### 4. Email Notifications
- Send confirmation emails on registration
- Event reminders
- Status change notifications

### 5. Event Image Upload
- Add image upload functionality
- Store images in file system or cloud
- Display event images in UI

### 6. Advanced Search
- Multi-criteria search
- Date range filtering
- Location-based search

### 7. Export Functionality
- Export events to PDF
- Export to Excel
- Generate reports

### 8. Calendar View
- Display events in calendar format
- Month/Week/Day views
- Drag-and-drop event scheduling

## 🧪 Testing the Console Version

To test the Java console version:

```bash
# Compile
javac console-version/EventManagementConsole.java

# Run
java -cp console-version EventManagementConsole
```

The console application demonstrates:
- Classes and Objects
- ArrayList and HashMap
- CRUD operations
- Search and filter functionality
- Input validation

## 🐛 Troubleshooting

### Backend Issues

**Problem**: Application won't start
- Check MySQL is running
- Verify database credentials in application.properties
- Ensure port 8080 is not in use

**Problem**: Database connection error
- Verify MySQL service is running
- Check database name and credentials
- Ensure database exists

### Frontend Issues

**Problem**: Cannot connect to API
- Verify backend is running on port 8080
- Check browser console for CORS errors
- Ensure API_BASE_URL in app.js is correct

**Problem**: Events not loading
- Check network tab in browser developer tools
- Verify API responses
- Check console for JavaScript errors

## 📝 License

This project is created for educational purposes.

## 👥 Contributors

- Event Management Team

## 📞 Support

For issues and questions:
- Check troubleshooting section
- Review API documentation
- Check browser console for errors
- Verify MySQL and Spring Boot logs

---

**Happy Event Managing! 🎉**
