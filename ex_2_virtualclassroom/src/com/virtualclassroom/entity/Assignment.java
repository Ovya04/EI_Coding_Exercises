/**
 * Assignment Entity Class (Multi-Student Submission)
 *
 * Represents an assignment that can be submitted by multiple students.
 * Tracks submissions, grades, and feedback per student.
 */
package com.virtualclassroom.entity;

import com.virtualclassroom.util.Logger;
import com.virtualclassroom.util.ValidationHelper;
import com.virtualclassroom.exception.ValidationException;
import com.virtualclassroom.enums.AssignmentStatus;

import java.util.*;

public class Assignment {
    private final String assignmentId;
    private final String title;
    private final String description;
    private final int maxPoints;
    private final String gradingRubric;

    // Submission related fields (multi-student)
    private final Set<String> submittedStudentIds;
    private final Map<String, List<String>> submittedFiles;
    private final Map<String, Double> grades;
    private final Map<String, String> feedbacks;
    private AssignmentStatus status;

    private final Logger logger;

    private Assignment(Builder builder) {
        this.assignmentId = generateAssignmentId();
        this.title = builder.title;
        this.description = builder.description;
        this.maxPoints = builder.maxPoints;
        this.gradingRubric = builder.gradingRubric;
        this.status = AssignmentStatus.SCHEDULED;
        this.submittedStudentIds = new HashSet<>();
        this.submittedFiles = new HashMap<>();
        this.grades = new HashMap<>();
        this.feedbacks = new HashMap<>();
        this.logger = Logger.getInstance();
        logger.info("Assignment created: " + assignmentId + " - " + title);
    }

    public static class Builder {
        private String title;
        private String description;
        private int maxPoints = 100;
        private String gradingRubric = "Standard grading rubric";

        public Builder setTitle(String title) { this.title = title; return this; }
        public Builder setDescription(String description) { this.description = description; return this; }
        public Builder setMaxPoints(int maxPoints) { this.maxPoints = maxPoints; return this; }
        public Builder setGradingRubric(String gradingRubric) { this.gradingRubric = gradingRubric; return this; }

        public Assignment build() throws ValidationException {
            validateBuilder();
            return new Assignment(this);
        }

        private void validateBuilder() throws ValidationException {
            if (!ValidationHelper.isValidTitle(title)) throw new ValidationException("Invalid assignment title: " + title);
            if (ValidationHelper.isNullOrEmpty(description)) throw new ValidationException("Assignment description cannot be empty");
            if (maxPoints <= 0) throw new ValidationException("Max points must be greater than 0");
        }
    }

    // --- Getters ---
    public String getAssignmentId() { return assignmentId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getMaxPoints() { return maxPoints; }
    public String getGradingRubric() { return gradingRubric; }
    public AssignmentStatus getStatus() { return status; }
    public Set<String> getSubmittedStudentIds() { return new HashSet<>(submittedStudentIds); }
    public Map<String, List<String>> getSubmittedFiles() { return new HashMap<>(submittedFiles); }
    public Map<String, Double> getGrades() { return new HashMap<>(grades); }
    public Map<String, String> getFeedbacks() { return new HashMap<>(feedbacks); }

    // --- Core Logic Methods ---

    /**
     * Marks the assignment as submitted by a student.
     */
    public void markAsSubmitted(String studentId) throws ValidationException {
        ValidationHelper.validateNotNullOrEmpty(studentId, "Student ID");
        submittedStudentIds.add(studentId);
        status = AssignmentStatus.SUBMITTED;
        logger.info("Assignment " + assignmentId + " marked as submitted by " + studentId);
    }

    public void addSubmittedFile(String studentId, String fileName) throws ValidationException {
        ValidationHelper.validateNotNullOrEmpty(fileName, "File name");
        ValidationHelper.validateNotNullOrEmpty(studentId, "Student ID");
        submittedFiles.computeIfAbsent(studentId, k -> new ArrayList<>());
        if (submittedFiles.get(studentId).contains(fileName)) {
            throw new ValidationException("File already submitted: " + fileName);
        }
        submittedFiles.get(studentId).add(fileName);
        logger.info("File added to assignment " + assignmentId + ": " + fileName + " by " + studentId);
    }

    public boolean hasSubmitted(String studentId) {
        return submittedStudentIds.contains(studentId);
    }

    public void gradeAssignment(String studentId, double points, String feedback) throws ValidationException {
        if (!hasSubmitted(studentId)) throw new ValidationException("Student has not submitted this assignment");
        if (points < 0 || points > maxPoints) throw new ValidationException("Grade must be between 0 and " + maxPoints);
        grades.put(studentId, points);
        feedbacks.put(studentId, feedback != null ? feedback : "No feedback provided");
        status = AssignmentStatus.GRADED;
        logger.info("Assignment " + assignmentId + " graded for " + studentId + ": " + points + "/" + maxPoints);
    }

    public double getGradePercentage(String studentId) {
        Double grade = grades.get(studentId);
        if (grade == null || maxPoints == 0) return 0.0;
        return (grade / maxPoints) * 100;
    }

    public String getLetterGrade(String studentId) {
        double percentage = getGradePercentage(studentId);
        if (percentage >= 90) return "A";
        if (percentage >= 80) return "B";
        if (percentage >= 70) return "C";
        if (percentage >= 60) return "D";
        return "F";
    }

    public List<String> getGradedStudentIds() {
        return new ArrayList<>(grades.keySet());
    }

    public boolean hasAnyGraded() {
        return !grades.isEmpty();
    }

    public String getAssignmentDetails() {
        StringBuilder details = new StringBuilder();
        details.append("=== Assignment Details ===\n");
        details.append("ID: ").append(assignmentId).append("\n");
        details.append("Title: ").append(title).append("\n");
        details.append("Description: ").append(description).append("\n");
        details.append("Max Points: ").append(maxPoints).append("\n");
        details.append("Status: ").append(status).append("\n");
        details.append("Submitted By: ").append(String.join(", ", submittedStudentIds)).append("\n");
        details.append("Files Submitted: ").append(submittedFiles.size()).append("\n");
        if (!grades.isEmpty()) {
            details.append("Grades:\n");
            for (String studentId : grades.keySet()) {
                details.append("  ").append(studentId).append(": ").append(grades.get(studentId)).append("/").append(maxPoints)
                        .append(" (").append(getLetterGrade(studentId)).append(")\n");
            }
        }
        return details.toString();
    }

    private String generateAssignmentId() {
        return "ASG-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
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
        return String.format("Assignment{id='%s', title='%s', status=%s}", assignmentId, title, status);
    }
}