package messaging_system;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project 4 Option 2
 * <p>
 * This program holds the main method for the program, and uses the classes Message, Customer, Users, Seller, and Store
 * to create an e-commerce mockup with a focus on the messaging system.
 *
 * @author Ishaan, Nandini, Nick, Vinay, Zishuo, LO1
 * @version November 13, 2023
 */
public class Processor {

    private static final ArrayList<Store> allStores = new ArrayList<Store>();
    private static final ArrayList<Message> allMessages = new ArrayList<Message>();
    private static final ArrayList<Seller> allSellers = new ArrayList<Seller>();
    private static final ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    private static final ArrayList<Users> allUsers = new ArrayList<Users>();
    private static BufferedReader reader = null;
    private static PrintWriter writer = null;

    //main method to run program
    public static void main(String[] args) throws IOException {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Waiting for the client to connect...");
            Socket socket;
            socket = serverSocket.accept();
            System.out.println("Client connected!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean fileCheck = false;
        //loads files for saved data
        File f1 = new File("user_info.txt");
        File f2 = new File("store_info.txt");
        File f3 = new File("message_info.txt");
        if (f1.exists() && f2.exists() && f3.exists()) {
            fileCheck = true;
            loadFiles(f1, f2, f3);
        }
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        //starts console menu loop
        do {
            saveAll(); //Saves all data everytime outer-main menu is loaded
            //System.out.println("Main Menu. Please choose an option.\n1.Login\n2.Create Account\n3.Exit");
            System.out.println("about to read");
            String menuChoice = reader.readLine();
            System.out.println(menuChoice);
            /*System.out.println("Read, will write now!");
            writer.println("hey");
            writer.flush();
            System.out.println("read!");*/

            switch (menuChoice) {
                case "1":
                    Users user = login();
                    if (user != null) {
                        boolean loggedIn = true;
                        showNewMessages(user);
                        do {
                            saveAll(); //Saves all data everytime inner-main menu is loaded

                            String userName = user.getEmail();
                            writer.println(userName);
                            writer.flush();
                            //switch case for menu
                            if (user instanceof Seller) {
                                //writer.println("seller");
                                //writer.flush();
                                //Prints seller menu
                            } else {
                                //writer.println("customer");
                                //writer.flush();
                                //Prints customer menu
                            }

                            String choice = reader.readLine();

                            switch (choice) {
                                case "1":
                                    printMsgs(user);
                                    break;
                                case "2":
                                    sendMessage(user);
                                    break;
                                case "3":
                                    editAccount(user);
                                    break;
                                case "4":
                                    if (user instanceof Seller) {
                                        for (Store x : allStores) {
                                            if (x.getSeller().getEmail().equals(user.getEmail())) {
                                                allStores.remove(x);
                                            }
                                        }
                                        allSellers.remove(user);
                                    }
                                    if (user instanceof Customer) {
                                        allCustomers.remove(user);
                                    }
                                    allUsers.remove(user);
                                    //writer.println("User " + user.getEmail() + " has been deleted");
                                    writer.println("Deleted!");
                                    writer.flush();
                                    user.deleteAccnt();
                                    loggedIn = false;
                                    break;
                                case "5":
                                    printUsers(user, "seller");
//                                    System.out.println("Enter user you would like to hide from:");
                                    //hides from a user by adding to a users hidden list
                                    String hidden = reader.readLine();
                                    for (Users allUser : allUsers) {
                                        if (allUser.getEmail().equalsIgnoreCase(hidden)) {
                                            user.hide(allUser.getEmail());
                                        }
                                    }
                                    writer.println("User hidden.");
                                    writer.flush();
                                    break;
                                case "6":
                                    printUsers(user, "seller");
//                                    System.out.println("Enter user you would like to block:");
                                    //blocks a user by adding them to a users blocked list
                                    String blocked = reader.readLine();
                                    for (Users allUser : allUsers) {
                                        if (allUser.getEmail().equalsIgnoreCase(blocked)) {
                                            user.block(allUser.getEmail());
                                        }
                                    }
                                    writer.println("User blocked.");
                                    writer.flush();
                                    break;
                                case "7":
                                    getStatistics(user);
                                    break;
                                case "8":
                                    writer.println("Logged out!");
                                    writer.flush();
                                    loggedIn = false;
                                    break;
                                case "9":
                                    editMessage(user);
                                    break;
                                case "10":
                                    deleteMessage(user);
                                    break;
                                case "11":
                                    exportCSV(user);
                                    break;
                                case "12":
                                    if (user instanceof Customer) {
                                        buyProducts((Customer) user);
                                        break;
                                    }
                                    makeStore((Seller) user);
                                    break;
                                case "13":
                                    //censors cetain text
                                    //System.out.println("What text would you like to censor");
                                    String censor = reader.readLine();
                                    user.addCensored(censor);
                                    user.setHaveCensor(true);
                                    //System.out.println("How would you like to replace the censored texts?\n1" +
                                            //".Use default which is ****\n2.Make your own replacement");
                                    String switc = reader.readLine();
                                    switch (switc) {
                                        case "1":
                                            user.setCensorReplacement("****");
                                            break;
                                        case "2":
                                            //System.out.println("Enter your replacement words");
                                            String replaceWords = reader.readLine();
                                            user.setCensorReplacement(replaceWords);
                                            break;
                                        default:
                                            //System.out.println("Invalid input.");
                                            break;
                                    }

                                    break;

                                default:
                                    System.out.println("\nInvalid input.\n");
                                    break;
                            }
                        } while (loggedIn);
                    }
                    break;
                case "2":
                    System.out.println(createAccount());
                    break;
                case "3":
                    exit = true;
                    writer.println("Ending application!");
                    writer.flush();
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;

            }
            System.out.println();
        } while (!exit);
    }


    //prints message history depending on the user, includes disappearing and censor functionality
    public static void printMsgs(Users user) {
        int numMes = user.getMessagesReceived().size() + user.getMessagesSent().size();
        String numMessages = Integer.toString(numMes);
        System.out.println("what gives?");
        writer.println(numMessages);
        System.out.println(numMessages);
        writer.flush();
        if (user.haveCensor) {
            for (int i = 0; i < numMes; i++)
            {
                for (int j = 0; j < user.censored.size(); j++) {
                    if (allMessages.get(i).getContent().contains(user.censored.get(j))) {
                        allMessages.get(i).editMessage(allMessages.get(i).getContent().replaceAll(
                                user.censored.get(j),
                                user.censorReplacement));
                    }
                }
            }
        }

        for (int i = 0; i < numMes; i++) {

            if (user.getEmail().equals(allMessages.get(i).getSenderID()) ||
                    user.getEmail().equals(allMessages.get(i).getRecipientID())) {
                String content = allMessages.get(i).getContent();
                String time = allMessages.get(i).getTimeStamp();
                String sender = allMessages.get(i).getSenderID();
                String recipient = allMessages.get(i).getRecipientID();
                if (user.getEmail().equals(recipient) && !allMessages.get(i).isDisappearing()) {
                    writer.println("[" + time + "] " + sender + " messaged you" +
                            ": " + content);
                    writer.flush();
                    for (messaging_system.Message message : user.getMessagesReceived()) {
                        if (message.getContent().equals(content)) {
                            message.setHasRead(true);
                        }
                    }
                } else {
                    if (!allMessages.get(i).isDisappearing()) {
                        writer.println("[" + time + "] " + "You messaged " + recipient +
                                ": " + content);
                        writer.flush();
                    }
                }
            }
        }
    }

    //shows new messages when a user logs on
    public static void showNewMessages(Users user) {
        ArrayList<Message> messagesReceived = user.getMessagesReceived();
        ArrayList<Message> unread = new ArrayList<>();
        for (Message message : messagesReceived) {
            if (message.getRecipientID().equals(user.getEmail()) && !message.HasRead()) {
                unread.add(message);
                message.setHasRead(true);
            }
        }
        writer.println(unread.size());
        writer.flush();
        if (unread.isEmpty()) {
            writer.println("No new Messages!");
            writer.flush();
        } else {
            writer.println("Unread messages: ");
            writer.flush();
            for (Message m : unread) {
                writer.println("[" + m.getTimeStamp() + "] " + m.getSenderID() + " messaged you" +
                        ": " + m.getContent());
                writer.flush();
            }
        }


    }

    //Method for login functionality
    public static Users login() throws IOException {
        //System.out.println("yo");
        String email = reader.readLine();
        String password = reader.readLine();
        for (Users allUser : allUsers) {
            if (allUser.getEmail().equals(email)) {
                if (allUser.getPassword().equals(password)) {
                    writer.println("Logged in!");
                    System.out.println("Written hahahah");
                    writer.flush();
                    if (allUser instanceof Seller) {
                        writer.println("seller");
                        writer.flush();
                        System.out.println("seller@");
                    } else {
                        writer.println("customer");
                        writer.flush();
                    }


                    return allUser;
                }
            }
        }
        writer.println("Email or password is incorrect.");
        writer.flush();
        return null;
    }

    //Handles all functionality for sending a message
    public static void sendMessage(Users user) throws IOException {
        Scanner scanner = new Scanner(System.in);
        /*if (user instanceof Seller) {
            writer.println("seller");
            writer.flush();
//            System.out.println("1. View a list of people to message.\n2. Message a specific user.");
        } else {
            writer.println("customer");
            writer.flush();
//            System.out.println("1. View a list of stores to message.\n2. Message a specific seller.");
        } */
        String choice = reader.readLine();
        switch (choice) {
            case "1":
                printUsers(user, "store");
            case "2":
//                System.out.println("Who would you like to message?");
                String recipient = reader.readLine();
                String blockMessage = "";
                Users recipUser = null;
                if (user instanceof Seller) {
                    for (Users allCustomer : allCustomers) {
                        if (allCustomer.getEmail().equals(recipient)) {
                            if (allCustomer.blockedUsers.contains(user.getEmail()) ||
                                    allCustomer.invisibleUsers.contains(user.getEmail())) {
                                blockMessage = "You cannot message that customer.";
                            } else {
                                recipUser = allCustomer;
                            }
                            writer.println(blockMessage);
                            writer.flush();
                        }
                    }
                } else {
                    for (Seller allSeller : allSellers) {
                        if (allSeller.getEmail().equals(recipient)) {
                            if (allSeller.blockedUsers.contains(user.getEmail())) {
                                blockMessage = "You cannot message that seller.";
                            } else {
                                recipUser = allSeller;
                            }


                        }
                    }
                    for (Store allStore : allStores) {
                        if (allStore.getName().equals(recipient)) {
                            if (allStore.getSeller().blockedUsers.contains(user.getEmail())) {
                                blockMessage = "You cannot message that store.";
                            } else {
                                recipUser = allStore.getSeller();
                            }
                        }
                    }
                    writer.println(blockMessage);
                    writer.flush();
                }
                String nullCheck = "";
                if (recipUser == null) {
                    nullCheck = "Invalid recipient.";
                    break;
                }
                writer.println(nullCheck);
                writer.flush();


//                System.out.println("How would you like to send the message?\n1. Type the message\n2. " +
//                        "Import a text file");
                String choice2 = reader.readLine();
                switch (choice2) {
                    case "1":
//                        System.out.println("Do you want your message to disappear after it's read? (Yes/No)");
                        String confirm = reader.readLine();
//                        System.out.println("What is your message?");
                        String content = reader.readLine();

                        messaging_system.Message message = new messaging_system.Message(content, user.getEmail(),
                                recipUser.getEmail(),
                                LocalTime.now().toString(), false);
                        if (confirm.equalsIgnoreCase("yes")) {
                            message.setDisappearing(true);
                        }
                        allMessages.add(message);
                        writer.println(user.sendMessage(message, recipUser));
                        writer.flush();
                        System.out.println("SENT");
                        for (var i = 0; i < user.messagesSent.size(); i++) {
                            if (user.messagesSent.get(i).isDisappearing()) {
                                user.messagesSent.remove(i);
                            }
                        }
                        break;
                    case "2":
                        //allows user to import a txt file for a message
                        // System.out.println("What is the text file you would like to import?");
                        String file = reader.readLine();
                        content = importText(file);
                        if (!content.equals("Wrong")) {
                            message = new Message(content, user.getEmail(), recipUser.getEmail(),
                                    LocalTime.now().toString(), false);
                            allMessages.add(message);
                            user.sendMessage(message, recipUser);
                            break;
                        } else {
                            break;
                        }
                    default:
                        //System.out.println("Invalid input.");
                        break;
                }


                break;
            default:
                System.out.println("Invalid input.");
                break;
        }
    }

    //prints a list of messageable users
    public static void printUsers(Users user, String storeOrSeller) {

        if (user instanceof Customer) {
            if (storeOrSeller.equals("store")){
                writer.println(allStores.size());
            writer.flush();
            for (Store allStore : allStores) {
                writer.println(allStore.getName());
                writer.flush();
            }
        } else{ //storeOrSeller is equal to "seller"
                writer.println(allSellers.size());
                writer.flush();
                for(Seller seller: allSellers){
                    if(!seller.invisibleUsers.contains(user.getEmail())) {
                        writer.println(seller.getEmail());
                        writer.flush();
                    } else{
                        writer.println("hidden");
                        writer.flush();
                    }
                }

            }
        } else {
            writer.println(allCustomers.size());
            writer.flush();
            for (Customer allCustomer : allCustomers) {
                if (!allCustomer.invisibleUsers.contains(user.getEmail())) {
                    writer.println(allCustomer.getEmail());
                    writer.flush();
                } else{
                    writer.println("hidden");
                    writer.flush();
                }
            }
        }
        System.out.println();
    }

    //edit account functionality
    public static void editAccount(Users user) throws IOException {
        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter updated email:");
        String email = reader.readLine();
        boolean alrExists = false;
        String existMessage = "0";
        for (Users allUser : allUsers) {
            if (allUser.getEmail().equals(email)) {
                existMessage = "Account with that email already exists.";
                alrExists = true;
            }
        }
        writer.println(existMessage);
        writer.flush();
//            System.out.println("Enter updated password:");
            String password = reader.readLine();
            user.editAccnt(email, password);

    }

    //handles the getStatistics functionality
    public static void getStatistics(Users user) throws IOException {
        System.out.println("STARTING");
        Scanner scanner = new Scanner(System.in);
        //System.out.println("Would you like to sort your data?\n1. Yes\n2. No");
        boolean sort = reader.readLine().equals("1");
        System.out.println(sort);
        ArrayList<String> data = new ArrayList<String>();
        ArrayList<String> data2 = new ArrayList<String>();
        if (user instanceof Seller) {
            for (Customer x : allCustomers) {
                data.add(x.getEmail() + " has sent " + x.getNumMessages() + " messages." +
                        " Common words include: " + getCommonWords(allMessages));
            }
        } else {
            for (Store x : allStores) {
                data.add(x.getName() + " has received " + x.getSeller().messagesReceived.size() + " messages.");
            }
            for (Store x : allStores) {

                int count = 0;
                for (Message message : x.getSeller().messagesReceived) {
                    if (message.getSenderID().equals(user.email)) {
                        count++;
                    }
                }
                data2.add(x.getName() + " has received " + count + " messages from you.");
            }
        }

        if (sort) {
            Collections.sort(data);
            Collections.sort(data2);
        }
        writer.println(data.size());
        System.out.println(data.size() + "weird");
        writer.flush();
        for (String x : data) {
            writer.println(x);
            writer.flush();
            System.out.println(x);
        }

        if (user instanceof Customer) {
            writer.println(data2.size());
            writer.flush();
            for (String x : data2) {
                writer.println(x);
                writer.flush();
            }
        }
    }

    //method for editing previous messages
    public static void editMessage(Users user) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Message> msgsSent = new ArrayList<Message>();

        if (user.messagesSent != null) {
            msgsSent = user.messagesSent;
        }
        int i = 1;
        int size = msgsSent.size();
        writer.println(size);
        writer.flush();
        for (Message x : msgsSent) {
            writer.println(i + ": " + x.getContent());
            writer.flush();
            i++;
        }
        //System.out.println("\nEnter the number of the message you would like to edit:");
        try {
            //Message message = msgsSent.get(Integer.parseInt(reader.readLine()) - 1);
            //since combo box starts counting from 0
            Message message = msgsSent.get(Integer.parseInt(reader.readLine()));
            //writer.println("entered try.");
            //writer.flush();
            String content = message.getContent();
            //System.out.println("What would you like the message to say now.");
            String update = reader.readLine();
            message.editMessage(update);
            allMessages.get(allMessages.indexOf(message)).editMessage(update);
            //System.out.println("Message updated.");
        } catch (Exception e) {
            //System.out.println("Invalid response.");
        }
    }

