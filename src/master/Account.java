package master;

public class Account {
    private String username, password; //these are for login
    private String name, email; //these are credentials
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public void setUsername(String username) {
        username = this.username;
    }
    public String getUsername() {
        return this.username;
    }
    public void setPassword(String password) {
        password = this.password;
    }
}
