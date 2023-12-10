package messaging_system;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.*;

/**
 * Project 5 Option 2
 *
 * This class is responsible for the client side of the messaging system. Directly relates to the processor
 * class which is the server side. Key components of this class contains socket which allows server connections
 * and multithreading, GUI which allows user to view a colorful frontend, and functions that are required
 * to match with the server side, to help the program runs smoothly
 *
 * @author Ishaan, Nandini, Nick, Vinay, Zishuo, LO1
 *
 * @version December 10, 2023
 *
 */

public class Client {
    public static boolean loggedIn;
    public static boolean exit;


    public static void main(String[] args) throws IOException {
        // Socket setup
        // Replace "path_to_custom_icon.png" with the actual path to your custom icon
        ImageIcon customIcon = new ImageIcon("messaging_system/pete.png");

        UIManager.put("OptionPane.informationIcon", customIcon);
        UIManager.put("OptionPane.warningIcon", customIcon);
        UIManager.put("OptionPane.errorIcon", customIcon);
        UIManager.put("OptionPane.questionIcon", customIcon);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 16));
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        UIManager.put("OptionPane.background", new Color(60, 63, 65));
        UIManager.put("Panel.background", new Color(60, 63, 65));
        UIManager.put("Button.background", new Color(75, 110, 175));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("Button.border", BorderFactory.createEmptyBorder(8, 20, 8, 20));


        Socket socket = new Socket();
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            socket = new Socket("localhost", 12345);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        exit = false;
        Scanner scanner = new Scanner(System.in);
        int size = 0;

        // Main menu switch case
        do {
            //System.out.println("Main Menu. Please choose an option.\n1.Login\n2.Create Account\n3.Exit");
            // Drop down
            String[] options = {"1", "2", "3"};
            String menuOption = (String) JOptionPane.showInputDialog(null,
                    "Main Menu. Please choose an option.\n1.Login\n2.Create Account\n3.Exit",
                    "Messaging System", JOptionPane.QUESTION_MESSAGE, null, options,
                    options[0]);
            // sending first choice 1-3
            writer.println(menuOption);
            writer.flush();
            String email = "";
            String password = "";


            switch (menuOption) {
                // dropdown
                case "1":
                    // user logs in
                    // System.out.println("Enter your email:");
                    email = JOptionPane.showInputDialog(null, "Enter your email: ",
                            "Messaging System",
                            JOptionPane.QUESTION_MESSAGE);
                    // email = scanner.nextLine();
                    writer.println(email);
                    writer.flush();
                    password = JOptionPane.showInputDialog(null, "Enter your password: ",
                            "Messaging System",
                            JOptionPane.QUESTION_MESSAGE);
                    // System.out.println("Enter your password:");
                    // password = scanner.nextLine();
                    writer.println(password);
                    writer.flush();
                    String logInResult = reader.readLine();
                    JOptionPane.showMessageDialog(null, logInResult, "Messaging System", JOptionPane.PLAIN_MESSAGE);
                    System.out.println(logInResult);
                    if (logInResult.equals("Email or password is incorrect.")) {
                        break;
                    }
                    loggedIn = true;

                    // Change to a long string and print in a JOption Message
                    int unreadSize = Integer.parseInt(reader.readLine());
                    String messageTitle = reader.readLine();
                    String ret = "";
                    ret += (messageTitle);
                    if (!messageTitle.equals("No new Messages!")) {
                        for (int i = 0; i < unreadSize; i++) {
                            ret += (reader.readLine()) + "\n";
                        }
                    }
                    JOptionPane.showMessageDialog(null, ret, "Messaging System",
                            JOptionPane.PLAIN_MESSAGE);
                    do {
                        String ret2 = "";
                        String userName = reader.readLine();
                        // Change to a long string and print in a JOption Message
                        ret2 += (userName);
                        String sOrC = reader.readLine();
                        // Change to a long string and print in a JOption Message
                        if (sOrC.equals("customer")) {
                            ret2 += ("\n1.See messages\n2.Send message\n3.Edit Account\n4.Delete Account\n"
                                    + "5.Hide User\n6.Block User\n7.Get Statistics\n8.Logout\n" +
                                    "9.Edit Message\n10.Delete Message\n11.Export CSV\n" +
                                    "12.Buy products\n13.Censor Texts");
                        } else {
                            ret2 += ("\n1.See messages\n2.Send message\n3.Edit Account\n" +
                                    "4.Delete Account\n" + "5.Hide User\n6.Block User\n" +
                                    "7.Get Statistics\n8.Logout\n"
                                    + "9.Edit Message\n10.Delete Message\n11.Export CSV\n12." +
                                    "Create Store\n13.Censor Texts");
                        }
                        String[] options2 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
                        String choice = (String) JOptionPane.showInputDialog(null,
                                ret2, "Messaging System", JOptionPane.QUESTION_MESSAGE, null, options2,
                                options2[0]);
                        writer.println(choice);
                        writer.flush();
                        switch (choice) {

                            //See messages
                            case "1":
                                int numMessages = Integer.parseInt(reader.readLine());
                                String msgs = "";
                                msgs += (numMessages + " messages total\n");
                                // Change to a long string and print in a JOption Message
                                for (int i = 0; i < numMessages; i++) {
                                    msgs += reader.readLine() + "\n";
                                }
                                JOptionPane.showMessageDialog(null, msgs, "Messaging System",
                                        JOptionPane.PLAIN_MESSAGE);
                                break;


                            // send messages
                            case "2":
                                sOrC = reader.readLine();
                                String[] msgOption = {"1", "2"};
                                String messageChoice = "";
                                if (sOrC.equals("seller")) {
                                    String question = ("1. View a list of people to message.\n2. Message a specific " +
                                            "user.");
                                    messageChoice = (String) JOptionPane.showInputDialog(null,
                                            question, "Messaging System", JOptionPane.QUESTION_MESSAGE, null, msgOption,
                                            msgOption[0]);

                                } else {
                                    String question = ("1. View a list of stores to message.\n2. Message a specific " +
                                            "user.");
                                    messageChoice = (String) JOptionPane.showInputDialog(null,
                                            question, "Messaging System", JOptionPane.QUESTION_MESSAGE, null,
                                            msgOption, msgOption[0]);
                                }

                                writer.println(messageChoice);
                                writer.flush();
                                switch (messageChoice) {
                                    case "1":
                                        size = Integer.parseInt(reader.readLine());
                                        if (size == 0) {
                                            if (sOrC.equals("customer")) {
                                                JOptionPane.showMessageDialog(null, "There are no stores!", "Messaging System", JOptionPane.PLAIN_MESSAGE);
                                            } else {
                                                JOptionPane.showMessageDialog(null, "There are no customers right " +
                                                        "now!", "Messaging System", JOptionPane.PLAIN_MESSAGE);

                                            }
                                        } else {
                                            String printNames = "";
                                            for (int i = 0; i < size; i++) {
                                                String userNames = reader.readLine();
                                                if (!userNames.equals("hidden")) {
                                                    printNames += (userNames) + "\n";
                                                }
                                            }
                                            JOptionPane.showMessageDialog(null, printNames,
                                                    "Messaging System", JOptionPane.PLAIN_MESSAGE);
                                        }

                                    case "2":
                                        String recipient = JOptionPane.showInputDialog(null, "Who would you like to " +
                                                        "message?",
                                                "Messaging System", JOptionPane.QUESTION_MESSAGE);
                                        writer.println(recipient);
                                        writer.flush();
                                        String blockMessage = reader.readLine();
                                        if (!blockMessage.isEmpty()) {
                                            System.out.println(blockMessage);
                                            JOptionPane.showMessageDialog(null, blockMessage, "Messaging System",
                                                    JOptionPane.PLAIN_MESSAGE);
                                            break;
                                        }
                                        String nullCheck = reader.readLine();
                                        if (!nullCheck.isEmpty()) {
                                            JOptionPane.showMessageDialog(null, nullCheck, "Messaging System",
                                                    JOptionPane.PLAIN_MESSAGE);
                                            break;
                                        }
                                        String q = ("How would you like to send the message?\n1. Type the message\n2." +
                                                " " +
                                                "Import a text file");
                                        //here
                                        String[] choices = {"1", "2"};
                                        String choice2 = (String) JOptionPane.showInputDialog(null,
                                                q, "Messaging System", JOptionPane.QUESTION_MESSAGE, null,
                                                choices, choices[0]);
                                        writer.println(choice2);
                                        writer.flush();
                                        switch (choice2) {
                                            case "1":
                                                String[] array = {"yes", "no"};
                                                String prompt = ("Do you want your message to disappear after it's " +
                                                        "read? " +
                                                        "(Yes/No)");
                                                String disappear = (String) JOptionPane.showInputDialog(null,
                                                        prompt, "Messaging System", JOptionPane.QUESTION_MESSAGE, null,
                                                        array, array[0]);
                                                writer.println(disappear);
                                                writer.flush();
                                                String message = JOptionPane.showInputDialog(null,
                                                        "What is your message?", "Messaging System",
                                                        JOptionPane.QUESTION_MESSAGE);
                                                writer.println(message);
                                                writer.flush();
                                                JOptionPane.showMessageDialog(null, reader.readLine(), "Messaging System",
                                                        JOptionPane.PLAIN_MESSAGE);
                                                break;
                                            case "2":
                                                String file = JOptionPane.showInputDialog(null,
                                                        "What is the text file you would like to import?",
                                                        "Messaging System", JOptionPane.QUESTION_MESSAGE);
                                                writer.println(file);
                                                writer.flush();
                                                break;
                                            default:
                                                JOptionPane.showMessageDialog(null,"Invalid input.", "Messaging System",
                                                        JOptionPane.PLAIN_MESSAGE);
                                                break;
                                        }
                                        break;
                                    default:
                                        JOptionPane.showMessageDialog(null,"Invalid input.", "Messaging System",
                                                JOptionPane.PLAIN_MESSAGE);
                                        break;
                                }

                                break;
                            case "3":
                                email = JOptionPane.showInputDialog(null,
                                        "Enter updated email:", "Messaging System",
                                        JOptionPane.QUESTION_MESSAGE);
                                writer.println(email);
                                writer.flush();
                                String existMessage = reader.readLine();
                                if (!existMessage.equals("0")) {
                                    JOptionPane.showMessageDialog(null,existMessage, "Messaging System",
                                            JOptionPane.PLAIN_MESSAGE);
                                    break;
                                }
                                password = JOptionPane.showInputDialog(null,
                                        "Enter updated password:", "Messaging System",
                                        JOptionPane.QUESTION_MESSAGE);
                                writer.println(password);
                                writer.flush();
                                break;
                            case "4":
                                String deleteMessage = reader.readLine();
                                JOptionPane.showMessageDialog(null, deleteMessage, "Messaging System",
                                        JOptionPane.PLAIN_MESSAGE);
                                loggedIn = false;
                                break;
                            case "5":
                                size = Integer.parseInt(reader.readLine());
                                String out = "Users:\n";
                                for (int i = 0; i < size; i++) {
                                    String userNames = reader.readLine();
                                    if (!userNames.equals("hidden")) {
                                        out += (userNames) + "\n";
                                    }
                                }

                                out += ("\nEnter user you would like to hide from:");
                                String hidden = JOptionPane.showInputDialog(null,
                                        out, "Messaging System",
                                        JOptionPane.QUESTION_MESSAGE);
                                writer.println(hidden);
                                writer.flush();
                                String hiddenMessage = reader.readLine();
                                JOptionPane.showMessageDialog(null, hiddenMessage, "Messaging System",
                                        JOptionPane.PLAIN_MESSAGE);
                                break;
                            case "6":
                                // block users
                                size = Integer.parseInt(reader.readLine());
                                String outBlock = "Users:\n";
                                for (int i = 0; i < size; i++) {
                                    String userNames = reader.readLine();
                                    if (!userNames.equals("hidden")) {
                                        outBlock += (userNames) + "\n";
                                    }
                                }
                                outBlock += ("Enter user you would like to block:\n");
                                String block = JOptionPane.showInputDialog(null,
                                        outBlock, "Messaging System",
                                        JOptionPane.QUESTION_MESSAGE);
                                writer.println(block);
                                writer.flush();
                                String blockMessage = reader.readLine();
                                JOptionPane.showMessageDialog(null, blockMessage, "Messaging System",
                                        JOptionPane.PLAIN_MESSAGE);
                                break;
                            case "7":
                                // get stats
                                String[] choices = {"1","2"};
                                String sort = (String) JOptionPane.showInputDialog(null,
                                        "Would you like to sort your data?\n1. Yes\n2. No",
                                        "Messaging System", JOptionPane.QUESTION_MESSAGE, null,
                                        choices, choices[0]);
                                writer.println(sort);
                                writer.flush();
                                String stats = "";
                                int sizeData = Integer.parseInt(reader.readLine());
                                for (int i = 0; i < sizeData; i++) {
                                    stats += (reader.readLine()) + "\n";
                                }

                                if (sOrC.equalsIgnoreCase("customer")) {
                                    int size2 = Integer.parseInt(reader.readLine());
                                    for (int i = 0; i < size2; i++) {
                                        //check if this should be a separate message
                                        stats += (reader.readLine()) + "\n";
                                    }
                                }
                                JOptionPane.showMessageDialog(null, stats, "Messaging System",
                                        JOptionPane.PLAIN_MESSAGE);
                                break;
                            case "8":
                                //logout
                                String logOutMessage = reader.readLine();
                                JOptionPane.showMessageDialog(null, logOutMessage, "Messaging System",
                                        JOptionPane.PLAIN_MESSAGE);
                                loggedIn = false;
                                break;
                            case "9":
                                // edit message
                                String printNames = "";
                                size = Integer.parseInt(reader.readLine());
                                for (int i = 0; i < size; i++) {
                                    String userNames = reader.readLine();
                                    if(!userNames.equals("hidden")){
                                        printNames += (userNames) + "\n";
                                    }
                                }
                                JOptionPane.showMessageDialog(null, printNames,
                                        "Messaging System", JOptionPane.PLAIN_MESSAGE);


                                String editedMessage = JOptionPane.showInputDialog(null,
                                        printNames + "\nEnter the number of the message you would like to edit:",
                                        "Messaging System",
                                        JOptionPane.QUESTION_MESSAGE);
                                writer.println(editedMessage);
                                writer.flush();
                                String enteredTry = reader.readLine();
                                if (enteredTry.equals("entered try.")) {
                                    String update = JOptionPane.showInputDialog(null,
                                            printNames + "What would you like the message to say?",
                                            "Messaging System",
                                            JOptionPane.QUESTION_MESSAGE);
                                    writer.println(update);
                                    writer.flush();
                                    JOptionPane.showMessageDialog(null, "Message Updated",
                                            "Messaging System", JOptionPane.PLAIN_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, printNames,
                                            "Messaging System", JOptionPane.PLAIN_MESSAGE);
                                }

                                break;
                            case "10":
                                // delete message
                                String printMsgs = "";
                                int msgsSize = Integer.parseInt(reader.readLine());
                                if (msgsSize == 0) {
                                    printMsgs = reader.readLine();
                                    JOptionPane.showMessageDialog(null, printMsgs,
                                            "Messaging System", JOptionPane.PLAIN_MESSAGE);
                                } else {
                                    for (int i = 0; i < msgsSize; i++) {
                                        String userNames = reader.readLine();
                                        if(!userNames.equals("hidden")){
                                            printMsgs += (userNames) + "\n";
                                        }
                                    }






                                    String index = JOptionPane.showInputDialog(null,
                                            printMsgs + "\nEnter the number of the message you would like to delete",
                                            "Messaging System",
                                            JOptionPane.QUESTION_MESSAGE);
                                    writer.println(index);
                                    writer.flush();
                                    String result = reader.readLine();
                                    JOptionPane.showMessageDialog(null, result,
                                            "Messaging System", JOptionPane.PLAIN_MESSAGE);
                                }
                                break;
                            case "11":
                                // export csv
                                String printUser = "";
                                if (sOrC.equalsIgnoreCase("customer")) {
                                    int sellerSize = Integer.parseInt(reader.readLine());
                                    for (int i = 0; i < sellerSize; i++) {
                                        String userNames = reader.readLine();
                                        if (!userNames.equals("hidden")) {
                                            printUser += (userNames) + "\n";
                                        }
                                    }
                                } else if (sOrC.equalsIgnoreCase("seller")) {
                                    int customerSize = Integer.parseInt(reader.readLine());
                                    for (int i = 0; i < customerSize; i++) {
                                        String userNames = reader.readLine();
                                        if (!userNames.equals("hidden")) {
                                            printUser += (userNames) + "\n";
                                        }
                                    }

                                }
                                String name = JOptionPane.showInputDialog(null,
                                        printUser + "\nWhose conversation would you like to export (leave blank for all).",
                                        "Messaging System",
                                        JOptionPane.QUESTION_MESSAGE);
                                writer.println(name);
                                writer.flush();
                                JOptionPane.showMessageDialog(null, "Exported!",
                                        "Messaging System", JOptionPane.PLAIN_MESSAGE);

                                break;
                            case "12":
                                // create store / buy products
                                if (sOrC.equalsIgnoreCase("customer")) {
                                    String printStore = "";
                                    int storeSize = Integer.parseInt(reader.readLine());
                                    if (storeSize == 0) {
                                        JOptionPane.showMessageDialog(null, "No Stores Available!",
                                                "Messaging System", JOptionPane.PLAIN_MESSAGE);
                                    } else {


                                        for (int i = 0; i < storeSize; i++) {
                                            String userNames = reader.readLine();
                                            printStore += (userNames) + "\n";
                                        }
                                        String store = JOptionPane.showInputDialog(null,
                                                printStore + "\nEnter the number for the store you want to purchase from:",
                                                "Messaging System",
                                                JOptionPane.QUESTION_MESSAGE);
                                        writer.println(store);
                                        writer.flush();
                                        String printItems = "";
                                        int itemsSize = Integer.parseInt(reader.readLine());
                                        for (int i = 0; i < itemsSize; i++) {
                                            String userNames = reader.readLine();
                                            printItems += (userNames) + "\n";
                                        }


                                        String item = JOptionPane.showInputDialog(null,
                                                printStore + "\nEnter the number for the store you want to purchase from:",
                                                "Messaging System",
                                                JOptionPane.QUESTION_MESSAGE);
                                        writer.println(item);
                                        writer.flush();
                                        String result = reader.readLine();
                                        JOptionPane.showMessageDialog(null, result,
                                                "Messaging System", JOptionPane.PLAIN_MESSAGE);
                                    }
                                } else if (sOrC.equalsIgnoreCase("seller")) {
                                    String storeName = JOptionPane.showInputDialog(null,
                                            "Enter the store name:",
                                            "Messaging System",
                                            JOptionPane.QUESTION_MESSAGE);
                                    writer.println(storeName);
                                    writer.flush();
                                    System.out.println("How many items will you be selling?");
                                    String items = JOptionPane.showInputDialog(null,
                                            "How many items will you be selling?",
                                            "Messaging System",
                                            JOptionPane.QUESTION_MESSAGE);
                                    writer.println(items);
                                    writer.flush();
                                    for (int i = 1; i <= Integer.parseInt(items); i++) {
                                        String itemName = JOptionPane.showInputDialog(null,
                                                "Name of product " + i + "?",
                                                "Messaging System",
                                                JOptionPane.QUESTION_MESSAGE);
                                        writer.println(itemName);
                                        writer.flush();
                                    }
                                    String resultStore = reader.readLine();
                                    JOptionPane.showMessageDialog(null, resultStore,
                                            "Messaging System", JOptionPane.PLAIN_MESSAGE);
                                }
                                break;
                            case "13":
                                // censor texts
                                String censor = JOptionPane.showInputDialog(null,
                                        "What text would you like to censor",
                                        "Messaging System",
                                        JOptionPane.QUESTION_MESSAGE);

                                writer.println(censor);
                                writer.flush();

                                String censorChoice = JOptionPane.showInputDialog(null,
                                        "How would you like to replace the censored texts?\n1" +
                                                "Use default which is ****\n2.Make your own replacement",
                                        "Messaging System",
                                        JOptionPane.QUESTION_MESSAGE);

                                writer.println(censorChoice);
                                writer.flush();
                                if (censorChoice.equals("2")) {
                                    String replace = JOptionPane.showInputDialog(null,
                                            "Enter your replacement words",
                                            "Messaging System",
                                            JOptionPane.QUESTION_MESSAGE);
                                    writer.println(replace);
                                    writer.flush();
                                } else if (!censorChoice.equals("1")) {
                                    JOptionPane.showMessageDialog(null, "Invalid Input!",
                                            "Messaging System", JOptionPane.PLAIN_MESSAGE);
                                }
                                break;

                        }
                    } while (loggedIn);

                    break;

                case "2":
                    // user create account
                    email = JOptionPane.showInputDialog(null,
                            "Enter your email",
                            "Messaging System",
                            JOptionPane.QUESTION_MESSAGE);
                    writer.println(email);
                    writer.flush();

                    String successfulCreation = reader.readLine();
                    if (!successfulCreation.equals("Account with that email already exists.")) {
                        //System.out.println("Enter your password:");
                        password = JOptionPane.showInputDialog(null,
                                "Enter your password",
                                "Messaging System",
                                JOptionPane.QUESTION_MESSAGE);
                        writer.println(password);
                        writer.flush();


                        String accountChoice = JOptionPane.showInputDialog(null,
                                "Choose type of account to create:\n1.Customer\n2.Seller\n",
                                "Messaging System",
                                JOptionPane.QUESTION_MESSAGE);
                        writer.println(accountChoice);
                        writer.flush();

                        String finalMessage = reader.readLine();
                        JOptionPane.showMessageDialog(null, finalMessage,
                                "Messaging System", JOptionPane.PLAIN_MESSAGE);

                    } else {
                        JOptionPane.showMessageDialog(null, "Account with that email already exists!",
                                "Messaging System", JOptionPane.PLAIN_MESSAGE);
                    }
                    break;

                case "3":
                    // user exits application
                    exit = true;
                    String exitMessage = reader.readLine();
                    JOptionPane.showMessageDialog(null, exitMessage,
                            "Messaging System", JOptionPane.PLAIN_MESSAGE);
                    break;
            }
        } while (!exit);
    }
}
