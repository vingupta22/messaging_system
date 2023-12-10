# BoilerNetwork
1. To run and compile the program you need to run the Processor class, and then an instance of the Client class for each client required. One possible way to do this is to execute the following:
```
   javac Processor.java
   java Processor
   javac Client.java
   java Client.java
```
2. Zishuo Wang submitted the report on brightspace, Ishaan Handa sumbitted the video presentation and Vinay Gupta submitted the Vocareum Workspace.
3. **Classes:**
   - **Message**
       - The Message class contains the outline for a Message object. It represents a message that can be sent or received by users of the system. It has four private fields: content, senderID, recipientID, and timeStamp. These fields store the text of the message, the identifier of the sender, the identifier of the recipient, and the time when the message was created, respectively. The class has a constructor that takes four parameters and assigns them to the corresponding fields. It also has four getter methods that return the values of the fields. Additionally, it has a method editMessage that allows the sender to modify the content of the message after it has been created. This method takes a new message as a parameter and updates the content field accordingly. Message objects are created and stored in arrays within the Processor class.

Instance Variables:

private String content: Represents the content of the message.
private String senderID: Represents the sender's ID.
private String recipientID: Represents the recipient's ID.
private String timeStamp: Represents the timestamp of the message.
private boolean hasRead: Indicates whether the message has been read.

Constructor:

public Message(String content, String senderID, String recipientID, String timeStamp, boolean hasRead): Initializes a Message object with the provided content, sender ID, recipient ID, timestamp, and read status.

Accessor and Mutator Methods:

public boolean hasRead(): Returns the read status of the message.
public void setHasRead(boolean hasRead): Sets the read status of the message.
public void editMessage(String newMessage): Edits the content of the message.
public String getContent(): Returns the content of the message.
public String getRecipientID(): Returns the recipient's ID.
public String getSenderID(): Returns the sender's ID.
public String getTimeStamp(): Returns the timestamp of the message.
    
It was tested using the following main method:
```
public static void main(String[] args) {
        Message first = new Message("Hello World", "Customer1", "Seller1", "13:09:13.103653700", false);
        System.out.println("Test 1 for getContent method");
        if (first.getContent().equals("Hello World")) {
            System.out.println("getContent method passed the test case");
        } else {
            System.out.println("There's an error in the getContent method");
        }
        System.out.println("Test 2 for getSenderID method");
        if (first.getSenderID().equals("Customer1")) {
            System.out.println("getSenderID method passed the test case");
        } else {
            System.out.println("There's an error in the getSenderID method");
        }
        System.out.println("Test 3 for getRecipientID method");
        if (first.getRecipientID().equals("Seller1")) {
            System.out.println("getRecipientID method passed the test case");
        } else {
            System.out.println("There's an error in the getRecipientID method");
        }
        System.out.println("Test 4 for getTimeStamp method");
        if (first.getTimeStamp().equals("13:09:13.103653700")) {
            System.out.println("getTimeStamp method passed the test case");
        } else {
            System.out.println("There's an error in the getTimeStamp method");
        }
        System.out.println("Test 5 for editMessage method");
        first.editMessage("Hello Earth");
        if (first.getContent().equals("Hello Earth")) {
            System.out.println("getContent method passed the test case");
        } else {
            System.out.println("There's an error in the getContent method");
        }
    }
```
         
   - **Users**
       - The Users class contains the outline for a User object. It is extended by Customer and Seller. The class Users is part of the messaging_system package. It represents a user of the system who can send and receive messages, as well as block or hide other users. It contains methods and fields that are shared by both the Seller and Customer objects.
         
   Instance Variables:
   
   String email: Represents the email of the user.
   String password: Represents the password of the user.
   ArrayList<String> blockedUsers: Represents a list of users blocked by the current user.
   ArrayList<String> invisibleUsers: Represents a list of users that are hidden from the current user.
   ArrayList<Message> messagesSent: Represents a list of messages sent by the user.
   ArrayList<Message> messagesReceived: Represents a list of messages received by the user.
   ArrayList<String> censored: Represents a list of censored words.
   String censorReplacement: Represents the replacement string for censored words.
   Boolean haveCensor: Indicates whether the user has a censor filter enabled.
   
   Constructor:
   
   public Users(String email, String password): Initializes a Users object with the provided email and password. It also initializes various ArrayLists and other variables.
   Accessor and Mutator Methods:
   
   Accessor and mutator methods for email, password, blockedUsers, invisibleUsers, censored, censorReplacement, and haveCensor.
   Additional methods include addCensored, getCensored, setCensored, setCensorReplacement, and getCensorReplacement.
   
   User Management Methods:
   
   hide(String hiddenUser): Adds a user to the invisible list, effectively hiding them.
   block(String blockUser): Adds a user to the blocked list, effectively blocking them.
   deleteAccnt(): Deletes the user account by setting relevant fields to null.
   editAccnt(String email, String password): Edits the user account by updating the email and password.
   
   Message Handling Methods:
   
   sendMessage(Message msg, Users user): Sends a message to another user. It checks if the sender is a seller or customer and handles the message accordingly.
   deleteMessage(Message msg): Deletes a specific message from the messagesSent list.
   
   Message-related Accessor Methods:
   
   getMessagesSent(): Returns the list of messages sent by the user.
   getMessagesReceived(): Returns the list of messages received by the user.
   setMessagesSent(ArrayList<Message> messageList): Sets the list of messages sent by the user.
   setMessagesReceived(ArrayList<Message> messageList): Sets the list of messages received by the user.
   getNumMessages(): Returns the number of messages sent by the user.

   It was tested using the following main method:
