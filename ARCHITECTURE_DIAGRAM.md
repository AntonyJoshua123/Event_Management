# Event Management System - Architecture Diagrams

## 1. High-Level System Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                          CLIENT LAYER                                │
│                                                                       │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐              │
│  │   HTML5      │  │    CSS3      │  │ JavaScript   │              │
│  │  (Structure) │  │  (Styling)   │  │   (Logic)    │              │
│  └──────────────┘  └──────────────┘  └──────────────┘              │
│                                                                       │
│  ┌──────────────────────────────────────────────────────┐           │
│  │           Bootstrap 5 + Font Awesome                  │           │
│  └──────────────────────────────────────────────────────┘           │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
                                │ HTTP/REST API
                                │ (JSON)
                                ↓
┌─────────────────────────────────────────────────────────────────────┐
│                      APPLICATION LAYER                               │
│                      (Spring Boot 3.2.0)                             │
│                                                                       │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │                    CONTROLLER LAYER                          │   │
│  │  ┌────────────────────────────────────────────────────┐     │   │
│  │  │         EventController (@RestController)          │     │   │
│  │  │  - POST   /api/events                              │     │   │
│  │  │  - GET    /api/events                              │     │   │
│  │  │  - GET    /api/events/{id}                         │     │   │
│  │  │  - PUT    /api/events/{id}                         │     │   │
│  │  │  - DELETE /api/events/{id}                         │     │   │
│  │  │  - GET    /api/events/search?name=                 │     │   │
│  │  │  - GET    /api/events/status/{status}              │     │   │
│  │  │  - POST   /api/events/{id}/register                │     │   │
│  │  └────────────────────────────────────────────────────┘     │   │
│  └──────────────────────────┬──────────────────────────────────┘   │
│                              │                                       │
│  ┌──────────────────────────┼──────────────────────────────────┐   │
│  │                    SERVICE LAYER                             │   │
│  │  ┌───────────────────────▼──────────────────────────────┐   │   │
│  │  │         EventService (Interface)                      │   │   │
│  │  └───────────────────────┬──────────────────────────────┘   │   │
│  │  ┌───────────────────────▼──────────────────────────────┐   │   │
│  │  │         EventServiceImpl (@Service)                   │   │   │
│  │  │  - Business Logic                                     │   │   │
│  │  │  - Validation Rules                                   │   │   │
│  │  │  - Transaction Management                             │   │   │
│  │  │  - Entity ↔ DTO Conversion                           │   │   │
│  │  └───────────────────────┬──────────────────────────────┘   │   │
│  └──────────────────────────┼──────────────────────────────────┘   │
│                              │                                       │
│  ┌──────────────────────────┼──────────────────────────────────┐   │
│  │                  REPOSITORY LAYER                            │   │
│  │  ┌───────────────────────▼──────────────────────────────┐   │   │
│  │  │    EventRepository (JpaRepository)                    │   │   │
│  │  │  - CRUD Operations                                    │   │   │
│  │  │  - Custom Queries                                     │   │   │
│  │  │  - Search & Filter Methods                            │   │   │
│  │  └───────────────────────┬──────────────────────────────┘   │   │
│  └──────────────────────────┼──────────────────────────────────┘   │
│                              │                                       │
│  ┌──────────────────────────┼──────────────────────────────────┐   │
│  │                     ENTITY LAYER                             │   │
│  │  ┌───────────────────────▼──────────────────────────────┐   │   │
│  │  │              Event (@Entity)                          │   │   │
│  │  │  - JPA Annotations                                    │   │   │
│  │  │  - Validation Annotations                             │   │   │
│  │  │  - Lifecycle Callbacks                                │   │   │
│  │  └───────────────────────┬──────────────────────────────┘   │   │
│  └──────────────────────────┼──────────────────────────────────┘   │
│                              │                                       │
│  ┌──────────────────────────┼──────────────────────────────────┐   │
│  │                  CROSS-CUTTING CONCERNS                      │   │
│  │  ┌──────────────────┐  ┌──────────────────┐                │   │
│  │  │ Exception        │  │  CORS            │                │   │
│  │  │ Handling         │  │  Configuration   │                │   │
│  │  │ (@ControllerAdv) │  │  (@Config)       │                │   │
│  │  └──────────────────┘  └──────────────────┘                │   │
│  └─────────────────────────────────────────────────────────────┘   │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
                                │ JDBC
                                │ (SQL Queries)
                                ↓
