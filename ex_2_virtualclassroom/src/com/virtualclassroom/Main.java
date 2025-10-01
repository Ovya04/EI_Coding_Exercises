package com.virtualclassroom;

import com.virtualclassroom.manager.VirtualClassroomManager;
import com.virtualclassroom.util.Logger;

public class Main {
    
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
