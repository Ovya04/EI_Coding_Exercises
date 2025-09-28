package ex_1_designpatterns.behavioural.chain_of_responsibility;

public class Level2Support extends SupportHandler {
    @Override
    public void handleRequest(SupportTicket ticket) {
        if (ticket.getPriority() == SupportTicket.Priority.MEDIUM) {
            System.out.println("Level 2 Support handled: " + ticket);
            System.out.println("Solution: Advanced configuration applied\n");
        } else if (nextHandler != null) {
            System.out.println("Level 2 Support: Escalating ticket - " + ticket.getIssue());
            nextHandler.handleRequest(ticket);
        }
    }
}

