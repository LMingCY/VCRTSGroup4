package backend.master;

public class Idgenerator {
    private static int userCounter = 100_000_000;
    private static int jobCounter = 200_000_000;
    private static int vehicleCounter = 300_000_000;
    private static int adminCounter = 900_000_000;

    public static synchronized int generateUserId() {
        return userCounter++;
    }

    public static synchronized int generateJobId() {
        return jobCounter++;
    }

    public static synchronized int generateVehicleId() {
        return vehicleCounter++;
    }

    public static synchronized int generateAdminId() {
        return adminCounter++;
    }
}
