//import java.io.*; not needed
import java.util.ArrayList;

public class Ship {
    private int ID; // Ship ID
    private String name; // Ship name
    private double fuelConsumptionPerKm; // Fuel consumption per kilometer
    private int maxWeight; // Maximum weight the ship can carry
    private int maxContCount; // Maximum number of containers the ship can carry
    private int maxHeavyContainers; // Maximum number of heavy containers the ship can carry
    private Port currentPort; // Current port where the ship is located
    private double fuelInLt; // Liters of fuel currently in the ship
    private ArrayList<Container> loadedContainers; // List of containers loaded on the ship

    // Constructor for a Ship object with specified attributes
    public Ship(int ID, String name, double fuelConsumptionPerKm, int maxWeight,
                int maxContainerCount, int maxHeavyContainers, Port currentPort) {
        this.ID = ID;
        this.name = name;
        this.fuelConsumptionPerKm = fuelConsumptionPerKm;
        this.maxWeight = maxWeight;
        this.maxContCount = maxContainerCount;
        this.maxHeavyContainers = maxHeavyContainers;
        this.currentPort = currentPort;
        this.fuelInLt = 0; // Initially 0
        this.loadedContainers = new ArrayList<>(); // Initialize the list of loaded containers
    }


    public boolean loadContainer(Container container) {
        // Calculate current total weight
        double currentTotalWeight = getTotalWeight();

        // Check if adding this container exceeds the total weight limit
        if (loadedContainers.size() < maxContCount && currentTotalWeight + container.getWeight() <= maxWeight ) {
            if (container instanceof RefrigeratedContainer || container instanceof LiquidContainer) {
                long heavyContainersCount = loadedContainers.stream()
                        .filter(c -> c instanceof HeavyContainer).count();
                if (heavyContainersCount < maxHeavyContainers) {
                    loadedContainers.add(container);
                    return true;
                }
            } else if (container instanceof SimpleContainer) {  // Ensure only simple containers are loaded here
                loadedContainers.add(container);
                return true;
            }
        }
        return false;
    }

    private double getTotalWeight() {
        return loadedContainers.stream().mapToDouble(Container::getWeight).sum();
    }


    // Method to calculate the fuel required to travel to a destination port
    public double calcRequiredFuel(Port destination) {
        double distance = currentPort.calculateDistance(destination); // Assume method exists to calculate distance
        double totalContainerFuelConsumption = loadedContainers.stream()
                .mapToDouble(Container::calculateFuelConsumption).sum();
        return distance * totalContainerFuelConsumption;
    }

    // Add fuel method
    public void addFuel(Port destination) {
        // Calculate the required fuel based on the destination port.
        double requiredFuel = calcRequiredFuel(destination);
        // Add the calculated fuel to the ship's fuel reserves.
        fuelInLt += requiredFuel;
        System.out.println("Added " + requiredFuel + " liters of fuel for travel to " + destination.getName());
    }

    // Travel method
    public void travelToPort(Port destination) {
        double requiredFuel = calcRequiredFuel(destination);
        if (fuelInLt >= requiredFuel) {
            currentPort.removeShip(this); // remove ship from current port
            currentPort = destination; // set new current port
            destination.addShip(this); // add ship to destination port
        } else {
            System.out.println("Not enough fuel to travel to the destination port.");
        }
    }

    public void unloadContainers() {
        if (currentPort != null) {
            for (Container container : loadedContainers) {
                currentPort.addContainer(container);  // Use addContainer to append to port's list
            }
            loadedContainers.clear();  // Clear the ship's container list after unloading
        }
    }


    // Method to return a string representation of the ship
    @Override
    public String toString() {
        return "Ship{id=" + ID + ", name='" + name + "', fuelConsumptionPerKm=" + fuelConsumptionPerKm +
                ", maxWeight=" + maxWeight + ", maxContainerCount=" + maxContCount +
                ", maxHeavyContainers=" + maxHeavyContainers + ", currentPort=" + currentPort.getName() +
                ", fuelInLiters=" + fuelInLt + ", loadedContainers=" + loadedContainers.size() + "}";
    }

    // Getters and setters

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFuelConsumptionPerKm() {
        return fuelConsumptionPerKm;
    }

    public void setFuelConsumptionPerKm(double fuelConsumptionPerKm) {
        this.fuelConsumptionPerKm = fuelConsumptionPerKm;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getMaxContCount() {
        return maxContCount;
    }

    public void setMaxContCount(int maxContainerCount) {
        this.maxContCount = maxContainerCount;
    }

    public int getMaxHeavyContainers() {
        return maxHeavyContainers;
    }

    public void setMaxHeavyContainers(int maxHeavyContainers) {
        this.maxHeavyContainers = maxHeavyContainers;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public double getFuelInLiters() {
        return fuelInLt;
    }

    public void setFuelInLiters(double fuelInLiters) {
        this.fuelInLt = fuelInLiters;
    }

    public ArrayList<Container> getLoadedContainers() {
        return loadedContainers;
    }

    public void setLoadedContainers(ArrayList<Container> loadedContainers) {
        this.loadedContainers = loadedContainers;
    }
}