```
public static void main(String[] args){
    Users user = new Users("email@email.com", "password");
    System.out.println("Test1: Check getEmail and getPassword: ");
    if(user.getEmail().equals("email@email.com") && user.getPassword().equals("password")){
        System.out.println("Test Successful");
    } else{
        System.out.println("Test Failed: incorrect username or password");
    }


    System.out.println("");


    System.out.println("Test2: hide and getInvisibleUsers methods");
    user.hide("hiddenUser");
    ArrayList<String> invisibleUsers = user.getInvisibleUsers();
    if (invisibleUsers.size() == 1 && invisibleUsers.contains("hiddenUser")) {
        System.out.println("Test Successful");
    } else {
        System.out.println("Test failed: Invisible users not updated correctly");
    }


    System.out.println("");


    System.out.println("Test3: block and getBlockedUsers methods");
    user.block("blockedUser");
    ArrayList<String> blockedUsers = user.getBlockedUsers();
    if(!blockedUsers.isEmpty() && blockedUsers.contains("blockedUser")){
        System.out.println("Test Successful");
    } else{
        System.out.println("Test failed: blocked user not added correctly.");
    }

    System.out.println("");


    System.out.println("Test4: Checks the editAccount method");
    user.editAccnt("new@gmail.com", "newPassword");
    if(user.getEmail().equals("new@gmail.com") && user.getPassword().equals("newPassword")){
        System.out.println("Test Successful");
    } else{
        System.out.println("Test failed. There was an error editing the account");
    }

    System.out.println("");


    System.out.println("Test5: Checks the sendMessage method");
    Customer customer = new Customer("customer@gmail.com", "password");
    Seller seller = new Seller("seller@gmail.com", "password");
    Message message = new Message("testMessage", "customer@gmail.com", "seller@gmail.com", "", false);
    customer.sendMessage(message, seller);
    if(customer.getMessagesSent().size() == 1 && seller.getMessagesReceived().size() == 1){
        System.out.println("Test Successful");
    } else{
        System.out.println("Test failed: the message was either not sent, or there was an error");
    }

    System.out.println("");


    System.out.println("Test6: chekcs the delete message method");
    customer.deleteMessage(message);
    if(customer.getMessagesSent().isEmpty()){
        System.out.println("Test Successful");
    } else{
        System.out.println("Test failed: there was an error deleting the message");
    }

    System.out.println("");


    System.out.println("Test7: checks to see if the account deleted properly");
    user.deleteAccnt();
    if(user.getEmail() == null && user.getPassword() == null){
        System.out.println("Test Successful");
    } else{
        System.out.println("Test failed: there was an error with deleting the account");
    }




}
```


   - **Customer**
      - The Customer class contains the outline for a Customer object. It represents a User that is of the type Customer. The Customer class extends the User class.
     
Instance Variables:

