package ex_1_designpatterns.behavioural.chain_of_responsibility;

import java.util.Scanner;

public class Main {
    private static int ticketCounter = 1001;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
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
        
        while (true) {
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
                System.out.println();
                System.out.println("🎯 Select priority level:");
                System.out.println("1. 🟢 Low Priority (Basic questions, how-to queries)");
                System.out.println("2. 🟡 Medium Priority (Configuration issues, feature requests)");
                System.out.println("3. 🟠 High Priority (Service disruption, urgent issues)");
                System.out.println("4. 🔴 Critical Priority (System down, security breach)");
                System.out.print("Enter choice (1-4): ");
                
                int priorityChoice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                SupportTicket.Priority priority;
                switch (priorityChoice) {
                    case 1: priority = SupportTicket.Priority.LOW; break;
                    case 2: priority = SupportTicket.Priority.MEDIUM; break;
                    case 3: priority = SupportTicket.Priority.HIGH; break;
                    case 4: priority = SupportTicket.Priority.CRITICAL; break;
                    default:
                        System.out.println("❌ Invalid choice! Using Medium priority as default.");
                        priority = SupportTicket.Priority.MEDIUM;
                        break;
                }
                
                // Create ticket
                String ticketId = String.valueOf(ticketCounter++);
                SupportTicket ticket = new SupportTicket(ticketId, customerName, issue, priority);
                
                System.out.println();
                System.out.println("🎫 TICKET CREATED SUCCESSFULLY!");
                System.out.println("============================================================");
                System.out.println(ticket);
                System.out.println("============================================================");
                
                System.out.println();
                System.out.println("🔄 PROCESSING THROUGH CHAIN OF RESPONSIBILITY...");
                System.out.println();
                
                // Process through chain
                long startTime = System.currentTimeMillis();
                level1.handleRequest(ticket);
                long endTime = System.currentTimeMillis();
                
                System.out.println("✅ TICKET PROCESSING COMPLETE!");
                System.out.println("⏱️ Total Processing Time: " + (endTime - startTime) + "ms");
                
                System.out.println();
                System.out.println("🔍 Chain of Responsibility Pattern Explanation:");
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
                        System.out.println("   • Level 3 handled " + priority.getDescription() + " - chain ended");
                        break;
                }
                System.out.println("   • Each handler decides: handle or pass to next");
                System.out.println("   • Sender doesn't know which handler will process the request!");
                
                System.out.println();
                System.out.print("🔄 Submit another ticket? [y/n]: ");
                if (!scanner.nextLine().toLowerCase().startsWith("y")) {
                    break;
                }
                System.out.println();
                
            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please try again.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        System.out.println();
        System.out.println("🙏 Thank you for learning Chain of Responsibility Pattern!");
        System.out.println("👨‍🏫 Key Takeaway: Chain Pattern passes requests until someone can handle it!");
        System.out.println("🎯 Benefits: Loose coupling, dynamic chain modification, single responsibility!");
        scanner.close();
    }
}
