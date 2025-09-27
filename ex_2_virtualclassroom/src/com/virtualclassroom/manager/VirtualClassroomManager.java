package com.virtualclassroom.manager;

import com.virtualclassroom.entity.*;
import com.virtualclassroom.util.*;
import com.virtualclassroom.exception.*;

import java.util.*;
import java.util.stream.Collectors;

public class VirtualClassroomManager {

    private final Map<String, Classroom> classrooms;
    private final Map<String, Student> students;
    private final List<String> notifications;
    private final Scanner scanner;
    private final Logger logger;
    private boolean isRunning;
    private static final String WELCOME_MESSAGE = """
            ╔══════════════════════════════════════════════════════════════╗
            ║                 Virtual Classroom Manager v3.0               ║
            ║                         (Date-Free)                          ║
            ╚══════════════════════════════════════════════════════════════╝
            """;

    private static final String MENU_OPTIONS = """
            
            ═══════════════════ AVAILABLE COMMANDS ════════════════════════
            
            📚 CLASSROOM MANAGEMENT:
               add_classroom <name> [description] [capacity]
               remove_classroom <name>
               list_classrooms [page] [pageSize] [filter]
               classroom_details <name>
            
            👥 STUDENT MANAGEMENT:
               add_student <studentId> <name> <email> <className>
               remove_student <studentId> <className>
               list_students <className> [page] [pageSize] [filter]
               student_profile <studentId>
            
            📝 ASSIGNMENT MANAGEMENT:
               schedule_assignment <className> <title> <description> [maxPoints]
               submit_assignment <studentId> <className> <assignmentTitle> [fileName]
               list_assignments <className>
               grade_assignment <className> <assignmentTitle> <studentId> <grade> [feedback]
            
            📋 ATTENDANCE MANAGEMENT:
               mark_attendance <className> <studentId:present/absent> [...]
               view_attendance <className>
               attendance_report <className>
            
            🔔 NOTIFICATIONS & ANALYTICS:
               notify_grades <className>
               classroom_analytics <className>
               submission_analytics <className>
               student_progress <studentId>
            
            ⚙️  SYSTEM COMMANDS:
               help - Show this menu
               exit - Exit the application
               clear - Clear screen
               debug [on/off] - Toggle debug logging
            
            ═══════════════════════════════════════════════════════════════
            """;

    public VirtualClassroomManager() {
        this.classrooms = new HashMap<>();
        this.students = new HashMap<>();
        this.notifications = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.logger = Logger.getInstance();
        this.isRunning = false;
        logger.info("Virtual Classroom Manager initialized");
    }

    public void start() {
        logger.info("Starting Virtual Classroom Manager");
        System.out.println(WELCOME_MESSAGE);
        System.out.println("Welcome! Type 'help' to see available commands.");

        isRunning = true;

        while (isRunning) {
            try {
                System.out.print("\n🎓 VCM > ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    continue;
                }

                processCommand(input);

            } catch (Exception e) {
                logger.error("Unexpected error in main loop", e);
                System.out.println("❌ An unexpected error occurred. Please try again.");
            }
        }

        cleanup();
    }

