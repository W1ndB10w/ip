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
        System.out.println(HORIZONTAL_LINE);
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

    public void showTaskMarked(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task.getFullStatus());
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(" OK, I've marked this task as unfinished:");
        System.out.println("   " + task.getFullStatus());
    }

    public void showTaskList(TaskList tasks) throws ReverieException {
        if (tasks.isEmpty()) {
            System.out.println(" No tasks added yet!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + tasks.get(i).getFullStatus());
            }
        }
    }

    public void showFoundTasks(TaskList tasks) throws ReverieException {
        
    }

    public void showLoadedTasks(int count) {
        if (count > 0) {
            showLine();
            System.out.println(" Loaded " + count + " task(s) from file.");
        }
    }

    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }

    public void close() {
        scanner.close();
    }
}
