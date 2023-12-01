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
                        String messages = reader.readLine();
                        System.out.println(messages);
                        System.out.println("\nEnter the number of the message you would like to edit:");
                        int choice = scanner.nextInt();
                        writer.println(choice);
                        writer.flush();

                        String response = reader.readLine();
                        if (!response.equals("Invalid response.")) {
                            System.out.println(response);
                            String edit = scanner.nextLine();
                            writer.println(edit);
                            writer.flush();
                            String success = reader.readLine();
                            System.out.println(success);

                        } else {
                            System.out.println(response);
                        }

                    } else if (nextOption.equals("10")) {
                        String messages = reader.readLine();
                        System.out.println(messages);
                        String out = reader.readLine();
                        if (!reader.equals("No message history.")) {
                            System.out.println("\nEnter the number of the message you would like to delete:");
                            int choice = scanner.nextInt();
                            writer.println(choice);
                            writer.flush();
                            String finalResp = reader.readLine();
                            System.out.println(finalResp);
                        } else {
                            System.out.println(out);
                        }

                    } else if (nextOption.equals("11")) {
                        String emails = reader.readLine();
                        System.out.println(emails);
                        System.out.println("Whose conversation would you like to export (leave blank for all).");
                        String name = scanner.nextLine();
                        writer.println(name);
                        writer.flush();

                        String success = reader.readLine();
                        System.out.println(success);

                    } else if (nextOption.equals("12")) {
                        if (userStatus.equalsIgnoreCase("customer")) {
                            String stores = reader.readLine();
                            System.out.println(stores);
                            System.out.println("Enter the number for the store you want to purchase from:");
                            int choice = scanner.nextInt();
                            writer.println(choice);
                            writer.flush();

                            String storeOptions = reader.readLine();
                            System.out.println(storeOptions);
                            if (!storeOptions.equals("Invalid response.")) {
                                System.out.println("Enter the number for the product you want to buy:");
                                int select = scanner.nextInt();
                                writer.println(select);
                                writer.flush();
                                String success = reader.readLine();
                                System.out.println(success);
                            } else {
                                System.out.println(storeOptions);
                            }
                        } else {
                            System.out.println("Enter the store name:");
                            String name = scanner.nextLine();
                            writer.println(name);
                            writer.flush();
                            String response = reader.readLine();
                            if (!response.equals("Invalid response.")) {
                                int items = scanner.nextInt();
                                writer.println(items);
                                writer.flush();

                                for (int i = 1; i <= items; i++) {
                                    System.out.println("Name of product " + i + "?");
                                    String item = scanner.nextLine();
                                    writer.println(item);
                                    writer.flush();
                                }
                                String success = reader.readLine();
                                System.out.println(success);
                            } else {
                                System.out.println(response);
                            }
                        }

                    } else if (nextOption.equals("13")) {
                        System.out.println("What text would you like to censor");
                        String censor = scanner.nextLine();
                        writer.println(censor);
                        writer.flush();
                        System.out.println("How would you like to replace the censored texts?\n1" +
                                ".Use default which is ****\n2.Make your own replacement");
                        int choice = scanner.nextInt();
                        writer.println(choice);
                        writer.flush();
                        if (choice == 2) {
                            String out = reader.readLine();
                            if (out.equals("Enter your replacement words")) {
                                System.out.println(out);
                                String rep = scanner.nextLine();
                                writer.println(rep);
                                writer.flush();
                            }
                            else {
                                System.out.println(out);
                            }
                        }
                    }
                } while (loggedIn);

            } else if (initOption.equals("2")) {
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
            } else if (initOption.equals("3")) {
                exit = true;
                System.out.println("Ending application!");
            } else {
                System.out.println("error");
            }
        } while (!exit); //FIX LOOP

    }
}
