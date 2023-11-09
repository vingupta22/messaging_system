package messaging_system;

import java.sql.Array;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Processor {

    private static ArrayList<Store> allStores = new ArrayList<Store>();
    private static ArrayList<Message> allMessages = new ArrayList<Message>();
    private static ArrayList<Seller> allSellers = new ArrayList<Seller>();
    private static ArrayList<Customer> allCustomers = new ArrayList<Customer>();

    private static ArrayList<Users> allUsers = new ArrayList<Users>();

    public static void main(String[] args) {
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
                                        + "9.Edit Message\n10.Delete Message\n11.Create Store");
                            } else {
                                System.out.println("1.See messages\n2.Send message\n3.Edit Account\n4.Delete Account\n"
                                        + "5.Hide User\n6.Block User\n7.Get Statistics\n8.Logout\n" +
                                        "9.Edit Message\n10.Delete Message\n11.Buy products");
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
                                    allUsers.remove(user);
                                    user.deleteAccnt();
                                    break;
                                case "5":
                                    System.out.println("Enter user you would like to hide from:");
                                    String hidden = scanner.nextLine();
                                    for (Users allUser : allUsers) {
                                        if (allUser.getEmail().equals(hidden)) {
                                            user.hide(allUser);
                                        }
                                    }
                                    System.out.println("User hidden.");
                                    break;
                                case "6":
                                    System.out.println("Enter user you would like to block:");
                                    String blocked = scanner.nextLine();
                                    for (Users allUser : allUsers) {
                                        if (allUser.getEmail().equals(blocked)) {
                                            user.block(allUser);
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
                                    break;
                                case "9":
                                    editMessage(user);
                                    break;
                                case "10":
                                    deleteMessage(user);
                                    break;
                                case "11":
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
                        } while(loggedIn);
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
        } while(!exit);
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
                        " Common words include: "); //no idea how to figure this out
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
        Message message = msgsSent.get(scanner.nextInt()-1);
        String content = message.getContent();
        scanner.nextLine();
        System.out.println("What would you like the message to say now.");
        String update = scanner.nextLine();
        message.editMessage(update);
        System.out.println("Message updated.");
    }

    public static void deleteMessage(Users user) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Message> msgsSent= new ArrayList<Message>();
        if (user.messagesSent != null) {
            msgsSent = user.messagesSent;
        }
        int i = 1;
        for (Message x : msgsSent) {
            System.out.println(i + ": " + x.getContent());
        }
        System.out.println("\nEnter the number of the message you would like to delete:");
        Message message = msgsSent.get(scanner.nextInt()-1);
        scanner.nextLine();
        user.deleteMessage(message);
        System.out.println("Message deleted.");
    }

    public static void buyProducts(Customer user) {
        Scanner scanner = new Scanner(System.in);
        int i = 1;
        for (Store allStore : allStores) {
            System.out.println(i + ". " + allStore.getName());
        }
        System.out.println("Enter the number for the store you want to purchase from:");
        Store store = allStores.get(scanner.nextInt()-1);
        scanner.nextLine();
        for (String product : store.getProductList()) {
            System.out.println(i + ". " + product);
        }
        System.out.println("Enter the number for the product you want to buy:");
        ArrayList<String> finalList = new ArrayList<>();
        String product = store.getProductList().get(scanner.nextInt()-1);
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



}
