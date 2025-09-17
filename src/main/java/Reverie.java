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
        return {
                "██████╗ ███████╗██╗   ██╗███████╗██████╗ ██╗███████╗",
                "██╔══██╗██╔════╝██║   ██║██╔════╝██╔══██╗██║██╔════╝",
                "██████╔╝█████╗  ██║   ██║█████╗  ██████╔╝██║█████╗  ",
                "██╔══██╗██╔══╝  ╚██╗ ██╔╝██╔══╝  ██╔══██╗██║██╔══╝  ",
                "██║  ██║███████╗ ╚████╔╝ ███████╗██║  ██║██║███████╗",
                "╚═╝  ╚═╝╚══════╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝╚═╝╚══════╝"
        };
    }

    public static void main(String[] args) {
        printWelcomeMessage();
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();
            System.out.println(HORIZONTAL_LINE);

            if (input.equalsIgnoreCase("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(HORIZONTAL_LINE);
                break;
            } else if (input.equalsIgnoreCase("list")) {
                if (taskCount == 0) {
                    System.out.println(" No tasks added yet!");   // Default response for empty tasks
                } else {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(" " + (i + 1) + "." + tasks[i].getFullStatus());
                    }
                }
                System.out.println(HORIZONTAL_LINE);
            } else if (input.toLowerCase().startsWith("mark ")) {
                if (taskCount == 0) {
                    System.out.println(" No tasks added yet!");   // Default response for empty tasks
                } else {
                    int taskNumber = Integer.parseInt(input.substring(5)) - 1;
                    tasks[taskNumber].markAsDone();
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   " + tasks[taskNumber].getFullStatus());
                    System.out.println(HORIZONTAL_LINE);
                }
            } else if (input.toLowerCase().startsWith("unmark ")) {
                if (taskCount == 0) {
                    System.out.println(" No tasks added yet!");   // Default response for empty tasks
                } else {
                    int taskNumber = Integer.parseInt(input.substring(7)) - 1;
                    tasks[taskNumber].markAsUndone();
                    System.out.println(" OK, I've marked this task as unfinished:");
                    System.out.println("   " + tasks[taskNumber].getFullStatus());
                    System.out.println(HORIZONTAL_LINE);
                }
            } else if (input.toLowerCase().startsWith("todo ")) {
                String description = input.substring(5);
                tasks[taskCount] = new Todo(description);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount].getFullStatus());
                taskCount++;
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println(HORIZONTAL_LINE);
            } else if (input.toLowerCase().startsWith("deadline ")) {
                String temp = input.substring(9);
                String[] parts = temp.split(" /by ");
                tasks[taskCount] = new Deadline(parts[0], parts[1]);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount].getFullStatus());
                taskCount++;
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println(HORIZONTAL_LINE);
            } else if (input.toLowerCase().startsWith("event ")) {
                String temp = input.substring(6);
                String[] parts = temp.split(" /from | /to ");
                tasks[taskCount] = new Event(parts[0], parts[1], parts[2]);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[taskCount].getFullStatus());
                taskCount++;
                System.out.println(" Now you have " + taskCount + " tasks in the list.");
                System.out.println(HORIZONTAL_LINE);
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println(" added: " + input);
                System.out.println(HORIZONTAL_LINE);
            }
        }

        scanner.close();

    }
}
