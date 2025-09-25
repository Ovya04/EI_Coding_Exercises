/**
 * Assignment Category Enumeration
 * 
 * Defines different types of assignments that can be created in the system.
 * Used for categorization, filtering, and different grading strategies.
 * 
 * @author Virtual Classroom Development Team
 * @version 2.0
 * @since 2025-09-25
 */

package com.virtualclassroom.enums;

public enum AssignmentCategory {
    HOMEWORK("Regular homework assignments", 10),
    PROJECT("Long-term projects", 25),
    QUIZ("Short quizzes and assessments", 15),
    EXAM("Major examinations", 30),
    PRESENTATION("Student presentations", 20),
    RESEARCH("Research papers and reports", 25),
    LAB("Laboratory exercises", 15),
    DISCUSSION("Discussion forum participation", 5);
    
    private final String description;
    private final int defaultMaxPoints;
    
    AssignmentCategory(String description, int defaultMaxPoints) {
        this.description = description;
        this.defaultMaxPoints = defaultMaxPoints;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getDefaultMaxPoints() {
        return defaultMaxPoints;
    }
    
    @Override
    public String toString() {
        return name() + " (" + description + ")";
    }
}