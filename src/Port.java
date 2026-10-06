import java.util.ArrayList;// ... to manage lists

//class oh yeah
public class Port {
    private int id;
    private String name;
    private double lat;
    private double lon;
    private ArrayList<Container> containers;
    private ArrayList<Ship> ships;

    //constr to initialize a port
    public Port(int id, String name, double lat, double lon) {
        this.id = id;   // sets needed info
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.containers = new ArrayList<>(); // init lists
        this.ships = new ArrayList<>();
    }

    // method using the haverside formula to find distance
    public double calculateDistance(Port other) {
        double lat1 = this.lat;     //give info of this port
        double lon1 = this.lon;
        double lat2 = other.lat;
        double lon2 = other.lon;
        double distance = Math.acos((Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))) +
                (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))) *
                        (Math.cos(Math.toRadians(lon2) - Math.toRadians(lon1)))) * 6371; // earths rad
        return distance;    // return result 
    }

    public void addContainer(Container container) {
        containers.add(container); //add container to list
    }

    public void addShip(Ship ship) {
        ships.add(ship);    //add ship to the list
    }

    public void removeShip(Ship ship) {
        ships.remove(ship);
    }

    @Override   //override this in order to print port data in string form
    public String toString() {
        return "Port{id=" + id + ", name='" + name + "', lat=" + lat + ", lon=" + lon +
                ", containers=" + containers.size() + ", ships=" + ships.size() + "}";
    }

    // Getters and setters
    // needed and not needed
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<Container> containers) {
        this.containers = containers;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }
}