private ArrayList<String> productsPurchased: This ArrayList is used to store the products purchased by the customer. Each product is represented as a String.
private ArrayList<Message> messageThread: This ArrayList is used to store messages related to the customer. The assumption is that there is another class named Message.

Constructor:

public Customer(String email, String password): This constructor initializes a Customer object by calling the constructor of the superclass Users with the provided email and password. It also initializes the productsPurchased and messageThread ArrayLists.

Accessor Methods:

public ArrayList<String> getProductsPurchased(): Returns the ArrayList containing the products purchased by the customer.
public void setProductsPurchased(ArrayList<String> products): Sets the ArrayList of products purchased to the provided ArrayList.

It was tested using the following Main method:
```
public static void main(String[] args) {

        Users user = new Users("user@example.com", "password");

        System.out.println("Test 2: getEmail and getPassword methods");
        if (user.getEmail().equals("user@example.com") && user.getPassword().equals("password")) {
            System.out.println("Success: getEmail and getPassword methods");
        } else {
            System.out.println("Test failed: Incorrect email or password");
        }

        System.out.println("Test 3: hide and getInvisibleUsers methods");
        user.hide("hiddenUser");
        ArrayList<String> invisibleUsers = user.getInvisibleUsers();
        if (invisibleUsers.size() == 1 && invisibleUsers.contains("hiddenUser")) {
            System.out.println("Success: hide and getInvisibleUsers methods");
        } else {
            System.out.println("Test failed: Invisible users not updated correctly");
        }

        System.out.println("Test 4: block and getBlockedUsers methods");
        user.block("blockedUser");
        ArrayList<String> blockedUsers = user.getBlockedUsers();
        if (blockedUsers.size() == 1 && blockedUsers.contains("blockedUser")) {
            System.out.println("Success: block and getBlockedUsers methods");
        } else {
            System.out.println("Test failed: Blocked users not updated correctly");
        }

        System.out.println("Test 5: deleteAccnt method");
        user.deleteAccnt();
        if (user.getEmail() == null) {
            System.out.println("Success: deleteAccnt method");
        } else {
            System.out.println("Test failed: Email not deleted after deleting account");
        }

        System.out.println("Test 6: editAccnt method");
        user.editAccnt("newemail@example.com", "newpassword");
        if (user.getEmail().equals("newemail@example.com") && user.getPassword().equals("newpassword")) {
            System.out.println("Success: editAccnt method");
        } else {
            System.out.println("Test failed: Email or password not updated correctly");
        }

        System.out.println("All tests completed!");
    }
```
   - Seller
      - The Seller class represents a seller in a messaging system. It extends the Users class, inheriting basic user functionality, and includes methods specific to sellers, such as managing stores and messages. The class allows sellers to retrieve and modify information about their associated stores and message   
     
Instance Variables:

private ArrayList<Store> stores: Represents a list of stores associated with the seller.
private ArrayList<Message> messageThread: Represents a list of messages associated with the seller.

Constructor:

public Seller(String email, String password): Initializes a Seller object by calling the constructor of the superclass Users with the provided email and password. It also initializes the stores and messageThread ArrayLists.

Accessor and Mutator Methods:

public ArrayList<Store> getStores(): Returns the list of stores associated with the seller.
public void addStore(Store store): Adds a store to the list of stores associated with the seller.
public ArrayList<Message> getMessageThread(): Returns the list of messages associated with the seller.
public void addMessage(Message message): Adds a message to the list of messages associated with the seller.
public void setStores(ArrayList<Store> storeList): Sets the list of stores associated with the seller.

