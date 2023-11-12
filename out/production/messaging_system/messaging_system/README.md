# messaging_system

1. n/a
2. Nandini Pande submitted the report on brightspace and Nick Patel submitted the Vocareum Workspace.
3. Classes:
   - Message
       - The Message class contains the outline for a Message object. It represents a message that can be sent or received by users of the system. It has four private fields: content, senderID, recipientID, and timeStamp. These fields store the text of the message, the identifier of the sender, the identifier of the recipient, and the time when the message was created, respectively. The class has a constructor that takes four parameters and assigns them to the corresponding fields. It also has four getter methods that return the values of the fields. Additionally, it has a method editMessage that allows the sender to modify the content of the message after it has been created. This method takes a new message as a parameter and updates the content field accordingly. Message objects are created and stored in arrays within the Processor class. 
   - Users
       - The Users class contains the outline for a User object. It is extended by Customer and Seller. The class Users is part of the messaging_system package. It represents a user of the system who can send and receive messages, as well as block or hide other users. It has six private fields: email, password, blockedUsers, invisibleUsers, messagesSent, and messagesReceived. These fields store the user’s email address, password, a list of blocked users, a list of invisible users, a list of messages sent by the user, and a list of messages received by the user, respectively.

The class has a constructor that takes two parameters (email and password) and assigns them to the corresponding fields. It also initializes the other four fields as empty lists. It also has getter and setter methods for each field, allowing the user to access or modify their information.

The class has several methods that define the user’s actions and behaviors. These are:

hide: This method takes another user as a parameter and adds them to the user’s invisible list. This means that the user will not see any messages from the hidden user, and the hidden user will not see the user’s online status.
block: This method takes another user as a parameter and adds them to the user’s blocked list. This means that the user will not receive any messages from the blocked user, and the blocked user will not be able to send any messages to the user.
deleteAccnt: This method deletes the user’s account by setting all the fields to null. This means that the user will no longer be able to log in or use the system.
editAccnt: This method allows the user to change their email and password by taking two parameters (email and password) and updating the corresponding fields.
sendMessage: This method allows the user to send a message to another user by taking two parameters (msg and user). The message is an object of the Message class, and the user is an object of the Users class. The method checks if the user is allowed to send the message based on their type (Seller or Customer) and the recipient’s type. If the message is allowed, it adds the message to the user’s messagesSent list and the recipient’s messagesReceived list, and prints a confirmation message. If the message is not allowed, it prints an error message.
msgHist: This method writes the user’s message history to a file called “messageHistory.csv”. The method uses a BufferedWriter to write each message in a single line, with the format: content, senderID, recipientID, timestamp. The method throws an IOException if there is a problem with writing to the file.
getNumMessages: This method returns the number of messages sent by the user as an int.
deleteMessage: This method allows the user to delete a message from their messagesSent list by taking a parameter (msg) of the Message class. The method loops through the messagesSent list and removes the message if it matches the parameter.

   - Customer
   - Seller
   - Store
   - Processor

1. n/a
2. Nandini Pande submitted the report on brightspace and Nick Patel submitted the Vocareum Workspace.
3. Classes:
   - Message
       - The Message class contains the outline for a Message object. It represents a message that can be sent or received by users of the system. It has four private fields: content, senderID, recipientID, and timeStamp. These fields store the text of the message, the identifier of the sender, the identifier of the recipient, and the time when the message was created, respectively. The class has a constructor that takes four parameters and assigns them to the corresponding fields. It also has four getter methods that return the values of the fields. Additionally, it has a method editMessage that allows the sender to modify the content of the message after it has been created. This method takes a new message as a parameter and updates the content field accordingly. Message objects are created and stored in arrays within the Processor class. 
   - Users
       - The Users class contains the outline for a User object. It is extended by Customer and Seller. The class Users is part of the messaging_system package. It represents a user of the system who can send and receive messages, as well as block or hide other users. It has six private fields: email, password, blockedUsers, invisibleUsers, messagesSent, and messagesReceived. These fields store the user’s email address, password, a list of blocked users, a list of invisible users, a list of messages sent by the user, and a list of messages received by the user, respectively.

The class has a constructor that takes two parameters (email and password) and assigns them to the corresponding fields. It also initializes the other four fields as empty lists. It also has getter and setter methods for each field, allowing the user to access or modify their information.

The class has several methods that define the user’s actions and behaviors. These are:

hide: This method takes another user as a parameter and adds them to the user’s invisible list. This means that the user will not see any messages from the hidden user, and the hidden user will not see the user’s online status.
block: This method takes another user as a parameter and adds them to the user’s blocked list. This means that the user will not receive any messages from the blocked user, and the blocked user will not be able to send any messages to the user.
deleteAccnt: This method deletes the user’s account by setting all the fields to null. This means that the user will no longer be able to log in or use the system.
editAccnt: This method allows the user to change their email and password by taking two parameters (email and password) and updating the corresponding fields.
sendMessage: This method allows the user to send a message to another user by taking two parameters (msg and user). The message is an object of the Message class, and the user is an object of the Users class. The method checks if the user is allowed to send the message based on their type (Seller or Customer) and the recipient’s type. If the message is allowed, it adds the message to the user’s messagesSent list and the recipient’s messagesReceived list, and prints a confirmation message. If the message is not allowed, it prints an error message.
msgHist: This method writes the user’s message history to a file called “messageHistory.csv”. The method uses a BufferedWriter to write each message in a single line, with the format: content, senderID, recipientID, timestamp. The method throws an IOException if there is a problem with writing to the file.
getNumMessages: This method returns the number of messages sent by the user as an int.
deleteMessage: This method allows the user to delete a message from their messagesSent list by taking a parameter (msg) of the Message class. The method loops through the messagesSent list and removes the message if it matches the parameter.

   - Customer
   - Seller
   - Store
   - Processor
