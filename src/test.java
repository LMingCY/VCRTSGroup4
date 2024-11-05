import dashboard.ClientDashboard;
import dashboard.OwnerDashboard;
import job.Job;
import master.Idgenerator;
import vehicle.Vehicle;

import java.time.Duration;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        OwnerDashboard owner = new OwnerDashboard();
        int ownerId = Idgenerator.generateUserId();
        ClientDashboard client = new ClientDashboard();
        int clientId = Idgenerator.generateUserId();
        Date deadline = new Date(2024,11,30,13,00);
        for (int i = 0; i < 101; i++){
            Vehicle vehicle = owner.addVehicle("Toyota", "Corolla", ownerId, Duration.ofHours(5));

            // Output vehicle information to a file
            owner.writeVehicleToFile(vehicle, "vehicles.txt");

            System.out.println("Vehicle added and information written to file.");
        }
        for (int i = 0; i < 101; i++) {
            Job job = client.addJob("Calculation", clientId, Duration.ofHours(1),deadline);
            client.writeJobToFile(job, "jobs.txt");
            System.out.println("job added and information written to file.");

        }
    }
}
