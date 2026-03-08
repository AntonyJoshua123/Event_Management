# Quick Start Guide - Event Management System

Get your Event Management System up and running in 5 minutes!

## 🚀 Prerequisites Check

Before starting, ensure you have:
- [ ] Java 17 or higher installed
- [ ] MySQL 8.0 or higher installed and running
- [ ] Maven installed (or use IDE with Maven support)
- [ ] A web browser

## ⚡ Quick Setup (5 Steps)

### Step 1: Setup Database (2 minutes)

Open MySQL command line or MySQL Workbench and run:

```sql
CREATE DATABASE event_management_db;
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

### Step 2: Configure Database Connection (30 seconds)

Edit `backend/src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

Replace `YOUR_MYSQL_PASSWORD` with your actual MySQL password.

### Step 3: Run Backend (1 minute)

Open terminal in project root directory:

```bash
# Build and run
mvn spring-boot:run
```

Wait for the message:
```
Event Management System Started!
Access at: http://localhost:8080
```

### Step 4: Open Frontend (30 seconds)

Simply open `frontend/index.html` in your web browser.

Or use a local server:
```bash
# Using Python
cd frontend
python -m http.server 8000

# Then open: http://localhost:8000
```

### Step 5: Test the Application (1 minute)

1. Click "Add New Event" button
2. Fill in the form:
   - Event Name: "Test Event"
   - Date: Select any future date
   - Location: "Test Location"
   - Max Participants: 100
3. Click "Save Event"
4. See your event appear in the table!

## ✅ Verification

Your application is working if you can:
- [ ] See the statistics cards at the top
- [ ] Add a new event
- [ ] View the event in the table
- [ ] Edit the event
- [ ] Register a participant
- [ ] Search for events
- [ ] Delete the event

## 🎯 Quick Test Scenarios

### Test 1: Create Event
1. Click "Add New Event"
2. Enter event details
3. Click "Save Event"
4. ✅ Event appears in table

### Test 2: Register Participant
1. Click the user-plus icon on any event
2. Confirm registration
3. ✅ Participant count increases

### Test 3: Search
1. Type event name in search box
2. Press Enter
3. ✅ Filtered results appear

### Test 4: Filter by Status
1. Click "Upcoming" button
2. ✅ Only upcoming events shown

## 🐛 Common Issues & Quick Fixes

### Issue: Backend won't start

**Error**: "Connection refused" or "Cannot connect to database"

**Fix**:
```bash
# Check if MySQL is running
# Windows:
net start MySQL80

# Mac/Linux:
sudo systemctl start mysql
```

### Issue: Port 8080 already in use

**Fix**: Change port in `application.properties`:
```properties
server.port=8081
```

Then update `frontend/js/app.js`:
```javascript
const API_BASE_URL = 'http://localhost:8081/api/events';
```

### Issue: Frontend can't connect to backend

**Fix**: 
1. Verify backend is running (check console)
2. Check browser console for errors (F12)
3. Ensure CORS is configured (already done in code)

### Issue: "Access denied for user"

**Fix**: Update MySQL credentials in `application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## 📱 Quick Feature Tour

### Dashboard
- View total events, upcoming events, completed events
- See total participants across all events

### Add Event
- Click "Add New Event"
- Fill required fields (marked with *)
- Date must be in the future
- Max participants must be at least 1

### View Event
- Click eye icon (👁️) to view full details
- See all event information
- View creation and update timestamps

### Edit Event
- Click edit icon (✏️)
- Modify any field
- Change event status
- Save changes

### Register Participant
- Click user-plus icon (👤+)
- Confirm registration
- Cannot register if event is full
- Cannot register for cancelled events

### Search
- Type in search box
- Press Enter or click Search
- Case-insensitive search
- Searches event names

### Filter
- Click status buttons (All, Upcoming, Ongoing, Completed, Cancelled)
- View events by status
- Color-coded badges

### Delete Event
- Click trash icon (🗑️)
- Confirm deletion
- Event removed permanently

## 🎨 Visual Indicators

- **Blue background**: Completed events
- **Red background**: Cancelled events
- **Yellow background**: Full events (no seats available)
- **Green progress bar**: Less than 50% full
- **Yellow progress bar**: 50-80% full
- **Red progress bar**: More than 80% full

## 📊 Sample Data

Want to test with sample data? Run this SQL:

```sql
INSERT INTO events (event_name, event_description, event_date, event_location, max_participants, registered_count, event_status) 
VALUES 
('Tech Conference 2026', 'Annual technology conference', '2026-04-15', 'New York', 500, 245, 'UPCOMING'),
('Spring Boot Workshop', 'Hands-on Spring Boot', '2026-03-20', 'San Francisco', 50, 30, 'UPCOMING'),
('AI Summit', 'AI and ML summit', '2026-05-10', 'Boston', 300, 150, 'UPCOMING');
```

Refresh the page to see the sample events!

## 🔧 IDE Setup (Optional)

### IntelliJ IDEA
1. Open project folder
2. Wait for Maven import
3. Right-click `EventManagementApplication.java`
4. Click "Run"

### Eclipse
1. File → Import → Maven → Existing Maven Projects
2. Select project folder
3. Right-click project → Run As → Spring Boot App

### VS Code
1. Open project folder
2. Install "Spring Boot Extension Pack"
3. Press F5 to run

## 📚 Next Steps

Once everything is working:

1. **Explore the API**: Check `API_TESTING_GUIDE.md`
2. **Read Documentation**: See `README.md` for detailed info
3. **Try Console Version**: Run `EventManagementConsole.java`
4. **Customize**: Modify colors, add features, extend functionality
5. **Deploy**: Prepare for production deployment

## 🆘 Need Help?

1. Check `README.md` for detailed documentation
2. Review `IMPLEMENTATION_SUMMARY.md` for architecture details
3. See `API_TESTING_GUIDE.md` for API testing
4. Check browser console (F12) for frontend errors
5. Check Spring Boot console for backend errors

## 🎉 Success!

If you can see events in your browser and perform CRUD operations, congratulations! Your Event Management System is fully operational.

**Happy Event Managing!** 🚀

---

## Quick Command Reference

```bash
# Start MySQL (Windows)
net start MySQL80

# Start MySQL (Mac/Linux)
sudo systemctl start mysql

# Run Spring Boot
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Build JAR
mvn clean package

# Run JAR
java -jar target/event-management-system-1.0.0.jar

# Run frontend server (Python)
python -m http.server 8000

# Run frontend server (Node.js)
npx http-server frontend
```

## Quick MySQL Commands

```sql
-- Show databases
SHOW DATABASES;

-- Use database
USE event_management_db;

-- Show tables
SHOW TABLES;

-- View all events
SELECT * FROM events;

-- Count events
SELECT COUNT(*) FROM events;

-- Delete all events
DELETE FROM events;

-- Drop database (careful!)
DROP DATABASE event_management_db;
```

---

**Time to complete setup: ~5 minutes** ⏱️
