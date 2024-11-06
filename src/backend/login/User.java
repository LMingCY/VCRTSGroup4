package backend.login;

import backend.master.Account;

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

}
