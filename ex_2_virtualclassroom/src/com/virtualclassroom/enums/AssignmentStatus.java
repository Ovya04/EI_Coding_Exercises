package com.virtualclassroom.enums;

public enum AssignmentStatus {
    SCHEDULED("Assignment has been scheduled but not yet submitted"),
    SUBMITTED("Assignment has been submitted by student"),
    GRADED("Assignment has been graded with feedback"),
    OVERDUE("Assignment submission deadline has passed"),
    CANCELLED("Assignment has been cancelled");
    
    private final String description;
    
    AssignmentStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return name() + " (" + description + ")";
    }
}
