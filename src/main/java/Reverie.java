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

            try {
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
                        throw new ReverieException("I'm sorry, but I don't know what that means :-(");
                }
            } catch (ReverieException e) {
                System.out.println(" Error: " + e.getMessage());
                System.out.println(HORIZONTAL_LINE);
            }
        }
    }

    /*private static void handleDefault(String input) {
        tasks[taskCount] = new Task(input);
        taskCount++;
        System.out.println(" added: " + input);
        System.out.println(HORIZONTAL_LINE);
    }*/

    private static void handleExit(Scanner scanner) {
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
        scanner.close();
    }

    private static void handleList() throws ReverieException {
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

    private static void handleMark(String input, boolean isMark) throws ReverieException {
        if (taskCount == 0) {
            throw new ReverieException("No tasks available to mark!");
        }

        int prefixLength = isMark ? "mark ".length() : "unmark ".length();

        if (input.length() <= prefixLength) {
            throw new ReverieException("Please specify a task number to " + (isMark ? "mark" : "unmark"));
        }

        try {
            int taskNumber = Integer.parseInt(input.substring(prefixLength).trim()) - 1;

            if (taskNumber < 0 || taskNumber >= taskCount) {
                throw new ReverieException("Invalid task number! Please choose between 1 and " + taskCount);
            }

            if (isMark) {
                tasks[taskNumber].markAsDone();
                System.out.println(" Nice! I've marked this task as done:");
            } else {
                tasks[taskNumber].markAsUndone();
                System.out.println(" OK, I've marked this task as unfinished:");
            }
            System.out.println("   " + tasks[taskNumber].getFullStatus());
            System.out.println(HORIZONTAL_LINE);
        } catch (NumberFormatException e) {
            throw new ReverieException("Please enter a valid number after '" + (isMark ? "mark" : "unmark") + "'");
        }
    }

    private static void handleTodo(String input) throws ReverieException {
        if (input.length() <= "todo ".length()) {
            throw new ReverieException("The description of a todo cannot be empty!");
        }

        String description = input.substring("todo ".length()).trim();
        if (description.isEmpty()) {
            throw new ReverieException("The description of a todo cannot be empty!");
        }

        addTask(new Todo(description));
    }

    private static void handleDeadline(String input) throws ReverieException {
        if (input.length() <= "deadline ".length()) {
            throw new ReverieException("The description of a deadline cannot be empty!\nFormat: deadline <description> /by <time>");
        }

        String content = input.substring("deadline ".length()).trim();
        String[] parts = content.split(" /by ");

        if (parts.length < 2) {
            throw new ReverieException("Invalid deadline format!\nFormat: deadline <description> /by <time>");
        }

        String description = parts[0].trim();
        String by = parts[1].trim();

        if (description.isEmpty()) {
            throw new ReverieException("The description of a deadline cannot be empty!");
        }
        if (by.isEmpty()) {
            throw new ReverieException("The deadline time cannot be empty!");
        }

        addTask(new Deadline(description, by));
    }

    private static void handleEvent(String input) throws ReverieException {
        if (input.length() <= "event ".length()) {
            throw new ReverieException("The description of an event cannot be empty!\nFormat: event <description> /from <start> /to <end>");
        }

        String content = input.substring("event ".length()).trim();
        String[] parts = content.split(" /from | /to ");

        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();
        //String[] parts = input.substring("event ".length()).split(" /from | /to ");
        //addTask(new Event(parts[0], parts[1], parts[2]));
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
