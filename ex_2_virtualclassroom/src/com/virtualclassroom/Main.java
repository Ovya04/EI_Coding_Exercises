 
/**
 * Virtual Classroom Manager - Main Entry Point
 * 
 * This is the main class that starts the Virtual Classroom Manager application.
 * It initializes the VirtualClassroomManager and starts the command-line interface.
 * 
 * Features:
 * - Simple entry point following separation of concerns
 * - Exception handling for application startup
 * - Clean shutdown mechanism
 * 
 * @author Virtual Classroom Development Team
 * @version 2.0
 * @since 2025-09-25
 */

package com.virtualclassroom;

import com.virtualclassroom.manager.VirtualClassroomManager;
import com.virtualclassroom.util.Logger;

public class Main {
    
    /**
     * Main method - Entry point of the application
     * 
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        
        try {
            logger.info("=== Virtual Classroom Manager Starting ===");
            
            // Initialize and start the Virtual Classroom Manager
            VirtualClassroomManager manager = new VirtualClassroomManager();
            manager.start();
            
            logger.info("=== Virtual Classroom Manager Shutting Down ===");
            
        } catch (Exception e) {
            logger.error("Fatal error occurred during application startup: " + e.getMessage());
            System.err.println("Application failed to start. Check logs for details.");
            System.exit(1);
        }
    }
}