package dashboard;
/*
    TODO:
    >Submit job
    >Inspect job
    >See available vehicles
    >Cancel job
 */

import job.Job;
import master.Idgenerator;
import vehicle.Vehicle;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class ClientDashboard {
    private int clientId;
    public Job addJob(String jobName, int clientId, Duration duration, Date deadline) {
        int jobId = Idgenerator.generateJobId();
        char status = 1;
        String result = "";
        Job job = new Job(jobId, clientId, jobName, status, result, deadline, duration);
        return job;
    }
    public void writeJobToFile(Job job, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(job.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing job information to file: " + e.getMessage());
        }
    }
}
