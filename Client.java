package messaging_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.spec.RSAOtherPrimeInfo;
import java.time.LocalTime;
import java.util.*;

public class Client {
    public static boolean loggedIn;
    public static boolean exit;

    public static void main(String[] args) throws IOException {
        // Socket setup
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

        // Main menu switch case

        do {
            System.out.println("Main Menu. Please choose an option.\n1.Login\n2.Create Account\n3.Exit");
            String menuOption = scanner.nextLine();
            // sending first choice 1-3
            writer.println(menuOption);
            writer.flush();
            String email = "";
            String password = "";


            switch (menuOption) {

                case "1":
                    // user logs in
                    System.out.println("Enter your email:");
                    email = scanner.nextLine();
                    writer.println(email);
                    writer.flush();
                    System.out.println("Enter your password:");
                    password = scanner.nextLine();
                    writer.println(password);
                    writer.flush();
                    String logInResult = reader.readLine();
                    System.out.println(logInResult);
                    loggedIn = true;


                    int unreadSize = Integer.parseInt(reader.readLine());
                    if (unreadSize == 0) { // TODO: Not printing no new messages, prints 0.
                        System.out.println("No new Messages!");
                    } else {
                        String messageTitle = reader.readLine();
                        System.out.println(messageTitle);
                        if (!messageTitle.equals("No new Messages!")) {
                            for (int i = 0; i < unreadSize; i++) {
                                System.out.println(reader.readLine());
                            }
                        }
                    }

                    do {
                        String userName = reader.readLine();
                        System.out.println(userName);
                        String sOrC = reader.readLine();
                        if (sOrC.equals("customer")) {
                            System.out.println("1.See messages\n2.Send message\n3.Edit Account\n4.Delete Account\n"
                                    + "5.Hide User\n6.Block User\n7.Get Statistics\n8.Logout\n" +
                                    "9.Edit Message\n10.Delete Message\n11.Export CSV\n" +
                                    "12.Buy products\n13.Censor Texts");
                        } else {
                            System.out.println("1.See messages\n2.Send message\n3.Edit Account\n" +
                                    "4.Delete Account\n" + "5.Hide User\n6.Block User\n" +
                                    "7.Get Statistics\n8.Logout\n"
                                    + "9.Edit Message\n10.Delete Message\n11.Export CSV\n12." +
                                    "Create Store\n13.Censor Texts");
                        }
                        String choice = scanner.nextLine();
                        writer.println(choice);
                        writer.flush();
                        switch (choice) {


                            //See messages
                            case "1":
                                int numMessages = Integer.parseInt(reader.readLine());
                                System.out.println(numMessages + " messages total");
                                String msgs = "";

                                for (int i = 0; i < numMessages; i++) {
                                    msgs = reader.readLine();
                                    System.out.println(msgs);
                                }

                                break;


                            // send messages
                            case "2":
                                sOrC = reader.readLine();
                                if (sOrC.equals("seller")) {
                                    System.out.println("1. View a list of people to message.\n2. Message a specific user.");
                                } else {
                                    System.out.println("1. View a list of stores to message.\n2. Message a specific seller.");
                                }
                                choice = scanner.nextLine();
                                writer.println(choice);
                                writer.flush();
                                switch (choice) {
                                    case "1":
                                        int size = Integer.parseInt(reader.readLine());
                                        for (int i = 0; i < size; i++) {
                                            System.out.println(reader.readLine());
                                        }

                                        break;
                                    case "2":
                                        System.out.println("Who would you like to message?");
                                        String recipient = scanner.nextLine();
                                        writer.println(recipient);
                                        writer.flush();
                                        String blockMessage = reader.readLine();
                                        System.out.println(blockMessage);
                                        if (!blockMessage.isEmpty()) {
                                            System.out.println(blockMessage);
                                            break;
                                        }
                                        String nullCheck = reader.readLine();
                                        if (!nullCheck.isEmpty()) {
                                            System.out.println(nullCheck);
                                            break;
                                        }
                                        System.out.println("How would you like to send the message?\n1. Type the message\n2. " +
                                                "Import a text file");
                                        String choice2 = scanner.nextLine();
                                        writer.println(choice2);
                                        writer.flush();
                                        switch (choice2) {
                                            case "1":
                                                System.out.println("Do you want your message to disappear after it's read? (Yes/No)");
                                                String disappear = scanner.nextLine();
                                                writer.println(disappear);
                                                writer.flush();
                                                System.out.println("What is your message?");
                                                String message = scanner.nextLine();
                                                writer.println(message);
                                                writer.flush();
                                                System.out.println(reader.readLine());
                                                break;
                                            case "2":
                                                System.out.println("What is the text file you would like to import?");
                                                String file = scanner.nextLine();
                                                writer.println(file);
                                                writer.flush();
                                                break;
                                            default:
                                                System.out.println("Invalid input.");
                                                break;

                                        }
                                        break;
                                    default:
                                        System.out.println("Invalid input.");
                                        break;
                                }

                                break;
                            case "3":
                                // edit account
                                break;
                            case "4":
                                // delete account
                                break;
                            case "5":
                                // hide users
                                break;
                            case "6":
                                // block users
                                break;
                            case "7":
                                // get stats
                                break;
                            case "8":
                               String logOutMessage = reader.readLine();
                                System.out.println(logOutMessage);
                                loggedIn = false;
                                break;
                            case "9":
                                // edit message
                                break;
                            case "10":
                                // delete message
                                break;
                            case "11":
                                // export csv
                                break;
                            case "12":
                                // create store / buy products
                                break;
                            case "13":
                                // censor texts
                                break;

                        }
                    } while (loggedIn);

                    break;

                case "2":
                    // user create account
                    System.out.println("Enter your email:");
                    email = scanner.nextLine();
                    writer.println(email);
                    writer.flush();

                    String successfulCreation = reader.readLine();
                    if (!successfulCreation.equals("Account with that email already exists.")) {
                        System.out.println("Enter your password:");
                        password = scanner.nextLine();
                        writer.println(password);
                        writer.flush();

                        System.out.println("Choose type of account to create:\n1.Customer\n2.Seller\n");
                        String accountChoice = scanner.nextLine();
                        writer.println(accountChoice);
                        writer.flush();

                        String finalMessage = reader.readLine();
                        System.out.println(finalMessage);

                    } else {
                        System.out.println("Account with that email already exists.");
                    }
                    break;

                case "3":
                    // user exits application
                    exit = true;
                    String exitMessage = reader.readLine();
                    System.out.println(exitMessage);
                    break;
            }
        } while (!exit);
    }
}
