package backend.dashboard;

import backend.job.Job;
import backend.vehicle.Vehicle;
import java.util.HashMap;

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

    public void checkAvailability() {
        System.out.println("Checking Vehicle availability:");
        for (Vehicle vehicle : vehicles.values()) {
            System.out.println("Vehicle ID: " + vehicle.getVehicleId() + ", Available: " + vehicle.checkAvailability());
        }
    }
    public void checkJobStatus() {
        System.out.println("Checking Job status:");
        for (Job job : jobs.values()) {
            System.out.println("Job ID: " + job.getJobId() + ", Available: " + job.getStatus());
        }
    }
    public void manageVehicles() {

    }

}
