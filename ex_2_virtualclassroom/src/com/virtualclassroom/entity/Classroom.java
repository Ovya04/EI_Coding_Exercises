/**
 * Classroom Entity Class
 * 
 * Represents a virtual classroom with comprehensive functionality for managing students,
 * assignments, attendance, and analytics.
 * 
 * Features:
 * - Student enrollment and management
 * - Assignment scheduling and tracking
 * - Attendance management with timestamps
 * - Performance analytics and reporting
 * - Pagination and filtering capabilities
 * - Cascade operations for safe removal
 * 
 * Design Patterns Used:
 * - Strategy Pattern for sorting and filtering
 * - Observer Pattern for notifications
 * 
 * @author Virtual Classroom Development Team
 * @version 2.0
 * @since 2025-09-25
 */

package com.virtualclassroom.entity;

import com.virtualclassroom.util.Logger;
import com.virtualclassroom.util.ValidationHelper;
import com.virtualclassroom.exception.ValidationException;
import com.virtualclassroom.enums.AssignmentCategory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Classroom {
    private final String classroomId;
    private String name;
    private String description;
    private final LocalDateTime creationDate;
    private final Map<String, Student> enrolledStudents;
    private final List<Assignment> assignments;
    private final Map<LocalDateTime, Map<String, Boolean>> attendanceRecord;
    private int maxCapacity;
    private boolean isActive;
    private final Logger logger;
    
    public Classroom(String name) throws ValidationException {
        this(name, "Default classroom description", 50);
    }
    
    public Classroom(String name, String description, int maxCapacity) throws ValidationException {
        ValidationHelper.validateNotNull(name, "Classroom name");
        if (!ValidationHelper.isValidClassroomName(name)) {
            throw new ValidationException("Invalid classroom name format: " + name);
        }
        
        this.classroomId = generateClassroomId();
        this.name = name.trim();
        this.description = description != null ? description.trim() : "";
        this.maxCapacity = maxCapacity > 0 ? maxCapacity : 50;
        this.creationDate = LocalDateTime.now();
        this.enrolledStudents = new HashMap<>();
        this.assignments = new ArrayList<>();
        this.attendanceRecord = new HashMap<>();
        this.isActive = true;
        this.logger = Logger.getInstance();
        
        logger.info("Classroom created: " + classroomId + " - " + name);
    }
    
    // Getters
    public String getClassroomId() { return classroomId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public int getMaxCapacity() { return maxCapacity; }
    public boolean isActive() { return isActive; }
    public int getEnrolledStudentCount() { return enrolledStudents.size(); }
    public int getAssignmentCount() { return assignments.size(); }
    
    /**
     * Add a student to the classroom
     */
    public void addStudent(Student student) throws ValidationException {
        ValidationHelper.validateNotNull(student, "Student");
        
        if (!isActive) {
            throw new ValidationException("Cannot add student to inactive classroom");
        }
        
        if (enrolledStudents.size() >= maxCapacity) {
            throw new ValidationException("Classroom has reached maximum capacity: " + maxCapacity);
        }
        
        if (enrolledStudents.containsKey(student.getStudentId())) {
            throw new ValidationException("Student already enrolled: " + student.getStudentId());
        }
        
        enrolledStudents.put(student.getStudentId(), student);
        student.enrollInClassroom(this.name);
        
        logger.info("Student " + student.getStudentId() + " enrolled in classroom " + name);
    }
    
    /**
     * Remove a student from the classroom (cascade operation)
     */
    public void removeStudent(String studentId) throws ValidationException {
        ValidationHelper.validateNotNull(studentId, "Student ID");
        
        Student student = enrolledStudents.get(studentId);
        if (student == null) {
            throw new ValidationException("Student not found in classroom: " + studentId);
        }
        
        // Cascade: Handle pending assignments
        List<Assignment> pendingAssignments = assignments.stream()
                .filter(a -> a.getSubmittedBy() == null || !a.getSubmittedBy().equals(studentId))
                .collect(Collectors.toList());
        
        if (!pendingAssignments.isEmpty()) {
            logger.info("Student " + studentId + " has " + pendingAssignments.size() + " pending assignments");
        }
        
        enrolledStudents.remove(studentId);
        student.unenrollFromClassroom(this.name);
        
        logger.info("Student " + studentId + " removed from classroom " + name);
    }
    
    /**
     * Get enrolled students with pagination and filtering
     */
    public List<Student> getStudents(int page, int pageSize, String filter) {
        List<Student> students = new ArrayList<>(enrolledStudents.values());
        
        // Apply filter if provided
        if (filter != null && !filter.trim().isEmpty()) {
            String filterLower = filter.toLowerCase();
            students = students.stream()
                    .filter(s -> s.getName().toLowerCase().contains(filterLower) ||
                               s.getStudentId().toLowerCase().contains(filterLower))
                    .collect(Collectors.toList());
        }
        
        // Apply pagination
        int start = page * pageSize;
        int end = Math.min(start + pageSize, students.size());
        
        if (start >= students.size()) {
            return new ArrayList<>();
        }
        
        return students.subList(start, end);
    }
    
    /**
     * Get all enrolled students
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(enrolledStudents.values());
    }
    
    /**
     * Check if student is enrolled
     */
    public boolean hasStudent(String studentId) {
        return enrolledStudents.containsKey(studentId);
    }
    
    /**
     * Get student by ID
     */
    public Student getStudent(String studentId) {
        return enrolledStudents.get(studentId);
    }
    
    /**
     * Schedule a new assignment
     */
    public void scheduleAssignment(Assignment assignment) throws ValidationException {
        ValidationHelper.validateNotNull(assignment, "Assignment");
        
        if (!isActive) {
            throw new ValidationException("Cannot schedule assignment in inactive classroom");
        }
        
        // Check for duplicate assignment titles
        boolean duplicateTitle = assignments.stream()
                .anyMatch(a -> a.getTitle().equalsIgnoreCase(assignment.getTitle()));
        
        if (duplicateTitle) {
            throw new ValidationException("Assignment with title already exists: " + assignment.getTitle());
        }
        
        assignments.add(assignment);
        logger.info("Assignment scheduled in " + name + ": " + assignment.getTitle());
    }
    
    /**
     * Get assignments with filtering
     */
    public List<Assignment> getAssignments(AssignmentCategory category, boolean onlyActive) {
        return assignments.stream()
                .filter(a -> category == null || a.getCategory() == category)
                .filter(a -> !onlyActive || !a.isOverdue())
                .collect(Collectors.toList());
    }
    
    /**
     * Get all assignments
     */
    public List<Assignment> getAllAssignments() {
        return new ArrayList<>(assignments);
    }
    
    /**
     * Find assignment by title
     */
    public Assignment findAssignmentByTitle(String title) {
        return assignments.stream()
                .filter(a -> a.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Mark attendance for all students on a specific date
     */
    public void markAttendance(LocalDateTime dateTime, Map<String, Boolean> studentAttendance) 
            throws ValidationException {
        ValidationHelper.validateNotNull(dateTime, "Date time");
        ValidationHelper.validateNotNull(studentAttendance, "Student attendance");
        
        // Validate all students exist in classroom
        for (String studentId : studentAttendance.keySet()) {
            if (!enrolledStudents.containsKey(studentId)) {
                throw new ValidationException("Student not enrolled in classroom: " + studentId);
            }
        }
        
        attendanceRecord.put(dateTime, new HashMap<>(studentAttendance));
        
        // Update individual student records
        for (Map.Entry<String, Boolean> entry : studentAttendance.entrySet()) {
            Student student = enrolledStudents.get(entry.getKey());
            if (student != null) {
                student.markAttendance(dateTime, entry.getValue());
            }
        }
        
        logger.info("Attendance marked for " + name + " on " + dateTime);
    }
    
    /**
     * Get attendance record for a specific date
     */
    public Map<String, Boolean> getAttendance(LocalDateTime dateTime) {
        return attendanceRecord.getOrDefault(dateTime, new HashMap<>());
    }
    
    /**
     * Calculate classroom attendance statistics
     */
    public double getOverallAttendancePercentage() {
        if (attendanceRecord.isEmpty() || enrolledStudents.isEmpty()) {
            return 0.0;
        }
        
        int totalSessions = attendanceRecord.size();
        int totalStudents = enrolledStudents.size();
        int totalPossibleAttendance = totalSessions * totalStudents;
        
        long totalPresent = attendanceRecord.values().stream()
                .flatMap(attendance -> attendance.values().stream())
                .mapToLong(present -> present ? 1 : 0)
                .sum();
        
        return (double) totalPresent / totalPossibleAttendance * 100;
    }
    
    /**
     * Get comprehensive classroom statistics
     */
    public String getClassroomStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== Classroom Statistics ===\n");
        stats.append("Name: ").append(name).append("\n");
        stats.append("ID: ").append(classroomId).append("\n");
        stats.append("Created: ").append(creationDate).append("\n");
        stats.append("Status: ").append(isActive ? "Active" : "Inactive").append("\n");
        stats.append("Students Enrolled: ").append(enrolledStudents.size()).append("/").append(maxCapacity).append("\n");
        stats.append("Total Assignments: ").append(assignments.size()).append("\n");
        stats.append("Overall Attendance: ").append(String.format("%.2f%%", getOverallAttendancePercentage())).append("\n");
        
        // Assignment statistics
        long completedAssignments = assignments.stream()
                .mapToLong(a -> a.getStatus().toString().equals("GRADED") ? 1 : 0)
                .sum();
        
        stats.append("Completed Assignments: ").append(completedAssignments).append("/").append(assignments.size()).append("\n");
        
        // Average grade
        double avgGrade = assignments.stream()
                .filter(a -> a.getStatus().toString().equals("GRADED"))
                .mapToDouble(Assignment::getGradePercentage)
                .average()
                .orElse(0.0);
        
        stats.append("Average Grade: ").append(String.format("%.2f%%", avgGrade)).append("\n");
        
        return stats.toString();
    }
    
    /**
     * Get assignment submission analytics
     */
    public String getSubmissionAnalytics() {
        StringBuilder analytics = new StringBuilder();
        analytics.append("=== Assignment Submission Analytics ===\n");
        
        for (Assignment assignment : assignments) {
            analytics.append("Assignment: ").append(assignment.getTitle()).append("\n");
            analytics.append("  Status: ").append(assignment.getStatus()).append("\n");
            analytics.append("  Due Date: ").append(assignment.getDueDate()).append("\n");
            
            if (assignment.getSubmittedBy() != null) {
                analytics.append("  Submitted By: ").append(assignment.getSubmittedBy()).append("\n");
                analytics.append("  Submission Time: ").append(assignment.getSubmissionTime()).append("\n");
                if (assignment.getStatus().toString().equals("GRADED")) {
                    analytics.append("  Grade: ").append(assignment.getGrade()).append("/").append(assignment.getMaxPoints()).append("\n");
                }
            } else {
                analytics.append("  Status: Not submitted\n");
            }
            analytics.append("\n");
        }
        
        return analytics.toString();
    }
    
    /**
     * Deactivate classroom (soft delete)
     */
    public void deactivate() {
        this.isActive = false;
        logger.info("Classroom deactivated: " + name);
    }
    
    /**
     * Reactivate classroom
     */
    public void activate() {
        this.isActive = true;
        logger.info("Classroom activated: " + name);
    }
    
    /**
     * Update classroom information
     */
    public void updateClassroomInfo(String newName, String newDescription, int newMaxCapacity) 
            throws ValidationException {
        if (newName != null && !ValidationHelper.isValidClassroomName(newName)) {
            throw new ValidationException("Invalid classroom name: " + newName);
        }
        
        if (newMaxCapacity > 0 && newMaxCapacity < enrolledStudents.size()) {
            throw new ValidationException("Cannot reduce capacity below current enrollment");
        }
        
        if (newName != null) this.name = newName.trim();
        if (newDescription != null) this.description = newDescription.trim();
        if (newMaxCapacity > 0) this.maxCapacity = newMaxCapacity;
        
        logger.info("Classroom information updated: " + classroomId);
    }
    
    /**
     * Generate unique classroom ID
     */
    private String generateClassroomId() {
        return "CLS-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Classroom classroom = (Classroom) obj;
        return Objects.equals(classroomId, classroom.classroomId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(classroomId);
    }
    
    @Override
    public String toString() {
        return String.format("Classroom{id='%s', name='%s', students=%d/%d, assignments=%d, active=%s}", 
                           classroomId, name, enrolledStudents.size(), maxCapacity, assignments.size(), isActive);
    }
}