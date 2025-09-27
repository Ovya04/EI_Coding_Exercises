/**
 * Classroom Entity Class (Multi-Student Assignment, Date-Free Attendance)
 */
package com.virtualclassroom.entity;

import com.virtualclassroom.util.Logger;
import com.virtualclassroom.util.ValidationHelper;
import com.virtualclassroom.exception.ValidationException;

import java.util.*;
import java.util.stream.Collectors;

public class Classroom {
    private final String classroomId;
    private String name;
    private String description;
    private final Map<String, Student> enrolledStudents;
    private final List<Assignment> assignments;
    private final Map<String, Map<String, Boolean>> attendanceRecord; // className -> studentId -> present
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
    public int getMaxCapacity() { return maxCapacity; }
    public boolean isActive() { return isActive; }
    public int getEnrolledStudentCount() { return enrolledStudents.size(); }
    public int getAssignmentCount() { return assignments.size(); }

    public void addStudent(Student student) throws ValidationException {
        ValidationHelper.validateNotNull(student, "Student");
        if (!isActive) throw new ValidationException("Cannot add student to inactive classroom");
        if (enrolledStudents.size() >= maxCapacity) throw new ValidationException("Classroom has reached maximum capacity: " + maxCapacity);
        if (enrolledStudents.containsKey(student.getStudentId())) throw new ValidationException("Student already enrolled: " + student.getStudentId());
        enrolledStudents.put(student.getStudentId(), student);
        student.enrollInClassroom(this.name);
        logger.info("Student " + student.getStudentId() + " enrolled in classroom " + name);
    }

    public void removeStudent(String studentId) throws ValidationException {
        ValidationHelper.validateNotNull(studentId, "Student ID");
        Student student = enrolledStudents.get(studentId);
        if (student == null) throw new ValidationException("Student not found in classroom: " + studentId);
        enrolledStudents.remove(studentId);
        student.unenrollFromClassroom(this.name);
        logger.info("Student " + studentId + " removed from classroom " + name);
    }

    public List<Student> getStudents(int page, int pageSize, String filter) {
        List<Student> students = new ArrayList<>(enrolledStudents.values());
        if (filter != null && !filter.trim().isEmpty()) {
            String filterLower = filter.toLowerCase();
            students = students.stream()
                    .filter(s -> s.getName().toLowerCase().contains(filterLower) ||
                               s.getStudentId().toLowerCase().contains(filterLower))
                    .collect(Collectors.toList());
        }
        int start = page * pageSize;
        int end = Math.min(start + pageSize, students.size());
        if (start >= students.size()) return new ArrayList<>();
        return students.subList(start, end);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(enrolledStudents.values());
    }

    public boolean hasStudent(String studentId) {
        return enrolledStudents.containsKey(studentId);
    }

    public Student getStudent(String studentId) {
        return enrolledStudents.get(studentId);
    }

    public void scheduleAssignment(Assignment assignment) throws ValidationException {
        ValidationHelper.validateNotNull(assignment, "Assignment");
        if (!isActive) throw new ValidationException("Cannot schedule assignment in inactive classroom");
        boolean duplicateTitle = assignments.stream().anyMatch(a -> a.getTitle().equalsIgnoreCase(assignment.getTitle()));
        if (duplicateTitle) throw new ValidationException("Assignment with title already exists: " + assignment.getTitle());
        assignments.add(assignment);
        logger.info("Assignment scheduled in " + name + ": " + assignment.getTitle());
    }

    public List<Assignment> getAssignments(boolean onlyActive) {
        return assignments.stream().collect(Collectors.toList());
    }

    public List<Assignment> getAllAssignments() {
        return new ArrayList<>(assignments);
    }

