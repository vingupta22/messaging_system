package messaging_system;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Project 4 Option 2
 *
 * This program holds the main method for the program, and uses the classes Message, Customer, Users, Seller, and Store
 * to create an e-commerce mockup with a focus on the messaging system.
 *
 * @author Ishaan, Nandini, Nick, Vinay, Zishuo, LO1
 *
 * @version November 13, 2023
 *
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


        //Port setup
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Waiting for the client to connect...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                handleClient(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //prints message history depending on the user, includes disappearing and censor functionality
    public static String printMsgs(Users user) {
        String ret = "";
        for (int i = 0; i < allMessages.size(); i++) {
            if (user.haveCensor) {
                for (int j = 0; i < user.censored.size(); i++) {
                    if (allMessages.get(i).getContent().contains(user.censored.get(i))) {
                        allMessages.get(i).editMessage(allMessages.get(i).getContent().replaceAll("\\b" +
                                        user.censored.get(i) + "\\b",
                                user.censorReplacement));
                    }
                }
            }
            if (user.getEmail().equals(allMessages.get(i).getSenderID()) ||
                    user.getEmail().equals(allMessages.get(i).getRecipientID())) {
                String content = allMessages.get(i).getContent();
                String time = allMessages.get(i).getTimeStamp();
                String sender = allMessages.get(i).getSenderID();
                String recipient = allMessages.get(i).getRecipientID();
                if (user.getEmail().equals(recipient) && !allMessages.get(i).isDisappearing()) {
                    ret += ("[" + time + "] " + sender + " messaged you" +
                            ": " + content) + "\n";
                    for (messaging_system.Message message : user.getMessagesReceived()) {
                        if (message.getContent().equals(content)) {
                            message.setHasRead(true);
                        }
                    }

                } else {
                    if (!allMessages.get(i).isDisappearing()) {
                        ret += ("[" + time + "] " + "You messaged " + recipient +
                                ": " + content) + "\n";

                    }

                }
            }
        }
        ret += "END";
        return ret;
    }

    //shows new messages when a user logs on
    public static String showNewMessages(Users user) {
        String ret = "";
        ArrayList<Message> messagesReceived = user.getMessagesReceived();
        if (messagesReceived.isEmpty()) {
            ret = "No new Messages!";
        } else {

            ArrayList<Message> unread = new ArrayList<>();
            for (Message message : messagesReceived) {
                if (message.getRecipientID().equals(user.getEmail()) && !message.HasRead()) {
                    unread.add(message);
                    message.setHasRead(true);
                }
            }
            if (unread.isEmpty()) {
                ret = "No new Messages!";
            } else {
                ret = "Unread messages:\n";
                for (Message m : unread) {
                    ret += "[" + m.getTimeStamp() + "] " + m.getSenderID() + " messaged you" +
                            ": " + m.getContent();
                    ret += "\n";
                }
            }

        }
        return ret;
    }

    //Method for login functionality
    public static Users login() throws IOException {
        Scanner scanner = new Scanner(System.in);
        //System.out.println("Enter your email:");
        String email = reader.readLine();
        //System.out.println("Enter your password:");
        String password = reader.readLine();

        for (Users allUser : allUsers) {
            if (allUser.getEmail().equals(email)) {
                if (allUser.getPassword().equals(password)) {
                    writer.println("Logged in!");
                    writer.flush();
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
        if (user instanceof Seller) {
            writer.println("1. View a list of people to message.\n2. Message a specific user.\nEND"); // fix reader here
            writer.flush();
        } else {
            writer.println("1. View a list of stores to message.\n2. Message a specific seller.\nEND");
            writer.flush();
        }
        String messageChoice = reader.readLine();
        switch (messageChoice) {
            case "1":
                String users = printUsers(user);
                writer.println(users);
                writer.flush();
            case "2":
                //System.out.println("Who would you like to message?");
                String recipient = reader.readLine();
                String ret = "";
                Users recipUser = null;
                if (user instanceof Seller) {
                    for (Users allCustomer : allCustomers) {
                        if (allCustomer.getEmail().equals(recipient)) {
                            if (allCustomer.blockedUsers.contains(user.getEmail()) ||
                                    allCustomer.invisibleUsers.contains(user.getEmail())) {
                                ret = ("You cannot message that customer.");
                                writer.println(ret);
                                writer.flush();
                            } else {
                                recipUser = allCustomer;
                                writer.println("");
                                writer.flush();
                            }
                        }
                    }
                } else {
                    for (Seller allSeller : allSellers) {
                        if (allSeller.getEmail().equals(recipient)) {
                            if (allSeller.blockedUsers.contains(user.getEmail())) {
                                ret = ("You cannot message that seller.");
                                writer.println();
                                writer.flush();
                            } else {
                                recipUser = allSeller;
                                writer.println("");
                                writer.flush();
                            }

                        }
                    }
                    for (Store allStore : allStores) {
                        if (allStore.getName().equals(recipient)) {
                            if (allStore.getSeller().blockedUsers.contains(user.getEmail())) {
                                ret = ("You cannot message that store.");
                                writer.println(ret);
                                writer.flush();
                            } else {
                                recipUser = allStore.getSeller();
                                writer.println("");
                                writer.flush();
                            }

                        }
                    }

                }

                if (recipUser == null) {
                    ret = ("Invalid recipient.");
                    writer.println(ret);
                    writer.flush();
                    break;
                }
                String msgChoice = reader.readLine();

                switch (msgChoice) {
                    case "1":
                        //System.out.println("Do you want your message to disappear after it's read? (Yes/No)");
                        String confirm = reader.readLine();
                        //System.out.println("What is your message?");
                        String content = reader.readLine();

                        messaging_system.Message message = new messaging_system.Message(content, user.getEmail(),
                                recipUser.getEmail(),
                                LocalTime.now().toString(), false);
                        if (confirm.equalsIgnoreCase("yes")) {
                            message.setDisappearing(true);
                        }
                        allMessages.add(message);
                        user.sendMessage(message, recipUser);
                        for (var i = 0; i < user.messagesSent.size(); i++) {
                            if (user.messagesSent.get(i).isDisappearing()) {
                                user.messagesSent.remove(i);
                            }
                        }
                        break;
                    case "2":
                        //allows user to import a txt file for a message
                        //System.out.println("What is the text file you would like to import?");
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
                        System.out.println("Invalid input.");
                        break;
                }


                break;
            default:
                System.out.println("Invalid input.");
                break;
        }
    }

    //prints a list of messageable users
    public static String printUsers(Users user) {
        String ret = "";
        if (user instanceof Customer) {
            for (Store allStore : allStores) {
                ret += (allStore.getName()) + "\n";
            }
        } else {
            for (Customer allCustomer : allCustomers) {
                if (!allCustomer.invisibleUsers.contains(user.getEmail())) {
                    ret += (allCustomer.getEmail()) + "\n";
                }
            }
        }
        ret += "END";
        return ret;
    }

    //edit account functionality
    public static void editAccount(Users user) throws IOException {
        String email = reader.readLine();
        boolean alrExists = false;
        for (Users allUser : allUsers) {
            if (allUser.getEmail().equals(email)) {
                //System.out.println("Account with that email already exists."); // fix
                alrExists = true;
                writer.println("exists");
                writer.flush();
            }
        }
        writer.println("not exist");
        writer.flush();
        if (!alrExists) {
            String password = reader.readLine();
            user.editAccnt(email, password);
        }
    }

    //handles the getStatistics functionality
    public static void getStatistics(Users user) throws IOException {
        //Scanner scanner = new Scanner(System.in);
        //System.out.println("Would you like to sort your data?\n1. Yes\n2. No");
        String choice = reader.readLine();
        boolean sort = choice.equals("1");
        ArrayList<String> data = new ArrayList<String>();
        ArrayList<String> data2 = new ArrayList<String>();
        if (user instanceof Seller) {
            for (Customer x : allCustomers) {
                data.add(x.getEmail() + " has sent " + x.getNumMessages() + " messages." +
                        " Common words include: " + getCommonWords(allMessages));
            }
        } else {
            for (Store x : allStores) {
                data.add(x.getName() + " has received  " + x.getSeller().messagesReceived.size() + " messages.");
            }
            for (Store x : allStores) {

                int count = 0;
                for (Message message : x.getSeller().messagesReceived) {
                    if (message.getSenderID().equals(user.email)) {
                        count++;
                    }
                }
                data2.add(x.getName() + " has received  " + count + " messages from you.");
            }
        }

        if (sort) {
            Collections.sort(data);
            Collections.sort(data2);
        }
        String ret = "";
        for (String x : data) {
            ret += (x) + "\n"; // fix reader
        }
        ret += "END";
        writer.println(ret);
        writer.flush();
        System.out.println();
        String ret2 = "";
        if (user instanceof Customer) {
            for (String x : data2) {
                ret2 += (x) + "\n";
            }
            ret2 += "END";
            writer.println(ret2);
            writer.flush();
            System.out.println();
        }
    }

    //method for editing previous messages
    public static void editMessage(Users user) throws IOException {
        String ret = "";
        Scanner scanner = new Scanner(System.in);
        ArrayList<Message> msgsSent = new ArrayList<Message>();

        if (user.messagesSent != null) {
            msgsSent = user.messagesSent;
        }
        int i = 1;
        for (Message x : msgsSent) {
            ret += (i + ": " + x.getContent()) + "\n";
        }
        ret += "END";
        writer.println(ret);
        writer.flush();

        int choice = Integer.parseInt(reader.readLine());
        try {
            Message message = msgsSent.get(choice - 1);
            String content = message.getContent();
            scanner.nextLine();
            writer.println("What would you like the message to say now.");
            writer.flush();
            String update = reader.readLine();
            message.editMessage(update);
            writer.println("Message updated.");
            writer.flush();
        } catch (Exception e) {
            writer.println("Invalid response.");
            writer.flush();
        }
    }

    //similar to edit message method, but for deletion of messages
    public static void deleteMessage(Users user) {
        String ret = "";
        Scanner scanner = new Scanner(System.in);
        ArrayList<Message> msgsSent = new ArrayList<Message>();
        if (user.messagesSent != null) {
            msgsSent = user.messagesSent;
        }
        int i = 1;
        for (Message x : msgsSent) {
            ret += (i + ": " + x.getContent()) + "\n";
        }
        ret += "END";
        writer.println(ret);
        writer.flush();
        if (msgsSent.isEmpty()) {
            writer.println("No message history.");
            writer.flush();
        } else {
            //System.out.println("\nEnter the number of the message you would like to delete:");
            try {
                int choice = Integer.parseInt(reader.readLine());
                Message message = msgsSent.get(choice - 1);
                scanner.nextLine();
                user.deleteMessage(message);
                writer.println("Message deleted.");
                writer.flush();
            } catch (Exception e) {
                writer.println("Invalid response.");
                writer.flush();
            }
        }

    }

    //allows a user to buy a product, adds it to the user's list of purchased products
    public static void buyProducts(Customer user) throws IOException {
        String ret = "";
        Scanner scanner = new Scanner(System.in);
        int i = 1;
        for (Store allStore : allStores) {
            ret += (i + ". " + allStore.getName()) + "\n";
            i++;
        }
        ret += "END";
        writer.println(ret);
        writer.flush();
        //System.out.println("Enter the number for the store you want to purchase from:");
        int choice = Integer.parseInt(reader.readLine());
        try {
            String ret1 = "";
            Store store = allStores.get(choice);
            i = 1;
            for (String product : store.getProductList()) {
                ret1 += (i + ". " + product) + "\n";
                i++;
            }
            ret1 += "END";
            writer.println(ret1);
            writer.flush();
            //System.out.println("Enter the number for the product you want to buy:");
            int pick = Integer.parseInt(reader.readLine());
            ArrayList<String> finalList = new ArrayList<>();
            String product = store.getProductList().get(pick - 1);
            scanner.nextLine();
            if (user.getProductsPurchased() != null) {
                finalList = user.getProductsPurchased();
            }
            finalList.add(product);
            user.setProductsPurchased(finalList);
            writer.println("Purchased!");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Invalid response.");
        }
    }

    //allows a seller to create a store, and sell a certain number of products
    public static void makeStore(Seller user) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String name = reader.readLine();

        try {
            String ret = "";
            writer.println("How many items will you be selling?");
            writer.flush();
            int count = Integer.parseInt(reader.readLine());
            ArrayList<String> products = new ArrayList<String>();
            for (int i = 1; i <= count; i++) {
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
        //Scanner scanner = new Scanner(System.in);
        //System.out.println("Enter your email:");
        String email = reader.readLine();
        for (Users user : allUsers) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return "Account with that email already exists.";
            }
        }
        //System.out.println("Enter your password:");
        String password = reader.readLine();
        //System.out.println("Choose type of account to create:\n1.Customer\n2.Seller\n");
        String option = reader.readLine();
        switch (option) {
            case "1":
                Customer customer = new Customer(email, password);
                allCustomers.add(customer);
                allUsers.add(customer);
                return "Account created! You may now login.";
            case "2":
                Seller seller = new Seller(email, password);
                allSellers.add(seller);
                allUsers.add(seller);
                return "Account created! You may now login.";
            default:
                return "Invalid input.\n";
        }

    }

    //exports all or partial message history to a csv
    public static void exportCSV(Users user) throws IOException {
        String ret = "";
        Scanner scanner = new Scanner(System.in);
        if (user instanceof Customer) {
            for (Seller x : allSellers) {
                ret += (x.getEmail()) + "\n";
            }
        } else {
            for (Customer allCustomer : allCustomers) {
                if (!allCustomer.invisibleUsers.contains(user.getEmail())) {
                    ret += (allCustomer.getEmail()) + "\n";
                }
            }
        }
        ret += "END";
        writer.println(ret);
        writer.flush();

        String name = reader.readLine();
        File f = new File("messageHistory.csv");
        BufferedWriter brw = new BufferedWriter(new FileWriter("messageHistory.csv"));
        if (name.isEmpty()) {
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
        writer.println("Exported!");
        writer.flush();
        brw.close();
    }

    //uses a hashmap to find the most common words in a user's message history with a seller
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
                    return String.join(" ", highestStrings);
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

    public static void handleClient(Socket socket) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());

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
            String initOption = reader.readLine();
            switch (initOption) {
                case "1":
                    Users user = login();
                    if (user != null) {
                        boolean loggedIn = true;
                        do {
                            saveAll(); //Saves all data everytime inner-main menu is loaded
                            writer.println("\nUser " + user.getEmail());
                            writer.flush();
                            String newMessages = showNewMessages(user);
                            writer.println(newMessages);
                            writer.flush();
                            //switch case for menu
                            if (user instanceof Seller) {
                                writer.println("Seller");
                                writer.flush();
                                String ret = "1.See messages\n2.Send message\n3.Edit Account\n" +
                                        "4.Delete Account\n" + "5.Hide User\n6.Block User\n" +
                                        "7.Get Statistics\n8.Logout\n"
                                        + "9.Edit Message\n10.Delete Message\n11.Export CSV\n12." +
                                        "Create Store\n13.Censor Texts\nEND";
                                writer.println(ret);
                                writer.flush();
                            } else {
                                writer.println("Customer");
                                writer.flush();
                                String ret = "1.See messages\n2.Send message\n3.Edit Account\n" +
                                        "4.Delete Account\n" + "5.Hide User\n6.Block User\n" +
                                        "7.Get Statistics\n8.Logout\n"
                                        + "9.Edit Message\n10.Delete Message\n11.Export CSV\n12." +
                                        "Buy Products\n13.Censor Texts\nEND";
                                writer.println(ret);
                                writer.flush();
                            }
                            String nextOption = reader.readLine();
                            switch (nextOption) {
                                case "1":
                                    String allMsgs = printMsgs(user);
                                    writer.println(allMsgs);
                                    writer.flush();
                                    break;
                                case "2":
                                    sendMessage(user);
                                    break;
                                case "3":
                                    editAccount(user);
                                    break;
                                case "4":
                                    if (user instanceof Seller) {
                                        allSellers.remove(user);
                                    }
                                    if (user instanceof Customer) {
                                        allCustomers.remove(user);
                                    }
                                    allUsers.remove(user);
                                    user.deleteAccnt();
                                    loggedIn = false;
                                    break;
                                case "5":
                                    printUsers(user);
                                    writer.println("Enter user you would like to hide from:");
                                    writer.flush();
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
                                    printUsers(user);
                                    writer.println("Enter user you would like to block:");
                                    writer.flush();
                                    //blocks a user by adding them to a users blocked list
                                    String blocked = reader.readLine();
                                    for (Users allUser : allUsers) {
                                        if (allUser.getEmail().equalsIgnoreCase(blocked)) {
                                            user.block(allUser.getEmail());
                                        }
                                    }
                                    writer.println("User blocked.");
                                    break;
                                case "7":
                                    getStatistics(user);
                                    break;
                                case "8":
                                    System.out.println("Logging out!");
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
                                    String censor = reader.readLine();
                                    user.addCensored(censor);
                                    user.setHaveCensor(true);
                                    String choice = reader.readLine();
                                    switch (choice) {
                                        case "1":
                                            user.setCensorReplacement("****");
                                            break;
                                        case "2":
                                            writer.println("Enter your replacement words");
                                            writer.flush();
                                            String replaceWords = reader.readLine();
                                            user.setCensorReplacement(replaceWords);
                                            break;
                                        default:
                                            writer.println("\nInvalid input.\n");
                                            writer.flush();
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
                    writer.println(createAccount());
                    writer.flush();
                    break;
                case "3":
                    exit = true;
                    //System.out.println("Ending application!");
                    break;
                default:
                    //System.out.println("Invalid input.");
                    break;

            }
            System.out.println();
        } while (!exit);
    }
}

