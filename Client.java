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
    static String accType = "hi";
    static String acountExists = "no";
    static int numMessages = 0;
    static String message = "one";


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
        //System.out.println(reader.readLine());
        //System.out.println("yo");

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


        //login.addActionListener(e -> cardLayout.show(mainPanel, "Login")); //switches to login panel
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("1");
                writer.flush();
                System.out.println("Written!");


                cardLayout.show(mainPanel, "Login");



            }
        });

        createAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("2");
                writer.flush();
                System.out.println("Written!");
                cardLayout.show(mainPanel, "Create Account");


            }
        });

        leave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("3");
                writer.flush();
                System.out.println("Written!");
                frame.dispose();


            }
        });


        //createAccount.addActionListener(e -> cardLayout.show(mainPanel, "Create Account")); //switches to create  panel
        //leave.addActionListener(e -> frame.dispose()); //closes program if exit button clicked


        //makes each separate panel
        //makeLoginScreen(loginScreen);
        // Add mainPanel and buttonPanel to the frame
        makeLoginScreen(loginScreen);
        makeCreateScreen(createScreen);
        makeSelectionScreen(selectionScreen);
        makeSeeMsgScreen(seeMsgScreen);
        makeSendMsgScreen(sendMsgScreen);
        makeEditAccntScreen(editAccntScreen);
        makeHideScreen(hideScreen);
        makeBlockScreen(blockScreen);
        makeStatisticsScreen(statisticsScreen);
        makeDeleteMsgScreen(deleteMsgScreen);
        makeEditMsgScreen(editMsgScreen);
        makeCensorScreen(censorScreen);
        makeBuyScreen(buyScreen);
        makeStoreScreen(storeScreen);

        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);


        /*do {
            System.out.println("Main Menu. Please choose an option.\n1.Login\n2.Create Account\n3.Exit");
            String menuOption = scanner.nextLine();
            // sending first choice 1-3



            switch (menuOption) {

                case "1":
                    // user logs in
                    System.out.println("Enter your email:");
                    String email = scanner.nextLine();
                    writer.println(email);
                    writer.flush();
                    System.out.println("Enter your password:");
                    String password = scanner.nextLine();
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
        } while (!exit); */


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
                writer.println(username.getText());
                writer.flush();
                writer.println(password.getText());
                writer.flush();

                try {
                    if (reader.readLine().equalsIgnoreCase("Logged in!")) {
                        try {
                            accType = reader.readLine();
                            System.out.println(accType);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        cardLayout.show(mainPanel, "Selection Screen");
                    } else if (password.getText().isEmpty() || username.getText().isEmpty()) { //if either box is null
                        JOptionPane.showMessageDialog(null, "Username/Password cannot be blank!",
                                "Messaging System", JOptionPane.ERROR_MESSAGE);
                    } else { //incorrect username or password
                        System.out.println("being rannnnn");
                        JOptionPane.showMessageDialog(null, "Incorrect username/password!",
                                "Messaging System", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }




            }
        });

    }
    public static void makeCreateScreen(JPanel panel) throws IOException {

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel userPanel = new JPanel();
        JPanel passPanel = new JPanel();
        // temporary just needed to collect account type, will change/handle errors
        JPanel typePanel = new JPanel();
        JPanel bottom = new JPanel();
        panel.add(userPanel);
        panel.add(passPanel);
        panel.add(typePanel);
        panel.add(bottom);
        JLabel userLabel = new JLabel("Username:");

        JTextField username = new JTextField(20);

        userPanel.add(userLabel);
        userPanel.add(username);
        JLabel passLabel = new JLabel("Password:");
        JTextField password = new JTextField(20);
        JLabel typeLabel = new JLabel("Type: ");
        JTextField type = new JTextField(20);
        //JButton customerButton = new JButton("Customer");
        passPanel.add(passLabel);
        passPanel.add(password);
        typePanel.add(typeLabel);
        typePanel.add(type);


        JButton create = new JButton("Create Account");


        //bottom.add(sellerButton);
        //bottom.add(customerButton);
        bottom.add(create);
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                writer.println(username.getText());
                writer.flush();
                writer.println(password.getText());
                writer.flush();
                if (type.getText().equalsIgnoreCase("customer")) {
                    writer.println("1");
                    writer.flush();

                } else if (type.getText().equalsIgnoreCase("seller")) {
                    writer.println("2");
                    writer.flush();


                }
                try {
                    acountExists = reader.readLine();



                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                //Check if username and password is valid, if not show error, feel free to switch order
                if (acountExists.equalsIgnoreCase("Account with that email already exists.")) {
                    JOptionPane.showMessageDialog(null, "Username is taken!",
                            "Messaging System", JOptionPane.ERROR_MESSAGE);

                } else if (password.getText().isEmpty() || username.getText().isEmpty()) { //if either box is null
                    JOptionPane.showMessageDialog(null, "Username/Password cannot be blank!",
                            "Messaging System", JOptionPane.ERROR_MESSAGE);
                } else { //if username is taken

                    cardLayout.show(mainPanel, "Home");
                }


            }
        });
    }


    public static void makeSelectionScreen(JPanel panel) throws IOException {
        //String accType = null;
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JLabel username = new JLabel("User: "/* + Add username here */);

        JPanel top = new JPanel();
        panel.add(top);
        top.add(username);

        JPanel center = new JPanel();
        JLabel unread = new JLabel("You have 0 unread messages!"); //add functionality here
        center.add(unread);
        panel.add(center);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(0, 3));
        JButton seeMsg = new JButton("See Messages");
        buttons.add(seeMsg);
        seeMsg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("1");
                writer.flush();
                //System.out.println("Written!");
                cardLayout.show(mainPanel, "See Messages");


            }
        });
        //seeMsg.addActionListener(e -> cardLayout.show(mainPanel, "See Messages"));
        JButton sendMsg = new JButton("Send Messages");
        buttons.add(sendMsg);
        sendMsg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("2");
                writer.flush();
                cardLayout.show(mainPanel, "Send Messages");


            }
        });
        //sendMsg.addActionListener(e -> cardLayout.show(mainPanel, "Send Messages"));
        JButton editAccnt = new JButton("Edit Account");
        buttons.add(editAccnt);
        //editAccnt.addActionListener(e -> cardLayout.show(mainPanel, "Edit Account"));
        editAccnt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("3");
                writer.flush();
                cardLayout.show(mainPanel, "Edit Account");


            }
        });
        JButton deleteAccnt = new JButton("Delete Account");
        buttons.add(deleteAccnt);

        deleteAccnt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("4");
                writer.flush();


            }
        });

        //deleteAccnt.addActionListener(e -> System.out.println()); //remove the print just call the delete account method
        JButton hide = new JButton("Hide User");
        buttons.add(hide);
        hide.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("5");
                writer.flush();
                cardLayout.show(mainPanel, "Hide Account");


            }
        });
        //hide.addActionListener(e -> cardLayout.show(mainPanel, "Hide Account"));
        JButton block = new JButton("Block User");
        buttons.add(block);
        block.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("6");
                writer.flush();
                cardLayout.show(mainPanel, "Block Account");


            }
        });
        //block.addActionListener(e -> cardLayout.show(mainPanel, "Block Account"));
        JButton statistics = new JButton("Get Statistics");
        buttons.add(statistics);
        //statistics.addActionListener(e -> cardLayout.show(mainPanel, "Statistics"));
        statistics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("7");
                writer.flush();
                cardLayout.show(mainPanel, "Statistics");


            }
        });
        JButton logout = new JButton("Logout");
        buttons.add(logout);
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("8");
                writer.flush();
                cardLayout.show(mainPanel, "Home");


            }
        });
        JButton editMsg = new JButton("Edit Messages");
        buttons.add(editMsg);
        editMsg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("9");
                writer.flush();
                cardLayout.show(mainPanel, "Edit Message");


            }
        });
        JButton deleteMsg = new JButton("Delete Messages");
        buttons.add(deleteMsg);
        deleteMsg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("10");
                writer.flush();
                cardLayout.show(mainPanel, "Delete Message");


            }
        });

        JButton censor = new JButton("Censor Messages");
        buttons.add(censor);
        censor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("13");
                writer.flush();
                cardLayout.show(mainPanel, "Censor Message");


            }
        });
        JButton export = new JButton("Export CSV");
        buttons.add(export);
        export.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                writer.println("11");
                writer.flush();


            }
        });
    //remove the print just call the export csv method

        //Next two are dependent on buyer/seller
        //String accType = reader.readLine();
        System.out.println(accType);
        System.out.println("it's null :(");



        if (accType.equalsIgnoreCase("customer")) { //for buyer
            JButton buy = new JButton("Buy Products");
            buttons.add(buy);
            buy.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {


                    writer.println("12");
                    writer.flush();
                    cardLayout.show(mainPanel, "Buy Product");

                }
            });
        } else { //for seller
            JButton store = new JButton("Create Store");
            buttons.add(store);
            store.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {


                    writer.println("12");
                    writer.flush();
                    cardLayout.show(mainPanel, "Create Store");

                }
            });
        }
        panel.add(buttons);


    }





    public static void makeSeeMsgScreen(JPanel panel) throws IOException {
        //writer.write("1");
        //writer.flush();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel messages = new JLabel("Messages:\n");
        JTextArea messagesText = new JTextArea("");
        //I would include all messages in this label, just iterate through the list

        //numMessages = Integer.parseInt(reader.readLine());
        String allMessages = "";
        System.out.println(numMessages);
        for (var i = 0; i < numMessages; i++) {

            //message = reader.readLine();
            allMessages.concat(message);


        }
        messagesText.setText(allMessages);


        JButton back = new JButton("Go Back");
        back.addActionListener(e -> cardLayout.show(mainPanel, "Selection Screen"));
        panel.add(messages);
        panel.add(back);
    }

    public static void makeSendMsgScreen(JPanel panel) {
        panel.setLayout(new GridLayout(4, 1));
        JLabel recLabel = new JLabel("Recipient:");
        JComboBox<String> recipients = new JComboBox();

        JPanel top = new JPanel();
        top.setLayout(new GridLayout(0, 2));
        panel.add(top);

        //need a for loop adding all the recipients here
        //consider hidden/blocked accounts
        recipients.addItem("Person 1");
        recipients.addItem("Person 2");

        top.add(recLabel);
        top.add(recipients);

        JLabel msgLabel = new JLabel("Enter Your Message Here\n(Leave Blank for File Input)");
        JLabel impLabel = new JLabel("Enter File Name Here\n(Leave Blank for Text Input)");
        JLabel recpLabel = new JLabel("Or type in your recipient:");


        JTextField message = new JTextField();
        JTextField fileName = new JTextField();
        JTextField recp = new JTextField();

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(2, 2));
        center.add(msgLabel);
        center.add(message);
        center.add(impLabel);
        center.add(fileName);
        center.add(recpLabel);
        center.add(recp);
        panel.add(center);


        JButton send = new JButton("Send Message");
        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writer.println("2");
                writer.flush();
                writer.println(recp.getText());
                try {
                    if (reader.readLine().equalsIgnoreCase("You cannot message that customer.") || reader.readLine().equalsIgnoreCase("You cannot message that seller.")) {
                        JOptionPane.showMessageDialog(null, "You cannot message that person!",
                                "Messaging System", JOptionPane.ERROR_MESSAGE);
                    } else if (reader.readLine().equalsIgnoreCase("You cannot message that store.")) {
                        JOptionPane.showMessageDialog(null, "You cannot message that store!",
                                "Messaging System", JOptionPane.ERROR_MESSAGE);
                    } else if (reader.readLine().equalsIgnoreCase("Invalid recipient.")) {
                        JOptionPane.showMessageDialog(null, "Invalid recipient!",
                                "Messaging System", JOptionPane.ERROR_MESSAGE);
                    } else {

                        if (message.getText().isEmpty()) {
                            writer.println("2");
                            writer.flush();
                            writer.println(fileName.getText());
                            writer.flush();
                        } else {
                            writer.println("1");
                            writer.flush();
                            int option = JOptionPane.showConfirmDialog(null,"Do you want the message to disappear?", "Messaging System",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                            if (option == JOptionPane.YES_OPTION) {
                                writer.write("yes");
                                writer.flush();
                            } else {
                                writer.write("no");
                                writer.flush();
                            }
                            writer.println(message.getText());
                            writer.flush();

                        }

                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }


        });
        //send.addActionListener(e -> cardLayout.show(mainPanel, "Selection Screen"));

        panel.add(send);
    }

    public static void makeEditAccntScreen(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JPanel userPanel = new JPanel();
        JPanel passPanel = new JPanel();
        JPanel bottom = new JPanel();
        panel.add(userPanel);
        panel.add(passPanel);
        panel.add(bottom);
        JLabel userLabel = new JLabel("Updated Username:");

        JTextField username = new JTextField(20);
        userPanel.add(userLabel);
        userPanel.add(username);
        JLabel passLabel = new JLabel("Updated Password:");
        JTextField password = new JTextField(20);
        passPanel.add(passLabel);
        passPanel.add(password);

        JButton update = new JButton("Update");
        bottom.add(update);
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                writer.println(username.getText());
                writer.flush();
                writer.println(password.getText());
                writer.flush();


                //Check if username and password is valid, if not show error, feel free to switch order
                try {
                    if (reader.readLine().equalsIgnoreCase("Account with that email already exists.")) {

                        JOptionPane.showMessageDialog(null, "Username is taken",
                                "Messaging System", JOptionPane.ERROR_MESSAGE);
                    } else if (password.getText().isEmpty() || username.getText().isEmpty()) { //if either box is null
                        JOptionPane.showMessageDialog(null, "Username/Password cannot be blank!",
                                "Messaging System", JOptionPane.ERROR_MESSAGE);
                    } else { //if username is taken
                        cardLayout.show(mainPanel, "Selection Screen");

                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
    }

    public static void makeHideScreen(JPanel panel) {
        panel.setLayout(new GridLayout(2, 0));
        JLabel recLabel = new JLabel("Hide from Which User:");
        JComboBox<String> users = new JComboBox();

        JPanel top = new JPanel();
        top.setLayout(new GridLayout(0, 2));
        panel.add(top);

        //need a for loop adding all the users here
        users.addItem("Person 1");
        users.addItem("Person 2");

        //top.add(recLabel);
        //top.add(users);
        JLabel hideLabel = new JLabel("Type in who you want to block:");
        JTextField userHide = new JTextField(20);
        top.add(hideLabel);
        top.add(userHide);

        panel.add(top);

        JButton hide = new JButton("Hide");
        hide.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e){
                writer.println(userHide.getText());
                writer.flush();

                try {
                    if (reader.readLine().equalsIgnoreCase("User hidden.")) {
                        JOptionPane.showConfirmDialog(null, "User hidden.", "Messaging System", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                cardLayout.show(mainPanel, "Selection Screen");

            }

        });

        panel.add(hide);
    }
    //hide and block very similar
    public static void makeBlockScreen(JPanel panel) {
        panel.setLayout(new GridLayout(2, 0));
        JLabel recLabel = new JLabel("Block Which User:");
        JComboBox<String> users = new JComboBox();

        JPanel top = new JPanel();
        top.setLayout(new GridLayout(0, 2));
        panel.add(top);

        JLabel blockLabel = new JLabel("Type in who you want to block:");
        JTextField userBlock = new JTextField(20);



        //need a for loop adding all the users here
        users.addItem("Person 1");
        users.addItem("Person 2");

        //top.add(recLabel);
        //top.add(users);
        top.add(blockLabel);
        top.add(userBlock);

        panel.add(top);

        JButton block = new JButton("Block");


        block.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e){
                writer.println(userBlock.getText());
                writer.flush();
                try {
                    if (reader.readLine().equalsIgnoreCase("User blocked.")) {
                        JOptionPane.showConfirmDialog(null, "User blocked.", "Messaging System", JOptionPane.PLAIN_MESSAGE);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                cardLayout.show(mainPanel, "Selection Screen");

            }
        });

        panel.add(block);
    }

    public static void makeStatisticsScreen(JPanel panel) {
        panel.setLayout(new GridLayout(4, 0));
        JButton sort = new JButton("Sort");
        //sort the data based on the value of this toggle
        //panel.add(sort);
        int option = JOptionPane.showConfirmDialog(null,"Do you want to sort the data?", "Messaging System",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.YES_OPTION) {
            writer.write("1");
            writer.flush();
        } else {
            writer.write("2");
            writer.flush();
        }

        //add the correct info to these two labels
        //for buyers, one label should be how many messages each store has received
        //second label should be how many messages the user has sent to each store
        //for sellers the first should be how many messages users have sent to the store
        //second message should be the common words
        JLabel data1 = new JLabel("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam");
        JLabel data2 = new JLabel("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam");
        panel.add(data1);
        panel.add(data2);

        JButton back = new JButton("Go Back");
        back.addActionListener(e -> cardLayout.show(mainPanel, "Selection Screen"));
        panel.add(back);

    }

    public static void makeDeleteMsgScreen(JPanel panel) {
        panel.setLayout(new GridLayout(2, 0));
        JLabel recLabel = new JLabel("Delete Which Message");
        JComboBox<String> users = new JComboBox();

        JPanel top = new JPanel();
        top.setLayout(new GridLayout(0, 2));
        panel.add(top);

        //need a for loop adding all messages the user has sent
        users.addItem("Message 1");
        users.addItem("Message 2");

        top.add(recLabel);
        top.add(users);

        panel.add(top);

        JButton delete = new JButton("Delete");
        delete.addActionListener(e -> cardLayout.show(mainPanel, "Selection Screen"));

        panel.add(delete);
    }

    public static void makeEditMsgScreen(JPanel panel) {
        panel.setLayout(new GridLayout(4, 1));
        JLabel recLabel = new JLabel("Edit Which Message");
        JComboBox<String> users = new JComboBox();

        JPanel top = new JPanel();
        top.setLayout(new GridLayout(0, 2));
        panel.add(top);

        //need a for loop adding all messages the user has sent
        users.addItem("Message 1");
        users.addItem("Message 2");

        top.add(recLabel);
        top.add(users);

        panel.add(top);


        JLabel label = new JLabel("Enter New Message");

        JTextField message = new JTextField();

        top.add(label);
        top.add(message);

        JButton delete = new JButton("Delete");
        delete.addActionListener(e -> cardLayout.show(mainPanel, "Selection Screen"));

        panel.add(delete);
    }

    public static void makeCensorScreen(JPanel panel) {
        panel.setLayout(new GridLayout(4,1));
        JLabel censorLabel = new JLabel("Text You Want Censored:");
        JTextField censorText = new JTextField();
        JLabel replacement = new JLabel("Replacement Text:");
        JTextField replacementText = new JTextField("*****");
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(0, 2));
        panel.add(top);
        top.add(censorLabel);
        top.add(censorText);
        top.add(replacement);
        top.add(replacementText);

        JButton censor = new JButton("Censor");
        censor.addActionListener(e -> cardLayout.show(mainPanel, "Selection Screen"));
        panel.add(censor);

    }

    public static void makeBuyScreen(JPanel panel) {
        panel.setLayout(new GridLayout(0, 1));

        JPanel top = new JPanel();
        top.setLayout(new GridLayout(0, 2));

        JLabel storeLabel = new JLabel("Store:");
        top.add(storeLabel);
        JComboBox<String> stores = new JComboBox<>();

        //for loop to add all the stores here
        stores.addItem("store 1");
        stores.addItem("store 2");

        top.add(stores);
        panel.add(top);

        JComboBox<String> products = new JComboBox<>(); //should start empty
        JButton selectStore = new JButton("Select Store");
        selectStore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Add any products from the selected store to the products ComboBox
                products.addItem("Item 1");
            }
        });

        panel.add(selectStore);

        JPanel middle = new JPanel();
        middle.setLayout(new GridLayout(0, 2));
        JLabel productLabel = new JLabel("Product:");
        middle.add(productLabel);

        middle.add(products);
        panel.add(middle);

        JButton buy = new JButton("Buy");
        buy.addActionListener(e -> cardLayout.show(mainPanel, "Selection Screen"));
        panel.add(buy);

    }

    public static void makeStoreScreen(JPanel panel) {
        panel.setLayout(new GridLayout(0, 1));

        JPanel top = new JPanel();
        top.setLayout(new GridLayout(0, 2));
        JLabel nameLabel = new JLabel("Store Name:");
        JTextField name = new JTextField();
        top.add(nameLabel);
        top.add(name);

        JLabel numLabel = new JLabel("Number of products:");
        JTextField num = new JTextField();
        top.add(numLabel);
        top.add(num);

        JLabel productLabel = new JLabel("Product Name:");
        JTextField product = new JTextField();
        top.add(productLabel);
        top.add(product);

        panel.add(top);

        JButton addProduct = new JButton("Add Products");
        panel.add(addProduct);
        //var ranOnce = false;
        final int[] count = {0};

        addProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (count[0] == 0) {
                    writer.println(name.getText());
                    writer.flush();
                    writer.println(num.getText());
                    writer.flush();
                    count[0] = 6;
                }
                writer.println(product.getText());
                writer.flush();




                /*writer.println(name.getText());
                ;
                writer.println(num.getText());
                writer.flush();*/
                //Write functionality for adding a product with whatever name is currently in the product
                //text field

                product.setText("");
            }
        });

        JButton create = new JButton("Create");
        create.addActionListener(e -> cardLayout.show(mainPanel, "Selection Screen"));
        panel.add(create);

    }



}
