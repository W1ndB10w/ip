import java.util.Scanner;

public class Reverie {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static String[] tasks = new String[100];
    private static int taskCount = 0;
    /*private void echoCommand(String command) {
        // Add some personality to specific commands
        String response = command;

        // Optional: Add some fun responses to certain keywords
        if (command.equalsIgnoreCase("hello") || command.equalsIgnoreCase("hi")) {
            response = command + "\n    Oh, hello there! Nice to meet you! 😊";
        } else if (command.equalsIgnoreCase("help")) {
            response = command + "\n    I'm here to echo whatever you say! Just type 'bye' when you want to leave.";
        } else if (command.toLowerCase().contains("thank")) {
            response = command + "\n    You're welcome! Happy to help! 💫";
        }

        System.out.println(response);
    }*/

    public static void main(String[] args) {
        // Reverie picture
        String[] logo = {
                "██████╗ ███████╗██╗   ██╗███████╗██████╗ ██╗███████╗",
                "██╔══██╗██╔════╝██║   ██║██╔════╝██╔══██╗██║██╔════╝",
                "██████╔╝█████╗  ██║   ██║█████╗  ██████╔╝██║█████╗  ",
                "██╔══██╗██╔══╝  ╚██╗ ██╔╝██╔══╝  ██╔══██╗██║██╔══╝  ",
                "██║  ██║███████╗ ╚████╔╝ ███████╗██║  ██║██║███████╗",
                "╚═╝  ╚═╝╚══════╝  ╚═══╝  ╚══════╝╚═╝  ╚═╝╚═╝╚══════╝"
        };

        System.out.println(HORIZONTAL_LINE);
        // Print Reverie picture
        for (String line : logo) {
            System.out.println(line);
        }

        // Welcome message
        System.out.println(" Hello! I'm Reverie");
        System.out.println(" What can I do for you?");
        System.out.println(HORIZONTAL_LINE);

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            input = scanner.nextLine();
            System.out.println(HORIZONTAL_LINE);

            if (input.equalsIgnoreCase("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(HORIZONTAL_LINE);
                break;
            } else {
                System.out.println(" " + input);
                System.out.println(HORIZONTAL_LINE);
            }
        }
        scanner.close();

    }
}
