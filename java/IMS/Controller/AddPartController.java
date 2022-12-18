package IMS.Controller;

import IMS.Model.InHouse;
import IMS.Model.Inventory;
import IMS.Model.OutSourced;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;


import static IMS.Model.Inventory.getAllParts;

/**
 * This class is the controller for the functions of the Add Part window
 */
public class AddPartController {

    Stage stage;
    Parent scene;

    @FXML private ToggleGroup addPartInOut;
    @FXML private RadioButton inHouseRBtn;
    @FXML private RadioButton outSourcedRBtn;
    @FXML private TextField partIDTxt;
    @FXML private TextField partInvTxt;
    @FXML private TextField partMachIDorCompanyTxt;
    @FXML private TextField partMaxTxt;
    @FXML private TextField partMinTxt;
    @FXML private TextField partNameTxt;
    @FXML private TextField partPriceCostTxt;
    @FXML private Label inHouseORoutLabel;

    /**
     * This method sets the label text for the subclass-specific variable, depending on which radio button is selected.
     */
    public void radioSwitch() {
        if (inHouseRBtn.isSelected())
            this.inHouseORoutLabel.setText("Machine ID");
        else
            this.inHouseORoutLabel.setText("Company Name");
    }

    /**
     * This method calls the radioSwitch method when a radio button is clicked
     * @param event the radio button is selected
     * @throws IOException
     */
    @FXML
    void onActionRadio(ActionEvent event) throws IOException {
        radioSwitch();
    }

    /**
     * This method exits to the main screen after the cancel is confirmed.
     * @param event the cancel button is clicked
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        if (MainScreenController.confirmDialog("Cancel?", "All entries will be discarded.")) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/IMS/MainScreen.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**
     * This method saves a part to the allParts list and returns to the main screen
     * @param event the Save button is clicked
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

        try {
            int id = generateID();
            String name = partNameTxt.getText();
            double price = Double.parseDouble(partPriceCostTxt.getText());
            int stock = Integer.parseInt(partInvTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());

            if (MainScreenController.confirmDialog("Save?", "Save this part?"))
                if (max < min) {
                    MainScreenController.errorDialog("Input Error", "Error in Max/Min fields", "Min value must be less than Max");
                }
                if (stock < min || stock > max) {
                    MainScreenController.errorDialog("Input Error", "Error in Inventory field", "Inventory value must be between Min and Max");
                }
            else {
                    if (inHouseRBtn.isSelected()) {
                        int machineID = Integer.parseInt(partMachIDorCompanyTxt.getText());
                        InHouse part = new InHouse(id, name, price, stock, min, max, machineID);
                        Inventory.addPart(part);
                    } else {
                        String companyName = partMachIDorCompanyTxt.getText();
                        OutSourced part = new OutSourced(id, name, price, stock, min, max, companyName);
                        Inventory.addPart(part);
                    }
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/IMS/MainScreen.fxml"));
                    stage.setTitle("Inventory Management System");
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

        } catch (Exception e) {
            MainScreenController.errorDialog("Input Error", "Error in saving part", "Check fields for proper input");
        }
    }

    /**
     * This method creates a newID that is sequential to the allParts list.
     * @return the new ID
     */
    public static int generateID(){
        int newID = 1;
        for (int i = 0; i < getAllParts().size(); i++) {
            newID++;
        }
        return newID;
    }
}


          