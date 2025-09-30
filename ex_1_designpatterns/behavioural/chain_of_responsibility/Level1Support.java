package ex_1_designpatterns.behavioural.chain_of_responsibility;

public class Level1Support extends SupportHandler {
    @Override
    public void handleRequest(SupportTicket ticket) {
        System.out.println("📞 Level 1 Support received: " + ticket);
        
        if (ticket.getPriority() == SupportTicket.Priority.LOW) {
            System.out.println("✅ Level 1 Support HANDLED the ticket!");
            System.out.println("🔧 Solution Applied:");
            System.out.println("   • Provided basic troubleshooting steps");
            System.out.println("   • Sent helpful documentation links");
            System.out.println("   • Issue resolved via standard procedures");
            System.out.println("⏱️ Resolution Time: 5 minutes");
        } else {
            System.out.println("⬆️ Level 1 Support: Cannot handle this priority level");
            System.out.println("🔄 Escalating to Level 2 Support...");
            if (nextHandler != null) {
                nextHandler.handleRequest(ticket);
            }
        }
        System.out.println();
    }
}
