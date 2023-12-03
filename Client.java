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

            switch (menuOption) {

                case "1":
                    // user logs in

                    do {


                        switch ("") {

                            case "1":
                                // see message
                                break;
                            case "2":
                                // send message
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
                                // logout
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
                    String email = scanner.nextLine();
                    writer.println(email);
                    writer.flush();

                    String successfulCreation = reader.readLine();
                    if (!successfulCreation.equals("Account with that email already exists."))
                    {
                        System.out.println("Enter your password:");
                        String password = scanner.nextLine();
                        writer.println(password);
                        writer.flush();

                        System.out.println("Choose type of account to create:\n1.Customer\n2.Seller\n");
                        String accountChoice = scanner.nextLine();
                        writer.println(accountChoice);
                        writer.flush();

                        String finalMessage = reader.readLine();
                        System.out.println(finalMessage);

                    }
                    else {
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