It was tested using the following main method:
```
public static void main(String[] args) {
        ArrayList<messaging_system.Message> messages = new ArrayList<messaging_system.Message>();
        messaging_system.Message m = new messaging_system.Message("Hey", null, null, null, false);
        messaging_system.Message l = new messaging_system.Message("Hi", null, null, null, false);
        messages.add(m);
        messages.add(l);
        ArrayList<String> prods = new ArrayList<String>();
        ArrayList<String> otherProds = new ArrayList<String>();
        prods.add("Gloves");
        prods.add("Briefcase");
        otherProds.add("Boots");
        otherProds.add("Jacket");
        Store s = new Store("S", prods, null);
        Store t = new Store("T", otherProds, null);

        ArrayList<Store> stores = new ArrayList<Store>();
        stores.add(s);
        stores.add(t);
        Seller theSeller = new Seller("a@google.com", "pancakes");
        for (var i = 0; i < stores.size(); i++) {
            stores.get(i).setSeller(theSeller);
        }
        theSeller.setStores(stores);



        if (theSeller.getStores().equals(stores)) {
            System.out.println("Test succeeded, stores successfully set and returned.");
        } else {
            System.out.println("Test failed, stores were not successfully set or returned.");
        }
        ArrayList<String> newProducts = new ArrayList<String>();
        newProducts.add("newShirt");
        newProducts.add("newShoes");
        Store newStore = new Store("New Store", newProducts, theSeller);
        theSeller.addStore(newStore);
        if (theSeller.getStores().get(2).equals(newStore)) {
            System.out.println("Test succeeded, store successfully added");
        } else {
            System.out.println("Test failed, store could not be added");
        }
        theSeller.addMessage(m);
        theSeller.addMessage(l);
        if (theSeller.messageThread.get(0).equals(m)) {
            System.out.println("Test succeeded, message added!");
        } else {
            System.out.println("Test failed, message could not be added!");

        }

        if (theSeller.getMessageThread().equals(messages)) {
            System.out.println("Test succeeded, message thread successfully got!");
        } else {
            System.out.println("Test failed, message thread not gotten");
        }
   }
```

   - Store
      - The Store class represents a store in the messaging system. It allows retrieval and modification of information about the store, such as its name, associated products, and the seller managing the store. The main method is used for testing the functionality of the class.
    
Instance Variables:

private String name: Represents the name of the store.
ArrayList<String> productList: Represents a list of products associated with the store.
private Seller seller: Represents the seller associated with the store.

Constructor:

public Store(String name, ArrayList<String> productList, Seller seller): Initializes a Store object with the provided name, product list, and seller.

Accessor and Mutator Methods:

public String getName(): Returns the name of the store.
public void setName(String name): Sets the name of the store.
public ArrayList<String> getProductList(): Returns the list of products associated with the store.
public Seller getSeller(): Returns the seller associated with the store.

Additional Method:

public boolean itemExists(String item): Checks if a specific item exists in the product list of the store.

It was tested using the following main method: 
```
   public static void main(String[] args) {
        ArrayList<String> products = new ArrayList<>();
        products.add("Iphone 91");
        products.add("Ipod 24");
        Seller customers = new Seller("apple@apple.com", "andriod");
        Store apple = new Store("Apple", products, customers);

        System.out.println("Test 1 for getName method");
        if (apple.getName().equals("Apple")) {
            System.out.println("getName method passed the test");
        } else {
            System.out.println("There's an error in the getName method");
        }
        System.out.println("Test 2 for getEmail method");
        if (apple.getSeller().getEmail().equals("apple@apple.com")) {
            System.out.println("getEmail method passed the test");
        } else {
            System.out.println("There's an error in the getEmail method");
        }
        System.out.println("Test 3 for getPassword method");
        if (apple.getSeller().getPassword().equals("andriod")) {
            System.out.println("getPassword method passed the test");
        } else {
            System.out.println("There's an error in the getPassword method");
        }
        System.out.println("Test 4 for getProductList method");
        if (apple.getProductList().equals(products)) {
            System.out.println("getProductList method passed the test");

        } else {
            System.out.println("There's an error in the getProductList method");
        }

        System.out.println("Test 5 for setName method");
        apple.setName("Andriod");
        if (apple.getName().equals("Andriod")) {
            System.out.println("setName method passed the test");
        } else {
            System.out.println("There's an error in the setName method");
        }
        System.out.println("Test 6 for itemExists method");
        if (apple.itemExists("Iphone 91")) {
            System.out.println("itemExists method passed the test");
        } else {
            System.out.println("There's an error in the itemExists method");
        }

    }
```
   - Processor
      - Processor contains the main method for the project and is the class that should be run to use the program. The code implements a console-based messaging system with distinct user roles, allowing customers and sellers to log in, send and receive messages, and perform various account actions. Sellers can create stores and add products, while customers can purchase products. The system tracks message history, displays unread messages, and provides statistical insights. Users can export conversation history to a CSV file. Data persistence is achieved through file storage, with user information, store details, and messages saved to respective files. The code follows object-oriented principles, organizing functionality into classes like Users, Seller, Customer, Store, Message, and Processor. Overall, the program provides a functional interface for users to interact with a comprehensive messaging and e-commerce system.
        
