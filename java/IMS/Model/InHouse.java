package IMS.Model;

/**
 * This is the InHouse class. Subclass of the abstract Part class.
 */
public class InHouse extends Part{

    private int machineID;

    /**
     * Full constructor for creating InHouse parts
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * Setter for machineID
     * @param machineID the machine ID to set
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    };

    /**
     *
     * @return the machine ID
     */
    public int getMachineID(){
        return machineID;
    }

}
