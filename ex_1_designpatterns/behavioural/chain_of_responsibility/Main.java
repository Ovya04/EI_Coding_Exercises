package ex_1_designpatterns.behavioural.chain_of_responsibility;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static int ticketCounter = 1001;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueProcessing = true;
        
        System.out.println("=".repeat(60));
        System.out.println("🎫 WELCOME TO CHAIN OF RESPONSIBILITY SUPPORT SYSTEM 🎫");
        System.out.println("=".repeat(60));
        System.out.println("Learn how Chain of Responsibility Pattern handles requests!");
        System.out.println();
        
        // Set up the chain of responsibility
        SupportHandler level1 = new Level1Support();
        SupportHandler level2 = new Level2Support();
        SupportHandler level3 = new Level3Support();
        
        level1.setNextHandler(level2);
        level2.setNextHandler(level3);
        
        System.out.println("🔗 Support Chain Established:");
        System.out.println("   Level 1 → Level 2 → Level 3");
        System.out.println();
        
        logger.info("Chain of Responsibility Support System started");
        
        do {
            try {
                System.out.println("🆕 CREATE NEW SUPPORT TICKET");
                System.out.println("-".repeat(30));
                
                // Customer name
                System.out.print("👤 Enter customer name: ");
                String customerName = scanner.nextLine().trim();
                if (customerName.isEmpty()) {
                    customerName = "Anonymous Customer";
                }
                
                // Issue description
                System.out.print("📝 Describe the issue: ");
                String issue = scanner.nextLine().trim();
                if (issue.isEmpty()) {
                    issue = "General inquiry";
                }
                
                // Priority selection
                System.out.println("\n🎯 Select priority level:");
                System.out.println("1. 🟢 Low Priority (Basic questions, how-to queries)");
                System.out.println("2. 🟡 Medium Priority (Configuration issues, feature requests)");
                System.out.println("3. 🟠 High Priority (Service disruption, urgent issues)");
                System.out.println("4. 🔴 Critical Priority (System down, security breach)");
                System.out.print("Enter choice (1-4): ");
                
                int priorityChoice = getValidIntInput(scanner, 1, 4);
                SupportTicket.Priority priority = getPriorityFromChoice(priorityChoice);
                
                // Create ticket
                String ticketId = String.valueOf(ticketCounter++);
                SupportTicket ticket = new SupportTicket(ticketId, customerName, issue, priority);
                
                System.out.println("\n🎫 TICKET CREATED SUCCESSFULLY!");
                System.out.println("=".repeat(50));
                System.out.println(ticket);
                System.out.println("=".repeat(50));
                
                logger.info("Support ticket created: " + ticket.toString());
                
                System.out.println("\n🔄 PROCESSING THROUGH CHAIN OF RESPONSIBILITY...");
                System.out.println();
                
                // Process through chain
                long startTime = System.currentTimeMillis();
                level1.handleRequest(ticket);
                long endTime = System.currentTimeMillis();
                
                System.out.println("✅ TICKET PROCESSING COMPLETE!");
                System.out.println("⏱️ Total Processing Time: " + (endTime - startTime) + "ms");
                
                displayPatternExplanation(priority);
                
                continueProcessing = getYesNoInput(scanner, "\n🔄 Submit another ticket? [y/n]: ");
                
            } catch (Exception e) {
                logger.severe("Error during ticket processing: " + e.getMessage());
                System.out.println("❌ An error occurred. Please try again.");
                continueProcessing = getYesNoInput(scanner, "Continue with new ticket? [y/n]: ");
            }
            
        } while (continueProcessing);
        
        System.out.println("\n🙏 Thank you for learning Chain of Responsibility Pattern!");
        System.out.println("👨‍🏫 Key Takeaway: Chain Pattern passes requests until someone can handle it!");
        logger.info("Chain of Responsibility Support System ended");
        scanner.close();
    }
    
    private static int getValidIntInput(Scanner scanner, int min, int max) {
        int attempts = 0;
        final int maxAttempts = 3;
        
        while (attempts < maxAttempts) {
            try {
                if (scanner.hasNextInt()) {
                    int input = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (input >= min && input <= max) {
                        return input;
                    } else {
                        System.out.print("❌ Please enter a number between " + min + " and " + max + ": ");
                    }
                } else {
                    System.out.print("❌ Please enter a valid number: ");
                    scanner.next();
                }
                attempts++;
            } catch (Exception e) {
                logger.warning("Invalid input: " + e.getMessage());
                attempts++;
                scanner.nextLine();
            }
        }
        
        logger.warning("Max attempts exceeded, using default value");
        return 2; // Default to medium priority
    }
    
    private static boolean getYesNoInput(Scanner scanner, String prompt) {
        int attempts = 0;
        final int maxAttempts = 3;
        
        while (attempts < maxAttempts) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim().toLowerCase();
                
                if (input.startsWith("y")) return true;
                if (input.startsWith("n")) return false;
                
                System.out.println("❌ Please enter 'y' for yes or 'n' for no.");
                attempts++;
            } catch (Exception e) {
                logger.warning("Error during input: " + e.getMessage());
                attempts++;
            }
        }
        
        return false;
    }
    
    private static SupportTicket.Priority getPriorityFromChoice(int choice) {
        switch (choice) {
            case 1: return SupportTicket.Priority.LOW;
            case 2: return SupportTicket.Priority.MEDIUM;
            case 3: return SupportTicket.Priority.HIGH;
            case 4: return SupportTicket.Priority.CRITICAL;
            default: return SupportTicket.Priority.MEDIUM;
        }
    }
    
    private static void displayPatternExplanation(SupportTicket.Priority priority) {
        System.out.println("\n🔍 Chain of Responsibility Pattern Explanation:");
        System.out.println("   • Request entered the chain at Level 1 Support");
        switch (priority) {
            case LOW:
                System.out.println("   • Level 1 could handle LOW priority - chain stopped");
                System.out.println("   • No escalation needed!");
                break;
            case MEDIUM:
                System.out.println("   • Level 1 passed to Level 2 (escalation)");
                System.out.println("   • Level 2 could handle MEDIUM priority - chain stopped");
                break;
            case HIGH:
            case CRITICAL:
                System.out.println("   • Level 1 passed to Level 2 (escalation)");
                System.out.println("   • Level 2 passed to Level 3 (escalation)");
                System.out.println("   • Level 3 handled " + priority + " priority - chain ended");
                break;
        }
        System.out.println("   • Each handler decides: handle or pass to next");
    }
}