Instance Variables:

private static ArrayList<Store> allStores: Stores information about all stores in the system.
private static ArrayList<Message> allMessages: Stores information about all messages in the system.
private static ArrayList<Seller> allSellers: Stores information about all sellers in the system.
private static ArrayList<Customer> allCustomers: Stores information about all customers in the system.
private static ArrayList<Users> allUsers: Stores information about all users in the system.

Main Method:

The main method is the entry point of the program.
It initializes variables and checks for the existence of files (user_info.txt, store_info.txt, and message_info.txt) to load data.
It acts as the backend processor for handling all readng/writing of data.

Methods:

login(): Takes user input for email and password, checks for validity, and returns the logged-in user.
sendMessage(Users user): Allows a user to send a message to another user or store.
editAccount(Users user): Allows a user to edit their account information.
getStatistics(Users user): Provides statistics, such as the number of messages sent, for sellers or stores.
editMessage(Users user): Allows a user to edit a previously sent message.
deleteMessage(Users user): Allows a user to delete a previously sent message.
buyProducts(Customer user): Allows a customer to buy products from a store.
makeStore(Seller user): Allows a seller to create a store.

Utility Methods:

printMsgs(Users user): Prints messages for a user.
showNewMessages(Users user): Shows new, unread messages for a user.
printUsers(Users user): Prints a list of users or stores for messaging.

File Handling:

loadFiles(File f1, File f2, File f3): Loads data from files.
saveAll(): Saves data to files.

Client.java: 
- Import Statements
   Imports necessary Java libraries for GUI components (javax.swing.*), network communication (java.io.*, java.net.Socket), and other utilities (java.awt.*, java.security.spec.RSAOtherPrimeInfo, java.time.LocalTime,    java.util.*).
   Class Variables
   public static boolean loggedIn: Indicates if a user is currently logged in.
   public static boolean exit: Flag to control the main loop, indicating if the application should exit.
- Main Method
   public static void main(String[] args): The entry point of the application.
   Initializes a socket connection to the server.
   Configures the UI elements (icons, fonts, colors).
   Implements a main menu loop with options to log in, create an account, or exit.
   Handles user inputs through GUI dialogs and communicates with the server via the socket.
   Displays messages and responses received from the server.
   Implements various functionalities based on user's role (customer/seller) such as sending messages, editing account details, viewing messages, deleting accounts, hiding/blocking users, obtaining statistics,    logging out, editing/deleting messages, exporting conversations to CSV, creating stores (for sellers), buying products (for customers), and censoring texts.
- Methods and Functionalities
   Socket Setup: Configures a socket for network communication and initializes BufferedReader and PrintWriter for reading from and writing to the socket.
   GUI Configuration: Sets custom icons, fonts, colors, and styles for the UI components.
   Main Menu: Presents a main menu with options to log in, create an account, or exit. Handles user interaction through GUI input dialogs.
   Login Functionality: Handles user login, displaying messages for login success or failure.
   Account Creation: Allows new users to create either a customer or seller account.
- User Interactions:
   Viewing, sending, editing, and deleting messages.
   Account management (editing and deleting accounts).
   Hiding and blocking other users.
   Viewing statistics (for sellers).
   Exporting conversation history to a CSV file.
   Additional functionalities specific to customer and seller roles:
   Customers can buy products.
   Sellers can create stores and manage products.
   Message Censoring: Provides an option to censor specific texts in messages.
   Loop Control: The main loop continues until the user chooses to exit, controlled by the exit variable.
- Exception Handling
   Handles IOException for network communication errors.
The Client class itself is overall responsible for all user input and returning an output through a sleek GUI.

Other Features:

The class handles the creation and management of different types of accounts (Customer, Seller).
It allows users to perform various actions like sending messages, editing accounts, getting statistics, and more.

The Processor and Client classes were tested manually so that we could test as many options and scenarios as possible, as well as provide as many different correct/incorrect inputs to ensure the program did not crash and functioned properly.

