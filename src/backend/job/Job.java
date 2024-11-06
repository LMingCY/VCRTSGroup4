package backend.job;

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
    public int completionTime;
    public Job(int jobId, int clientId, String jobName, char status, String result, Date deadline, Duration duration) {
        this.jobName=jobName;
        this.deadline = deadline;
        this.clientId=clientId;
        this.jobId=jobId;
        this.completionTime=0;
        this.duration=duration;
        this.status=status;
        this.result=result;

    }
    public Job(int jobId2, int clientId2, int jobName2, int status2, int result2, int deadline2, int duration2) {
		// TODO Auto-generated constructor stub
	}
	public Job(int jobId2, int duration2) {
		// TODO Auto-generated constructor stub
	}
	public int getJobId() {
        return jobId;
    }
    public Duration getDuration() {
    	return duration;
    }

    public char getStatus() {
        return status;
    }
    @Override
    public String toString() {
        return String.valueOf(jobId) + "|" + jobName + "|" + status + "|" + String.valueOf(clientId) + "|" + duration + "|" + deadline + "|" + result + "|";
    }
}