┌─────────────────────────────────────────────────────────────────────┐
│                        DATABASE LAYER                                │
│                        MySQL 8.0                                     │
│                                                                       │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │                      events TABLE                            │   │
│  │  ┌────────────────────────────────────────────────────┐     │   │
│  │  │  event_id (PK, AUTO_INCREMENT)                     │     │   │
│  │  │  event_name (VARCHAR, NOT NULL)                    │     │   │
│  │  │  event_description (TEXT)                          │     │   │
│  │  │  event_date (DATE, NOT NULL)                       │     │   │
│  │  │  event_location (VARCHAR, NOT NULL)                │     │   │
│  │  │  max_participants (INT, NOT NULL)                  │     │   │
│  │  │  registered_count (INT, NOT NULL)                  │     │   │
│  │  │  event_status (VARCHAR, NOT NULL)                  │     │   │
│  │  │  created_datetime (TIMESTAMP)                      │     │   │
│  │  │  updated_datetime (TIMESTAMP)                      │     │   │
│  │  └────────────────────────────────────────────────────┘     │   │
│  │                                                               │   │
│  │  Indexes:                                                     │   │
│  │  - idx_event_name                                             │   │
│  │  - idx_event_status                                           │   │
│  │  - idx_event_date                                             │   │
│  └─────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────┘
```

## 2. Request Flow Diagram

```
┌──────────┐
│  User    │
│ Browser  │
└────┬─────┘
     │
     │ 1. User Action (Click "Add Event")
     ↓
┌─────────────────┐
│   index.html    │
│   (Frontend)    │
└────┬────────────┘
     │
     │ 2. JavaScript Event Handler
     ↓
┌─────────────────┐
│    app.js       │
│  saveEvent()    │
└────┬────────────┘
     │
     │ 3. HTTP POST Request
     │    URL: /api/events
     │    Body: JSON (EventDTO)
     ↓
┌─────────────────────────────────┐
│   EventController               │
│   @PostMapping("/api/events")   │
└────┬────────────────────────────┘
     │
     │ 4. Validate Input (@Valid)
     │ 5. Call Service Method
     ↓
┌─────────────────────────────────┐
│   EventServiceImpl              │
│   createEvent(EventDTO)         │
└────┬────────────────────────────┘
     │
     │ 6. Business Validation
     │ 7. Convert DTO to Entity
     │ 8. Call Repository
     ↓
┌─────────────────────────────────┐
│   EventRepository               │
│   save(Event)                   │
└────┬────────────────────────────┘
     │
     │ 9. Generate SQL INSERT
     │ 10. Execute Query
     ↓
┌─────────────────────────────────┐
│   MySQL Database                │
│   INSERT INTO events...         │
└────┬────────────────────────────┘
     │
     │ 11. Return Saved Entity
     ↓
┌─────────────────────────────────┐
│   EventServiceImpl              │
│   Convert Entity to DTO         │
└────┬────────────────────────────┘
     │
     │ 12. Return EventDTO
     ↓
┌─────────────────────────────────┐
│   EventController               │
│   Wrap in ApiResponse           │
└────┬────────────────────────────┘
     │
     │ 13. HTTP 201 Created
     │     JSON Response
     ↓
┌─────────────────┐
│    app.js       │
│  Handle Response│
└────┬────────────┘
     │
     │ 14. Update UI
     │     Show Notification
     ↓
┌─────────────────┐
│   index.html    │
│   Display Event │
└────┬────────────┘
     │
     │ 15. User Sees Result
     ↓
