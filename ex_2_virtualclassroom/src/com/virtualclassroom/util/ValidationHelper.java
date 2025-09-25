 
/**
 * Validation Helper Utility
 * 
 * Provides centralized validation methods for input data.
 * Implements defensive programming practices and data integrity checks.
 * 
 * Features:
 * - Student ID format validation
 * - Email format validation
 * - Name validation with special characters
 * - Classroom name validation
 * - Null and empty string checks
 * 
 * @author Virtual Classroom Development Team
 * @version 2.0
 * @since 2025-09-25
 */

package com.virtualclassroom.util;

import java.util.regex.Pattern;

public class ValidationHelper {
    
    // Regex patterns for validation
    private static final Pattern STUDENT_ID_PATTERN = Pattern.compile("^[A-Z]{2}\\d{4,6}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s]{2,50}$");
    private static final Pattern CLASSROOM_NAME_PATTERN = Pattern.compile("^[A-Za-z0-9\\s-_]{2,30}$");
    private static final Pattern TITLE_PATTERN = Pattern.compile("^[A-Za-z0-9\\s-_.,!?]{2,100}$");
    
    /**
     * Validate student ID format (e.g., ST1234, AB123456)
     */
    public static boolean isValidStudentId(String studentId) {
        return studentId != null && STUDENT_ID_PATTERN.matcher(studentId.trim()).matches();
    }
    
    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Validate person name (letters and spaces only)
     */
    public static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name.trim()).matches();
    }
    
    /**
     * Validate classroom name
     */
    public static boolean isValidClassroomName(String classroomName) {
        return classroomName != null && CLASSROOM_NAME_PATTERN.matcher(classroomName.trim()).matches();
    }
    
    /**
     * Validate assignment/project title
     */
    public static boolean isValidTitle(String title) {
        return title != null && TITLE_PATTERN.matcher(title.trim()).matches();
    }
    
    /**
     * Check if string is null or empty
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Validate not null with custom message
     */
    public static void validateNotNull(Object obj, String fieldName) throws com.virtualclassroom.exception.ValidationException {
        if (obj == null) {
            throw new com.virtualclassroom.exception.ValidationException("Field cannot be null: " + fieldName);
        }
    }
    
    /**
     * Validate string is not null or empty
     */
    public static void validateNotNullOrEmpty(String str, String fieldName) throws com.virtualclassroom.exception.ValidationException {
        if (isNullOrEmpty(str)) {
            throw new com.virtualclassroom.exception.ValidationException("Field cannot be null or empty: " + fieldName);
        }
    }
    
    /**
     * Validate numeric range
     */
    public static void validateRange(int value, int min, int max, String fieldName) throws com.virtualclassroom.exception.ValidationException {
        if (value < min || value > max) {
            throw new com.virtualclassroom.exception.ValidationException(
                String.format("Field %s must be between %d and %d, got: %d", fieldName, min, max, value));
        }
    }
    
    /**
     * Validate double range
     */
    public static void validateRange(double value, double min, double max, String fieldName) throws com.virtualclassroom.exception.ValidationException {
        if (value < min || value > max) {
            throw new com.virtualclassroom.exception.ValidationException(
                String.format("Field %s must be between %.2f and %.2f, got: %.2f", fieldName, min, max, value));
        }
    }
    
    /**
     * Sanitize input string (remove potentially harmful characters)
     */
    public static String sanitizeInput(String input) {
        if (input == null) return null;
        
        // Remove potentially harmful characters and trim
        return input.replaceAll("[<>\"'&]", "").trim();
    }
    
    /**
     * Validate string length
     */
    public static void validateLength(String str, int minLength, int maxLength, String fieldName) throws com.virtualclassroom.exception.ValidationException {
        if (str == null) {
            throw new com.virtualclassroom.exception.ValidationException("Field cannot be null: " + fieldName);
        }
        
        int length = str.trim().length();
        if (length < minLength || length > maxLength) {
            throw new com.virtualclassroom.exception.ValidationException(
                String.format("Field %s length must be between %d and %d characters, got: %d", 
                            fieldName, minLength, maxLength, length));
        }
    }
    
    /**
     * Generate a safe student ID suggestion
     */
    public static String generateStudentIdSuggestion(String name) {
        if (isNullOrEmpty(name)) {
            return "ST" + String.format("%04d", (int)(Math.random() * 10000));
        }
        
        String cleanName = name.replaceAll("[^A-Za-z]", "").toUpperCase();
        String prefix = cleanName.length() >= 2 ? cleanName.substring(0, 2) : "ST";
        int suffix = (int)(Math.random() * 10000);
        
        return prefix + String.format("%04d", suffix);
    }
    
    /**
     * Mask sensitive information (for logging)
     */
    public static String maskEmail(String email) {
        if (isNullOrEmpty(email) || !email.contains("@")) {
            return "***";
        }
        
        String[] parts = email.split("@");
        String username = parts[0];
        String domain = parts[1];
        
        if (username.length() <= 3) {
            return "***@" + domain;
        }
        
        return username.substring(0, 2) + "***@" + domain;
    }
}