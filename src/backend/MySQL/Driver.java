package backend.MySQL;

import backend.login.User;

import java.sql.*;

public class Driver {
    public Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/vcrts", "leon", "11111111");
    }
    public void addUser(int userId, String username, String password, String name, String email) {
        String query = "INSERT INTO users (user_id, username, password, name, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, name);
            pstmt.setString(5, email);

            pstmt.executeUpdate();
            System.out.println("User added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }
    public User getUserByUserId(String userId) {
        String query = "SELECT * FROM userlogin WHERE userId = ?";
        User user = null;

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int retrievedUserId = rs.getInt("userId");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String email = rs.getString("email");

                user = new User(retrievedUserId, username, password, name, email);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }

        return user;
    }
    public void deleteUser(int userId) {
        String query = "DELETE FROM userlogin WHERE userId = ?";

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.format("%09d", userId));

            pstmt.executeUpdate();
            System.out.println("User deleted successfully.");

        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }
    public User validateLogin(String username, String password) {
        String query = "SELECT * FROM userlogin WHERE username = ? AND password = ?";
        User user = null;

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int userId = Integer.parseInt(rs.getString("userId"));
                String retrievedUsername = rs.getString("username");
                String retrievedPassword = rs.getString("password");
                String name = rs.getString("name");
                String email = rs.getString("email");

                user = new User(userId, retrievedUsername, retrievedPassword, name, email);
            }

        } catch (SQLException e) {
            System.out.println("Error validating login: " + e.getMessage());
        }

        return user;
    }
    public void addUser(User user) {
        String query = "INSERT INTO userlogin (userId, username, password, name, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.format("%09d", user.getUserId()));
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getName());
            pstmt.setString(5, user.getEmail());

            pstmt.executeUpdate();
            System.out.println("User added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

}
