// Ααφηρημένη θεμελιώδης υπερκλάση για τους επερχόμενους τύπους containers
public abstract class Container {
    protected int ID; // μοναδικός identifier
    protected int weight; 

    // constr για την αρχικοποίηση του ID και weight
    public Container(int ID, int weight) {
        this.ID = ID;
        this.weight = weight;
    }

    // avstract method for fuel calc - implemented by subclasses
    public abstract double calculateFuelConsumption();

    // override to show string representation of container 
    @Override
    public String toString() {
        return "Container ID: " + ID + ", Weight: " + weight + ", Fuel Consumption: " + calculateFuelConsumption() + " liters/km";
    }

    //getters - setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

// υποκλαση για συγκεκριμένο τύπο κοντεινερ
class SimpleContainer extends Container {
    // constr for init
    public SimpleContainer(int ID, int weight) {
        super(ID, weight);
    }

    // here the abstract method from before is implemented
    @Override
    public double calculateFuelConsumption() {
        return 20 * weight; // fuel consumption rate is 20 liters per ton
    }
}

// αφηρημένη υποκλάση για τα Βαρέων Βαρών κοντεινερ
abstract class HeavyContainer extends Container {
    // μια από τα ίδια κονστρ για αρχικοποίηση - για υπερκλάση
    public HeavyContainer(int ID, int weight) {
        super(ID, weight);
    }
    // HeavyContainer does not define its own fuel consumption, thus remains abstract
}


// 
class RefrigeratedContainer extends HeavyContainer {
    public RefrigeratedContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double calculateFuelConsumption() {
        return 35 * weight; // Higher fuel consumption due to refrigeration
    }
}

class LiquidContainer extends HeavyContainer {
    public LiquidContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double calculateFuelConsumption() {
        return 27 * weight; // Fuel consumption specific to liquid containers
    }
}