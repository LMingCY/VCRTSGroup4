package job;

import java.time.Duration;
import java.util.Date;
/*
    TODO:
    >Maybe good to change date format to something else;
 */
public class Job {
    private String jobName;
    private int jobId, clientId;
    private char status; //representation of status i.e. 1-unstarted 2-inprocess 3-suspended 9-finished 0-deleted.
    private String result; //using result as placeholder, don't know what the result is gonna be
    private Date deadline;
    private Duration duration;
    public Job(int jobId, int clientId, String jobName, char status, String result, Date deadline, Duration duration) {
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

    public char getStatus() {
        return status;
    }
    @Override
    public String toString() {
        return String.valueOf(jobId) + "|" + jobName + "|" + status + "|" + String.valueOf(clientId) + "|" + duration + "|" + deadline + "|" + result + "|";
    }
}
