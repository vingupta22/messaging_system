package messaging_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class guiTemplate extends JComponent implements Runnable {

    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JPanel createPanel() {
        return new JPanel();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new guiTemplate());
    }

    public void run() {
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
        JButton exit = new JButton("Exit");

        homeScreen.add(login);
        homeScreen.add(createAccount);
        homeScreen.add(exit);

        login.addActionListener(e -> cardLayout.show(mainPanel, "Login")); //switches to login panel
        createAccount.addActionListener(e -> cardLayout.show(mainPanel, "Create Account")); //switches to create  panel
        exit.addActionListener(e -> frame.dispose()); //closes program if exit button clicked


        //makes each separate panel
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

        // Add mainPanel and buttonPanel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void makeLoginScreen(JPanel panel) {
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


            }
        });
    }

    public void makeCreateScreen(JPanel panel) {
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

        JButton create = new JButton("Create Account");
        bottom.add(create);
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //Check if username and password is valid, if not show error, feel free to switch order
                if (true) {
                    cardLayout.show(mainPanel, "Home");
                } else if (password.getText().isEmpty() || username.getText().isEmpty()) { //if either box is null
                    JOptionPane.showMessageDialog(null, "Username/Password cannot be blank!",
                            "Messaging System", JOptionPane.ERROR_MESSAGE);
                } else { //if username is taken
                    JOptionPane.showMessageDialog(null, "Username is taken!",
                            "Messaging System", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }
    public void makeSelectionScreen(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JLabel username = new JLabel("User: " /* + Add username here */ );

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
        seeMsg.addActionListener(e -> cardLayout.show(mainPanel, "See Messages"));
        JButton sendMsg = new JButton("Send Messages");
        buttons.add(sendMsg);
        sendMsg.addActionListener(e -> cardLayout.show(mainPanel, "Send Messages"));
        JButton editAccnt = new JButton("Edit Account");
        buttons.add(editAccnt);
        editAccnt.addActionListener(e -> cardLayout.show(mainPanel, "Edit Account"));
        JButton deleteAccnt = new JButton("Delete Account");
        buttons.add(deleteAccnt);
        deleteAccnt.addActionListener(e -> System.out.println()); //remove the print just call the delete account method
        JButton hide = new JButton("Hide User");
        buttons.add(hide);
        hide.addActionListener(e -> cardLayout.show(mainPanel, "Hide Account"));
        JButton block = new JButton("Block User");
        buttons.add(block);
        block.addActionListener(e -> cardLayout.show(mainPanel, "Block Account"));
        JButton statistics = new JButton("Get Statistics");
        buttons.add(statistics);
        statistics.addActionListener(e -> cardLayout.show(mainPanel, "Statistics"));
        JButton logout = new JButton("Logout");
        buttons.add(logout);
        logout.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        JButton editMsg = new JButton("Edit Messages");
        buttons.add(editMsg);
        editMsg.addActionListener(e -> cardLayout.show(mainPanel, "Edit Message"));
        JButton deleteMsg = new JButton("Delete Messages");
        buttons.add(deleteMsg);
        deleteMsg.addActionListener(e -> cardLayout.show(mainPanel, "Delete Message"));
        JButton censor = new JButton("Censor Messages");
        buttons.add(censor);
        censor.addActionListener(e -> cardLayout.show(mainPanel, "Censor Message"));
        JButton export = new JButton("Export CSV");
        buttons.add(export);
        censor.addActionListener(e -> System.out.println()); //remove the print just call the export csv method

        //Next two are dependent on buyer/seller
        if (true) { //for buyer
            JButton buy = new JButton("Buy Products");
            buttons.add(buy);
            buy.addActionListener(e -> cardLayout.show(mainPanel, "Buy Product"));
        } else { //for seller
            JButton store = new JButton("Create Store");
            buttons.add(store);
            store.addActionListener(e -> cardLayout.show(mainPanel, "Create Store"));
        }
        panel.add(buttons);


    }

    public void makeSeeMsgScreen(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel messages = new JLabel("Messages:\n");
        //I would include all messages in this label, just iterate through the list

        JButton back = new JButton("Go Back");
        back.addActionListener(e -> cardLayout.show(mainPanel, "Selection Screen"));
        panel.add(messages);
        panel.add(back);
    }

    public void makeSendMsgScreen(JPanel panel) {
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

        JTextField message = new JTextField();
        JTextField fileName = new JTextField();

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(2, 2));
        center.add(msgLabel);
        center.add(message);
        center.add(impLabel);
        center.add(fileName);
        panel.add(center);


        JButton send = new JButton("Send Message");
        send.addActionListener(e -> cardLayout.show(mainPanel, "Selection Screen"));

        panel.add(send);
    }

    public void makeEditAccntScreen(JPanel panel) {
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

                //Check if username and password is valid, if not show error, feel free to switch order
                if (true) {
                    cardLayout.show(mainPanel, "Selection Screen");
                } else if (password.getText().isEmpty() || username.getText().isEmpty()) { //if either box is null
                    JOptionPane.showMessageDialog(null, "Username/Password cannot be blank!",
                            "Messaging System", JOptionPane.ERROR_MESSAGE);
                } else { //if username is taken
                    JOptionPane.showMessageDialog(null, "Username is taken",
                            "Messaging System", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }

    public void makeHideScreen(JPanel panel) {
        panel.setLayout(new GridLayout(2, 0));
        JLabel recLabel = new JLabel("Hide from Which User:");
        JComboBox<String> users = new JComboBox();

        JPanel top = new JPanel();
        top.setLayout(new GridLayout(0, 2));
        panel.add(top);

        //need a for loop adding all the users here
        users.addItem("Person 1");
        users.addItem("Person 2");

        top.add(recLabel);
        top.add(users);

        panel.add(top);

        JButton hide = new JButton("Hide");
        hide.addActionListener(e -> cardLayout.show(mainPanel, "Selection Screen"));

        panel.add(hide);
    }
    //hide and block very similar
    public void makeBlockScreen(JPanel panel) {
        panel.setLayout(new GridLayout(2, 0));
        JLabel recLabel = new JLabel("Block Which User:");
        JComboBox<String> users = new JComboBox();

        JPanel top = new JPanel();
        top.setLayout(new GridLayout(0, 2));
        panel.add(top);

        //need a for loop adding all the users here
        users.addItem("Person 1");
        users.addItem("Person 2");

        top.add(recLabel);
        top.add(users);

        panel.add(top);

        JButton block = new JButton("Block");
        block.addActionListener(e -> cardLayout.show(mainPanel, "Selection Screen"));

        panel.add(block);
    }

    public void makeStatisticsScreen(JPanel panel) {
        panel.setLayout(new GridLayout(4, 0));
        JToggleButton sort = new JToggleButton("Sort");
        //sort the data based on the value of this toggle
        panel.add(sort);

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

    public void makeDeleteMsgScreen(JPanel panel) {
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

    public void makeEditMsgScreen(JPanel panel) {
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

    public void makeCensorScreen(JPanel panel) {
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

    public void makeBuyScreen(JPanel panel) {
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

    public void makeStoreScreen(JPanel panel) {
        panel.setLayout(new GridLayout(0, 1));

        JPanel top = new JPanel();
        top.setLayout(new GridLayout(0, 2));
        JLabel nameLabel = new JLabel("Store Name:");
        JTextField name = new JTextField();
        top.add(nameLabel);
        top.add(name);

        JLabel productLabel = new JLabel("Product Name:");
        JTextField product = new JTextField();
        top.add(productLabel);
        top.add(product);

        panel.add(top);

        JButton addProduct = new JButton("Add Product to Store");
        panel.add(addProduct);
        addProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
