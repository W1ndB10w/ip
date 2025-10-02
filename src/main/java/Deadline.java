public class Deadline extends Task {
    protected String by;
    protected LocalDate byDate;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        this.byDate = parseDate(by);
    }

    private LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
    
    @Override
    public String getFullStatus() {
        return "[D]" + super.getFullStatus() + " (by: " + by + ")";
    }
}
