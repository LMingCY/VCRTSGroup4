package backend.MySQL;

import backend.job.Job;
import backend.login.User;
import backend.vehicle.Vehicle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class Driver {
    public Connection connection() throws SQLException, IOException{
        Path path = Paths.get("/Users/leonming/Documents/ipandports.txt");
        //Path path = Paths.get("E:\\ipandports.txt");
        List<String> lines= Files.readAllLines(path);
        String ip = lines.get(0);
        String mysqlPort = lines.get(1);
        String username = lines.get(3);
        String password = lines.get(4);
        return DriverManager.getConnection("jdbc:mysql://"+ip + mysqlPort+"/vcrts", username, password);

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

        } catch (SQLException | IOException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }

        return user;
    }
    public void deleteUser(int userId) {
        String query = "DELETE FROM userlogin WHERE userId = ?";

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, String.format("%09d", userId)); //convert int to 9-digit String

            pstmt.executeUpdate();
            System.out.println("User deleted successfully.");

        } catch (SQLException | IOException e) {
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

        } catch (SQLException | IOException e) {
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

        } catch (SQLException | IOException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }
    public void addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO vehicles (vehicleId, make, model, ownerId, vehicleStatus, residencyTime, currentJob, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String jobData = vehicle.toString();
        String[] jobParts = jobData.split(",");


        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, jobParts[0]);
            pstmt.setString(2, jobParts[1]);
            pstmt.setString(3, jobParts[2]);
            pstmt.setString(4, jobParts[3]);
            pstmt.setString(5, jobParts[4]);
            pstmt.setInt(6, Integer.valueOf(jobParts[5]));
            pstmt.setString(7, jobParts[6]);
            pstmt.setString(8, jobParts[7]);


            pstmt.executeUpdate();
            System.out.println("Vehicle added successfully.");

        } catch (SQLException | IOException e) {
            System.out.println("Error adding vehicle: " + e.getMessage());
        }


    }
    public void addJob(Job job) {
        String query = "INSERT INTO jobs (jobId, jobName, clientId, duration, deadline, status, result, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String jobData = job.toString();
        String[] jobParts = jobData.split(",");

        try (Connection conn = connection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, jobParts[0]);
            pstmt.setString(2, jobParts[1]);
            pstmt.setString(3, jobParts[2]);
            pstmt.setInt(4, Integer.parseInt(jobParts[3]));
            pstmt.setString(5, jobParts[4]);
            pstmt.setString(6, jobParts[5]);
            pstmt.setString(7, jobParts[6]);
            pstmt.setString(8, jobParts[7]);


            pstmt.executeUpdate();
            System.out.println("Job added successfully.");

        } catch (SQLException | IOException e) {
            System.out.println("Error adding job: " + e.getMessage());
        }
    }

}
