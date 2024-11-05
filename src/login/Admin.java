package login;

import master.Account;

public class Admin extends Account {
    private int adminId;
    public Admin(String username, String password, int adminId) {
        super(username, password);
        this.adminId=adminId;
    }

    public int getAdminId() {
        return adminId;
    }
}