┌──────────┐
│  User    │
│ Browser  │
└──────────┘
```

## 3. Component Interaction Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                        FRONTEND                                  │
│                                                                  │
│  ┌──────────────┐      ┌──────────────┐      ┌──────────────┐ │
│  │   HTML       │◄────►│     CSS      │◄────►│  JavaScript  │ │
│  │  Structure   │      │   Styling    │      │    Logic     │ │
│  └──────────────┘      └──────────────┘      └──────┬───────┘ │
│                                                       │          │
│  ┌────────────────────────────────────────────────┐ │          │
│  │           Bootstrap Components                  │ │          │
│  │  - Modals  - Forms  - Tables  - Toasts        │ │          │
│  └────────────────────────────────────────────────┘ │          │
└────────────────────────────────────────────────────┼──────────┘
                                                      │
                                    Fetch API         │
                                    (async/await)     │
                                                      ↓
┌─────────────────────────────────────────────────────────────────┐
│                         BACKEND                                  │
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │                    REST Controller                        │  │
│  │  ┌────────────┐  ┌────────────┐  ┌────────────┐         │  │
│  │  │   @Post    │  │    @Get    │  │   @Put     │         │  │
│  │  │   @Delete  │  │  @PathVar  │  │ @RequestBody│        │  │
│  │  └────────────┘  └────────────┘  └────────────┘         │  │
│  └──────────────────────┬───────────────────────────────────┘  │
│                         │                                       │
│  ┌──────────────────────▼───────────────────────────────────┐  │
│  │                  Service Layer                            │  │
│  │  ┌──────────────────────────────────────────────────┐    │  │
│  │  │  Business Logic:                                  │    │  │
│  │  │  - Validate event date is future                 │    │  │
│  │  │  - Check participant capacity                     │    │  │
│  │  │  - Verify event status                            │    │  │
│  │  │  - Calculate statistics                           │    │  │
│  │  └──────────────────────────────────────────────────┘    │  │
│  └──────────────────────┬───────────────────────────────────┘  │
│                         │                                       │
│  ┌──────────────────────▼───────────────────────────────────┐  │
│  │                Repository Layer                           │  │
│  │  ┌──────────────────────────────────────────────────┐    │  │
│  │  │  JPA Methods:                                     │    │  │
│  │  │  - save()                                         │    │  │
│  │  │  - findById()                                     │    │  │
│  │  │  - findAll()                                      │    │  │
│  │  │  - delete()                                       │    │  │
│  │  │  - findByEventNameContaining()                    │    │  │
│  │  │  - findByEventStatus()                            │    │  │
│  │  └──────────────────────────────────────────────────┘    │  │
│  └──────────────────────┬───────────────────────────────────┘  │
│                         │                                       │
│  ┌──────────────────────▼───────────────────────────────────┐  │
│  │                   Entity Layer                            │  │
│  │  ┌──────────────────────────────────────────────────┐    │  │
│  │  │  Event Entity:                                    │    │  │
│  │  │  @Entity, @Table, @Id                             │    │  │
│  │  │  @NotNull, @NotBlank, @Min, @Future              │    │  │
│  │  │  @PrePersist, @PreUpdate                          │    │  │
│  │  └──────────────────────────────────────────────────┘    │  │
│  └──────────────────────┬───────────────────────────────────┘  │
│                         │                                       │
│  ┌──────────────────────▼───────────────────────────────────┐  │
│  │              Exception Handling                           │  │
│  │  ┌──────────────────────────────────────────────────┐    │  │
│  │  │  @RestControllerAdvice                            │    │  │
│  │  │  - ResourceNotFoundException → 404                │    │  │
│  │  │  - InvalidInputException → 400                    │    │  │
│  │  │  - ValidationException → 400                      │    │  │
│  │  │  - Exception → 500                                │    │  │
│  │  └──────────────────────────────────────────────────┘    │  │
│  └───────────────────────────────────────────────────────────┘  │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             │ Hibernate/JPA
                             │ SQL Queries
                             ↓
┌─────────────────────────────────────────────────────────────────┐
│                      MySQL Database                              │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │  events table with indexes and constraints                │  │
│  └───────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

## 4. Data Flow Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                      CREATE EVENT FLOW                           │
└─────────────────────────────────────────────────────────────────┘

User Input                EventDTO              Event Entity
    │                         │                      │
    │  Fill Form              │                      │
    ├────────────────────────►│                      │
    │                         │                      │
    │                         │  Validation          │
    │                         ├─────────────────────►│
    │                         │                      │
    │                         │  Convert DTO→Entity  │
    │                         ├─────────────────────►│
    │                         │                      │
    │                         │                      │  Save to DB
    │                         │                      ├──────────────►
    │                         │                      │
    │                         │                      │  Return Saved
    │                         │  Convert Entity→DTO  │◄──────────────
    │                         │◄─────────────────────┤
    │                         │                      │
    │  Display Success        │                      │
    │◄────────────────────────┤                      │
    │                         │                      │

┌─────────────────────────────────────────────────────────────────┐
│                    REGISTER PARTICIPANT FLOW                     │
└─────────────────────────────────────────────────────────────────┘

User Click              Service Logic          Database
    │                         │                      │
    │  Register Button        │                      │
    ├────────────────────────►│                      │
    │                         │                      │
    │                         │  Get Event           │
    │                         ├─────────────────────►│
    │                         │                      │
    │                         │  Event Data          │
    │                         │◄─────────────────────┤
    │                         │                      │
    │                         │  Check Capacity      │
    │                         │  Check Status        │
    │                         │                      │
    │                         │  Increment Count     │
    │                         ├─────────────────────►│
    │                         │                      │
    │                         │  Updated Event       │
    │                         │◄─────────────────────┤
    │                         │                      │
    │  Show Success           │                      │
    │◄────────────────────────┤                      │
    │                         │                      │

┌─────────────────────────────────────────────────────────────────┐
│                      SEARCH/FILTER FLOW                          │
└─────────────────────────────────────────────────────────────────┘

User Input              Repository Query       Database
    │                         │                      │
    │  Search Term            │                      │
    ├────────────────────────►│                      │
    │                         │                      │
    │                         │  SQL LIKE Query      │
    │                         ├─────────────────────►│
    │                         │                      │
    │                         │  Matching Events     │
    │                         │◄─────────────────────┤
    │                         │                      │
    │  Display Results        │                      │
    │◄────────────────────────┤                      │
    │                         │                      │
```

