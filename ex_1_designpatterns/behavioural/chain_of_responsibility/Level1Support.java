package ex_1_designpatterns.behavioural.chain_of_responsibility;



public class Level1Support extends SupportHandler {
    @Override
    public void handleRequest(SupportTicket ticket) {
        if (ticket.getPriority() == SupportTicket.Priority.LOW) {
            System.out.println("Level 1 Support handled: " + ticket);
            System.out.println("Solution: Basic troubleshooting steps provided\n");
        } else if (nextHandler != null) {
            System.out.println("Level 1 Support: Escalating ticket - " + ticket.getIssue());
            nextHandler.handleRequest(ticket);
        }
    }
}

