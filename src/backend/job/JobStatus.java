package backend.job;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JobStatus implements Serializable {
    private static final String STATUS_FILE = "job_status.txt";
    private Map<String, String> jobStatuses;

    public JobStatus() {
        this.jobStatuses = new HashMap<>();
    }

    public void saveJobStatus(String jobName, String status, int clientId) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STATUS_FILE, true))) {
            writer.println(clientId + "," + jobName + "," + status);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getJobStatuses(int clientId) {
        Map<String, String> userJobStatuses = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(STATUS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && Integer.parseInt(parts[0]) == clientId) {
                    userJobStatuses.put(parts[1], parts[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userJobStatuses;
    }

    public void clearOldStatuses(int clientId) {
        File inputFile = new File(STATUS_FILE);
        File tempFile = new File("temp_status.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && Integer.parseInt(parts[0]) != clientId) {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputFile.exists()) {
            inputFile.delete();
        }
        tempFile.renameTo(inputFile);
    }
}
