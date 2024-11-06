package backend.master;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Idgenerator {
    private static final String FILE_PATH = "id_tracker.txt";
    private static Map<String, Integer> idMap = new HashMap<>();

    static {
        initializeCounters();
    }

    private static void initializeCounters() {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            try {
                file.createNewFile();
                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    writer.println("User=100000001");
                    writer.println("Job=200000001");
                    writer.println("Vehicle=300000001");
                    writer.println("Admin=900000001");
                }
            } catch (IOException e) {
                System.out.println("Error creating ID tracker file: " + e.getMessage());
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String entityType = parts[0];
                    int lastId = Integer.parseInt(parts[1]);
                    idMap.put(entityType, lastId);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading ID tracker file: " + e.getMessage());
        }
    }

    private static void updateFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, Integer> entry : idMap.entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error updating ID tracker file: " + e.getMessage());
        }
    }

    public static synchronized int generateUserId() {
        int newId = idMap.get("User") + 1;
        idMap.put("User", newId);
        updateFile();
        return newId;
    }

    public static synchronized int generateJobId() {
        int newId = idMap.get("Job") + 1;
        idMap.put("Job", newId);
        updateFile();
        return newId;
    }

    public static synchronized int generateVehicleId() {
        int newId = idMap.get("Vehicle") + 1;
        idMap.put("Vehicle", newId);
        updateFile();
        return newId;
    }

    public static synchronized int generateAdminId() {
        int newId = idMap.get("Admin") + 1;
        idMap.put("Admin", newId);
        updateFile();
        return newId;
    }
}