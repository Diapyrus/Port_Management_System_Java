import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; // for io errors
import java.util.ArrayList; // dynamic array mngmnt

// reads container info from file
public class ContainerLoader {

    // static method - loads containers
    public static ArrayList<Container> loadContainers(String fileName) {
        ArrayList<Container> containers = new ArrayList<>();    // list for the loaded containers
        
        // try-w-res  to stop buffread automatically
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            // διαβάζει κάθε γραμμη απο το φιλε
            while ((line = br.readLine()) != null) {
                try {
                    line = line.trim(); //cuts useless space
                    String[] parts = line.split(",");

                    if (parts.length != 3) {    //check to see if the text format agrees with the reading style
                        throw new IllegalArgumentException("Invalid line format: " + line);
                    }
                    
                    int id = Integer.parseInt(parts[0].trim()); //parse cont ID
                    String type = parts[1].trim();  //gets cont type
                    int weight = Integer.parseInt(parts[2].trim()); //parse cont weight

                    Container container = null; // Initialize container as null
                    switch (type) {
                        case "απλό":
                            container = new SimpleContainer(id, weight);
                            break;
                        case "ψυγείο":
                            container = new RefrigeratedContainer(id, weight);
                            break;
                        case "βυτίο":
                            container = new LiquidContainer(id, weight);
                            break;
                        default:    // found new type or different information
                            throw new IllegalArgumentException("Unknown container type: " + type);
                    }

                    if (container != null) {    // new object added to the list
                        containers.add(container);
                    }

                } catch (Exception e) { // checks for errors in line reading
                    System.err.println("Error while processing line: " + line + "; Error: " + e.getMessage());
                }
            }

        } catch (IOException e) {   // checks for problems while loading file
            System.err.println("Error while reading file: " + e.getMessage());
        }
        // return list full of containers
        return containers;
    }
}
