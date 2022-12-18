package IMS.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import IMS.Model.Inventory;
import IMS.Model.Part;
import IMS.Model.Product;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This method contains the controller functions for the Modify Product window.
 */
public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML    private TextField prodIDTxt;
    @FXML    private TextField prodInvTxt;
    @FXML    private TextField prodMaxTxt;
    @FXML    private TextField prodMinTxt;
    @FXML    private TextField prodNameTxt;
    @FXML    private TextField prodPriceTxt;
    @FXML    private TableColumn<Product, Integer> ASCpartIDCol;
    @FXML    private TableColumn<Product, Integer> ASCpartInvLvlCol;
    @FXML    private TableColumn<Product, String> ASCpartNameCol;
    @FXML    private TableColumn<Product, Double> ASCpartPriceCostCol;
    @FXML    private TableColumn<Product, Integer> partIDCol;
    @FXML    private TableColumn<Product, Integer> partInvLvlCol;
    @FXML    private TableColumn<Product, String> partNameCol;
    @FXML    private TableColumn<Product, Double> partPriceCostCol;
    @FXML private TableView<Part> partTableView;
    @FXML private TableView<Part> AscPartTableView;
    @FXML private TextField queryPart;


    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> tempParts = FXCollections.observableArrayList();

    /**
     * This method saves the product to the allProducts list and returns to the main screen.
     * @param event the save button is clicked
     * @throws IOException
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(prodIDTxt.getText());
            String name = prodNameTxt.getText();
            double price = Double.parseDouble(prodPriceTxt.getText());
            int stock = Integer.parseInt(prodInvTxt.getText());
            int min = Integer.parseInt(prodMinTxt.getText());
            int max = Integer.parseInt(prodMaxTxt.getText());

            if (max < min) {
                MainScreenController.errorDialog("Input Error", "Error in Max/Min fields", "Min value must be less than Max");
                return;
            }
            if (stock < min || stock > max) {
                MainScreenController.errorDialog("Input Error", "Error in Inventory field", "Inventory value must be between Min and Max");
                return;
            }

            if (MainScreenController.confirmDialog("Save?", "Save this product?")) {
                associatedParts = tempParts;
                Product product = new Product(id, name, price, stock, min, max, associatedParts);
                Inventory.updateProduct(product.getId(), product);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/IMS/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (Exception e) {
            MainScreenController.errorDialog("Input Error", "Error in saving product", "Check fields for proper input");
        }
    }

    /**
     * This method sets the products data to the proper text fields.
     * @param product the product passed from the main screen.
     */

    public void sendProduct(Product product) {
        prodIDTxt.setText(String.valueOf(product.getId()));
        prodNameTxt.setText((product.getName()));
        prodMaxTxt.setText(String.valueOf(product.getMax()));
        prodMinTxt.setText(String.valueOf(product.getMin()));
        prodPriceTxt.setText(String.valueOf(product.getPrice()));
        prodInvTxt.setText(String.valueOf(product.getStock()));
        associatedParts.addAll(product.getAllAssociatedParts());
    }

    /**
     * This method adds a part to the associated parts table
     * @param event the add button is clicked
     * @throws IOException
     */
    @FXML void onActionAddPart(ActionEvent event) throws IOException {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            tempParts.add(selectedPart);
            updateAscPartTable();
        }
        else {
            MainScreenController.errorDialog("Error", "Part must be selected", "Select a Part to Add to Product");
        }
    }

    /**
     * This method removes a part from the associated parts table.
     * @param event the remove button is clicked
     * @throws IOException
     */
    @FXML void onActionRemovePart(ActionEvent event) throws IOException {
        Part selectedPart = AscPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            if (MainScreenController.confirmDialog("Removing Part", "Are you sure? Remove?")) {
                tempParts.remove(selectedPart);
                updateAscPartTable();
            }
        }
        else {
            MainScreenController.errorDialog("Error", "Select a Part", "A Part must be selected to remove from Product");
        }
    }

    /**
     * This method searches the parts list for a match of partial or full name, or partID.
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
     * This method returns to the main screen.
     * @param event the cancel button is clicked.
     * @throws IOException
     */
    @FXML void onActionCancel(ActionEvent event) throws IOException {
        if (MainScreenController.confirmDialog("Cancelling", "Cancel returns to Main Screen")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/IMS/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This method sets the associated parts table with the tempParts list.
     */
    private void updateAscPartTable()  {
        AscPartTableView.setItems(tempParts);
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

            tempParts = associatedParts;

            AscPartTableView.setItems(tempParts);
            ASCpartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            ASCpartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            ASCpartInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            ASCpartPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            updateAscPartTable();
        }
}
