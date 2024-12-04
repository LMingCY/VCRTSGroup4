package backend.dashboard;
/*
    TODO:
    >Submit backend.job
    >Inspect backend.job
    >See available vehicles
    >Cancel backend.job
 */

import backend.job.Job;
import backend.login.User;
import backend.master.Idgenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;

public class ClientDashboard {
    private User user;
    private int clientId;

    public Job addJob(String jobName, int clientId, Duration duration, LocalDate deadline) {
        int jobId = Idgenerator.generateJobId();
        char status = 1;
        String result = "N/A";
        Job job = new Job(jobId, clientId, jobName, status, result, deadline, duration);
        return job;
    }

    public int getClientId() {
        clientId = user.getUserId();
        return clientId;
    }
}
