package backend.dashboard;

import backend.master.Idgenerator;
import backend.vehicle.Vehicle;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

/*
    TODO:
    > Update backend.vehicle status
 */

public class OwnerDashboard {
    private int OwnerId;
    public Vehicle addVehicle(String make, String model, int ownerId, Duration residencyTime) {
        int vehicleId = Idgenerator.generateVehicleId();
        int vehicleStatus = 0; //0 will indicate free;
        String currentJob = "N/A";
        Vehicle vehicle = new Vehicle(vehicleId, make, model, ownerId, vehicleStatus, residencyTime , currentJob);
        return vehicle;
    }
    public void writeVehicleToFile(Vehicle vehicle, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(vehicle.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing backend.vehicle information to file: " + e.getMessage());
        }
    }
}