## 5. Technology Stack Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                      PRESENTATION TIER                           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐          │
│  │   HTML5      │  │  Bootstrap 5 │  │ Font Awesome │          │
│  │              │  │              │  │     6.4      │          │
│  └──────────────┘  └──────────────┘  └──────────────┘          │
│  ┌──────────────┐  ┌──────────────┐                            │
│  │    CSS3      │  │ JavaScript   │                            │
│  │              │  │    ES6+      │                            │
│  └──────────────┘  └──────────────┘                            │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ REST API (JSON)
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                      APPLICATION TIER                            │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │                   Spring Boot 3.2.0                       │   │
│  │  ┌────────────┐  ┌────────────┐  ┌────────────┐         │   │
│  │  │ Spring Web │  │ Spring JPA │  │ Validation │         │   │
│  │  └────────────┘  └────────────┘  └────────────┘         │   │
│  └──────────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │                      Java 17                              │   │
│  └──────────────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │                   Maven 3.6+                              │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ JDBC
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                        DATA TIER                                 │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │                    MySQL 8.0                              │   │
│  │  ┌────────────┐  ┌────────────┐  ┌────────────┐         │   │
│  │  │  Tables    │  │  Indexes   │  │ Constraints│         │   │
│  │  └────────────┘  └────────────┘  └────────────┘         │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

## 6. Package Structure Diagram

```
com.eventmanagement
│
├── EventManagementApplication.java (Main Class)
│
├── controller/
│   └── EventController.java
│       ├── @RestController
│       ├── @RequestMapping("/api/events")
│       └── @CrossOrigin
│
├── service/
│   ├── EventService.java (Interface)
│   └── EventServiceImpl.java
│       ├── @Service
│       └── @Transactional
│
├── repository/
│   └── EventRepository.java
│       ├── extends JpaRepository<Event, Long>
│       └── @Repository
│
├── entity/
│   └── Event.java
│       ├── @Entity
│       ├── @Table(name = "events")
│       └── Validation Annotations
│
├── dto/
│   ├── EventDTO.java
│   │   └── Validation Annotations
│   └── ApiResponse.java
│       └── Generic Response Wrapper
│
├── exception/
│   ├── ResourceNotFoundException.java
│   ├── InvalidInputException.java
│   └── GlobalExceptionHandler.java
│       └── @RestControllerAdvice
│
└── config/
    └── CorsConfig.java
        └── @Configuration
```

## 7. Database Schema Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        events TABLE                          │
├─────────────────────────────────────────────────────────────┤
│  PK  │ event_id              │ BIGINT AUTO_INCREMENT        │
├──────┼───────────────────────┼──────────────────────────────┤
│      │ event_name            │ VARCHAR(255) NOT NULL        │
│      │ event_description     │ TEXT                         │
│      │ event_date            │ DATE NOT NULL                │
│      │ event_location        │ VARCHAR(255) NOT NULL        │
│      │ max_participants      │ INT NOT NULL DEFAULT 0       │
│      │ registered_count      │ INT NOT NULL DEFAULT 0       │
│      │ event_status          │ VARCHAR(50) NOT NULL         │
│      │ created_datetime      │ TIMESTAMP DEFAULT NOW()      │
│      │ updated_datetime      │ TIMESTAMP ON UPDATE NOW()    │
└──────┴───────────────────────┴──────────────────────────────┘

Indexes:
  - idx_event_name (event_name)
  - idx_event_status (event_status)
  - idx_event_date (event_date)

Constraints:
  - CHECK (registered_count <= max_participants)
  - CHECK (event_status IN ('UPCOMING','ONGOING','COMPLETED','CANCELLED'))
```

---

These diagrams provide a comprehensive visual understanding of the Event Management System architecture, data flow, and component interactions.
