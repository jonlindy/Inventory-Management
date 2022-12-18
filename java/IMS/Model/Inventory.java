package IMS.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Inventory class. Inventory is dependent on Part and Product classes.
 * Inventory contains methods for adding, deleting, updating, and searching for parts and products.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * This method searches for a part by name. This method is overloaded since it can pass in either a string or an integer.
     *
     * @param partialName a string to search for match
     * @return the matched part, if a match is found
     */
    public static ObservableList<Part> lookupPart(String partialName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(partialName)) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    /**
     * This method searches for a part by ID.
     *
     * @param ID The ID to search for match
     * @return the matched part, if a match is found
     */
    public static ObservableList<Part> lookupPart(int ID) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getId() == ID) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    /**
     * This method searches for a product by name
     *
     * @param partialName a string to search for match
     * @return the matched product, if a match is found
     */
    public static ObservableList<Product> lookupProduct(String partialName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().contains(partialName)) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    /**
     * This method searches for a product by ID.
     *
     * @param ID the ID to search for match
     * @return the matched product, if a match is found
     */
    public static ObservableList<Product> lookupProduct(int ID) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getId() == ID) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    /**
     * This method adds a part to the allParts observable list
     *
     * @param newPart the part object to add
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * This method is the getter for the allParts list
     *
     * @return all parts in inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method adds a product to the allProducts observable list
     *
     * @param newProduct the product object to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method is the getter for the allProducts list
     *
     * @return all products in Inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * This method sets a part to an existing part in the parts list
     *
     * @param id   the ID of the part to update
     * @param part the part object to set
     */
    public static void updatePart(int id, Part part) {
        int index = -1;

        for (Part selectedPart : Inventory.getAllParts()) {
            ++index;

            if (selectedPart.getId() == id) {
                Inventory.getAllParts().set(index, part);
            }
        }
    }

    /**
     * This method sets a product to an existing product in the products list
     *
     * @param id         the ID of the product to update
     * @param newProduct the product object to set
     */
    public static void updateProduct(int id, Product newProduct) {
        int index = -1;
        for (Product product : Inventory.getAllProducts()) {
            ++index;

            if (product.getId() == id) {
                Inventory.getAllProducts().set(index, newProduct);
            }
        }

    }

    /**
     * This method deletes a part from the parts list
     *
     * @param selectedPart the part to be deleted
     * @return returns true if the part is deleted
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * This method deletes a product from the products list
     *
     * @param selectedProduct the product to be deleted
     * @return returns true if the product is deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return Inventory.getAllProducts().remove(selectedProduct);
    }


    public static void addTestData() {
        InHouse IntFrame = new InHouse(10, "Internal Frame", 150.00, 40, 10, 60, 1);
        InHouse HipBelt = new InHouse(11, "Hip Belt", 25.00, 40, 10, 60, 3);

        OutSourced Bladder = new OutSourced(20, "Hydration Bladder", 35.00, 15, 5, 30, "Camel Tech");
        OutSourced Zipper = new OutSourced(21, "Zipper", 10.00, 120, 100, 300, "Smart Zip");
        ObservableList<Part> nullParts = FXCollections.observableArrayList();
        Product DayPack = new Product(50, "Day Pack", 150.00, 15, 10, 30, nullParts);

        Inventory.addPart(IntFrame);
        Inventory.addPart(HipBelt);
        Inventory.addPart(Bladder);
        Inventory.addPart(Zipper);
        Inventory.addProduct(DayPack);
    }
}

