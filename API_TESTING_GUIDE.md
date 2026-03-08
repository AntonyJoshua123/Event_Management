# API Testing Guide - Event Management System

## Testing Tools

You can test the APIs using:
- **Postman** (Recommended)
- **cURL** (Command line)
- **Browser** (for GET requests)
- **Thunder Client** (VS Code extension)

## Base URL
```
http://localhost:8080/api/events
```

## Test Scenarios

### 1. Create Event (POST)

**Endpoint**: `POST /api/events`

**Request Body**:
```json
{
  "eventName": "Spring Boot Workshop 2026",
  "eventDescription": "Learn Spring Boot from scratch",
  "eventDate": "2026-06-15",
  "eventLocation": "Tech Hub, San Francisco",
  "maxParticipants": 100
}
```

**Expected Response** (201 Created):
```json
{
  "success": true,
  "message": "Event created successfully",
  "data": {
    "eventId": 1,
    "eventName": "Spring Boot Workshop 2026",
    "eventDescription": "Learn Spring Boot from scratch",
    "eventDate": "2026-06-15",
    "eventLocation": "Tech Hub, San Francisco",
    "maxParticipants": 100,
    "registeredCount": 0,
    "eventStatus": "UPCOMING",
    "createdDatetime": "2026-03-08T10:30:00",
    "updatedDatetime": "2026-03-08T10:30:00"
  },
  "timestamp": "2026-03-08T10:30:00"
}
```

**cURL Command**:
```bash
curl -X POST http://localhost:8080/api/events \
  -H "Content-Type: application/json" \
  -d "{\"eventName\":\"Spring Boot Workshop 2026\",\"eventDescription\":\"Learn Spring Boot from scratch\",\"eventDate\":\"2026-06-15\",\"eventLocation\":\"Tech Hub, San Francisco\",\"maxParticipants\":100}"
```

---

### 2. Get All Events (GET)

**Endpoint**: `GET /api/events`

**Expected Response** (200 OK):
```json
{
  "success": true,
  "message": "Events retrieved successfully",
  "data": [
    {
      "eventId": 1,
      "eventName": "Spring Boot Workshop 2026",
      ...
    }
  ],
  "timestamp": "2026-03-08T10:30:00"
}
```

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/events
```

---

### 3. Get Event by ID (GET)

**Endpoint**: `GET /api/events/{id}`

**Example**: `GET /api/events/1`

**Expected Response** (200 OK):
```json
{
  "success": true,
  "message": "Event found",
  "data": {
    "eventId": 1,
    "eventName": "Spring Boot Workshop 2026",
    ...
  },
  "timestamp": "2026-03-08T10:30:00"
}
```

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/events/1
```

**Error Case** (404 Not Found):
```json
{
  "success": false,
  "message": "Event not found with id: '999'",
  "data": null,
  "timestamp": "2026-03-08T10:30:00"
}
```

---

### 4. Update Event (PUT)

**Endpoint**: `PUT /api/events/{id}`

**Request Body**:
```json
{
  "eventName": "Advanced Spring Boot Workshop",
  "eventDescription": "Deep dive into Spring Boot",
  "eventLocation": "Innovation Center, Boston",
  "maxParticipants": 150,
  "eventStatus": "ONGOING"
}
```

**Expected Response** (200 OK):
```json
{
  "success": true,
  "message": "Event updated successfully",
  "data": {
    "eventId": 1,
    "eventName": "Advanced Spring Boot Workshop",
    ...
  },
  "timestamp": "2026-03-08T10:30:00"
}
```

**cURL Command**:
```bash
curl -X PUT http://localhost:8080/api/events/1 \
  -H "Content-Type: application/json" \
  -d "{\"eventName\":\"Advanced Spring Boot Workshop\",\"eventStatus\":\"ONGOING\"}"
```

---

### 5. Delete Event (DELETE)

**Endpoint**: `DELETE /api/events/{id}`

**Expected Response** (200 OK):
```json
{
  "success": true,
  "message": "Event deleted successfully",
  "data": null,
  "timestamp": "2026-03-08T10:30:00"
}
```

**cURL Command**:
```bash
curl -X DELETE http://localhost:8080/api/events/1
```

---

### 6. Search Events by Name (GET)

**Endpoint**: `GET /api/events/search?name={searchTerm}`

**Example**: `GET /api/events/search?name=workshop`

**Expected Response** (200 OK):
```json
{
  "success": true,
  "message": "Search completed",
  "data": [
    {
      "eventId": 1,
      "eventName": "Spring Boot Workshop 2026",
      ...
    }
  ],
  "timestamp": "2026-03-08T10:30:00"
}
```

**cURL Command**:
```bash
curl -X GET "http://localhost:8080/api/events/search?name=workshop"
```

---

### 7. Filter Events by Status (GET)

**Endpoint**: `GET /api/events/status/{status}`

**Valid Status Values**:
- UPCOMING
- ONGOING
- COMPLETED
- CANCELLED

**Example**: `GET /api/events/status/UPCOMING`

**Expected Response** (200 OK):
```json
{
  "success": true,
  "message": "Events filtered by status: UPCOMING",
  "data": [
    {
      "eventId": 1,
      "eventStatus": "UPCOMING",
      ...
    }
  ],
  "timestamp": "2026-03-08T10:30:00"
}
```

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/events/status/UPCOMING
```

---

### 8. Register Participant (POST)

**Endpoint**: `POST /api/events/{id}/register`

**Expected Response** (200 OK):
```json
{
  "success": true,
  "message": "Participant registered successfully",
  "data": {
    "eventId": 1,
    "registeredCount": 1,
    "maxParticipants": 100,
    ...
  },
  "timestamp": "2026-03-08T10:30:00"
}
```

**cURL Command**:
```bash
curl -X POST http://localhost:8080/api/events/1/register
```

**Error Case - Event Full** (400 Bad Request):
```json
{
  "success": false,
  "message": "Event is full. Cannot register more participants. Current: 100/100",
  "data": null,
  "timestamp": "2026-03-08T10:30:00"
}
```

---

### 9. Get Available Events (GET)

**Endpoint**: `GET /api/events/available`

**Expected Response** (200 OK):
```json
{
  "success": true,
  "message": "Available events retrieved",
  "data": [
    {
      "eventId": 1,
      "registeredCount": 50,
      "maxParticipants": 100,
      ...
    }
  ],
  "timestamp": "2026-03-08T10:30:00"
}
```

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/events/available
```

