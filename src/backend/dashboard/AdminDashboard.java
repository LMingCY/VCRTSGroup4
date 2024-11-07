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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
<<<<<<< HEAD
import java.text.ParseException;
=======
import java.util.*;
>>>>>>> subbranch-Leon

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
<<<<<<< HEAD
    public List<Job> jobs = new ArrayList<>();
=======
    private List<Job> jobs = new ArrayList<>();
>>>>>>> subbranch-Leon

<<<<<<< HEAD
    public void checkAvailability() {
        System.out.println("Checking Vehicle availability:");
=======
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

    public String getJobSummary() {
        StringBuilder jobIds = new StringBuilder("Job ID(s): ");
        StringBuilder jobDurations = new StringBuilder("Job Duration(s): ");
        StringBuilder jobCompletionTimes = new StringBuilder("Completion Time: ");

        long cumulativeDurationMinutes = 0;

        for (Job job : jobs) {
            long durationMinutes = job.getDuration().toMinutes();

            jobIds.append(job.getJobId()).append(", ");
            jobDurations.append(durationMinutes).append(" min, ");

            cumulativeDurationMinutes += durationMinutes;
            jobCompletionTimes.append(cumulativeDurationMinutes).append(" min, ");
        }

        if (jobs.size() > 0) {
            jobIds.setLength(jobIds.length() - 2);
            jobDurations.setLength(jobDurations.length() - 2);
            jobCompletionTimes.setLength(jobCompletionTimes.length() - 2);
        }

        return jobIds.toString() + "\n" + jobDurations.toString() + "\n" + jobCompletionTimes.toString();
    }



        public void checkAvailability() {
        System.out.println("Checking backend.vehicle availability:");
>>>>>>> subbranch-Aldy
        for (Vehicle vehicle : vehicles.values()) {
            System.out.println("Vehicle ID: " + vehicle.getVehicleId() + ", Available: " + vehicle.checkAvailability());
        }
    }
<<<<<<< HEAD
<<<<<<< HEAD
    public void checkJobStatus() {
        System.out.println("Checking Job status:");
=======
    /* public void checkJobStatus() {
        System.out.println("Checking backend.job status:");
>>>>>>> subbranch-Aldy
        for (Job job : jobs.values()) {
            System.out.println("Job ID: " + job.getJobId() + ", Available: " + job.getStatus());
        }
    } */
=======
    public static Job parse(String line) {
        String[] parts = line.split(",");
        if (parts.length < 8) {
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
    public String getJobSummary() {
        StringBuilder jobIds = new StringBuilder("Job ID(s): ");
        StringBuilder jobDurations = new StringBuilder("Job Duration(s): ");
        StringBuilder jobCompletionTimes = new StringBuilder("Completion Time: ");

        long cumulativeDurationMinutes = 0;

        for (Job job : jobs) {
            long durationMinutes = job.getDuration().toMinutes();

            jobIds.append(job.getJobId()).append(", ");
            jobDurations.append(durationMinutes).append(" min, ");

            cumulativeDurationMinutes += durationMinutes;
            jobCompletionTimes.append(cumulativeDurationMinutes).append(" min, ");
        }

        if (jobs.size() > 0) {
            jobIds.setLength(jobIds.length() - 2);
            jobDurations.setLength(jobDurations.length() - 2);
            jobCompletionTimes.setLength(jobCompletionTimes.length() - 2);
        }

        return jobIds.toString() + "\n" + jobDurations.toString() + "\n" + jobCompletionTimes.toString();
    }
    public List<Job> getJobs() {
        return jobs;
    }


    public List<String> displayJobList() {
        List<String> jobDetails = new ArrayList<>();

        for (Job job : jobs) {
            StringBuilder jobInfo = new StringBuilder();
            jobInfo.append("Job ID: ").append(job.getJobId()).append("\n");
            jobInfo.append("Status: ").append(job.getStatus()).append("\n");
            jobInfo.append("Deadline: ").append(job.getDeadline()).append("\n");
            jobInfo.append("\n");
            jobDetails.add(jobInfo.toString());
        }

        return jobDetails;
    }




>>>>>>> subbranch-Leon
    public void manageVehicles() {
    }
}
