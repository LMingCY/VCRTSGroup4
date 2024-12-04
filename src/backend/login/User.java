package backend.login;

import backend.MySQL.Driver;
import backend.master.Account;
import backend.master.Idgenerator;

/*
    Needs Debate - whether we should have both user id and username since both are unique
    +userid is generated, easier to track and organize
    +username can be changed by user(?)
    +username is hybrid and could be complicated ($Ven0mSp1tt3rxXx11$), userid is only int, easy to manipulate(?)
 */

public class User extends Account {
    private int userId;
    private String name;
    private String email;
    private Idgenerator idgenerator = new Idgenerator();
    private Driver db = new Driver();

    public User(int userId, String username, String password, String name, String email) {
        super(username, password);
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String contactInfo) {
        this.email = contactInfo;
    }


    public boolean registerUser(int userId, String username, String password, String name, String email) {

        User newUser = new User(userId, username, password, name, email);
        db.addUser(newUser);

        System.out.println("User registered with ID: " + String.format("%09d", userId));
        return true;
    }



}
