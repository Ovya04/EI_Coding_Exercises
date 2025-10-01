package com.virtualclassroom.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance;
    private static final Object lock = new Object();
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LogLevel currentLogLevel = LogLevel.INFO;
    
    public enum LogLevel {
        DEBUG(0), INFO(1), WARN(2), ERROR(3);
        
        private final int level;
        
        LogLevel(int level) {
            this.level = level;
        }
        
        public int getLevel() {
            return level;
        }
    }
    
    private Logger() {}
    
    public static Logger getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }
    
    public void setLogLevel(LogLevel level) {
        this.currentLogLevel = level;
    }
    
    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }
    
    public void info(String message) {
        log(LogLevel.INFO, message);
    }
    
    public void warn(String message) {
        log(LogLevel.WARN, message);
    }
    
    public void error(String message) {
        log(LogLevel.ERROR, message);
    }
    
    public void error(String message, Throwable throwable) {
        log(LogLevel.ERROR, message + " - Exception: " + throwable.getMessage());
    }
    
    private synchronized void log(LogLevel level, String message) {
        if (level.getLevel() >= currentLogLevel.getLevel()) {
            String timestamp = LocalDateTime.now().format(formatter);
            String logEntry = String.format("[%s] %s: %s", timestamp, level.name(), message);
            
        
            System.out.println(logEntry);
        }
    }
    
    public void logMethodEntry(String className, String methodName) {
        debug("Entering method: " + className + "." + methodName);
    }
    
    public void logMethodExit(String className, String methodName) {
        debug("Exiting method: " + className + "." + methodName);
    }
    
    public void logPerformance(String operation, long durationMs) {
        info("Performance: " + operation + " took " + durationMs + "ms");
    }
}