    public Assignment findAssignmentByTitle(String title) {
        return assignments.stream()
                .filter(a -> a.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    // Attendance: mark for all students in class (no date)
    public void markAttendance(Map<String, Boolean> studentAttendance) throws ValidationException {
    ValidationHelper.validateNotNull(studentAttendance, "Student attendance");
    Map<String, Boolean> attendanceMap = new HashMap<>();
    for (String studentId : enrolledStudents.keySet()) {
        boolean isPresent = studentAttendance.getOrDefault(studentId, false); // default absent
        attendanceMap.put(studentId, isPresent);
        Student student = enrolledStudents.get(studentId);
        if (student != null) {
            student.markAttendance(name, isPresent);
        }
    }
    attendanceRecord.put(name, attendanceMap);
    logger.info("Attendance marked for " + name);
}
    // Get attendance for class (no date)
    public Map<String, Boolean> getAttendance() {
        return attendanceRecord.getOrDefault(name, new HashMap<>());
    }

    public double getOverallAttendancePercentage() {
        Map<String, Boolean> attendance = getAttendance();
        if (attendance.isEmpty() || enrolledStudents.isEmpty()) return 0.0;
        long presentCount = attendance.values().stream().filter(present -> present).count();
        return (double) presentCount / enrolledStudents.size() * 100;
    }

    public String getClassroomStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== Classroom Statistics ===\n");
        stats.append("Name: ").append(name).append("\n");
        stats.append("ID: ").append(classroomId).append("\n");
        stats.append("Status: ").append(isActive ? "Active" : "Inactive").append("\n");
        stats.append("Students Enrolled: ").append(enrolledStudents.size()).append("/").append(maxCapacity).append("\n");
        stats.append("Total Assignments: ").append(assignments.size()).append("\n");
        stats.append("Overall Attendance: ").append(String.format("%.2f%%", getOverallAttendancePercentage())).append("\n");
        long completedAssignments = assignments.stream().filter(a -> a.getStatus().toString().equals("GRADED")).count();
        stats.append("Completed Assignments: ").append(completedAssignments).append("/").append(assignments.size()).append("\n");
        double avgGrade = assignments.stream()
                .filter(a -> a.hasAnyGraded())
                .flatMap(a -> a.getGrades().values().stream())
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
        stats.append("Average Grade: ").append(String.format("%.2f%%", avgGrade)).append("\n");
        return stats.toString();
    }

    public String getSubmissionAnalytics() {
        StringBuilder analytics = new StringBuilder();
        analytics.append("=== Assignment Submission Analytics ===\n");
        for (Assignment assignment : assignments) {
            analytics.append("Assignment: ").append(assignment.getTitle()).append("\n");
            analytics.append("  Status: ").append(assignment.getStatus()).append("\n");
            Set<String> submittedIds = assignment.getSubmittedStudentIds();
            if (!submittedIds.isEmpty()) {
                analytics.append("  Submitted By: ").append(String.join(", ", submittedIds)).append("\n");
                if (assignment.hasAnyGraded()) {
                    for (String studentId : assignment.getGradedStudentIds()) {
                        analytics.append("    ").append(studentId).append(": Grade ").append(assignment.getGrades().get(studentId)).append("/").append(assignment.getMaxPoints()).append("\n");
                    }
                }
            } else {
                analytics.append("  Status: Not submitted\n");
            }
            analytics.append("\n");
        }
        return analytics.toString();
    }

    public void deactivate() {
        this.isActive = false;
        logger.info("Classroom deactivated: " + name);
    }

    public void activate() {
        this.isActive = true;
        logger.info("Classroom activated: " + name);
    }

    public void updateClassroomInfo(String newName, String newDescription, int newMaxCapacity) throws ValidationException {
        if (newName != null && !ValidationHelper.isValidClassroomName(newName)) throw new ValidationException("Invalid classroom name: " + newName);
        if (newMaxCapacity > 0 && newMaxCapacity < enrolledStudents.size()) throw new ValidationException("Cannot reduce capacity below current enrollment");
        if (newName != null) this.name = newName.trim();
        if (newDescription != null) this.description = newDescription.trim();
        if (newMaxCapacity > 0) this.maxCapacity = newMaxCapacity;
        logger.info("Classroom information updated: " + classroomId);
    }

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