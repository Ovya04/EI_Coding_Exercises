package ex_1_designpatterns.behavioural.chain_of_responsibility;

public class Level2Support extends SupportHandler {
    @Override
    public void handleRequest(SupportTicket ticket) {
        System.out.println("🔧 Level 2 Support received: " + ticket);
        
        if (ticket.getPriority() == SupportTicket.Priority.MEDIUM) {
            System.out.println("✅ Level 2 Support HANDLED the ticket!");
            System.out.println("🔧 Solution Applied:");
            System.out.println("   • Performed advanced system diagnostics");
            System.out.println("   • Applied custom configuration changes");
            System.out.println("   • Coordinated with technical team");
            System.out.println("⏱️ Resolution Time: 30 minutes");
        } else {
            System.out.println("⬆️ Level 2 Support: Cannot handle this priority level");
            System.out.println("🔄 Escalating to Level 3 Support...");
            if (nextHandler != null) {
                nextHandler.handleRequest(ticket);
            }
        }
        System.out.println();
    }
}