    private void processCommand(String input) {
        String[] parts = input.split("\\s+");
        String command = parts[0].toLowerCase();

        try {
            switch (command) {
                case "add_classroom" -> handleAddClassroom(parts);
                case "remove_classroom" -> handleRemoveClassroom(parts);
                case "list_classrooms" -> handleListClassrooms(parts);
                case "classroom_details" -> handleClassroomDetails(parts);

                case "add_student" -> handleAddStudent(parts);
                case "remove_student" -> handleRemoveStudent(parts);
                case "list_students" -> handleListStudents(parts);
                case "student_profile" -> handleStudentProfile(parts);

                case "schedule_assignment" -> handleScheduleAssignment(parts);
                case "submit_assignment" -> handleSubmitAssignment(parts);
                case "list_assignments" -> handleListAssignments(parts);
                case "grade_assignment" -> handleGradeAssignment(parts);

                case "mark_attendance" -> handleMarkAttendance(parts);
                case "view_attendance" -> handleViewAttendance(parts);
                case "attendance_report" -> handleAttendanceReport(parts);

                case "notify_grades" -> handleNotifyGrades(parts);
                case "classroom_analytics" -> handleClassroomAnalytics(parts);
                case "submission_analytics" -> handleSubmissionAnalytics(parts);
                case "student_progress" -> handleStudentProgress(parts);

                case "help" -> System.out.println(MENU_OPTIONS);
                case "exit" -> handleExit();
                case "clear" -> clearScreen();
                case "debug" -> handleDebugToggle(parts);

                default -> System.out.println("❌ Unknown command: " + command + ". Type 'help' for available commands.");
            }

        } catch (ValidationException e) {
            System.out.println("❌ Validation Error: " + e.getMessage());
            logger.warn("Validation error: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("❌ Invalid command format. Type 'help' for correct usage.");
            logger.warn("Invalid command format for: " + command);
        } catch (Exception e) {
            System.out.println("❌ Error executing command: " + e.getMessage());
            logger.error("Error executing command: " + command, e);
        }
    }

    // ==================== CLASSROOM MANAGEMENT METHODS ====================

    private void handleAddClassroom(String[] parts) throws ValidationException {
        if (parts.length < 2) {
            System.out.println("Usage: add_classroom <name> [description] [capacity]");
            return;
        }
        String name = parts[1];
        String description = parts.length > 2 ? parts[2] : "Default classroom";
        int capacity = parts.length > 3 ? Integer.parseInt(parts[3]) : 50;
        if (classrooms.containsKey(name)) {
            throw new ValidationException("Classroom already exists: " + name);
        }
        Classroom classroom = new Classroom(name, description, capacity);
        classrooms.put(name, classroom);
        System.out.println("✅ Classroom " + name + " has been created.");
        logger.info("Classroom created: " + name);
    }
    
    private void handleRemoveClassroom(String[] parts) throws ValidationException {
        if (parts.length < 2) {
            System.out.println("Usage: remove_classroom <name>");
            return;
        }
        String name = parts[1];
        Classroom classroom = classrooms.get(name);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + name);
        }
        List<Student> enrolledStudents = classroom.getAllStudents();
        if (!enrolledStudents.isEmpty()) {
            System.out.println("⚠️  Classroom has " + enrolledStudents.size() + " enrolled students.");
            System.out.print("Do you want to proceed? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();
            if (!confirmation.equals("yes")) {
                System.out.println("❌ Operation cancelled.");
                return;
            }
            for (Student student : enrolledStudents) {
                classroom.removeStudent(student.getStudentId());
            }
        }
        classroom.deactivate();
        classrooms.remove(name);
        System.out.println("✅ Classroom " + name + " has been removed.");
        logger.info("Classroom removed: " + name);
    }
    
    private void handleListClassrooms(String[] parts) {
        int page = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
        int pageSize = parts.length > 2 ? Integer.parseInt(parts[2]) : 10;
        String filter = parts.length > 3 ? parts[3] : null;
        List<Classroom> classroomList = new ArrayList<>(classrooms.values());
        if (filter != null && !filter.trim().isEmpty()) {
            String filterLower = filter.toLowerCase();
            classroomList = classroomList.stream()
                    .filter(c -> c.getName().toLowerCase().contains(filterLower) ||
                               c.getDescription().toLowerCase().contains(filterLower))
                    .collect(Collectors.toList());
        }
        int start = page * pageSize;
        int end = Math.min(start + pageSize, classroomList.size());
        if (start >= classroomList.size()) {
            System.out.println("📄 No classrooms found on page " + page);
            return;
        }
        System.out.println("\n📚 CLASSROOM LIST (Page " + (page + 1) + ")");
        System.out.println("═".repeat(80));
        for (int i = start; i < end; i++) {
            Classroom classroom = classroomList.get(i);
            System.out.printf("🏫 %-20s | Students: %2d/%-2d | Assignments: %2d | %s%n",
                            classroom.getName(),
                            classroom.getEnrolledStudentCount(),
                            classroom.getMaxCapacity(),
                            classroom.getAssignmentCount(),
                            classroom.isActive() ? "Active" : "Inactive");
        }
        System.out.println("═".repeat(80));
        System.out.println("Showing " + (end - start) + " of " + classroomList.size() + " total classrooms");
    }
    
