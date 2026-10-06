import java.io.BufferedReader;  // reads text from charcter-input stream
import java.io.FileReader;  // reads text
import java.io.IOException; // io excpeption checker
import java.util.ArrayList; // list manager

public class readPortData {

    // load port data from txt - return list of objects
    public static ArrayList<Port> loadPorts(String filename) {
        ArrayList<Port> ports = new ArrayList<>(); // objects sotred in this list
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line; // var to store lines that have been read 

            //read each line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");   // η γραμμή σπαει σε μέρη όπου έχει κόμμα
                int id = Integer.parseInt(parts[0].trim()); //τα μέρη γίνονται parsed για τισ λεπτομέρειες
                String name = parts[1].trim();
                double lat = Double.parseDouble(parts[2].trim());
                double lon = Double.parseDouble(parts[3].trim());

                //νεο αντικείμενο - αποθηκεύεται στη λίστα
                ports.add(new Port(id, name, lat, lon));
            }
        } catch (IOException e) {   // τσεκάρει για exceptions
            e.printStackTrace();    // debugging - helps us understand what went wrong - provides context
        }
        return ports;   // return list of ports
    }


}