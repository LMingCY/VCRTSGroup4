package dashboard;

import master.Idgenerator;
import vehicle.Vehicle;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

/*
    TODO:
    > Update vehicle status
 */

public class OwnerDashboard {
    private int OwnerId;
    public Vehicle addVehicle(String make, String model, int ownerId, Duration residencyTime) {
        int vehicleId = Idgenerator.generateVehicleId();
        char vehicleStatus = 0; //0 will indicate free;
        String currentJob = "";
        return new Vehicle(vehicleId, make, model, ownerId, vehicleStatus, currentJob, residencyTime);
    }
    public void writeVehicleToFile(Vehicle vehicle, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(vehicle.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error writing vehicle information to file: " + e.getMessage());
        }
    }
}