    private void handleClassroomDetails(String[] parts) throws ValidationException {
        if (parts.length < 2) {
            System.out.println("Usage: classroom_details <name>");
            return;
        }
        String name = parts[1];
        Classroom classroom = classrooms.get(name);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + name);
        }
        System.out.println("\n" + classroom.getClassroomStatistics());
        System.out.println(classroom.getSubmissionAnalytics());
        // Attendance summary
        Map<String, Boolean> attendance = classroom.getAttendance();
        long presentCount = attendance.values().stream().filter(p -> p).count();
        long absentCount = classroom.getEnrolledStudentCount() - presentCount;
        System.out.println("Attendance Summary: Present - " + presentCount + ", Absent - " + absentCount);
    }

    // ==================== STUDENT MANAGEMENT METHODS ====================

    private void handleAddStudent(String[] parts) throws ValidationException {
        if (parts.length < 5) {
            System.out.println("Usage: add_student <studentId> <name> <email> <className>");
            return;
        }
        String studentId = parts[1];
        String name = parts[2];
        String email = parts[3];
        String className = parts[4];
        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + className);
        }
        if (students.containsKey(studentId)) {
            Student existingStudent = students.get(studentId);
            classroom.addStudent(existingStudent);
        } else {
            Student student = new Student.Builder()
                    .setStudentId(studentId)
                    .setName(name)
                    .setEmail(email)
                    .build();
            students.put(studentId, student);
            classroom.addStudent(student);
        }
        System.out.println("✅ Student " + studentId + " has been enrolled in " + className + ".");
        logger.info("Student enrolled: " + studentId + " in " + className);
        notifications.add("📧 Welcome email sent to " + ValidationHelper.maskEmail(email));
    }
    
    private void handleRemoveStudent(String[] parts) throws ValidationException {
        if (parts.length < 3) {
            System.out.println("Usage: remove_student <studentId> <className>");
            return;
        }
        String studentId = parts[1];
        String className = parts[2];
        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + className);
        }
        if (!classroom.hasStudent(studentId)) {
            throw new ValidationException("Student not enrolled in classroom: " + studentId);
        }
        List<Assignment> pendingAssignments = classroom.getAllAssignments().stream()
                .filter(a -> !a.hasSubmitted(studentId))
                .collect(Collectors.toList());
        if (!pendingAssignments.isEmpty()) {
            System.out.println("⚠️  Student has " + pendingAssignments.size() + " pending assignments.");
            System.out.print("Do you want to proceed? (yes/no): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();
            if (!confirmation.equals("yes")) {
                System.out.println("❌ Operation cancelled.");
                return;
            }
        }
        classroom.removeStudent(studentId);
        System.out.println("✅ Student " + studentId + " has been removed from " + className + ".");
        logger.info("Student removed: " + studentId + " from " + className);
    }
    
    private void handleListStudents(String[] parts) throws ValidationException {
        if (parts.length < 2) {
            System.out.println("Usage: list_students <className> [page] [pageSize] [filter]");
            return;
        }
        String className = parts[1];
        int page = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
        int pageSize = parts.length > 3 ? Integer.parseInt(parts[3]) : 10;
        String filter = parts.length > 4 ? parts[4] : null;
        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + className);
        }
        List<Student> studentList = classroom.getStudents(page, pageSize, filter);
        if (studentList.isEmpty()) {
            System.out.println("📄 No students found matching criteria");
            return;
        }
        System.out.println("\n👥 STUDENT LIST - " + className + " (Page " + (page + 1) + ")");
        System.out.println("═".repeat(90));
        for (Student student : studentList) {
            System.out.printf("🎓 %-12s | %-20s | Avg Grade: %5.1f%% | Attendance: %5.1f%%%n",
                            student.getStudentId(),
                            student.getName(),
                            student.getGradeAverage(),
                            student.getAttendancePercentage());
        }
        System.out.println("═".repeat(90));
        System.out.println("Showing " + studentList.size() + " students");
    }
    
    private void handleStudentProfile(String[] parts) throws ValidationException {
        if (parts.length < 2) {
            System.out.println("Usage: student_profile <studentId>");
            return;
        }
        String studentId = parts[1];
        Student student = students.get(studentId);
        if (student == null) {
            throw new ValidationException("Student not found: " + studentId);
        }
        System.out.println("\n" + student.getStudentProfile());
        System.out.println("📚 Enrolled Classrooms:");
        for (String classroomName : student.getEnrolledClassrooms()) {
            Classroom classroom = classrooms.get(classroomName);
            if (classroom != null) {
                List<Assignment> assignments = student.getSubmittedAssignments(classroomName);
                System.out.println("  - " + classroomName + " (" + assignments.size() + " assignments submitted)");
            }
        }
    }

    // ==================== ASSIGNMENT MANAGEMENT METHODS ====================

    private void handleScheduleAssignment(String[] parts) throws ValidationException {
        if (parts.length < 4) {
            System.out.println("Usage: schedule_assignment <className> <title> <description> [maxPoints]");
            return;
        }

        String className = parts[1];
        String title = parts[2];
        String description = parts[3];
        int maxPoints = parts.length > 4 ? Integer.parseInt(parts[4]) : 100;

        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + className);
        }

        Assignment assignment = new Assignment.Builder()
                .setTitle(title)
                .setDescription(description)
                .setMaxPoints(maxPoints)
                .build();

        classroom.scheduleAssignment(assignment);

        System.out.println("✅ Assignment for " + className + " has been scheduled.");
        System.out.println("📝 Title: " + title);
        System.out.println("💯 Max Points: " + maxPoints);
        logger.info("Assignment scheduled: " + title + " for " + className);
    }

    private void handleSubmitAssignment(String[] parts) throws ValidationException {
        if (parts.length < 4) {
            System.out.println("Usage: submit_assignment <studentId> <className> <assignmentTitle> [fileName]");
            return;
        }

        String studentId = parts[1];
        String className = parts[2];
        String assignmentTitle = parts[3];
        String fileName = parts.length > 4 ? parts[4] : "submission.pdf";

        Student student = students.get(studentId);
        if (student == null) {
            throw new ValidationException("Student not found: " + studentId);
        }

        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + className);
        }

        Assignment assignment = classroom.findAssignmentByTitle(assignmentTitle);
        if (assignment == null) {
            throw new ValidationException("Assignment not found: " + assignmentTitle);
        }

        if (assignment.hasSubmitted(studentId)) {
            throw new ValidationException("Student has already submitted this assignment.");
        }

        assignment.addSubmittedFile(studentId, fileName);
        assignment.markAsSubmitted(studentId); 
        student.submitAssignment(className, assignment); 

        System.out.println("✅ Assignment submitted by Student " + studentId + " in " + className + ".");
        System.out.println("📝 Assignment: " + assignmentTitle);
        logger.info("Assignment submitted: " + assignmentTitle + " by " + studentId);
    }

    private void handleListAssignments(String[] parts) throws ValidationException {
        if (parts.length < 2) {
            System.out.println("Usage: list_assignments <className>");
            return;
        }

        String className = parts[1];
        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + className);
        }

        List<Assignment> assignments = classroom.getAssignments(false);
        if (assignments.isEmpty()) {
            System.out.println("📄 No assignments found for " + className);
            return;
        }

        System.out.println("\n📝 ASSIGNMENT LIST - " + className);
        System.out.println("═".repeat(80));
        System.out.printf("📚 %-40s | %-15s | %s%n", "TITLE", "STATUS", "SUBMITTED BY");
        System.out.println("─".repeat(80));

        for (Assignment assignment : assignments) {
            String submittedBy = assignment.getSubmittedStudentIds().isEmpty() ? "N/A"
                : String.join(", ", assignment.getSubmittedStudentIds());
            System.out.printf("  %-40s | %-15s | %s%n",
                    assignment.getTitle(),
                    assignment.getStatus().name(),
                    submittedBy);
        }
        System.out.println("═".repeat(80));
    }

    private void handleGradeAssignment(String[] parts) throws ValidationException {
        if (parts.length < 5) {
            System.out.println("Usage: grade_assignment <className> <assignmentTitle> <studentId> <grade> [feedback]");
            return;
        }
        String className = parts[1];
        String assignmentTitle = parts[2];
        String studentId = parts[3];
        double grade = Double.parseDouble(parts[4]);
        String feedback = parts.length > 5 ? String.join(" ", Arrays.copyOfRange(parts, 5, parts.length)) : "Good work!";
        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + className);
        }
        Assignment assignment = classroom.findAssignmentByTitle(assignmentTitle);
        if (assignment == null) {
            throw new ValidationException("Assignment not found: " + assignmentTitle);
        }
        if (!assignment.hasSubmitted(studentId)) {
            throw new ValidationException("Student " + studentId + " has not submitted this assignment.");
        }
        assignment.gradeAssignment(studentId, grade, feedback);
        System.out.println("✅ Assignment graded successfully!");
        System.out.println("📝 Assignment: " + assignmentTitle);
        System.out.println("💯 Grade: " + grade + "/" + assignment.getMaxPoints() + " (" + assignment.getGradePercentage(studentId) + "%)");
        System.out.println("🏆 Letter Grade: " + assignment.getLetterGrade(studentId));
        System.out.println("💬 Feedback: " + feedback);
        notifications.add("📧 Grade notification sent to " + studentId + " for " + assignmentTitle);
        logger.info("Assignment graded: " + assignmentTitle + " - " + grade + "/" + assignment.getMaxPoints());
    }

    // ==================== ATTENDANCE MANAGEMENT METHODS ====================

    private void handleMarkAttendance(String[] parts) throws ValidationException {
        if (parts.length < 3) {
            System.out.println("Usage: mark_attendance <className> <studentId:present/absent> [...]");
            System.out.println("Example: mark_attendance Math101 ST1234:present ST5678:absent");
            return;
        }
        String className = parts[1];
        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + className);
        }
        // Mark all students absent by default, then update those present
        Map<String, Boolean> attendanceData = new HashMap<>();
        for (String studentId : classroom.getAllStudents().stream().map(Student::getStudentId).toList()) {
            attendanceData.put(studentId, false); // default absent
        }
        for (int i = 2; i < parts.length; i++) {
            String[] attendanceEntry = parts[i].split(":");
            if (attendanceEntry.length != 2) {
                System.out.println("⚠️  Invalid format for: " + parts[i] + ". Skipping...");
                continue;
            }
            String studentId = attendanceEntry[0];
            boolean isPresent = attendanceEntry[1].equalsIgnoreCase("present");
            if (!classroom.hasStudent(studentId)) {
                System.out.println("⚠️  Student " + studentId + " not enrolled in " + className + ". Skipping...");
                continue;
            }
            attendanceData.put(studentId, isPresent);
        }
        classroom.markAttendance(attendanceData);
        System.out.println("✅ Attendance marked for " + className);
        System.out.println("📊 Summary:");
        long presentCount = attendanceData.values().stream().filter(present -> present).count();
        long absentCount = classroom.getEnrolledStudentCount() - presentCount;
        System.out.println("  Present: " + presentCount);
        System.out.println("  Absent: " + absentCount);
        attendanceData.entrySet().stream()
                .filter(entry -> !entry.getValue())
                .forEach(entry -> {
                    notifications.add("📧 Absence notification sent for student " + entry.getKey());
                });
        logger.info("Attendance marked for " + className + " - " + presentCount + " present, " + absentCount + " absent");
    }
    
    private void handleViewAttendance(String[] parts) throws ValidationException {
        if (parts.length < 2) {
            System.out.println("Usage: view_attendance <className>");
            return;
        }
        String className = parts[1];
        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + className);
        }
        System.out.println("\n📊 ATTENDANCE OVERVIEW - " + className);
        System.out.println("═".repeat(70));
        System.out.println("Overall Attendance Rate: " + String.format("%.2f%%", classroom.getOverallAttendancePercentage()));
        for (Student student : classroom.getAllStudents()) {
            System.out.printf("🎓 %-12s - %5.1f%% attendance%n", 
                            student.getStudentId(), 
                            student.getAttendancePercentage());
        }
    }
    
    private void handleAttendanceReport(String[] parts) throws ValidationException {
        if (parts.length < 2) {
            System.out.println("Usage: attendance_report <className>");
            return;
        }
        String className = parts[1];
        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + className);
        }
        System.out.println("\n📊 COMPREHENSIVE ATTENDANCE REPORT");
        System.out.println("🏫 Classroom: " + className);
        System.out.println("═".repeat(80));
        double overallRate = classroom.getOverallAttendancePercentage();
        System.out.println("📈 Overall Attendance Rate: " + String.format("%.2f%%", overallRate));
        List<Student> students = classroom.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("📄 No students enrolled in this classroom.");
            return;
        }
        students.sort(Comparator.comparingDouble(Student::getAttendancePercentage).reversed());
        System.out.println("\n👥 INDIVIDUAL ATTENDANCE RATES:");
        System.out.println("─".repeat(80));
        for (Student student : students) {
            double rate = student.getAttendancePercentage();
            String indicator = rate >= 90 ? "🟢" : rate >= 75 ? "🟡" : "🔴";
            System.out.printf("%s %-12s | %-20s | %5.1f%% | %s%n",
                            indicator,
                            student.getStudentId(),
                            student.getName(),
                            rate,
                            getAttendanceCategory(rate));
        }
        System.out.println("─".repeat(80));
        System.out.println("🟢 Excellent (90%+)  🟡 Good (75-89%)  🔴 Needs Improvement (<75%)");
    }

    // ==================== NOTIFICATION AND ANALYTICS METHODS ====================

    private void handleNotifyGrades(String[] parts) throws ValidationException {
        if (parts.length < 2) {
            System.out.println("Usage: notify_grades <className>");
            return;
        }
        String className = parts[1];
        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + className);
        }
        List<Assignment> gradedAssignments = classroom.getAllAssignments().stream()
                .filter(a -> a.hasAnyGraded())
                .collect(Collectors.toList());
        if (gradedAssignments.isEmpty()) {
            System.out.println("📄 No graded assignments found for " + className);
            return;
        }
        System.out.println("🔔 GRADE PUBLICATION NOTIFICATIONS - " + className);
        System.out.println("═".repeat(80));
        for (Assignment assignment : gradedAssignments) {
            for (String studentId : assignment.getGradedStudentIds()) {
                Double gradeValue = assignment.getGrades().get(studentId);
                System.out.println("📝 " + assignment.getTitle());
                System.out.println("  👤 Student: " + studentId);
                System.out.println("  💯 Grade: " + (gradeValue != null ? gradeValue : "N/A") + "/" + assignment.getMaxPoints() +
                                 " (" + assignment.getGradePercentage(studentId) + "%)");
                System.out.println("  🏆 Letter Grade: " + assignment.getLetterGrade(studentId));
                System.out.println();
                notifications.add("📧 Grade notification sent to " + studentId + " for " + assignment.getTitle());
            }
        }
        System.out.println("✅ Grade notifications sent for all graded assignments");
        logger.info("Grade notifications sent for " + className + " - " + gradedAssignments.size() + " assignments");
    }
    
    private void handleClassroomAnalytics(String[] parts) throws ValidationException {
        if (parts.length < 2) {
            System.out.println("Usage: classroom_analytics <className>");
            return;
        }
        String className = parts[1];
        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + className);
        }
        System.out.println(classroom.getClassroomStatistics());
        List<Assignment> assignments = classroom.getAllAssignments();
        if (!assignments.isEmpty()) {
            List<Assignment> gradedAssignments = assignments.stream()
                    .filter(a -> a.hasAnyGraded())
                    .collect(Collectors.toList());
            if (!gradedAssignments.isEmpty()) {
                System.out.println("\n🏆 GRADE DISTRIBUTION:");
                Map<String, Long> gradeDistribution = new HashMap<>();
                for (Assignment assignment : gradedAssignments) {
                    for (String studentId : assignment.getGradedStudentIds()) {
                        String letter = assignment.getLetterGrade(studentId);
                        gradeDistribution.put(letter, gradeDistribution.getOrDefault(letter, 0L) + 1);
                    }
                }
                for (Map.Entry<String, Long> entry : gradeDistribution.entrySet()) {
                    System.out.println("  Grade " + entry.getKey() + ": " + entry.getValue() + " students");
                }
            }
        }
    }
    
    private void handleSubmissionAnalytics(String[] parts) throws ValidationException {
        if (parts.length < 2) {
            System.out.println("Usage: submission_analytics <className>");
            return;
        }
        String className = parts[1];
        Classroom classroom = classrooms.get(className);
        if (classroom == null) {
            throw new ValidationException("Classroom not found: " + className);
        }
        System.out.println(classroom.getSubmissionAnalytics());
        List<Assignment> assignments = classroom.getAllAssignments();
        if (!assignments.isEmpty()) {
            long submittedCount = assignments.stream()
                    .mapToLong(a -> a.getSubmittedStudentIds().size())
                    .sum();
            double submissionRate = assignments.size() > 0 ? (double) submittedCount / (assignments.size() * classroom.getEnrolledStudentCount()) * 100 : 0.0;
            System.out.println("📈 SUBMISSION STATISTICS:");
            System.out.println("  Total Assignments: " + assignments.size());
            System.out.println("  Total Submissions: " + submittedCount);
            System.out.println("  Submission Rate: " + String.format("%.1f%%", submissionRate));
        }
    }
    
    private void handleStudentProgress(String[] parts) throws ValidationException {
        if (parts.length < 2) {
            System.out.println("Usage: student_progress <studentId>");
            return;
        }
        String studentId = parts[1];
        Student student = students.get(studentId);
        if (student == null) {
            throw new ValidationException("Student not found: " + studentId);
        }
        System.out.println("\n📊 STUDENT PROGRESS DASHBOARD");
        System.out.println("👤 Student: " + student.getName() + " (" + studentId + ")");
        System.out.println("═".repeat(80));
        Map<String, List<Assignment>> allSubmissions = student.getAllSubmittedAssignments();
        int totalSubmissions = allSubmissions.values().stream()
                .mapToInt(List::size)
                .sum();
        System.out.println("📚 Enrolled Classrooms: " + student.getEnrolledClassrooms().size());
        System.out.println("📝 Total Assignments Submitted: " + totalSubmissions);
        System.out.println("💯 Overall Grade Average: " + String.format("%.2f", student.getGradeAverage()));
        System.out.println("📋 Attendance Rate: " + String.format("%.2f%%", student.getAttendancePercentage()));
        System.out.println("\n🏫 PERFORMANCE BY CLASSROOM:");
        System.out.println("─".repeat(80));
        for (String classroomName : student.getEnrolledClassrooms()) {
            List<Assignment> classroomAssignments = student.getSubmittedAssignments(classroomName);
            if (!classroomAssignments.isEmpty()) {
                double avgGrade = classroomAssignments.stream()
                        .mapToDouble(a -> a.getGradePercentage(studentId))
                        .average()
                        .orElse(0.0);
                System.out.printf("📖 %-20s | %2d assignments | Avg: %5.1f%%%n",
                                classroomName, classroomAssignments.size(), avgGrade);
            }
        }
    }

    // ==================== SYSTEM COMMAND METHODS ====================

    private void handleExit() {
        System.out.println("👋 Thank you for using Virtual Classroom Manager!");
        System.out.println("📊 Session Summary:");
        System.out.println("  Classrooms: " + classrooms.size());
        System.out.println("  Students: " + students.size());
        System.out.println("  Notifications: " + notifications.size());
        logger.info("Application shutting down gracefully");
        isRunning = false;
    }
    
    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[2J\033[H");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
        System.out.println(WELCOME_MESSAGE);
    }
    
    private void handleDebugToggle(String[] parts) {
        boolean enableDebug = parts.length > 1 && parts[1].equalsIgnoreCase("on");
        if (enableDebug) {
            logger.setLogLevel(Logger.LogLevel.DEBUG);
            System.out.println("🐛 Debug logging enabled");
        } else {
            logger.setLogLevel(Logger.LogLevel.INFO);
            System.out.println("ℹ️  Debug logging disabled");
        }
    }

    // ==================== HELPER METHODS ====================

    private String getAttendanceCategory(double rate) {
        if (rate >= 90) return "Excellent";
        if (rate >= 75) return "Good";
        if (rate >= 60) return "Fair";
        return "Needs Improvement";
    }
    
    private void cleanup() {
        if (scanner != null) {
            scanner.close();
        }
        if (!notifications.isEmpty()) {
            System.out.println("\n🔔 SYSTEM NOTIFICATIONS:");
            notifications.forEach(System.out::println);
        }
        logger.info("Virtual Classroom Manager shutdown completed");
    }
}