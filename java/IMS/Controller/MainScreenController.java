package IMS.Controller;

import IMS.Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is the controller for the functions of the main screen.
 */
public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, Integer> partInvLvlCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Double> partPriceCostCol;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Product, Integer> prodInvLvlCol;
    @FXML private TableColumn<Product, Double> prodPriceCostCol;
    @FXML private TableView<Product> prodTableView;
    @FXML private TableColumn<Product, Integer> prodIDCol;
    @FXML private TableColumn<Product, String> prodNameCol;
    @FXML private TextField queryPart;
    @FXML private TextField queryProduct;


    /**
     * This method opens the Add Part window
     * @param event Add button clicked in Parts pane
     * @throws IOException
     */
    @FXML
    public void onActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/IMS/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method opens the Add Product window
     * @param event Add button clicked in Products pane
     * @throws IOException
     */
    @FXML
    public void onActionAddProd(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/IMS/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method deletes the selected part in the Parts table
     * @param event Delete button clicked in Parts pane
     */
    @FXML
    void onActionDelPart(ActionEvent event) {

        try {
            if (confirmDialog("Deleting Part", "Are you sure? Delete?")) {
                Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
            }
        }
        catch(Exception e){
                MainScreenController.errorDialog("Selection Error", "No Part Selected", "Select Part to delete.");
            }
        }


    /**
     * This method deletes the selected product in the Products table
     * @param event Delete button clicked in Products pane
     */
    @FXML
    void onActionDelProd(ActionEvent event) {

        try {
            Product product = prodTableView.getSelectionModel().getSelectedItem();

            if (!product.getAllAssociatedParts().isEmpty()) {
                MainScreenController.errorDialog("Error deleting Product", "Product has associated Parts", "Remove parts from Product before deleting.");
            } else {
                if (confirmDialog("Deleting Product", "Are you sure? Delete?")) {
                    Inventory.deleteProduct(product);
                }
            }
        }
        catch (Exception e) {
            MainScreenController.errorDialog("Selection Error", "No Product Selected", "Select Product to delete.");
            }
    }

    /**
     * This method opens the Modify Part screen with the selected part's data.
     * @param event Modify button clicked in Parts pane
     * @throws IOException
     */
    @FXML
    void onActionModPart(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/IMS/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPaController = loader.getController();
            MPaController.sendPart(partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (Exception e) {
            MainScreenController.errorDialog("Selection Error", "No Part Selected", "Select Part to modify.");
        }

    }

    /**
     * This method opens the Modify Product screen with the selected product's data.
     * @param event Modify button clicked in Products pane
     * @throws IOException
     */
    @FXML
    void onActionModProd(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/IMS/ModifyProduct.fxml"));
        loader.load();

        ModifyProductController MPrController = loader.getController();
        MPrController.sendProduct(prodTableView.getSelectionModel().getSelectedItem());

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method opens an error window with the passed in title, header, and message.
     * @param title the alert title
     * @param header the alert header
     * @param message the alert message
     */
    static void errorDialog(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * This method opens a confirm window with the passed in title and content.
     * @param title the alert title
     * @param content the alert content
     * @return returns true if OK button is pressed. or else it returns false.
     */
    static boolean confirmDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method searches the parts list for a match of partial or full name, or partID.
     * RUNTIME ERROR: I encountered a logical error in this method. I noticed when I searched for a part by string nothing would happen, but when I searched by ID it would function as expected.
     * Also, when I cleared the search field and hit enter, the parts table would still only display the part matched by ID.
     * After dissecting the method's code, I found that the setItems() function was inside the part ID if block.
     * Moving this function out of the if block fixed all the problems.
     * @param event A string is entered in the search field.
     * @throws IOException
     */
    @FXML void searchPartsHandler(ActionEvent event) throws IOException {
        String q = queryPart.getText();

        ObservableList<Part> parts = Inventory.lookupPart(q);

        if (parts.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                parts = Inventory.lookupPart(id);

            } catch (NumberFormatException e) {}
            if (parts.size() == 0) {
                MainScreenController.errorDialog("Error", "No Part was found", "Search for Part by Name or ID");
            }

        }
        partTableView.setItems(parts);
    }

    /**
     * This method searches the products list for a match of partial or full name, or productID.
     * @param event A string is entered in the search field
     * @throws IOException
     */
    @FXML void searchProductsHandler(ActionEvent event) throws IOException {
        String q = queryProduct.getText();

        ObservableList<Product> products = Inventory.lookupProduct(q);

        if (products.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                products = Inventory.lookupProduct(id);

            } catch (NumberFormatException e) {}
        }
        if (products.size() == 0) {
            MainScreenController.errorDialog("Error", "No Product was found", "Search for Product by Name or ID");
        }
        prodTableView.setItems(products);
    }

    /**
     * This method exits the application.
     * @param event Exit button clicked
     */
    public void onActionExit (ActionEvent event) {
        if (confirmDialog("Exiting Application", "Are you sure you want to exit?")) {
            System.exit(0);
        }
    }
    /**
     * This method initializes the table columns and sets items to tables.
      * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTableView.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodTableView.setItems(Inventory.getAllProducts());
        prodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}