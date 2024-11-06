package backend.dashboard;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
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
    private HashMap<String, Job> jobs = new HashMap();
    
    public static void main(String[] args) {
        String filePath = "user_transaction.txt";

        List<Job> jobs = readJobsFromFile(filePath);
        calculateCompletionTimes(jobs);
        for (Job job : jobs) {
            System.out.println("Job ID: " + job.getJobId() + ", Completion Time: " + job.completionTime + " minutes");
        }
    }
    public static List<Job> readJobsFromFile(String filePath) {
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

    public static void calculateCompletionTimes(List<Job> jobs) {
        int cumulativeTime = 0;

        for (Job job : jobs) {
            cumulativeTime += job.getDuration().toMinutes();
            job.completionTime = cumulativeTime;
        }
    }


    public void checkAvailability() {
        System.out.println("Checking backend.vehicle availability:");
        for (Vehicle vehicle : vehicles.values()) {
            System.out.println("Vehicle ID: " + vehicle.getVehicleId() + ", Available: " + vehicle.checkAvailability());
        }
    }
    public void checkJobStatus() {
        System.out.println("Checking backend.job status:");
        for (Job job : jobs.values()) {
            System.out.println("Job ID: " + job.getJobId() + ", Available: " + job.getStatus());
        }
    }
    public void manageVehicles() {
    }
}
