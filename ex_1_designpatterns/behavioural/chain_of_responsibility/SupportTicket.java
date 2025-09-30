package ex_1_designpatterns.behavioural.chain_of_responsibility;

public class SupportTicket {
    private String issue;
    private Priority priority;
    private String customerName;
    private String ticketId;

    public enum Priority {
        LOW("🟢", "Low Priority"), 
        MEDIUM("🟡", "Medium Priority"), 
        HIGH("🟠", "High Priority"), 
        CRITICAL("🔴", "Critical Priority");
        
        private final String emoji;
        private final String description;
        
        Priority(String emoji, String description) {
            this.emoji = emoji;
            this.description = description;
        }
        
        public String getEmoji() { return emoji; }
        public String getDescription() { return description; }
    }

    public SupportTicket(String ticketId, String customerName, String issue, Priority priority) {
        this.ticketId = ticketId;
        this.customerName = customerName;
        this.issue = issue;
        this.priority = priority;
    }

    public String getIssue() { return issue; }
    public Priority getPriority() { return priority; }
    public String getCustomerName() { return customerName; }
    public String getTicketId() { return ticketId; }

    @Override
    public String toString() {
        return String.format("Ticket #%s [%s %s] - %s (Customer: %s)", 
            ticketId, priority.getEmoji(), priority.getDescription(), issue, customerName);
    }
}
