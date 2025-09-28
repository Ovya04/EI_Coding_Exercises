package ex_1_designpatterns.behavioural.chain_of_responsibility;



public abstract class SupportHandler {
    protected SupportHandler nextHandler;

    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleRequest(SupportTicket ticket);
}

