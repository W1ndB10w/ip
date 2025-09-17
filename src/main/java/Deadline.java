public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String getFullStatus() {
        return "[D]" + super.getFullStatus() + " (by: " + by + ")";
    }
}