    //similar to edit message method, but for deletion of messages
    public static void deleteMessage(Users user) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Message> msgsSent = new ArrayList<Message>();
        msgsSent = user.messagesSent;
        int i = 1;
        writer.println(msgsSent.size());
        writer.flush();
        if (msgsSent.isEmpty()) {
            writer.println("No message history.");
            writer.flush();
        } else {
            for (Message x : msgsSent) {
                writer.println(i + ": " + x.getContent());
                writer.flush();
                i++;
            }
            //System.out.println("\nEnter the number of the message you would like to delete:");
            try {
                Message message = msgsSent.get(Integer.parseInt(reader.readLine()));
                user.deleteMessage(message);
                allMessages.remove(message);
                writer.println("Message deleted.");
                writer.flush();
            } catch (Exception e) {
                writer.println("Invalid response.");
                writer.flush();
            }
        }

    }

    //allows a user to buy a product, adds it to the user's list of purchased products
    public static void buyProducts(Customer user) {
        Scanner scanner = new Scanner(System.in);
        int i = 1;
        writer.println(allStores.size());
        if (allStores.size() > 0) {
            for (Store allStore : allStores) {
                writer.println(i + ". " + allStore.getName());
                writer.flush();
                System.out.println("Sent!!!");
                i++;
            }
        }
        //System.out.println("Enter the number for the store you want to purchase from:");
        try {
            Store store = allStores.get(Integer.parseInt(reader.readLine()));
            i = 1;
            writer.println(store.getProductList().size());
            writer.flush();
            for (String product : store.getProductList()) {
                writer.println(i + ". " + product);
                writer.flush();
                i++;
            }
            //System.out.println("Enter the number for the product you want to buy:");
            ArrayList<String> finalList = new ArrayList<>();
            String product = store.getProductList().get(Integer.parseInt(reader.readLine()));
            if (user.getProductsPurchased() != null) {
                finalList = user.getProductsPurchased();
            }
            finalList.add(product);
            user.setProductsPurchased(finalList);
            writer.println("Purchased!");
            writer.flush();
        } catch (Exception e) {
            writer.println("Invalid response.");
            writer.flush();
        }
    }

    //allows a seller to create a store, and sell a certain number of products
    public static void makeStore(Seller user) throws IOException {
        Scanner scanner = new Scanner(System.in);
        //System.out.println("Enter the store name:");
        String name = reader.readLine();
        //System.out.println("How many items will you be selling?");
        try {
            int count = Integer.parseInt(reader.readLine());
            //int count = 1;
            ArrayList<String> products = new ArrayList<String>();
            for (int i = 1; i <= count; i++) {
                //System.out.println("Name of product " + i + "?");
                products.add(reader.readLine());
            }
            Store store = new Store(name, products, user);
            allStores.add(store);
            user.addStore(store);
            writer.println("Store made!");
            writer.flush();
        } catch (Exception e) {
            writer.println("Invalid response.");
            writer.flush();
        }
    }

    //functionality for creating new, unique, accounts
    public static String createAccount() throws IOException {

        Scanner scanner = new Scanner(System.in);
        //System.out.println("Enter your email:");
        String email = reader.readLine();
        System.out.println(email);
        for (Users user : allUsers) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                writer.println("Account with that email already exists.");
                writer.flush();
                return "Account with that email already exists.";
                // send this back
            }
        }
        // Send empty line here if acc is new
        writer.println("");
        writer.flush();

        // System.out.println("Enter your password:");
        String password = reader.readLine();
        System.out.println(password);
        // System.out.println("Choose type of account to create:\n1.Customer\n2.Seller\n");
        String accountChoice = reader.readLine();
        System.out.println(accountChoice);
        switch (accountChoice) {
            case "1":
                Customer customer = new Customer(email, password);
                allCustomers.add(customer);
                allUsers.add(customer);
                writer.println("Account created! You may now login.");
                writer.flush();
                return "Account created! You may now login.";

            case "2":
                Seller seller = new Seller(email, password);
                allSellers.add(seller);
                allUsers.add(seller);
                writer.println("Account created! You may now login.");
                writer.flush();
                return "Account created! You may now login.";
            default:
                writer.println("Invalid input.");
                writer.flush();
                return "Invalid input.";
        }

    }

    //exports all or partial message history to a csv
    public static void exportCSV(Users user) throws IOException {
        Scanner scanner = new Scanner(System.in);
        if (user instanceof Customer) {
            writer.println(allSellers.size());
            writer.flush();
            for (Seller x : allSellers) {
                writer.println(x.getEmail());
                writer.flush();
            }
        } else {
            writer.println(allCustomers.size());
            writer.flush();
            for (Customer allCustomer : allCustomers) {
                writer.println(allCustomer.getEmail());
                writer.flush();
            }
        }
        //System.out.println("Whose conversation would you like to export (leave blank for all).");
        String name = reader.readLine();
        File f = new File("messageHistory.csv");
        BufferedWriter brw = new BufferedWriter(new FileWriter("messageHistory.csv"));
        if (name.equalsIgnoreCase("All.")) {
            for (Message allMessage : allMessages) {
                if (allMessage.getRecipientID().equals(user.email) || allMessage.getSenderID().equals(user.email)) {
                    String content = allMessage.getContent();
                    String time = allMessage.getTimeStamp();
                    String sender = allMessage.getSenderID();
                    brw.write(time + ", " + sender + ", " + content + "\n");
                }
            }
        } else {
            for (Message allMessage : allMessages) {
                if (allMessage.getRecipientID().equals(name)) {
                    String content = allMessage.getContent();
                    String time = allMessage.getTimeStamp();
                    String sender = allMessage.getSenderID();
                    brw.write(time + ", " + sender + ", " + content + "\n");
                }
            }
        }
        //System.out.println("Exported!");
        brw.close();
    }

    //uses a hasmap to find the most common words in a user's message history with a seller
    public static String getCommonWords
    (ArrayList<Message> allMessages) // returns with words separated by a space
    {
        HashMap<String, Integer> words = new HashMap<>();
        for (Message message : allMessages) {
            String[] messageContent = message.getContent().split(" ");
            for (String word : messageContent) {
                word = word.trim();
                if (!word.isEmpty()) {
                    words.put(word, words.getOrDefault(word, 0) + 1);
                }
            }
        }

        if (words.isEmpty()) {
            System.out.println("There are no messages saved!");
        } else {
            List<Map.Entry<String, Integer>> sortedEntries = words.entrySet()
                    .stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .collect(Collectors.toList());

            if (sortedEntries.isEmpty()) {
                System.out.println("The sorted entries list is empty. No words found.");
            } else {
                List<String> highestStrings = sortedEntries.stream()
                        .limit(3)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());

                if (highestStrings.isEmpty()) {
                    System.out.println("The 'highestStrings' list is empty. No top words found.");
                } else {
                    String ret = "";
                    for (String s : highestStrings)
                    {
                        ret += s + " ";
                    }
                    return ret;
                }
            }
        }

        return "No top words found";

    }

    //saves all current data to the 3 files store_info, user_info, message_info
    public static void saveAll() throws IOException {
        File users = new File("user_info.txt");
        BufferedWriter usersWriter = new BufferedWriter(new FileWriter(users));
        for (Users user : allUsers) {
            if (user instanceof Seller) {
                usersWriter.write("Seller\n");
            }
            if (user instanceof Customer) {
                usersWriter.write("Customer\n");
            }
            String email = user.getEmail();
            String password = user.getPassword();
            usersWriter.write(email + "\n");
            usersWriter.write(password + "\n");
            String blockedUsers = ";";
            if (!user.getBlockedUsers().isEmpty()) {
                for (String blockedUser : user.getBlockedUsers()) {
                    blockedUsers += blockedUser + ",";
                }
                blockedUsers = blockedUsers.substring(0, blockedUsers.length() - 1);
            }
            blockedUsers += ";";
            usersWriter.write(blockedUsers + "\n");

            String invisibleUsers = "|";
            if (!user.getInvisibleUsers().isEmpty()) {
                for (String invisibleUser : user.getInvisibleUsers()) {
                    invisibleUsers += invisibleUser + ",";
                }
                invisibleUsers = invisibleUsers.substring(0, invisibleUsers.length() - 1);
            }
            invisibleUsers += "|";
            usersWriter.write(invisibleUsers + "\n");


            usersWriter.write("Sent" + "\n");
            for (Message message : user.getMessagesSent()) {
                String content = message.getContent();
                String time = message.getTimeStamp();
                String sender = message.getSenderID();
                String recipient = message.getRecipientID();
                boolean hasRead = message.HasRead();
                usersWriter.write(time + "," + sender + "," + recipient + "," + content + "," + hasRead + "\n");
            }

            usersWriter.write("Received" + "\n");
            for (Message message : user.getMessagesReceived()) {
                String content = message.getContent();
                String time = message.getTimeStamp();
                String sender = message.getSenderID();
                String recipient = message.getRecipientID();
                boolean hasRead = message.HasRead();
                usersWriter.write(time + "," + sender + "," + recipient + "," + content + "," + hasRead + "\n");
            }

            if (user instanceof Seller) {
                usersWriter.write("Stores\n");
                for (Store store : ((Seller) user).getStores()) {
                    String name = store.getName();
                    String sellerEmail = store.getSeller().getEmail();
                    String items = "";
                    for (String str : store.getProductList()) {
                        items += str + ",";
                    }
                    items = items.substring(0, items.length() - 1);
                    usersWriter.write(name + "," + sellerEmail + "," + ";" + items + ";");
                }
                usersWriter.write("\n");
            }
            if (user instanceof Customer) {
                usersWriter.write("Purchased\n");
                for (String str : ((Customer) user).getProductsPurchased()) {
                    String items = "";
                    items += str + ",";
                    items = items.substring(0, items.length() - 1);
                    usersWriter.write(items);
                }
                usersWriter.write("\n");
            }
            usersWriter.write("Done\n");
        }
        usersWriter.close();


        File stores = new File("store_info.txt");
        BufferedWriter storeWriter = new BufferedWriter(new FileWriter(stores));
        for (Store store : allStores) {
            String name = store.getName();
            String sellerEmail = store.getSeller().getEmail();
            String items = "";
            for (String str : store.getProductList()) {
                items += str + ",";
            }
            items = items.substring(0, items.length() - 1);
            storeWriter.write(name + "," + sellerEmail + "," + ";" + items + ";");
        }
        storeWriter.close();

        File messages = new File("message_info.txt");
        BufferedWriter messageWriter = new BufferedWriter(new FileWriter(messages));
        for (Message message : allMessages) {
            String content = message.getContent();
            String time = message.getTimeStamp();
            String sender = message.getSenderID();
            String recipient = message.getRecipientID();
            boolean hasRead = message.HasRead();
            messageWriter.write(time + "," + sender + "," + recipient + "," + content + "," + hasRead +
                    "\n");
        }
        messageWriter.close();


    }


    //loads files that have saved data from previous uses
    public static void loadFiles(File fileOne, File fileTwo, File fileThree) throws
            IOException {
        try (BufferedReader bfr = new BufferedReader(new FileReader(fileOne))) {
            String line = bfr.readLine();
            while (line != null) {
                String sOrC = line;
                line = bfr.readLine();
                String email = line;
                line = bfr.readLine();
                String password = line;
                ArrayList<String> blockedUsers = new ArrayList<>();
                line = bfr.readLine();
                if (!line.equals(";;")) {
                    String blockCheck = line.substring(1, line.length() - 1);
                    String[] blockCheck2 = blockCheck.split(",");
                    blockedUsers.addAll(Arrays.asList(blockCheck2));
                }
                line = bfr.readLine();
                ArrayList<String> invisibleUsers = new ArrayList<>();
                if (!line.equals("||")) {
                    String invCheck = line.substring(1, line.length() - 1);
                    String[] invCheck2 = invCheck.split(",");
                    invisibleUsers.addAll(Arrays.asList(invCheck2));
                }
                line = bfr.readLine();
                line = bfr.readLine();
                ArrayList<Message> messagesSent = new ArrayList<>();
                while (!(line.equals("Received"))) {
                    if (!line.isEmpty()) {
                        String[] messageCheck = line.split(",");
                        Message message = new Message(messageCheck[3], messageCheck[1], messageCheck[2],
                                messageCheck[0], Boolean.parseBoolean(messageCheck[4]));
                        messagesSent.add(message);
                    }
                    line = bfr.readLine();
                }
                line = bfr.readLine();
                ArrayList<Message> messagesReceived = new ArrayList<>();
                while (!(line.equals("Stores") || line.equals("Purchased"))) {
                    if (!line.isEmpty()) {
                        String[] messageCheck = line.split(",");
                        Message message = new Message(messageCheck[3], messageCheck[1], messageCheck[2],
                                messageCheck[0], Boolean.parseBoolean(messageCheck[4]));
                        messagesReceived.add(message);
                    }
                    line = bfr.readLine();
                }
                if (sOrC.equals("Seller")) {
                    Seller seller = new Seller(email, password);
                    ArrayList<Store> stores = new ArrayList<>();
                    line = bfr.readLine();
                    boolean storesExist = true;
                    if (!line.isEmpty()) {
                        while (line != null && !line.equals("Done")) {
                            String storeCheck = line.substring(0, line.indexOf(";"));
                            String[] storeInfo = storeCheck.split(",");
                            String storeName = storeInfo[0];
                            String storeSeller = storeInfo[1];
                            String products = line.substring(line.indexOf(";") + 1, line.length() - 1);
                            String[] productList = products.split(",");
                            ArrayList<String> prodList = new ArrayList<>(Arrays.asList(productList));

                            Store store = new Store(storeName, prodList, seller);
                            stores.add(store);

                            line = bfr.readLine();
                        }
                    } else {
                        storesExist = false;
                        line = bfr.readLine();
                    }
                    seller.setBlockedUsers(blockedUsers);
                    seller.setInvisibleUsers(invisibleUsers);
                    seller.setMessagesSent(messagesSent);
                    seller.setMessagesReceived(messagesReceived);
                    if (storesExist) {
                        seller.setStores(stores);
                    }

                    allSellers.add(seller);
                    allUsers.add(seller);

                } else {
                    Customer customer = new Customer(email, password);
                    ArrayList<String> purchased = new ArrayList<>();
                    line = bfr.readLine();
                    String[] tempProd = line.split(",");
                    purchased.addAll(Arrays.asList(tempProd));

                    customer.setBlockedUsers(blockedUsers);
                    customer.setInvisibleUsers(invisibleUsers);
                    customer.setMessagesSent(messagesSent);
                    customer.setMessagesReceived(messagesReceived);
                    customer.setProductsPurchased(purchased);

                    line = bfr.readLine();
                    allCustomers.add(customer);
                    allUsers.add(customer);
                }
                line = bfr.readLine();
            }
        }

        try (BufferedReader bfr2 = new BufferedReader(new FileReader(fileTwo))) {
            String line = bfr2.readLine();
            while (line != null) {
                String storeInfo = line.substring(0, line.indexOf(";"));
                String[] storeList1 = storeInfo.split(",");
                String storeName = storeList1[0];
                String storeSellerID = storeList1[1];
                Seller storeSeller = new Seller("", "");
                for (Seller seller : allSellers) {
                    if (storeSellerID.equals(seller.getEmail())) {
                        storeSeller = seller;
                        break;
                    }
                }
                String products = line.substring(line.indexOf(";") + 1, line.length() - 1);
                String[] productList = products.split(",");
                ArrayList<String> prodList = new ArrayList<>(Arrays.asList(productList));

                Store store = new Store(storeName, prodList, storeSeller);

                allStores.add(store);
                line = bfr2.readLine();
            }
        }

        try (BufferedReader bfr3 = new BufferedReader(new FileReader(fileThree))) {
            String line = bfr3.readLine();
            while (line != null) {
                String[] messageInfo = line.split(",");
                Message message = new Message(messageInfo[3], messageInfo[1], messageInfo[2], messageInfo[0],
                        Boolean.parseBoolean(messageInfo[4]));
                allMessages.add(message);
                line = bfr3.readLine();
            }
        }
    }

    //imports text from a file to be used in a message
    public static String importText(String importedFIle) throws IOException {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(importedFIle));
            String line = "";
            StringBuilder recipient = new StringBuilder();
            while ((line = bfr.readLine()) != null) {
                recipient.append(line);
            }
            bfr.close();
            return recipient.toString();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return "Wrong";
        }
    }
}