---

### 10. Get Statistics (GET)

**Endpoint**: `GET /api/events/statistics`

**Expected Response** (200 OK):
```json
{
  "success": true,
  "message": "Statistics retrieved",
  "data": {
    "totalEvents": 10,
    "upcomingEvents": 5,
    "completedEvents": 3,
    "cancelledEvents": 2,
    "totalParticipants": 450
  },
  "timestamp": "2026-03-08T10:30:00"
}
```

**cURL Command**:
```bash
curl -X GET http://localhost:8080/api/events/statistics
```

---

## Validation Test Cases

### Test Case 1: Empty Event Name

**Request**:
```json
{
  "eventName": "",
  "eventDate": "2026-06-15",
  "eventLocation": "Test Location",
  "maxParticipants": 100
}
```

**Expected Response** (400 Bad Request):
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "eventName": "Event name is required"
  },
  "timestamp": "2026-03-08T10:30:00"
}
```

---

### Test Case 2: Past Event Date

**Request**:
```json
{
  "eventName": "Past Event",
  "eventDate": "2020-01-01",
  "eventLocation": "Test Location",
  "maxParticipants": 100
}
```

**Expected Response** (400 Bad Request):
```json
{
  "success": false,
  "message": "Event date must be in the future",
  "data": null,
  "timestamp": "2026-03-08T10:30:00"
}
```

---

### Test Case 3: Invalid Max Participants

**Request**:
```json
{
  "eventName": "Test Event",
  "eventDate": "2026-06-15",
  "eventLocation": "Test Location",
  "maxParticipants": 0
}
```

**Expected Response** (400 Bad Request):
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "maxParticipants": "Max participants must be at least 1"
  },
  "timestamp": "2026-03-08T10:30:00"
}
```

---

## Postman Collection

### Import into Postman

1. Create a new collection named "Event Management API"
2. Add the following requests:

#### Request 1: Create Event
- Method: POST
- URL: `{{baseUrl}}/api/events`
- Body: Raw JSON (see above)

#### Request 2: Get All Events
- Method: GET
- URL: `{{baseUrl}}/api/events`

#### Request 3: Get Event by ID
- Method: GET
- URL: `{{baseUrl}}/api/events/1`

#### Request 4: Update Event
- Method: PUT
- URL: `{{baseUrl}}/api/events/1`
- Body: Raw JSON (see above)

#### Request 5: Delete Event
- Method: DELETE
- URL: `{{baseUrl}}/api/events/1`

#### Request 6: Search Events
- Method: GET
- URL: `{{baseUrl}}/api/events/search?name=workshop`

#### Request 7: Filter by Status
- Method: GET
- URL: `{{baseUrl}}/api/events/status/UPCOMING`

#### Request 8: Register Participant
- Method: POST
- URL: `{{baseUrl}}/api/events/1/register`

#### Request 9: Get Available Events
- Method: GET
- URL: `{{baseUrl}}/api/events/available`

#### Request 10: Get Statistics
- Method: GET
- URL: `{{baseUrl}}/api/events/statistics`

### Environment Variables

Create an environment with:
- Variable: `baseUrl`
- Value: `http://localhost:8080`

---

## Testing Workflow

### Complete Test Sequence

1. **Create 3 events** (POST /api/events)
2. **Get all events** (GET /api/events)
3. **Get event by ID** (GET /api/events/1)
4. **Search for event** (GET /api/events/search?name=workshop)
5. **Register 5 participants** (POST /api/events/1/register) - Call 5 times
6. **Get statistics** (GET /api/events/statistics)
7. **Update event status** (PUT /api/events/1)
8. **Filter by status** (GET /api/events/status/UPCOMING)
9. **Get available events** (GET /api/events/available)
10. **Delete event** (DELETE /api/events/1)

---

## Expected Behavior

### Successful Operations
- All successful operations return appropriate HTTP status codes
- Response includes success flag, message, and data
- Timestamps are included in all responses

### Error Handling
- Invalid input returns 400 Bad Request
- Resource not found returns 404 Not Found
- Server errors return 500 Internal Server Error
- Error messages are descriptive and user-friendly

### Business Rules
- Cannot register for full events
- Cannot register for cancelled events
- Event date must be in the future
- Max participants cannot be less than registered count

---

## Troubleshooting

### Common Issues

**Issue**: Connection refused
- **Solution**: Ensure Spring Boot application is running

**Issue**: 404 Not Found on all endpoints
- **Solution**: Check base URL and port number

**Issue**: CORS errors
- **Solution**: Verify CorsConfig is properly configured

**Issue**: Validation errors
- **Solution**: Check request body matches required format

---

## Performance Testing

### Load Testing Recommendations

1. Test with 100 concurrent users
2. Test creating 1000 events
3. Test searching with large dataset
4. Monitor response times
5. Check database performance

### Expected Response Times

- GET requests: < 100ms
- POST requests: < 200ms
- PUT requests: < 200ms
- DELETE requests: < 100ms
- Search operations: < 150ms

---

**Happy Testing! 🧪**
