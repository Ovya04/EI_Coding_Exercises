/**
 * Student Entity Class
 * 
 * Represents a student in the virtual classroom system with comprehensive
 * functionality for managing student data, assignments, and attendance.
 * 
 * Features:
 * - Student profile management
 * - Assignment submission tracking
 * - Attendance tracking with timestamps
 * - Performance analytics
 * - Validation and data integrity
 * 
 * Design Patterns Used:
 * - Builder Pattern for complex student creation
 * - Observer Pattern for grade notifications
 * 
 * @author Virtual Classroom Development Team
 * @version 2.0
 * @since 2025-09-25
 */

package com.virtualclassroom.entity;

import com.virtualclassroom.util.Logger;
import com.virtualclassroom.util.ValidationHelper;
import com.virtualclassroom.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.*;

public class Student {
    private final String studentId;
    private String name;
    private String email;
    private final LocalDateTime enrollmentDate;
    private final Map<String, List<Assignment>> submittedAssignments;
    private final Map<LocalDateTime, Boolean> attendanceRecord;
    private final Set<String> enrolledClassrooms;
    private double totalGrade;
    private int totalAssignments;
    private final Logger logger;
    
    /**
     * Private constructor to enforce Builder pattern usage
     */
    private Student(Builder builder) {
        this.studentId = builder.studentId;
        this.name = builder.name;
        this.email = builder.email;
        this.enrollmentDate = LocalDateTime.now();
        this.submittedAssignments = new HashMap<>();
        this.attendanceRecord = new HashMap<>();
        this.enrolledClassrooms = new HashSet<>();
        this.totalGrade = 0.0;
        this.totalAssignments = 0;
        this.logger = Logger.getInstance();
        
        logger.info("Student created: " + studentId + " - " + name);
    }
    
    /**
     * Builder Class for Student creation
     */
    public static class Builder {
        private String studentId;
        private String name;
        private String email;
        
        public Builder setStudentId(String studentId) {
            this.studentId = studentId;
            return this;
        }
        
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        
        public Student build() throws ValidationException {
            validateBuilder();
            return new Student(this);
        }
        
        private void validateBuilder() throws ValidationException {
            if (!ValidationHelper.isValidStudentId(studentId)) {
                throw new ValidationException("Invalid student ID format: " + studentId);
            }
            if (!ValidationHelper.isValidName(name)) {
                throw new ValidationException("Invalid student name: " + name);
            }
            if (!ValidationHelper.isValidEmail(email)) {
                throw new ValidationException("Invalid email format: " + email);
            }
        }
    }
    
    // Getters
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public Set<String> getEnrolledClassrooms() { return new HashSet<>(enrolledClassrooms); }
    
    /**
     * Submit an assignment for a specific classroom
     */
    public void submitAssignment(String className, Assignment assignment) throws ValidationException {
        ValidationHelper.validateNotNull(className, "Classroom name");
        ValidationHelper.validateNotNull(assignment, "Assignment");
        
        if (!enrolledClassrooms.contains(className)) {
            throw new ValidationException("Student is not enrolled in classroom: " + className);
        }
        
        submittedAssignments.computeIfAbsent(className, k -> new ArrayList<>()).add(assignment);
        assignment.setSubmissionTime(LocalDateTime.now());
        assignment.setSubmittedBy(this.studentId);
        
        totalAssignments++;
        totalGrade += assignment.getGrade();
        
        logger.info("Assignment submitted by " + studentId + " in " + className + ": " + assignment.getTitle());
    }
    
    /**
     * Get submitted assignments for a specific classroom
     */
    public List<Assignment> getSubmittedAssignments(String className) {
        return submittedAssignments.getOrDefault(className, new ArrayList<>());
    }
    
    /**
     * Get all submitted assignments across all classrooms
     */
    public Map<String, List<Assignment>> getAllSubmittedAssignments() {
        return new HashMap<>(submittedAssignments);
    }
    
    /**
     * Mark attendance for a specific date and time
     */
    public void markAttendance(LocalDateTime dateTime, boolean isPresent) {
        attendanceRecord.put(dateTime, isPresent);
        logger.info("Attendance marked for " + studentId + " on " + dateTime + ": " + 
                   (isPresent ? "Present" : "Absent"));
    }
    
    /**
     * Get attendance record
     */
    public Map<LocalDateTime, Boolean> getAttendanceRecord() {
        return new HashMap<>(attendanceRecord);
    }
    
    /**
     * Calculate attendance percentage
     */
    public double getAttendancePercentage() {
        if (attendanceRecord.isEmpty()) return 0.0;
        
        long presentCount = attendanceRecord.values().stream()
                                          .mapToLong(present -> present ? 1 : 0)
                                          .sum();
        return (double) presentCount / attendanceRecord.size() * 100;
    }
    
    /**
     * Enroll student in a classroom
     */
    public void enrollInClassroom(String className) throws ValidationException {
        ValidationHelper.validateNotNull(className, "Classroom name");
        
        if (enrolledClassrooms.contains(className)) {
            throw new ValidationException("Student already enrolled in: " + className);
        }
        
        enrolledClassrooms.add(className);
        logger.info("Student " + studentId + " enrolled in classroom: " + className);
    }
    
    /**
     * Unenroll student from a classroom
     */
    public void unenrollFromClassroom(String className) throws ValidationException {
        ValidationHelper.validateNotNull(className, "Classroom name");
        
        if (!enrolledClassrooms.contains(className)) {
            throw new ValidationException("Student not enrolled in: " + className);
        }
        
        enrolledClassrooms.remove(className);
        // Keep historical data but mark as unenrolled
        logger.info("Student " + studentId + " unenrolled from classroom: " + className);
    }
    
    /**
     * Calculate overall grade average
     */
    public double getGradeAverage() {
        if (totalAssignments == 0) return 0.0;
        return totalGrade / totalAssignments;
    }
    
    /**
     * Get comprehensive student profile
     */
    public String getStudentProfile() {
        StringBuilder profile = new StringBuilder();
        profile.append("=== Student Profile ===\n");
        profile.append("ID: ").append(studentId).append("\n");
        profile.append("Name: ").append(name).append("\n");
        profile.append("Email: ").append(email).append("\n");
        profile.append("Enrollment Date: ").append(enrollmentDate).append("\n");
        profile.append("Enrolled Classrooms: ").append(enrolledClassrooms.size()).append("\n");
        profile.append("Total Assignments: ").append(totalAssignments).append("\n");
        profile.append("Grade Average: ").append(String.format("%.2f", getGradeAverage())).append("\n");
        profile.append("Attendance Percentage: ").append(String.format("%.2f%%", getAttendancePercentage())).append("\n");
        
        return profile.toString();
    }
    
    /**
     * Update student information
     */
    public void updateName(String newName) throws ValidationException {
        if (!ValidationHelper.isValidName(newName)) {
            throw new ValidationException("Invalid name format: " + newName);
        }
        this.name = newName;
        logger.info("Updated name for student " + studentId + " to: " + newName);
    }
    
    public void updateEmail(String newEmail) throws ValidationException {
        if (!ValidationHelper.isValidEmail(newEmail)) {
            throw new ValidationException("Invalid email format: " + newEmail);
        }
        this.email = newEmail;
        logger.info("Updated email for student " + studentId + " to: " + newEmail);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return Objects.equals(studentId, student.studentId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
    
    @Override
    public String toString() {
        return String.format("Student{id='%s', name='%s', classrooms=%d, assignments=%d}", 
                           studentId, name, enrolledClassrooms.size(), totalAssignments);
    }
}