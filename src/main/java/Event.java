public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String getFullStatus() {
        return "[E]" + super.getFullStatus() + " (from: " + from + " to: " + to + ")";
    }
}
