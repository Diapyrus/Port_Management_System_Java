// we used Java Swing to test ourselves with a framework we had never used before

import javax.swing.*;                       //spam imports
import java.awt.*;  //awt components for graphics
import java.awt.event.ActionListener; //button receiver
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException; // handle errors
import java.util.ArrayList;
import javax.imageio.ImageIO; // read image

public class MainFrame extends JFrame {

private ArrayList<Port> ports;// store port objects
private ArrayList<Ship> ships;
private ArrayList<Container> containers;
private BufferedImage backgroundImage;

public MainFrame() {    //constr
    setTitle("Ship and Port Management System");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // breakscode when window closes
    setSize(1000, 600);  // not necessary/ usefull since we set the window size to be the same as the image size later
    setLocationRelativeTo(null); //win in center

    // define func
    loadImage();    //Attempt2 to load the background image
    loadData();
    initializeComponents();
}

private void loadImage() {
    try {
        backgroundImage = ImageIO.read(new File("img.png"));    // finds location
        System.out.println("Background image loaded successfully.");    
    } catch (IOException e) {   // had problems loading the pic so i added
        e.printStackTrace();    //this as a check before the window pops up
        System.err.println("Error loading background image.");
    }
}

private void initializeComponents() {
    BackgroundPanel backgroundPanel = new BackgroundPanel();
    backgroundPanel.setLayout(null); // Use null layout for exact positioning

    addCustomButton(backgroundPanel, "Run Simulation A", e -> runSimulationA(), 50, 100, 200, 40, Color.cyan, Color.black);
    addCustomButton(backgroundPanel, "Run Simulation B", e -> runSimulationB(), 50, 150, 200, 40, Color.cyan, Color.black);
    addCustomButton(backgroundPanel, "Show Port Status", e -> showPortStatus(), 50, 200, 200, 40, Color.cyan, Color.black);
    addCustomButton(backgroundPanel, "Show Ship Status", e -> showShipStatus(), 50, 250, 200, 40, Color.cyan, Color.black);
    addCustomButton(backgroundPanel, "Reset to Initial State", e -> resetInitialState(), 1100, 150, 200, 40, Color.BLUE, Color.WHITE);
    addCustomButton(backgroundPanel, "Exit", e -> System.exit(0), 1150, 200, 100, 40, Color.BLACK, Color.WHITE); // Exit button at bottom right

    setContentPane(backgroundPanel);
    pack(); // Adjusts the frame size to fit the background image
}

private void addCustomButton(JPanel panel, String label, ActionListener action, int x, int y, int width, int height, Color bg, Color fg) {
    JButton button = new JButton(label);
    button.addActionListener(action);   //awaits and senses the click ogf the mouse
    button.setBounds(x, y, width, height);
    button.setBackground(bg);
    button.setForeground(fg);
    panel.add(button);  // add button to Jpanel
}

private void loadData() {
    ports = readPortData.loadPorts("ports.txt");
    ships = readShipData.loadShips("ships.txt", ports.get(0)); // Assuming the first port is default
    containers = ContainerLoader.loadContainers("containers.txt");
}

private void resetInitialState() {  //erases and resets progress to initial state
    loadData();
    JOptionPane.showMessageDialog(this, "System has been reset to initial state.");
}

private void runSimulationA() {
    Main.handleScenarioA(ports, ships, containers);
    JOptionPane.showMessageDialog(this, "Simulation A completed.");
}

private void runSimulationB() {
    Main.handleScenarioB(ports, ships, containers);
    JOptionPane.showMessageDialog(this, "Simulation B completed.");
}

private void showPortStatus() {     
    String status = ports.stream()
            .map(Port::toString)    //conversion to string
            .reduce((acc, port) -> acc + "\n" + port)// concatenate port strings 
            .orElse("No ports available to display.");// never used
    JOptionPane.showMessageDialog(this, status);
}

private void showShipStatus() {
    String status = ships.stream()
            .map(Ship::toString)
            .reduce((acc, ship) -> acc + "\n" + ship)
            .orElse("No ships available to display.");
    JOptionPane.showMessageDialog(this, status);
}

private class BackgroundPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);// καλεί την μέθοδοτης υπερκλάσης
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (backgroundImage != null) {
            return new Dimension(backgroundImage.getWidth(), backgroundImage.getHeight());
        }
        return super.getPreferredSize();    // reverts to default chosen size if image load fails
    }
}

//  this does the work. The main of this whole file - Starts the app
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        MainFrame mainFrame = new MainFrame();  //new instance
        mainFrame.setVisible(true); //make window visible to user
    });
}

}