import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// The class provides methods to load data from files and create objects
public class readShipData {
    // Method to load ships from a text file
    public static ArrayList<Ship> loadShips(String filename, Port defaultPort) {
        ArrayList<Ship> ships = new ArrayList<>(); // Initialize the list of ships


        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            // Read each line from the file
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                double fuelConsumptionPerKm = Double.parseDouble(parts[2].trim()); //  the fuel consumption per km
                int maxWeight = Integer.parseInt(parts[3].trim()); // prse maximum weight
                int maxContainerCount = Integer.parseInt(parts[4].trim()); //parse maximum container count
                int maxHeavyContainers = Integer.parseInt(parts[5].trim()); // parse maximum heavy containers

                ships.add(new Ship(id, name, fuelConsumptionPerKm, maxWeight, maxContainerCount, maxHeavyContainers, defaultPort));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ships;
    }
}
