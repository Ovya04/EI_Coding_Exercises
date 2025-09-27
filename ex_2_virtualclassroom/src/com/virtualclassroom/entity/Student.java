/**
 * Student Entity Class (Multi-Assignment, Date-Free Attendance)
 */
package com.virtualclassroom.entity;

import com.virtualclassroom.util.Logger;
import com.virtualclassroom.util.ValidationHelper;
import com.virtualclassroom.exception.ValidationException;

import java.util.*;

public class Student {
    private final String studentId;
    private String name;
    private String email;
    private final Map<String, List<Assignment>> submittedAssignments; // className -> assignments
    private final Map<String, Boolean> attendanceRecord; // className -> present/absent
    private final Set<String> enrolledClassrooms;
    private double totalGrade;
    private int totalAssignments;
    private final Logger logger;

    private Student(Builder builder) {
        this.studentId = builder.studentId;
        this.name = builder.name;
        this.email = builder.email;
        this.submittedAssignments = new HashMap<>();
        this.attendanceRecord = new HashMap<>();
        this.enrolledClassrooms = new HashSet<>();
        this.totalGrade = 0.0;
        this.totalAssignments = 0;
        this.logger = Logger.getInstance();
        logger.info("Student created: " + studentId + " - " + name);
    }

    public static class Builder {
        private String studentId;
        private String name;
        private String email;

        public Builder setStudentId(String studentId) { this.studentId = studentId; return this; }
        public Builder setName(String name) { this.name = name; return this; }
        public Builder setEmail(String email) { this.email = email; return this; }

        public Student build() throws ValidationException {
            validateBuilder();
            return new Student(this);
        }

        private void validateBuilder() throws ValidationException {
            if (!ValidationHelper.isValidStudentId(studentId)) throw new ValidationException("Invalid student ID format: " + studentId);
            if (!ValidationHelper.isValidName(name)) throw new ValidationException("Invalid student name: " + name);
            if (!ValidationHelper.isValidEmail(email)) throw new ValidationException("Invalid email format: " + email);
        }
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Set<String> getEnrolledClassrooms() { return new HashSet<>(enrolledClassrooms); }

    public void submitAssignment(String className, Assignment assignment) throws ValidationException {
        ValidationHelper.validateNotNull(className, "Classroom name");
        ValidationHelper.validateNotNull(assignment, "Assignment");
        if (!enrolledClassrooms.contains(className)) throw new ValidationException("Student is not enrolled in classroom: " + className);
        submittedAssignments.computeIfAbsent(className, k -> new ArrayList<>()).add(assignment);
        totalAssignments++;
        if (assignment.getGrades().containsKey(studentId)) {
            totalGrade += assignment.getGrades().get(studentId);
        }
        logger.info("Assignment submitted by " + studentId + " in " + className + ": " + assignment.getTitle());
    }

    public List<Assignment> getSubmittedAssignments(String className) {
        return submittedAssignments.getOrDefault(className, new ArrayList<>());
    }

    public Map<String, List<Assignment>> getAllSubmittedAssignments() {
        return new HashMap<>(submittedAssignments);
    }

    // Attendance: mark for class (no date)
    public void markAttendance(String className, boolean isPresent) {
        attendanceRecord.put(className, isPresent);
        logger.info("Attendance marked for " + studentId + " in " + className + ": " + (isPresent ? "Present" : "Absent"));
    }

    public Map<String, Boolean> getAttendanceRecord() {
        return new HashMap<>(attendanceRecord);
    }

    public double getAttendancePercentage() {
        if (attendanceRecord.isEmpty()) return 0.0;
        long presentCount = attendanceRecord.values().stream().mapToLong(present -> present ? 1 : 0).sum();
        return (double) presentCount / attendanceRecord.size() * 100;
    }

    public void enrollInClassroom(String className) throws ValidationException {
        ValidationHelper.validateNotNull(className, "Classroom name");
        if (enrolledClassrooms.contains(className)) throw new ValidationException("Student already enrolled in: " + className);
        enrolledClassrooms.add(className);
        logger.info("Student " + studentId + " enrolled in classroom: " + className);
    }

    public void unenrollFromClassroom(String className) throws ValidationException {
        ValidationHelper.validateNotNull(className, "Classroom name");
        if (!enrolledClassrooms.contains(className)) throw new ValidationException("Student not enrolled in: " + className);
        enrolledClassrooms.remove(className);
        logger.info("Student " + studentId + " unenrolled from classroom: " + className);
    }

    public double getGradeAverage() {
        if (totalAssignments == 0) return 0.0;
        return totalGrade / totalAssignments;
    }

    public String getStudentProfile() {
        StringBuilder profile = new StringBuilder();
        profile.append("=== Student Profile ===\n");
        profile.append("ID: ").append(studentId).append("\n");
        profile.append("Name: ").append(name).append("\n");
        profile.append("Email: ").append(email).append("\n");
        profile.append("Enrolled Classrooms: ").append(enrolledClassrooms.size()).append("\n");
        profile.append("Total Assignments: ").append(totalAssignments).append("\n");
        profile.append("Grade Average: ").append(String.format("%.2f", getGradeAverage())).append("\n");
        profile.append("Attendance Percentage: ").append(String.format("%.2f%%", getAttendancePercentage())).append("\n");
        return profile.toString();
    }

    public void updateName(String newName) throws ValidationException {
        if (!ValidationHelper.isValidName(newName)) throw new ValidationException("Invalid name format: " + newName);
        this.name = newName;
        logger.info("Updated name for student " + studentId + " to: " + newName);
    }

    public void updateEmail(String newEmail) throws ValidationException {
        if (!ValidationHelper.isValidEmail(newEmail)) throw new ValidationException("Invalid email format: " + newEmail);
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