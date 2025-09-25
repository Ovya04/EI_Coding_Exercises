 
/**
 * Assignment Entity Class
 * 
 * Represents an assignment in the virtual classroom system with comprehensive
 * functionality for managing assignment details, submissions, and grading.
 * 
 * Features:
 * - Rich assignment metadata (title, description, due date, category)
 * - Grading rubrics and automated scoring
 * - Submission tracking with timestamps
 * - Status management (scheduled, submitted, graded)
 * - File simulation for submission handling
 * 
 * Design Patterns Used:
 * - Builder Pattern for complex assignment creation
 * - State Pattern for assignment status management
 * 
 * @author Virtual Classroom Development Team
 * @version 2.0
 * @since 2025-09-25
 */

package com.virtualclassroom.entity;

import com.virtualclassroom.util.Logger;
import com.virtualclassroom.util.ValidationHelper;
import com.virtualclassroom.exception.ValidationException;
import com.virtualclassroom.enums.AssignmentStatus;
import com.virtualclassroom.enums.AssignmentCategory;

import java.time.LocalDateTime;
import java.util.*;

public class Assignment {
    private final String assignmentId;
    private final String title;
    private final String description;
    private final LocalDateTime dueDate;
    private final AssignmentCategory category;
    private final int maxPoints;
    private final String gradingRubric;
    
    // Submission related fields
    private LocalDateTime submissionTime;
    private String submittedBy;
    private AssignmentStatus status;
    private double grade;
    private String feedback;
    private final List<String> submittedFiles;
    private final LocalDateTime creationTime;
    
    private final Logger logger;
    
    /**
     * Private constructor to enforce Builder pattern usage
     */
    private Assignment(Builder builder) {
        this.assignmentId = generateAssignmentId();
        this.title = builder.title;
        this.description = builder.description;
        this.dueDate = builder.dueDate;
        this.category = builder.category;
        this.maxPoints = builder.maxPoints;
        this.gradingRubric = builder.gradingRubric;
        
        // Initialize submission fields
        this.status = AssignmentStatus.SCHEDULED;
        this.grade = 0.0;
        this.submittedFiles = new ArrayList<>();
        this.creationTime = LocalDateTime.now();
        this.logger = Logger.getInstance();
        
        logger.info("Assignment created: " + assignmentId + " - " + title);
    }
    
    /**
     * Builder Class for Assignment creation
     */
    public static class Builder {
        private String title;
        private String description;
        private LocalDateTime dueDate;
        private AssignmentCategory category = AssignmentCategory.HOMEWORK;
        private int maxPoints = 100;
        private String gradingRubric = "Standard grading rubric";
        
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }
        
