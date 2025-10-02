import java.util.Scanner;

public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    private String[] getLogo() {
        return new String[]{
                "██████╗ ███████╗██╗   ██╗███████╗██████╗ ██╗███████╗",
                "██╔══██╗██╔════╝██║   ██║██╔════╝██╔══██╗██║██╔════╝",
                "██████╔╝█████╗  ██║   ██║█████╗  ██████╔╝██║█████╗  ",
                "██╔══██╗██╔══╝  ╚██╗ ██╔╝██╔══╝  ██╔══██╗██║██╔══╝  ",
                "██║  ██║███████╗ ╚████╔╝ ███████╗██║  ██║██║███████╗",
                "╚═╝  ╚═╝╚══════╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝╚═╝╚══════╝"
        };
    }

    public void showLine() {
        System.out.print(HORIZONTAL_LINE);
    }

    public void showWelcome() {
        // Reverie picture
        String[] logo = getLogo();

        showLine();
        // Print Reverie picture
        for (String line : logo) {
            System.out.println(line);
        }

        // Welcome message
        System.out.println(" Hello! I'm Reverie");
        System.out.println(" What can I do for you?");
        showLine();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLoadingError() {
        System.out.println(" Error loading tasks from file. Starting with empty task list.");
        showLine();
    }

    public void showError(String message) {
        System.out.println(" Error: " + message);
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task.getFullStatus());
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task.getFullStatus());
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }
    
}
