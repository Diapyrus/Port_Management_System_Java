import java.util.ArrayList; // manage all lists
import java.util.Iterator;  // used later for the containers
import java.util.Scanner;   // read user input

public class Main {
    public static void main(String[] args) {
        try {   // call method to load ports
            ArrayList<Port> ports = readPortData.loadPorts("ports.txt");
            if (ports.isEmpty()) {
                System.out.println("All ports unavailable.");  //only checks if empty
                return;
            }
            Port defaultPort = ports.get(0);  // the first port be the default

            ArrayList<Ship> ships = readShipData.loadShips("ships.txt", defaultPort);// laods and sets to default ports
            ArrayList<Container> containers = ContainerLoader.loadContainers("containers.txt");

            System.out.println("Loaded containers: "); // display
            for (Container container : containers) {
                System.out.println(container);
            }

            // Use try-with-resources to ensure the Scanner is closed
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Choose loading scenario (A/B): ");
                String choice = scanner.nextLine().trim().toUpperCase();//read and convert to uppercase

                if ("A".equals(choice)) {
                    handleScenarioA(ports, ships, containers);
                } else if ("B".equals(choice)) {
                    handleScenarioB(ports, ships, containers);
                } else {
                    System.out.println("Invalid choice. Exiting.");
                    return;// wrong choice? gets the f out
                }

                printStatus(ports, ships);
            } // Scanner closes here

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(); // for debugging
        }
    }

    // φόρτωμα βυτιων, μετακίνηση πλοίων στα λιμάνια τους
    static void handleScenarioA(ArrayList<Port> ports, ArrayList<Ship> ships, ArrayList<Container> containers) {
        for (Container container : containers) {
            boolean isLoaded = false;
            for (Ship ship : ships) {
                if (ship.loadContainer(container)) {
                    System.out.println("Ship " + ship.getName() + " loaded container " + container.getID());
                    isLoaded = true;
                    break;  // after container is loaded,we get out
                }
            }
            if (!isLoaded) {
                System.out.println("No ship can load container " + container.getID());
            }
        }
        for (Ship ship : ships) {
            Port destination = findDestinationPort(ports, ship.getID());    //get destination port
            ship.addFuel(destination);// καύσιμο ανάλογα με την απόσταση
            ship.travelToPort(destination);
            ship.unloadContainers();
        }

    }

    // Β φόρτωμα όσων πλοίων είναι δυνατό να φορτωθούν
    static void handleScenarioB(ArrayList<Port> ports, ArrayList<Ship> ships, ArrayList<Container> containers) {
        for (Container container : containers) {
            boolean isLoaded = false;
            for (Ship ship : ships) {
                if (ship.loadContainer(container)) {
                    System.out.println("Ship " + ship.getName() + " loaded container " + container.getID());
                    isLoaded = true;
                    break;
                }
            }
            if (!isLoaded) {
                System.out.println("No ship can load container " + container.getID());
            }
        }
        while (!containers.isEmpty()) {// συνέχεια μέχρι να μην μείνει διαθέσιμο βυτίο
            Iterator<Container> iterator = containers.iterator();// εδώ χρειάζεται η java.util.Iterator

            boolean anyLoaded = false;  // To track if any container is loaded in this cycle

            while (iterator.hasNext()) {
                Container container = iterator.next();
                boolean isLoaded = false;

                for (Ship ship : ships) {
                    if (ship.loadContainer(container)) {
                        System.out.println("Ship " + ship.getName() + " loaded container " + container.getID());
                        isLoaded = true;
                        anyLoaded = true;
                        iterator.remove();  // Remove the container from the list after it's loaded
                        break;
                    }
                }

                if (!isLoaded) {
                    System.out.println("No ship can load container " + container.getID());
                }
            }

            // If no containers were loaded in this entire pass, break to avoid infinite loop
            if (!anyLoaded) {
                break;
            }

        }
        // After trying all containers, perform port operations
        for (Ship ship : ships) {
            Port destination = findDestinationPort(ports, ship.getID());// get destination port here too
            ship.addFuel(destination);
            ship.travelToPort(destination);
            ship.unloadContainers();
        }
    }

    private static Port findDestinationPort(ArrayList<Port> ports, int shipId) {
        return ports.get(shipId % ports.size());
    }

    private static void printStatus(ArrayList<Port> ports, ArrayList<Ship> ships) {
        for (Port port : ports) {
            System.out.println(port);
        }
        for (Ship ship : ships) {
            System.out.println(ship);
        }
    }
}