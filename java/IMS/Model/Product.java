package IMS.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Product class. This class creates Product objects and includes methods to interact with the Products list.
 */
public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Full constructor. Used to create full product objects.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param parts
     */
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList<Part> parts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = parts;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method adds a part to the associated parts list
     * @param part
     */
    public void addAssociatedPart(ObservableList<Part> part) {
        this.associatedParts.addAll(part);
    }

    /**
     * This method removes a part from the associated parts list
     * @param selectedAssociatedPart
     * @return
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * This method returns the associated parts list
     * @return associated parts list
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;

    }
}
