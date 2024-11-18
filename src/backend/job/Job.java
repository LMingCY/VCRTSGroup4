package backend.job;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
/*
    TODO:
    >Maybe good to change date format to something else;
 */
public class Job {
    private String jobName;
    private int jobId, clientId;
    private int status; //representation of status i.e. 1-unstarted 2-inprocess 3-suspended 9-finished 0-deleted.
    private String result; //using result as placeholder, don't know what the result is gonna be
    private LocalDate deadline;
    private Duration duration;
    public Job(int jobId, int clientId, String jobName, int status, String result, LocalDate deadline, Duration duration) {
        this.jobName=jobName;
        this.deadline = deadline;
        this.clientId=clientId;
        this.jobId=jobId;
        this.duration=duration;
        this.status=status;
        this.result=result;
    }
    public int getJobId() {
        return jobId;
    }
    public Duration getDuration() {
        return duration;
    }
    public int getStatus() {
        return status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
    public int getClientId() {
        return clientId;
    }

    @Override
    public String toString() {
        String timestamp = LocalDateTime.now().toString();
        return String.valueOf(jobId) + "," + jobName + "," + status + "," + String.valueOf(clientId) + "," + duration + "," + deadline + "," + result + "," + timestamp;
    }
}
