package messaging_system;

import java.io.*;
import java.sql.Array;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Processor {

    private static ArrayList<Store> allStores = new ArrayList<Store>();
    private static ArrayList<Message> allMessages = new ArrayList<Message>();
    private static ArrayList<Seller> allSellers = new ArrayList<Seller>();
    private static ArrayList<Customer> allCustomers = new ArrayList<Customer>();

    private static ArrayList<Users> allUsers = new ArrayList<Users>();

    public static void main(String[] args) throws IOException {
        File f1 = new File("user_info.txt");
        File f2 = new File("store_info.txt");
        File f3 = new File("message_info.txt");
        if(f1.exists() && f2.exists() && f3.exists()){
            loadFiles(f1,f2,f3);
        }
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Main Menu. Please choose an option.\n1.Login\n2.Create Account\n3.Exit\n");
            switch (scanner.nextLine()) {
                case "1":
                    Users user = login();
                    if (user != null) {
                        boolean loggedIn = true;
                        do {
                            System.out.println("\nUser " + user.getEmail());
                            if (user instanceof Seller) {
                                System.out.println("1.See messages\n2.Send message\n3.Edit Account\n" +
                                        "4.Delete Account\n" + "5.Hide User\n6.Block User\n7.Get Statistics\n8.Logout\n"
                                        + "9.Edit Message\n10.Delete Message\n11.Export CSV\n12.Create Store");
                            } else {
                                System.out.println("1.See messages\n2.Send message\n3.Edit Account\n4.Delete Account\n"
                                        + "5.Hide User\n6.Block User\n7.Get Statistics\n8.Logout\n" +
                                        "9.Edit Message\n10.Delete Message\n11.Export CSV\n12.Buy products");
                            }
                            switch (scanner.nextLine()) {
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
                                    System.out.println("Enter user you would like to hide from:");
                                    String hidden = scanner.nextLine();
                                    for (Users allUser : allUsers) {
                                        if (allUser.getEmail().equals(hidden)) {
                                            user.hide(allUser.getEmail());
                                        }
                                    }
                                    System.out.println("User hidden.");
                                    break;
                                case "6":
                                    System.out.println("Enter user you would like to block:");
                                    String blocked = scanner.nextLine();
                                    for (Users allUser : allUsers) {
                                        if (allUser.getEmail().equals(blocked)) {
                                            user.block(allUser.getEmail());
                                        }
                                    }
                                    System.out.println("User blocked.");
                                    break;
                                case "7":
                                    getStatistics(user);
                                    break;
                                case "8":
                                    System.out.println("\nLogging out!\n");
                                    loggedIn = false;
                                    saveAll();
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
                    System.out.println("Ending application!");
                    break;
                default:
                    System.out.println("Invalid input.");
                    break;

            }
            System.out.println();
        } while (!exit);
    }

    public static void printMsgs(Users user) {
        for (Message allMessage : allMessages) {
            if (user.messagesReceived.contains(allMessage)) {
                String content = allMessage.getContent();
                String time = allMessage.getTimeStamp();
                String sender = allMessage.getSenderID();
                System.out.println("[" + time + "] " + sender + ": " + content);
            }
            if (user.messagesSent.contains(allMessage)) {
                String content = allMessage.getContent();
                String time = allMessage.getTimeStamp();
                String recipient = allMessage.getRecipientID();
                System.out.println("[" + time + "] " + "You messaged " + recipient + ": " + content);
            }
        }
    }

    public static Users login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        for (Users allUser : allUsers) {
            if (allUser.getEmail().equals(email)) {
                if (allUser.getPassword().equals(password)) {
                    System.out.println("Logged in!");
                    return allUser;
                }
            }
        }
        System.out.println("Email or password is incorrect.");
        return null;
    }

    public static void sendMessage(Users user) {
        Scanner scanner = new Scanner(System.in);
        if (user instanceof Seller) {
            System.out.println("1. View a list of people to message.\n2. Message a specific user.");
        } else {
            System.out.println("1. View a list of stores to message.\n2. Message a specific seller.");
        }
        switch (scanner.nextLine()) {
            case "1":
                printUsers(user);
            case "2":
                System.out.println("Who would you like to message?");
                String recipient = scanner.nextLine();
                Users recipUser = null;
                if (user instanceof Seller) {
                    for (Customer allCustomer : allCustomers) {
                        if (allCustomer.getEmail().equals(recipient)) {
                            if (allCustomer.blockedUsers.contains(user) || allCustomer.invisibleUsers.contains(user)) {
                                System.out.println("You cannot message that customer.");
                            } else {
                                recipUser = allCustomer;
                            }
                        }
                    }
                } else {
                    for (Seller allSeller : allSellers) {
                        if (allSeller.getEmail().equals(recipient)) {
                            if (allSeller.blockedUsers.contains(user)) {
                                System.out.println("You cannot message that seller.");
                            } else {
                                recipUser = allSeller;
                            }

                        }
                    }
                    for (Store allStore : allStores) {
                        if (allStore.getName().equals(recipient)) {
                            if (allStore.getSeller().blockedUsers.contains(user)) {
                                System.out.println("You cannot message that store.");
                            } else {
                                recipUser = allStore.getSeller();
                            }

                        }
                    }
                }

                if (recipUser == null) {
                    System.out.println("Invalid recipient.");
                    break;
                }
                System.out.println("What is your message?");
                String content = scanner.nextLine();

                Message message = new Message(content, user.getEmail(), recipUser.getEmail(),
                        LocalTime.now().toString());
                allMessages.add(message);
                user.sendMessage(message, recipUser);


                break;
            default:
                System.out.println("Invalid input.");
                break;
        }
    }

    public static void printUsers(Users user) {
        if (user instanceof Customer) {
            for (Store allStore : allStores) {
                System.out.println(allStore.getName());
            }
        } else {
            for (Customer allCustomer : allCustomers) {
                if (!allCustomer.invisibleUsers.contains(user)) {
                    System.out.println(allCustomer.getEmail());
                }
            }
        }
        System.out.println();
    }

    public static void editAccount(Users user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter updated email:");
        String email = scanner.nextLine();
        boolean alrExists = false;
        for (Users allUser : allUsers) {
            if (allUser.getEmail().equals(email)) {
                System.out.println("Account with that email already exists.");
                alrExists = true;
            }
        }
        if (!alrExists) {
            System.out.println("Enter updated password:");
            String password = scanner.nextLine();
            user.editAccnt(email, password);
        }
    }

    public static void getStatistics(Users user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to sort your data?\n1. Yes\n2. No");
        boolean sort = scanner.nextLine().equals("1");
        ArrayList<String> data = new ArrayList<String>();
        ArrayList<String> data2 = new ArrayList<String>();
        if (user instanceof Seller) {
            for (Customer x : allCustomers) {
                data.add(x.getEmail() + " has sent " + x.getNumMessages() + " messages." +
                        " Common words include: " + getCommonWords(allMessages)); //no idea how to figure this out
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
        for (String x : data) {
            System.out.println(x);
        }
        System.out.println();
        if (user instanceof Customer) {
            for (String x : data2) {
                System.out.println(x);
            }
            System.out.println();
        }
    }

    public static void editMessage(Users user) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Message> msgsSent = new ArrayList<Message>();

        if (user.messagesSent != null) {
            msgsSent = user.messagesSent;
        }
        int i = 1;
        for (Message x : msgsSent) {
            System.out.println(i + ": " + x.getContent());
        }
        System.out.println("\nEnter the number of the message you would like to edit:");
        Message message = msgsSent.get(scanner.nextInt() - 1);
        String content = message.getContent();
        scanner.nextLine();
        System.out.println("What would you like the message to say now.");
        String update = scanner.nextLine();
        message.editMessage(update);
        System.out.println("Message updated.");
    }

    public static void deleteMessage(Users user) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Message> msgsSent = new ArrayList<Message>();
        if (user.messagesSent != null) {
            msgsSent = user.messagesSent;
        }
        int i = 1;
        for (Message x : msgsSent) {
            System.out.println(i + ": " + x.getContent());
        }
        if (msgsSent.isEmpty()) {
            System.out.println("No messsage history.");
        } else {
            System.out.println("\nEnter the number of the message you would like to delete:");
            Message message = msgsSent.get(scanner.nextInt() - 1);
            scanner.nextLine();
            user.deleteMessage(message);
            System.out.println("Message deleted.");
        }

    }

    public static void buyProducts(Customer user) {
        Scanner scanner = new Scanner(System.in);
        int i = 1;
        for (Store allStore : allStores) {
            System.out.println(i + ". " + allStore.getName());
            i++;
        }
        System.out.println("Enter the number for the store you want to purchase from:");
        Store store = allStores.get(scanner.nextInt() - 1);
        scanner.nextLine();
        for (String product : store.getProductList()) {
            System.out.println(i + ". " + product);
        }
        System.out.println("Enter the number for the product you want to buy:");
        ArrayList<String> finalList = new ArrayList<>();
        String product = store.getProductList().get(scanner.nextInt() - 1);
        scanner.nextLine();
        if (user.getProductsPurchased() != null) {
            finalList = user.getProductsPurchased();
        }
        finalList.add(product);
        user.setProductsPurchased(finalList);
        System.out.println("Purchased!");
        scanner.nextLine();
    }

    public static void makeStore(Seller user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the store name:");
        String name = scanner.nextLine();
        System.out.println("How many items will you be selling?");
        int count = scanner.nextInt();
        scanner.nextLine();
        ArrayList<String> products = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            System.out.println("Name of product " + i + "?");
            products.add(scanner.nextLine());
        }
        Store store = new Store(name, products, user);
        allStores.add(store);
        user.addStore(store);
        System.out.println("Store made!");
    }

    public static String createAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        System.out.println("Choose type of account to create:\n1.Customer\n2.Seller\n");
        switch (scanner.nextLine()) {
            case "1":
                for (Customer allCustomer : allCustomers) {
                    if (allCustomer.getEmail().equals(email)) {
                        return "Account with that email already exists.";
                    }
                }
                Customer customer = new Customer(email, password);
                allCustomers.add(customer);
                allUsers.add(customer);
                return "Account created! You may now login.";
            case "2":
                for (Seller allSeller : allSellers) {
                    if (allSeller.getEmail().equals(email)) {
                        return "Account with that email already exists.";
                    }
                }
                Seller seller = new Seller(email, password);
                allSellers.add(seller);
                allUsers.add(seller);
                return "Account created! You may now login.";
            default:
                return "Invalid input.\n";
        }

    }

    public static void exportCSV(Users user) throws IOException {
        Scanner scanner = new Scanner(System.in);
        if (user instanceof Customer) {
            for (Seller x : allSellers) {
                System.out.println(x.getEmail());
            }
        } else {
            for (Customer allCustomer : allCustomers) {
                if (!allCustomer.invisibleUsers.contains(user)) {
                    System.out.println(allCustomer.getEmail());
                }
            }
        }
        System.out.println();

        System.out.println("Whose conversation would you like to export (leave blank for all).");
        String name = scanner.nextLine();
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
        System.out.println("Exported!");
        brw.close();
    }

    public static String getCommonWords(ArrayList<Message> allMessages) // returns with words separated by a space
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
                usersWriter.write(time + "," + sender + "," + recipient + "," + content + "\n");
            }

            usersWriter.write("Received" + "\n");
            for (Message message : user.getMessagesReceived()) {
                String content = message.getContent();
                String time = message.getTimeStamp();
                String sender = message.getSenderID();
                String recipient = message.getRecipientID();
                usersWriter.write(time + "," + sender + "," + recipient + "," +  content + "\n");
            }

            if (user instanceof Seller) {
                usersWriter.write("Stores");
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
            }
            if (user instanceof Customer) {
                usersWriter.write("Purchased");
                for (String str : ((Customer) user).getProductsPurchased()) {
                    String items = "";
                    items += str + ",";
                    items = items.substring(0, items.length() - 1);
                    usersWriter.write(items);
                }
            }
            usersWriter.write("Done");
        }
        usersWriter.close();


        File stores = new File("store_info.txt");
        BufferedWriter storeWriter = new BufferedWriter(new FileWriter(stores));
        for (Store store : allStores)
        {
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
        for (Message message : allMessages)
        {
            String content = message.getContent();
            String time = message.getTimeStamp();
            String sender = message.getSenderID();
            String recipient = message.getRecipientID();
            messageWriter.write(time + "," + sender + "," + recipient + ","  + content + "\n");
        }
        messageWriter.close();


    }






    public static void loadFiles(File fileOne, File fileTwo, File fileThree) throws FileNotFoundException, IOException{
        FileReader fr = new FileReader(fileOne);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        while(line!=null){
                String sOrC = line;
                line = bfr.readLine();
                String email = line;
                line = bfr.readLine();
                String password = line;
                ArrayList<String> blockedUsers = new ArrayList<>();
                line = bfr.readLine();
                if(!line.equals(";;")) {
                    String blockCheck = line.substring(1, line.length()-1);
                    String[] blockCheck2 = blockCheck.split(",");
                    blockedUsers.addAll(Arrays.asList(blockCheck2));
                }
                line = bfr.readLine();
                ArrayList<String> invisibleUsers = new ArrayList<>();
                if(!line.equals("||")){
                    String invCheck = line.substring(1, line.length()-1);
                    String[] invCheck2 = invCheck.split(",");
                    invisibleUsers.addAll(Arrays.asList(invCheck2));
                }
                line = bfr.readLine();
                line = bfr.readLine();
                ArrayList<Message> messagesSent = new ArrayList<>();
                while(!(line.equals("Received") )){
                    if(!line.isEmpty()) {
                        String[] messageCheck = line.split(",");
                        Message message = new Message(messageCheck[3], messageCheck[1], messageCheck[2], messageCheck[0]);
                        messagesSent.add(message);
                        line = bfr.readLine();
                    } else{
                        line = bfr.readLine();
                    }
                }
                line = bfr.readLine();
                ArrayList<Message> messagesReceived = new ArrayList<>();
                while(!(line.equals("Stores") || line.equals("Purchased"))){
                    if(!line.isEmpty()) {
                        String[] messageCheck = line.split(",");
                        Message message = new Message(messageCheck[3], messageCheck[1], messageCheck[2], messageCheck[0]);
                        messagesReceived.add(message);
                        line = bfr.readLine();
                    } else{
                        line = bfr.readLine();
                    }
                }
                if(sOrC.equals("Seller")){
                    Seller seller = new Seller(email, password);
                    ArrayList<Store> stores = new ArrayList<>();
                    line = bfr.readLine();
                    while(!line.equals("Done")){
                        String storeCheck = line.substring(0,line.indexOf(";"));
                        String[] storeInfo = storeCheck.split(",");
                        String storeName = storeInfo[0];
                        String storeSeller = storeInfo[1];
                        String products = line.substring(line.indexOf(";") + 1, line.length()-1);
                        String[] productList = products.split(",");
                        ArrayList<String> prodList = new ArrayList<>(Arrays.asList(productList));

                        Store store = new Store(storeName, prodList, seller);
                        stores.add(store);

                    }
                    seller.setBlockedUsers(blockedUsers);
                    seller.setInvisibleUsers(invisibleUsers);
                    seller.setMessagesSent(messagesSent);
                    seller.setMessagesReceived(messagesReceived);
                    seller.setStores(stores);

                    allSellers.add(seller);
                    allUsers.add(seller);

                }else{
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
        bfr.close();

        FileReader fr2 = new FileReader(fileTwo);
        BufferedReader bfr2 = new BufferedReader(fr2);
        line = bfr2.readLine();
        while(line!=null){
            String storeInfo = line.substring(0, line.indexOf(";"));
            String[] storeList1 = storeInfo.split(",");
            String storeName = storeList1[0];
            String storeSellerID = storeList1[1];
            Seller storeSeller = new Seller("", "");
            for(Seller seller: allSellers){
                if(storeSellerID.equals(seller.getEmail())){
                    storeSeller = new Seller(seller.getEmail(), seller.getPassword());
                    break;
                }
            }
            String products = line.substring(line.indexOf(";") + 1, line.length()-1);
            String[] productList = products.split(",");
            ArrayList<String> prodList = new ArrayList<>(Arrays.asList(productList));

            Store store = new Store(storeName, prodList, storeSeller);

            allStores.add(store);
            line = bfr.readLine();


        }
        bfr2.close();

        FileReader fr3 = new FileReader(fileThree);
        BufferedReader bfr3 = new BufferedReader(fr3);
        line = bfr3.readLine();

        while(line!=null){
            String[] messageInfo = line.split(",");
            Message message = new Message(messageInfo[3], messageInfo[1], messageInfo[2], messageInfo[0]);
            allMessages.add(message);
            line = bfr3.readLine();
        }

        bfr3.close();


    }


}
