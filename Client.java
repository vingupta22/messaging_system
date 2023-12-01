package messaging_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.*;

public class Client {
    private static boolean exit;
    public static boolean loggedIn;


    public static String userStatus = null;
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            socket = new Socket("localhost", 12345);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        exit = false;
        Scanner scanner = new Scanner(System.in);
        //replace print/scanner with GUI
        do {
        System.out.println("Main Menu. Please choose an option.\n1.Login\n2.Create Account\n3.Exit");
        String initOption = scanner.nextLine();
        //Sends input from login to the server
        writer.println(initOption);
        writer.flush();
            //need to fix error about keeping the menu going
            if (initOption.equals("1")) {
                System.out.println("Enter your email:");
                String email = scanner.nextLine();
                writer.println(email);
                writer.flush();
                System.out.println("Enter your password:");
                String password = scanner.nextLine();
                writer.println(password);
                writer.flush();
                String loginMessage = reader.readLine();
                if (loginMessage.equals("Logged in!"))
                    loggedIn = true;
                System.out.println(loginMessage);

                String newMessages = reader.readLine();
                System.out.println(newMessages);

                //If user is customer or seller
                String userType = reader.readLine();
                String nextOption = "";
                if (userType.equals("Seller")) {
                    userStatus = "Seller";
                    String menu = reader.readLine();
                    System.out.println(menu);
                    nextOption = scanner.nextLine();
                    writer.println(nextOption);
                    writer.flush();
                } else {
                    userStatus = "Customer";
                    String menu = reader.readLine();
                    System.out.println(menu);
                    nextOption = scanner.nextLine();
                    writer.println(nextOption);
                    writer.flush();
                }

                do {
                    if (nextOption.equals("1")) {
                        String messages = reader.readLine();
                        System.out.println(messages);
                    } else if (nextOption.equals("2")) {
                        String prompt = reader.readLine();
                        System.out.println(prompt);
                        String choice = scanner.nextLine();
                        writer.println(choice);
                        writer.println();
                        switch (choice) {
                            case "1":
                                String users = reader.readLine();
                                System.out.println(users);
                                break;
                            case "2":
                                System.out.println("Who would you like to message?");
                                String recipient = scanner.nextLine();
                                writer.println(recipient);
                                writer.flush();

                                //receives either user error message or blank string if successful
                                String next = reader.readLine();
                                System.out.println(next);
                                System.out.println("How would you like to send the message?\n1. Type the message\n2. " +
                                        "Import a text file");
                                String msgChoice = scanner.nextLine();
                                writer.println(msgChoice);
                                writer.flush();
                                switch (msgChoice) {
                                    case "1":
                                        System.out.println("Do you want your message to disappear after it's read? (Yes/No)");
                                        String confirm = scanner.nextLine();
                                        writer.println(confirm);
                                        writer.flush();
                                        System.out.println("What is your message?");
                                        String content = scanner.nextLine();
                                        writer.println(content);
                                        writer.flush();
                                        break;
                                    case "2":
                                        //allows user to import a txt file for a message
                                        System.out.println("What is the text file you would like to import?");
                                        String file = scanner.next();
                                        writer.println(file);
                                        writer.flush();
                                    default:
                                        System.out.println("Invalid input.");
                                        break;
                                }
                                break;
                            default:
                                System.out.println("Invalid input.");
                                break;
                        }
                    } else if (nextOption.equals("3")) {
                        System.out.println("Enter updated email:");
                        String newEmail = scanner.nextLine();
                        writer.println(newEmail);
                        String exists = reader.readLine();
                        if (exists.equals("exists")) {
                            System.out.println("Account with that email already exists.");
                        } else {
                            System.out.println("Enter updated password:");
                            String newPassword = scanner.nextLine();
                            writer.println(newPassword);
                            writer.flush();
                        }
                    } else if (nextOption.equals("4")) {
                        //log out here

                    } else if (nextOption.equals("5")) {
                        String hiddenUser = reader.readLine();
                        System.out.println(hiddenUser);
                        String selectedUser = scanner.nextLine();
                        writer.println(selectedUser);
                        writer.flush();

                    } else if (nextOption.equals("6")) {
                        String blockedUser = reader.readLine();
                        System.out.println(blockedUser);
                        String selectedUser = scanner.nextLine();
                        writer.println(selectedUser);
                        writer.flush();
                    } else if (nextOption.equals("7")) {
                        System.out.println("Would you like to sort your data?\n1. Yes\n2. No");
                        String choice = scanner.nextLine();
                        writer.println(choice);
                        writer.flush();
                        String ret = reader.readLine();
                        System.out.println(ret);
                        if (userType.equalsIgnoreCase(("customer"))) {
                            String ret2 = reader.readLine();
                            System.out.println(ret2);
                        }
                    } else if (nextOption.equals("8")) {
                        System.out.println("Logging out!");
                        loggedIn = false;

                    } else if (nextOption.equals("9")) {

                    } else if (nextOption.equals("10")) {

                    } else if (nextOption.equals("11")) {

                    } else if (nextOption.equals("12")) {

                    } else if (nextOption.equals("13")) {

                    }
                } while (loggedIn);

            }
            else if (initOption.equals("2"))
            {
                System.out.println("Enter your email:");
                String email = scanner.nextLine();
                writer.println(email);

                System.out.println("Enter your password:");
                String password = scanner.nextLine();
                writer.println(password);
                writer.flush();
                System.out.println("Choose type of account to create:\n1.Customer\n2.Seller\n");
                String choice = scanner.nextLine();
                writer.println(choice);
                writer.flush();

                String finalMessage = reader.readLine();
                System.out.println(finalMessage);
            }
            else if (initOption.equals("3"))
            {
                exit = true;
                System.out.println("Ending application!");
            }
            else {
                System.out.println("error");
            }
        } while (!exit); //FIX LOOP

    }
}
