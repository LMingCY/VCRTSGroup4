package backend.vehicle;

import backend.job.Job;

import java.time.*;
/*
    Heads-Up:
            - WTF is vehicleStatus?
 */
public class Vehicle {
    private int vehicleId, ownerId; //backend.vehicle identifiers
    private String make;
    private String model;
    private int vehicleStatus;
    private String currentJob; //realtime information
    private Duration residencyTime;

    //backend.vehicle info
    public Vehicle(int vehicleId, String make, String model,int ownerId, int vehicleStatus, Duration residencyTime,String currentJob) {
        this.vehicleId = vehicleId;
        this.make = make;
        this.model = model;
        this.ownerId = ownerId;
        this.vehicleStatus = vehicleStatus;
        this.currentJob = currentJob;
        this.residencyTime = residencyTime;
    }

    //getters and setters
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(char vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public Duration getResidencyTime() {
        return residencyTime;
    }

    public void setResidencyTime(Duration residencyTime) {
        this.residencyTime = residencyTime;
    }

    public boolean checkAvailability() {
        if (this.currentJob == null && residencyTime.toMinutes() >= 15) {
            return true;
        }
        else {
            return false;
        }
    }
    public Job completeJob(Job job) {
        return job;
    }

    @Override
    public String toString() {
        return String.valueOf(vehicleId) + "," + make + "," + model + "," + String.valueOf(ownerId) + "," + vehicleStatus + "," + residencyTime + "," + currentJob + ",";
    }
}