        public Builder setDueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }
        
        public Builder setCategory(AssignmentCategory category) {
            this.category = category;
            return this;
        }
        
        public Builder setMaxPoints(int maxPoints) {
            this.maxPoints = maxPoints;
            return this;
        }
        
        public Builder setGradingRubric(String gradingRubric) {
            this.gradingRubric = gradingRubric;
            return this;
        }
        
        public Assignment build() throws ValidationException {
            validateBuilder();
            return new Assignment(this);
        }
        
        private void validateBuilder() throws ValidationException {
            if (!ValidationHelper.isValidTitle(title)) {
                throw new ValidationException("Invalid assignment title: " + title);
            }
            if (description == null || description.trim().isEmpty()) {
                throw new ValidationException("Assignment description cannot be empty");
            }
            if (dueDate == null || dueDate.isBefore(LocalDateTime.now())) {
                throw new ValidationException("Due date must be in the future");
            }
            if (maxPoints <= 0) {
                throw new ValidationException("Max points must be greater than 0");
            }
        }
    }
    
    // Getters
    public String getAssignmentId() { return assignmentId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDueDate() { return dueDate; }
    public AssignmentCategory getCategory() { return category; }
    public int getMaxPoints() { return maxPoints; }
    public String getGradingRubric() { return gradingRubric; }
    public LocalDateTime getSubmissionTime() { return submissionTime; }
    public String getSubmittedBy() { return submittedBy; }
    public AssignmentStatus getStatus() { return status; }
    public double getGrade() { return grade; }
    public String getFeedback() { return feedback; }
    public List<String> getSubmittedFiles() { return new ArrayList<>(submittedFiles); }
    public LocalDateTime getCreationTime() { return creationTime; }
    
    // Setters for submission
    public void setSubmissionTime(LocalDateTime submissionTime) {
        this.submissionTime = submissionTime;
        this.status = AssignmentStatus.SUBMITTED;
        logger.info("Assignment " + assignmentId + " submitted at: " + submissionTime);
    }
    
    public void setSubmittedBy(String studentId) throws ValidationException {
        if (!ValidationHelper.isValidStudentId(studentId)) {
            throw new ValidationException("Invalid student ID: " + studentId);
        }
        this.submittedBy = studentId;
    }
    
    /**
     * Add a submitted file (simulated file upload)
     */
    public void addSubmittedFile(String fileName) throws ValidationException {
        ValidationHelper.validateNotNull(fileName, "File name");
        
        if (submittedFiles.contains(fileName)) {
            throw new ValidationException("File already submitted: " + fileName);
        }
        
        submittedFiles.add(fileName);
        logger.info("File added to assignment " + assignmentId + ": " + fileName);
    }
    
    /**
     * Grade the assignment
     */
    public void gradeAssignment(double points, String feedback) throws ValidationException {
        if (points < 0 || points > maxPoints) {
            throw new ValidationException("Grade must be between 0 and " + maxPoints);
        }
        
        if (status != AssignmentStatus.SUBMITTED) {
            throw new ValidationException("Cannot grade assignment that hasn't been submitted");
        }
        
        this.grade = points;
        this.feedback = feedback != null ? feedback : "No feedback provided";
        this.status = AssignmentStatus.GRADED;
        
        logger.info("Assignment " + assignmentId + " graded: " + points + "/" + maxPoints);
    }
    
    /**
     * Check if assignment is overdue
     */
    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(dueDate) && status == AssignmentStatus.SCHEDULED;
    }
    
    /**
     * Get time remaining until due date
     */
    public String getTimeRemaining() {
        if (isOverdue()) {
            return "OVERDUE";
        }
        
        LocalDateTime now = LocalDateTime.now();
        long hours = java.time.Duration.between(now, dueDate).toHours();
        
        if (hours < 24) {
            return hours + " hours remaining";
        } else {
            long days = hours / 24;
            return days + " days remaining";
        }
    }
    
    /**
     * Get assignment details for display
     */
    public String getAssignmentDetails() {
        StringBuilder details = new StringBuilder();
        details.append("=== Assignment Details ===\n");
        details.append("ID: ").append(assignmentId).append("\n");
        details.append("Title: ").append(title).append("\n");
        details.append("Description: ").append(description).append("\n");
        details.append("Category: ").append(category).append("\n");
        details.append("Due Date: ").append(dueDate).append("\n");
        details.append("Max Points: ").append(maxPoints).append("\n");
        details.append("Status: ").append(status).append("\n");
        details.append("Time Remaining: ").append(getTimeRemaining()).append("\n");
        
        if (status == AssignmentStatus.SUBMITTED || status == AssignmentStatus.GRADED) {
            details.append("Submitted By: ").append(submittedBy).append("\n");
            details.append("Submission Time: ").append(submissionTime).append("\n");
            details.append("Files Submitted: ").append(submittedFiles.size()).append("\n");
        }
        
        if (status == AssignmentStatus.GRADED) {
            details.append("Grade: ").append(grade).append("/").append(maxPoints).append("\n");
            details.append("Feedback: ").append(feedback).append("\n");
        }
        
        return details.toString();
    }
    
    /**
     * Calculate grade percentage
     */
    public double getGradePercentage() {
        if (maxPoints == 0) return 0.0;
        return (grade / maxPoints) * 100;
    }
    
    /**
     * Get letter grade based on percentage
     */
    public String getLetterGrade() {
        double percentage = getGradePercentage();
        if (percentage >= 90) return "A";
        if (percentage >= 80) return "B";
        if (percentage >= 70) return "C";
        if (percentage >= 60) return "D";
        return "F";
    }
    
    /**
     * Generate unique assignment ID
     */
    private String generateAssignmentId() {
        return "ASG-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }
    
    /**
     * Clone assignment for reuse
     */
    public Assignment cloneForNewTerm() throws ValidationException {
        return new Builder()
                .setTitle(this.title)
                .setDescription(this.description)
                .setCategory(this.category)
                .setMaxPoints(this.maxPoints)
                .setGradingRubric(this.gradingRubric)
                .setDueDate(LocalDateTime.now().plusDays(7)) // Default to one week from now
                .build();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Assignment assignment = (Assignment) obj;
        return Objects.equals(assignmentId, assignment.assignmentId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(assignmentId);
    }
    
    @Override
    public String toString() {
        return String.format("Assignment{id='%s', title='%s', category=%s, status=%s, due=%s}", 
                           assignmentId, title, category, status, dueDate);
    }
}