-- Event Management System Database Schema

-- Create Database
CREATE DATABASE IF NOT EXISTS event_management_db;
USE event_management_db;

-- Drop table if exists
DROP TABLE IF EXISTS events;

-- Create Events Table
CREATE TABLE events (
    event_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(255) NOT NULL,
    event_description TEXT,
    event_date DATE NOT NULL,
    event_location VARCHAR(255) NOT NULL,
    max_participants INT NOT NULL DEFAULT 0,
    registered_count INT NOT NULL DEFAULT 0,
    event_status VARCHAR(50) NOT NULL DEFAULT 'UPCOMING',
    created_datetime DATETIME NULL,
    updated_datetime DATETIME NULL
);

-- Create Index for faster searches
CREATE INDEX idx_event_name ON events(event_name);
CREATE INDEX idx_event_status ON events(event_status);
CREATE INDEX idx_event_date ON events(event_date);

-- Insert Sample Data
INSERT INTO events (event_name, event_description, event_date, event_location, max_participants, registered_count, event_status, created_datetime, updated_datetime) 
VALUES 
('Tech Conference 2026', 'Annual technology conference featuring latest innovations', '2026-04-15', 'Convention Center, New York', 500, 245, 'UPCOMING', NOW(), NOW()),
('Spring Boot Workshop', 'Hands-on workshop on Spring Boot development', '2026-03-20', 'Tech Hub, San Francisco', 50, 50, 'UPCOMING', NOW(), NOW()),
('AI Summit', 'Artificial Intelligence and Machine Learning summit', '2026-05-10', 'Innovation Center, Boston', 300, 150, 'UPCOMING', NOW(), NOW()),
('Web Development Bootcamp', 'Intensive 3-day web development training', '2026-02-28', 'Online', 100, 75, 'COMPLETED', NOW(), NOW());
