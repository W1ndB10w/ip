import java.util.Scanner;

public class Reverie {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;

    private static void printWelcomeMessage() {
        // Reverie picture
        String[] logo = getLogo();

        System.out.println(HORIZONTAL_LINE);
        // Print Reverie picture
        for (String line : logo) {
            System.out.println(line);
        }

        // Welcome message
        System.out.println(" Hello! I'm Reverie");
        System.out.println(" What can I do for you?");
        System.out.println(HORIZONTAL_LINE);
    }

    private static String[] getLogo() {
        return new String[]{
                "██████╗ ███████╗██╗   ██╗███████╗██████╗ ██╗███████╗",
                "██╔══██╗██╔════╝██║   ██║██╔════╝██╔══██╗██║██╔════╝",
                "██████╔╝█████╗  ██║   ██║█████╗  ██████╔╝██║█████╗  ",
                "██╔══██╗██╔══╝  ╚██╗ ██╔╝██╔══╝  ██╔══██╗██║██╔══╝  ",
                "██║  ██║███████╗ ╚████╔╝ ███████╗██║  ██║██║███████╗",
                "╚═╝  ╚═╝╚══════╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝╚═╝╚══════╝"
        };
    }

    private static void processUserCommands() {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();
            System.out.println(HORIZONTAL_LINE);

            if (input.equalsIgnoreCase("bye")) {
                handleExit(scanner);
                break;
            }

            switch (input.toLowerCase().split(" ")[0]) {
                case "list":
                    handleList();
                    break;
                case "mark":
                    handleMark(input, true);
                    break;
                case "unmark":
                    handleMark(input, false);
                    break;
                case "todo":
                    handleTodo(input);
                    break;
                case "deadline":
                    handleDeadline(input);
                    break;
                case "event":
                    handleEvent(input);
                    break;
                default:
                    handleDefault(input);
                    break;
            }
        }
    }

    private static void handleDefault(String input) {
        tasks[taskCount] = new Task(input);
        taskCount++;
        System.out.println(" added: " + input);
        System.out.println(HORIZONTAL_LINE);
    }

    private static void handleExit(Scanner scanner) {
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
        scanner.close();
    }

    private static void handleList() {
        if (taskCount == 0) {
            System.out.println(" No tasks added yet!");
        } else {
            System.out.println(" Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println(" " + (i + 1) + "." + tasks[i].getFullStatus());
            }
        }
        System.out.println(HORIZONTAL_LINE);
    }

    private static void handleMark(String input, boolean isMark) {
        if (taskCount == 0) {
            System.out.println(" No tasks added yet!");
            System.out.println(HORIZONTAL_LINE);
            return;
        }

        int prefixLength = isMark ? "mark ".length() : "unmark ".length();
        int taskNumber = Integer.parseInt(input.substring(prefixLength)) - 1;

        if (isMark) {
            tasks[taskNumber].markAsDone();
            System.out.println(" Nice! I've marked this task as done:");
        } else {
            tasks[taskNumber].markAsUndone();
            System.out.println(" OK, I've marked this task as unfinished:");
        }
        System.out.println("   " + tasks[taskNumber].getFullStatus());
        System.out.println(HORIZONTAL_LINE);
    }

    private static void handleTodo(String input) {
        String description = input.substring("todo ".length());
        addTask(new Todo(description));
    }

    private static void handleDeadline(String input) {
        String[] parts = input.substring("deadline ".length()).split(" /by ");
        addTask(new Deadline(parts[0], parts[1]));
    }

    private static void handleEvent(String input) {
        String[] parts = input.substring("event ".length()).split(" /from | /to ");
        addTask(new Event(parts[0], parts[1], parts[2]));
    }

    private static void addTask(Task task) {
        tasks[taskCount] = task;
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task.getFullStatus());
        taskCount++;
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(HORIZONTAL_LINE);
    }

    public static void main(String[] args) {
        printWelcomeMessage();
        processUserCommands();
    }
}
