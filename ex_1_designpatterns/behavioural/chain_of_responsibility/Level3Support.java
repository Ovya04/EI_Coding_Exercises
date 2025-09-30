package ex_1_designpatterns.behavioural.chain_of_responsibility;

public class Level3Support extends SupportHandler {
    @Override
    public void handleRequest(SupportTicket ticket) {
        System.out.println("🎯 Level 3 Support (Expert Team) received: " + ticket);
        
        if (ticket.getPriority() == SupportTicket.Priority.HIGH || 
            ticket.getPriority() == SupportTicket.Priority.CRITICAL) {
            System.out.println("✅ Level 3 Support HANDLED the ticket!");
            System.out.println("🔧 Solution Applied:");
            System.out.println("   • Expert analysis and custom solution development");
            System.out.println("   • Direct code fixes and system patches");
            System.out.println("   • Immediate escalation to development team if needed");
            if (ticket.getPriority() == SupportTicket.Priority.CRITICAL) {
                System.out.println("   • 🚨 Emergency protocols activated!");
                System.out.println("   • 📞 Customer called directly for updates");
            }
            System.out.println("⏱️ Resolution Time: " + 
                (ticket.getPriority() == SupportTicket.Priority.CRITICAL ? "2 hours" : "4 hours"));
        } else {
            System.out.println("❌ Level 3 Support: This shouldn't happen! All priorities should be handled by now.");
        }
        System.out.println();
    }
}
