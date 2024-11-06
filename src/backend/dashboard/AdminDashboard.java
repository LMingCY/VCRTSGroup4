package backend.dashboard;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import backend.job.Job;
import backend.vehicle.Vehicle;
import java.util.HashMap;
import java.text.ParseException;

public class AdminDashboard {
	
    /*
    TODO:
    -$ backend.vehicle.Vehicle methods.Availability
    - Read vehicles.txt to get all available vehicles.
    - Read jobs.txt to get all available jobs.
    - Job Status and Management
    - Manage Cars (Remove, Add)  -> also part of the read/write stuff.
     */
    private HashMap<String,Vehicle> vehicles = new HashMap();
    //private HashMap<String, Job> jobs = new HashMap();
    public List<Job> jobs = new ArrayList<>();

    /* public static List<Job> readJobsFromFile(String filePath) {
        List<Job> jobs = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] jobData = line.split(",");
                
                int jobId = Integer.parseInt(jobData[0].trim());
                int duration = Integer.parseInt(jobData[1].trim());
                
                jobs.add(new Job(jobId, duration));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return jobs;
    }
     */
    public static Job parse(String line) {
        String[] parts = line.split(",");
        if (parts.length < 8) {  // Ensuring there are enough parts, including the timestamp
            throw new IllegalArgumentException("Malformed job data");
        }

        try {
            int jobId = Integer.parseInt(parts[0].replaceAll("^[^\\d]+", ""));
            String jobName = parts[1];
            int status = Integer.parseInt(parts[2]);
            int clientId = Integer.parseInt(parts[3]);
            Duration duration = Duration.parse(parts[4]);
            LocalDate deadline = LocalDate.parse(parts[5], DateTimeFormatter.ISO_LOCAL_DATE);
            String result = parts[6];

            return new Job(jobId, clientId, jobName, status, result, deadline, duration);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error parsing numeric fields", e);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error parsing date/time fields", e);
        }
    }
    public void readJobsFromFile(String fileName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                try {
                    Job job = parse(line);
                    jobs.add(job);
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping invalid job record: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }


    public long calculateTotalCompletionTime() {
        long totalMinutes = 0;
        for (Job job : jobs) {
            Duration duration = job.getDuration();
            totalMinutes += duration.toMinutes(); // Assuming Duration is used in Job and you want total in minutes
        }
        return totalMinutes;
    }


    public void checkAvailability() {
        System.out.println("Checking backend.vehicle availability:");
        for (Vehicle vehicle : vehicles.values()) {
            System.out.println("Vehicle ID: " + vehicle.getVehicleId() + ", Available: " + vehicle.checkAvailability());
        }
    }
    /* public void checkJobStatus() {
        System.out.println("Checking backend.job status:");
        for (Job job : jobs.values()) {
            System.out.println("Job ID: " + job.getJobId() + ", Available: " + job.getStatus());
        }
    } */
    public void manageVehicles() {
    }
}
