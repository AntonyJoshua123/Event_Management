// Event Management System - JavaScript Application

// API Base URL
const API_BASE_URL = 'http://localhost:8080/api/events';

// Global variables
let allEvents = [];
let currentFilter = 'ALL';
let isEditMode = false;

// Initialize application when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    loadEvents();
    loadStatistics();
    setMinDate();
    
    // Add enter key listener for search
    document.getElementById('searchInput').addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            searchEvents();
        }
    });
});

/**
 * Set minimum date for event date input to today
 */
function setMinDate() {
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('eventDate').setAttribute('min', today);
}

/**
 * Load all events from API
 */
async function loadEvents() {
    try {
        showLoading();
        const response = await fetch(API_BASE_URL);
        const result = await response.json();
        
        if (result.success) {
            allEvents = result.data;
            displayEvents(allEvents);
            loadStatistics();
        } else {
            showNotification('Error loading events', 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showNotification('Failed to connect to server', 'error');
    } finally {
        hideLoading();
    }
}

/**
 * Display events in table
 */
function displayEvents(events) {
    const tbody = document.getElementById('eventsTableBody');
    const noEventsMsg = document.getElementById('noEventsMessage');
    
    if (!events || events.length === 0) {
        tbody.innerHTML = '';
        noEventsMsg.style.display = 'block';
        return;
    }
    
    noEventsMsg.style.display = 'none';
    
    tbody.innerHTML = events.map(event => {
        const participantPercentage = (event.registeredCount / event.maxParticipants) * 100;
        const isFull = event.registeredCount >= event.maxParticipants;
        const rowClass = getRowClass(event);
        
        return `
            <tr class="${rowClass} fade-in">
                <td>${event.eventId}</td>
                <td>
                    <strong>${event.eventName}</strong>
                    ${event.eventDescription ? `<br><small class="text-muted">${truncateText(event.eventDescription, 50)}</small>` : ''}
                </td>
                <td>${formatDate(event.eventDate)}</td>
                <td><i class="fas fa-map-marker-alt"></i> ${event.eventLocation}</td>
                <td>
                    <span class="participant-count ${isFull ? 'full' : 'available'}">
                        ${event.registeredCount}/${event.maxParticipants}
                    </span>
                    <div class="participant-progress">
                        <div class="participant-progress-bar ${getProgressClass(participantPercentage)}" 
                             style="width: ${participantPercentage}%"></div>
                    </div>
                </td>
                <td>
                    <span class="badge status-${event.eventStatus.toLowerCase()}">
                        ${event.eventStatus}
                    </span>
                </td>
                <td>
                    <button class="btn btn-sm btn-info action-btn" onclick="viewEvent(${event.eventId})" title="View Details">
                        <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn btn-sm btn-primary action-btn" onclick="editEvent(${event.eventId})" title="Edit">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-sm btn-success action-btn" onclick="registerForEvent(${event.eventId})" 
                            ${isFull || event.eventStatus === 'CANCELLED' ? 'disabled' : ''} title="Register">
                        <i class="fas fa-user-plus"></i>
                    </button>
                    <button class="btn btn-sm btn-danger action-btn" onclick="deleteEvent(${event.eventId})" title="Delete">
                        <i class="fas fa-trash"></i>
                    </button>
                </td>
            </tr>
        `;
    }).join('');
}

/**
 * Get row class based on event status
 */
function getRowClass(event) {
    if (event.eventStatus === 'COMPLETED') return 'event-completed';
    if (event.eventStatus === 'CANCELLED') return 'event-cancelled';
    if (event.registeredCount >= event.maxParticipants) return 'event-full';
    return '';
}

/**
 * Get progress bar class based on percentage
 */
function getProgressClass(percentage) {
    if (percentage < 50) return 'progress-low';
    if (percentage < 80) return 'progress-medium';
    return 'progress-high';
}

/**
 * Save event (Create or Update)
 */
async function saveEvent() {
    const form = document.getElementById('eventForm');
    
    // Validate form
    if (!form.checkValidity()) {
        form.classList.add('was-validated');
        return;
    }
    
    const eventId = document.getElementById('eventId').value;
    const eventData = {
        eventName: document.getElementById('eventName').value,
        eventDescription: document.getElementById('eventDescription').value,
        eventDate: document.getElementById('eventDate').value,
        eventLocation: document.getElementById('eventLocation').value,
        maxParticipants: parseInt(document.getElementById('maxParticipants').value)
    };
    
    // Add status if editing
    if (isEditMode) {
        eventData.eventStatus = document.getElementById('eventStatus').value;
    }
    
    try {
        const url = isEditMode ? `${API_BASE_URL}/${eventId}` : API_BASE_URL;
        const method = isEditMode ? 'PUT' : 'POST';
        
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(eventData)
        });
        
        const result = await response.json();
        
        if (result.success) {
            showNotification(result.message, 'success');
            closeModal('eventModal');
            loadEvents();
            resetForm();
        } else {
            showNotification(result.message || 'Operation failed', 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showNotification('Failed to save event', 'error');
    }
}

/**
 * View event details
 */
async function viewEvent(eventId) {
    try {
        const response = await fetch(`${API_BASE_URL}/${eventId}`);
        const result = await response.json();
        
        if (result.success) {
            const event = result.data;
            const detailsHtml = `
                <div class="detail-row">
                    <div class="row">
                        <div class="col-md-4 detail-label">Event ID:</div>
                        <div class="col-md-8 detail-value">${event.eventId}</div>
                    </div>
                </div>
                <div class="detail-row">
                    <div class="row">
                        <div class="col-md-4 detail-label">Event Name:</div>
                        <div class="col-md-8 detail-value"><strong>${event.eventName}</strong></div>
                    </div>
                </div>
                <div class="detail-row">
                    <div class="row">
                        <div class="col-md-4 detail-label">Description:</div>
                        <div class="col-md-8 detail-value">${event.eventDescription || 'N/A'}</div>
                    </div>
                </div>
                <div class="detail-row">
                    <div class="row">
                        <div class="col-md-4 detail-label">Date:</div>
                        <div class="col-md-8 detail-value">${formatDate(event.eventDate)}</div>
                    </div>
                </div>
                <div class="detail-row">
                    <div class="row">
                        <div class="col-md-4 detail-label">Location:</div>
                        <div class="col-md-8 detail-value"><i class="fas fa-map-marker-alt"></i> ${event.eventLocation}</div>
                    </div>
                </div>
                <div class="detail-row">
                    <div class="row">
                        <div class="col-md-4 detail-label">Participants:</div>
                        <div class="col-md-8 detail-value">
                            <strong>${event.registeredCount}</strong> / ${event.maxParticipants}
                            <div class="participant-progress mt-2">
                                <div class="participant-progress-bar ${getProgressClass((event.registeredCount / event.maxParticipants) * 100)}" 
                                     style="width: ${(event.registeredCount / event.maxParticipants) * 100}%"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="detail-row">
                    <div class="row">
                        <div class="col-md-4 detail-label">Status:</div>
                        <div class="col-md-8 detail-value">
                            <span class="badge status-${event.eventStatus.toLowerCase()}">${event.eventStatus}</span>
                        </div>
                    </div>
                </div>
                <div class="detail-row">
                    <div class="row">
                        <div class="col-md-4 detail-label">Created:</div>
                        <div class="col-md-8 detail-value">${formatDateTime(event.createdDatetime)}</div>
                    </div>
                </div>
                <div class="detail-row">
                    <div class="row">
                        <div class="col-md-4 detail-label">Last Updated:</div>
                        <div class="col-md-8 detail-value">${formatDateTime(event.updatedDatetime)}</div>
                    </div>
                </div>
            `;
            
            document.getElementById('eventDetailsBody').innerHTML = detailsHtml;
            openModal('viewEventModal');
        }
    } catch (error) {
        console.error('Error:', error);
        showNotification('Failed to load event details', 'error');
    }
}

/**
 * Edit event
 */
async function editEvent(eventId) {
    try {
        const response = await fetch(`${API_BASE_URL}/${eventId}`);
        const result = await response.json();
        
        if (result.success) {
            const event = result.data;
            
            // Populate form
            document.getElementById('eventId').value = event.eventId;
            document.getElementById('eventName').value = event.eventName;
            document.getElementById('eventDescription').value = event.eventDescription || '';
            document.getElementById('eventDate').value = event.eventDate;
            document.getElementById('eventLocation').value = event.eventLocation;
            document.getElementById('maxParticipants').value = event.maxParticipants;
            document.getElementById('eventStatus').value = event.eventStatus;
            
            // Show status field for editing
            document.getElementById('statusGroup').style.display = 'block';
            
            // Update modal title
            document.getElementById('modalTitle').textContent = 'Edit Event';
            isEditMode = true;
            
            // Open modal
            openModal('eventModal');
        }
    } catch (error) {
        console.error('Error:', error);
        showNotification('Failed to load event', 'error');
    }
}

/**
 * Delete event
 */
async function deleteEvent(eventId) {
    if (!confirm('Are you sure you want to delete this event?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/${eventId}`, {
            method: 'DELETE'
        });
        
        const result = await response.json();
        
        if (result.success) {
            showNotification('Event deleted successfully', 'success');
            loadEvents();
        } else {
            showNotification(result.message || 'Failed to delete event', 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showNotification('Failed to delete event', 'error');
    }
}

/**
 * Register participant for event
 */
async function registerForEvent(eventId) {
    if (!confirm('Do you want to register for this event?')) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/${eventId}/register`, {
            method: 'POST'
        });
        
        const result = await response.json();
        
        if (result.success) {
            showNotification('Registration successful!', 'success');
            loadEvents();
        } else {
            showNotification(result.message || 'Registration failed', 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showNotification('Failed to register', 'error');
    }
}

/**
 * Search events by name
 */
async function searchEvents() {
    const searchTerm = document.getElementById('searchInput').value.trim();
    
    if (!searchTerm) {
        loadEvents();
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/search?name=${encodeURIComponent(searchTerm)}`);
        const result = await response.json();
        
        if (result.success) {
            displayEvents(result.data);
            showNotification(`Found ${result.data.length} event(s)`, 'info');
        }
    } catch (error) {
        console.error('Error:', error);
        showNotification('Search failed', 'error');
    }
}

/**
 * Filter events by status
 */
async function filterByStatus(status) {
    currentFilter = status;
    
    // Update active button
    document.querySelectorAll('.btn-group .btn').forEach(btn => {
        btn.classList.remove('active');
    });
    event.target.classList.add('active');
    
    if (status === 'ALL') {
        displayEvents(allEvents);
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE_URL}/status/${status}`);
        const result = await response.json();
        
        if (result.success) {
            displayEvents(result.data);
        }
    } catch (error) {
        console.error('Error:', error);
        showNotification('Filter failed', 'error');
    }
}

/**
 * Load statistics
 */
async function loadStatistics() {
    try {
        const response = await fetch(`${API_BASE_URL}/statistics`);
        const result = await response.json();
        
        if (result.success) {
            const stats = result.data;
            document.getElementById('totalEvents').textContent = stats.totalEvents || 0;
            document.getElementById('upcomingEvents').textContent = stats.upcomingEvents || 0;
            document.getElementById('completedEvents').textContent = stats.completedEvents || 0;
            document.getElementById('totalParticipants').textContent = stats.totalParticipants || 0;
        }
    } catch (error) {
        console.error('Error loading statistics:', error);
    }
}

/**
 * Reset form
 */
function resetForm() {
    document.getElementById('eventForm').reset();
    document.getElementById('eventForm').classList.remove('was-validated');
    document.getElementById('eventId').value = '';
    document.getElementById('statusGroup').style.display = 'none';
    document.getElementById('modalTitle').textContent = 'Add New Event';
    isEditMode = false;
    setMinDate();
}

/**
 * Show notification toast
 */
function showNotification(message, type = 'info') {
    const toast = document.getElementById('notificationToast');
    const toastBody = document.getElementById('toastMessage');
    const toastTitle = document.getElementById('toastTitle');
    
    // Set message
    toastBody.textContent = message;
    
    // Set title and style based on type
    toast.className = 'toast';
    if (type === 'success') {
        toast.classList.add('toast-success');
        toastTitle.textContent = 'Success';
    } else if (type === 'error') {
        toast.classList.add('toast-error');
        toastTitle.textContent = 'Error';
    } else {
        toast.classList.add('toast-info');
        toastTitle.textContent = 'Info';
    }
    
    // Show toast
    const bsToast = new bootstrap.Toast(toast);
    bsToast.show();
}

/**
 * Open modal
 */
function openModal(modalId) {
    const modal = new bootstrap.Modal(document.getElementById(modalId));
    modal.show();
}

/**
 * Close modal
 */
function closeModal(modalId) {
    const modalElement = document.getElementById(modalId);
    const modal = bootstrap.Modal.getInstance(modalElement);
    if (modal) {
        modal.hide();
    }
}

/**
 * Show loading indicator
 */
function showLoading() {
    const tbody = document.getElementById('eventsTableBody');
    tbody.innerHTML = `
        <tr>
            <td colspan="7" class="text-center">
                <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </td>
        </tr>
    `;
}

/**
 * Hide loading indicator
 */
function hideLoading() {
    // Loading is replaced by actual content
}

/**
 * Format date
 */
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', { 
        year: 'numeric', 
        month: 'short', 
        day: 'numeric' 
    });
}

/**
 * Format date time
 */
function formatDateTime(dateTimeString) {
    const date = new Date(dateTimeString);
    return date.toLocaleString('en-US', { 
        year: 'numeric', 
        month: 'short', 
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

/**
 * Truncate text
 */
function truncateText(text, maxLength) {
    if (text.length <= maxLength) return text;
    return text.substring(0, maxLength) + '...';
}
