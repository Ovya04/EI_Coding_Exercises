package ex_1_designpatterns.behavioural.chain_of_responsibility;



public class Main {
    public static void main(String[] args) {
        // Create the chain of responsibility
        SupportHandler level1 = new Level1Support();
        SupportHandler level2 = new Level2Support();
        SupportHandler level3 = new Level3Support();

        // Set up the chain
        level1.setNextHandler(level2);
        level2.setNextHandler(level3);

        // Create different support tickets
        SupportTicket[] tickets = {
            new SupportTicket("Password reset request", SupportTicket.Priority.LOW),
            new SupportTicket("Software configuration issue", SupportTicket.Priority.MEDIUM),
            new SupportTicket("Server down - production impact", SupportTicket.Priority.HIGH),
            new SupportTicket("Security breach detected", SupportTicket.Priority.CRITICAL),
            new SupportTicket("Email not working", SupportTicket.Priority.LOW)
        };

        // Process tickets through the chain
        System.out.println("=== Processing Support Tickets ===\n");
        for (SupportTicket ticket : tickets) {
            level1.handleRequest(ticket);
        }
    }
}

