package ex_1_designpatterns.behavioural.chain_of_responsibility;

public class SupportTicket {
    private String issue;
    private Priority priority;

    public enum Priority {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    public SupportTicket(String issue, Priority priority) {
        this.issue = issue;
        this.priority = priority;
    }

    public String getIssue() {
        return issue;
    }

    public Priority getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Ticket [" + priority + "]: " + issue;
    }
}
