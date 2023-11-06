import java.util.ArrayList;

public class Users {
    String email;
    String password;
    ArrayList<Users> blockedUsers;
    ArrayList<Users> invisibleUsers;

    public Users(String email, String password) { //Constructor
        this.email = email;
        this.password = password;
        this.blockedUsers = new ArrayList<>();
        this.invisibleUsers = new ArrayList<>();
    }

    public void hide(Users hiddenUser) {
        this.invisibleUsers.add(hiddenUser);
    } //hides the user by adding it to the invisible list

    public void block(Users blockUser) { //block user my adding it to the user's blocklist
        this.blockedUsers.add(blockUser);
    }

    public void deleteAccnt() { //deleting account by setting each field to null
        this.email = null;
        this.password = null;
        this.blockedUsers = null;
        this.invisibleUsers = null;
    }

    public void editAccnt(String email, String password, ArrayList<Users> blockedUsers, ArrayList<Users> invisibleUsers) {
        this.setEmail(email);
        this.setPassword(password);
        this.setInvisibleUsers(invisibleUsers);
        this.setBlockedUsers(blockedUsers);
    } // editing account by giving parameters, which is different from the method we did in the beggining, but I'm not sure how to do it 
    //without parameters 


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Users> getBlockedUsers() {
        return blockedUsers;
    }

    public ArrayList<Users> getInvisibleUsers() {
        return invisibleUsers;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBlockedUsers(ArrayList<Users> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public void setInvisibleUsers(ArrayList<Users> invisibleUsers) {
        this.invisibleUsers = invisibleUsers;
    }

}
