package ex_1_designpatterns.behavioural.chain_of_responsibility;



public class Level3Support extends SupportHandler {
    @Override
    public void handleRequest(SupportTicket ticket) {
        if (ticket.getPriority() == SupportTicket.Priority.HIGH || 
            ticket.getPriority() == SupportTicket.Priority.CRITICAL) {
            System.out.println("Level 3 Support handled: " + ticket);
            System.out.println("Solution: Expert analysis and custom fix implemented\n");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(ticket);
        }
    }
}

