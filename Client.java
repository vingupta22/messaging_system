package messaging_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    static BufferedReader reader = null;
    static PrintWriter writer = null;
    private static JFrame frame;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;

    public static void main(String[] args) throws IOException {
        // Socket setup
        Socket socket = new Socket();


        try {
            socket = new Socket("localhost", 12345);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        exit = false;
        Scanner scanner = new Scanner(System.in);
        int size = 0;
        // Main menu switch case

        frame = new JFrame("Messaging System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Create and add panels to the mainPanel
        JPanel homeScreen = new JPanel();
        JPanel loginScreen = new JPanel();
        JPanel createScreen = new JPanel();
        JPanel selectionScreen = new JPanel();
        JPanel seeMsgScreen = new JPanel();
        JPanel sendMsgScreen = new JPanel();
        JPanel editAccntScreen = new JPanel();
        JPanel hideScreen = new JPanel();
        JPanel blockScreen = new JPanel();
        JPanel statisticsScreen = new JPanel();
        JPanel deleteMsgScreen = new JPanel();
        JPanel editMsgScreen = new JPanel();
        JPanel censorScreen = new JPanel();
        JPanel buyScreen = new JPanel();
        JPanel storeScreen = new JPanel();

        mainPanel.add(homeScreen, "Home");
        mainPanel.add(loginScreen, "Login");
        mainPanel.add(createScreen, "Create Account");
        mainPanel.add(selectionScreen, "Selection Screen");
        mainPanel.add(seeMsgScreen, "See Messages");
        mainPanel.add(sendMsgScreen, "Send Messages");
        mainPanel.add(editAccntScreen, "Edit Account");
        mainPanel.add(hideScreen, "Hide Account");
        mainPanel.add(blockScreen, "Block Account");
        mainPanel.add(statisticsScreen, "Statistics");
        mainPanel.add(deleteMsgScreen, "Delete Message");
        mainPanel.add(editMsgScreen, "Edit Message");
        mainPanel.add(censorScreen, "Censor Message");
        mainPanel.add(buyScreen, "Buy Product");
        mainPanel.add(storeScreen, "Create Store");

        // Create buttons to switch between panels
        JButton login = new JButton("Login");
        JButton createAccount = new JButton("Create Account");
        JButton leave = new JButton("Exit");

        homeScreen.add(login);
        homeScreen.add(createAccount);
        homeScreen.add(leave);

        login.addActionListener(e -> cardLayout.show(mainPanel, "Login")); //switches to login panel
        createAccount.addActionListener(e -> cardLayout.show(mainPanel, "Create Account")); //switches to create  panel
        leave.addActionListener(e -> frame.dispose()); //closes program if exit button clicked


        //makes each separate panel
        makeLoginScreen(loginScreen);
        // Add mainPanel and buttonPanel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);


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
                    if (logInResult.equals("Email or password is incorrect.")) {
                        break;
                    }
                    loggedIn = true;


                    int unreadSize = Integer.parseInt(reader.readLine());
                    String messageTitle = reader.readLine();
                    System.out.println(messageTitle);
                    if (!messageTitle.equals("No new Messages!")) {
                        for (int i = 0; i < unreadSize; i++) {
                            System.out.println(reader.readLine());
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
                                String messageChoice = scanner.nextLine();
                                writer.println(messageChoice);
                                writer.flush();
                                switch (messageChoice) {
                                    case "1":
                                        size = Integer.parseInt(reader.readLine());
                                        if (size == 0) {
                                            if (sOrC.equals("customer")) {
                                                System.out.println("There are no stores!");
                                            } else {
                                                System.out.println("There are no customers right now!");
                                            }
                                        }
                                        for (int i = 0; i < size; i++) {
                                            String userNames = reader.readLine();
                                            if(!userNames.equals("hidden")) {
                                                System.out.println(userNames);
                                            }
                                        }

                                    case "2":
                                        System.out.println("Who would you like to message?");
                                        String recipient = scanner.nextLine();
                                        writer.println(recipient);
                                        writer.flush();
                                        String blockMessage = reader.readLine();
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
                                System.out.println("Enter updated email:");
                                email = scanner.nextLine();
                                writer.println(email);
                                writer.flush();
                                String existMessage = reader.readLine();
                                if(!existMessage.equals("0")){
                                    System.out.println(existMessage);
                                    break;
                                }
                                System.out.println("Enter updated password:");
                                password = scanner.nextLine();
                                writer.println(password);
                                writer.flush();
                                break;
                            case "4":
                                String deleteMessage = reader.readLine();
                                System.out.println(deleteMessage);
                                loggedIn = false;
                                break;
                            case "5":
                                size = Integer.parseInt(reader.readLine());
                                for (int i = 0; i < size; i++) {
                                    String userNames = reader.readLine();
                                    if(!userNames.equals("hidden")) {
                                        System.out.println(userNames);
                                    }
                                }
                                System.out.println("Enter user you would like to hide from:");
                                String hidden = scanner.nextLine();
                                writer.println(hidden);
                                writer.flush();
                                String hiddenMessage = reader.readLine();
                                System.out.println(hiddenMessage);
                                break;
                            case "6":
                                // block users
                                size = Integer.parseInt(reader.readLine());
                                for (int i = 0; i < size; i++) {
                                    String userNames = reader.readLine();
                                    if(!userNames.equals("hidden")) {
                                        System.out.println(userNames);
                                    }
                                }
                                System.out.println("Enter user you would like to block:");
                                String block = scanner.nextLine();
                                writer.println(block);
                                writer.flush();
                                String blockMessage = reader.readLine();
                                System.out.println(blockMessage);

                                break;
                            case "7":
                                // get stats
                                System.out.println("Would you like to sort your data?\n1. Yes\n2. No");
                                String sort = scanner.nextLine();
                                writer.println(sort);
                                writer.flush();

                                int sizeData = Integer.parseInt(reader.readLine());
                                for (int i = 0; i < sizeData; i++) {
                                    System.out.println(reader.readLine());
                                }

                                if (sOrC.equalsIgnoreCase("customer")) {
                                    int size2 = Integer.parseInt(reader.readLine());
                                    for (int i = 0; i < size2; i++) {
                                        System.out.println(reader.readLine());
                                    }
                                }
                                break;
                            case "8":
                                //logout
                                String logOutMessage = reader.readLine();
                                System.out.println(logOutMessage);
                                loggedIn = false;
                                break;
                            case "9":
                                // edit message
                                size = Integer.parseInt(reader.readLine());
                                for (int i = 0; i < size; i++) {
                                    System.out.println(reader.readLine());
                                }
                                System.out.println("Enter the number of the message you would like to edit:");
                                String editedMessage = scanner.nextLine();
                                writer.println(editedMessage);
                                writer.flush();
                                String enteredTry = reader.readLine();
                                if (enteredTry.equals("entered try.")) {
                                    System.out.println("What would you like the message to say now.");
                                    String update = scanner.nextLine();
                                    writer.println(update);
                                    writer.flush();
                                    System.out.println("Message updated.");
                                } else {
                                    System.out.println("Invalid response.");
                                }

                                break;
                            case "10":
                                // delete message
                                int msgsSize = Integer.parseInt(reader.readLine());
                                if (msgsSize == 0) {
                                    System.out.println(reader.readLine());
                                } else {
                                    for (int i = 0; i < msgsSize; i++) {
                                        System.out.println(reader.readLine());
                                    }
                                    System.out.println("Enter the number of the message you would like to delete:");
                                    String index = scanner.nextLine();
                                    writer.println(index);
                                    writer.flush();
                                    String result = reader.readLine();
                                    System.out.println(result);
                                }
                                break;
                            case "11":
                                // export csv
                                if (sOrC.equalsIgnoreCase("customer")) {
                                    int sellerSize = Integer.parseInt(reader.readLine());
                                    for (int i = 0; i < sellerSize; i++) {
                                        System.out.println(reader.readLine());
                                    }
                                } else if (sOrC.equalsIgnoreCase("seller")) {
                                    int customerSize = Integer.parseInt(reader.readLine());
                                    for (int i = 0; i < customerSize; i++) {
                                        System.out.println(reader.readLine());
                                    }

                                }
                                System.out.println("Whose conversation would you like to export (leave blank for all).");
                                String name = scanner.nextLine();
                                writer.println(name);
                                writer.flush();
                                System.out.println("Exported!");

                                break;
                            case "12":
                                // create store / buy products
                                if (sOrC.equalsIgnoreCase("customer")) {
                                    int storeSize = Integer.parseInt(reader.readLine());
                                    if (storeSize == 0) {
                                        System.out.println("No available stores!");
                                    } else {
                                        for (int i = 0; i < storeSize; i++) {
                                            System.out.println(reader.readLine());
                                        }
                                        System.out.println("Enter the number for the store you want to purchase from:");
                                        String store = scanner.nextLine();
                                        writer.println(store);
                                        writer.flush();
                                        int itemsSize = Integer.parseInt(reader.readLine());
                                        for (int i = 0; i < itemsSize; i++) {
                                            System.out.println(reader.readLine());
                                        }
                                        System.out.println("Enter the number for the product you want to buy:");
                                        String item = scanner.nextLine();
                                        writer.println(item);
                                        writer.flush();
                                        String result = reader.readLine();
                                        System.out.println(result);
                                    }
                                } else if (sOrC.equalsIgnoreCase("seller")) {
                                    System.out.println("Enter the store name:");
                                    String storeName = scanner.nextLine();
                                    writer.println(storeName);
                                    writer.flush();
                                    System.out.println("How many items will you be selling?");
                                    String items = scanner.nextLine();
                                    writer.println(items);
                                    writer.flush();
                                    for (int i = 1; i <= Integer.parseInt(items); i++) {
                                        System.out.println("Name of product " + i + "?");
                                        writer.println(scanner.nextLine());
                                        writer.flush();
                                    }
                                    String resultStore = reader.readLine();
                                    System.out.println(resultStore);
                                }
                                break;
                            case "13":
                                // censor texts
                                System.out.println("What text would you like to censor");
                                String censor = scanner.nextLine();
                                writer.println(censor);
                                writer.flush();
                                System.out.println("How would you like to replace the censored texts?\n1" +
                                        ".Use default which is ****\n2.Make your own replacement");
                                String censorChoice = scanner.nextLine();
                                writer.println(censorChoice);
                                writer.flush();
                                if (censorChoice.equals("2")) {
                                    System.out.println("Enter your replacement words");
                                    String replace = scanner.nextLine();
                                    writer.println(replace);
                                    writer.flush();
                                } else if (!censorChoice.equals("1")) {
                                    System.out.println("Invalid input.");
                                }
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

    public static void makeLoginScreen(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel userPanel = new JPanel();
        JPanel passPanel = new JPanel();
        JPanel bottom = new JPanel();
        panel.add(userPanel);
        panel.add(passPanel);
        panel.add(bottom);
        JLabel userLabel = new JLabel("Username:");

        JTextField username = new JTextField(20);
        userPanel.add(userLabel);
        userPanel.add(username);
        JLabel passLabel = new JLabel("Password:");
        JTextField password = new JTextField(20);
        passPanel.add(passLabel);
        passPanel.add(password);

        JButton login = new JButton("Login");
        bottom.add(login);
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //Check if username and password is valid, if not show error, feel free to switch order
                if (true) {
                    cardLayout.show(mainPanel, "Selection Screen");
                } else if (password.getText().isEmpty() || username.getText().isEmpty()) { //if either box is null
                    JOptionPane.showMessageDialog(null, "Username/Password cannot be blank!",
                            "Messaging System", JOptionPane.ERROR_MESSAGE);
                } else { //incorrect username or password
                    JOptionPane.showMessageDialog(null, "Incorrect username/password!",
                            "Messaging System", JOptionPane.ERROR_MESSAGE);
                }

                writer.write("test, normally username and password would be written here");
                writer.flush();
                System.out.println("Written!");


            }
        });
    }
}
