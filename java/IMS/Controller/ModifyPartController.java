package IMS.Controller;

import IMS.Model.InHouse;
import IMS.Model.Inventory;
import IMS.Model.OutSourced;
import IMS.Model.Part;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * This class is the controller for the functions of the Modify Part window.
 */
public class ModifyPartController {
    Stage stage;
    Parent scene;
    public Part part;

    @FXML    private RadioButton inHouseRBtn;
    @FXML    private RadioButton outSourcedRBtn;
    @FXML    private ToggleGroup modPartInOut;
    @FXML    private TextField partIDTxt;
    @FXML    private TextField partInvTxt;
    @FXML    private TextField partMachIDorCompanyTxt;
    @FXML    private TextField partMaxTxt;
    @FXML    private TextField partMinTxt;
    @FXML    private TextField partNameTxt;
    @FXML    private TextField partPriceCostTxt;
    @FXML    private Label inHouseORoutLabel;

    /**
     * This method exits to the main screen after the cancel is confirmed.
     * @param event the cancel button is clicked
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        if(MainScreenController.confirmDialog("Cancel?", "All entries will be discarded.")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/IMS/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * This method saves a part to the allParts list and returns to the main screen.
     * @param event the save button is clicked
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(partIDTxt.getText());
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
                    Inventory.updatePart(part.getId(), part);
                } else {
                    String companyName = partMachIDorCompanyTxt.getText();
                    OutSourced part = new OutSourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(part.getId(), part);
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
     * This method sets the part's data to the proper text fields.
     * @param part the selected part passed from the main screen .
     */
    public void sendPart(Part part) {

        partIDTxt.setText(String.valueOf(part.getId()));
        partNameTxt.setText((part.getName()));
        partMaxTxt.setText(String.valueOf(part.getMax()));
        partMinTxt.setText(String.valueOf(part.getMin()));
        partPriceCostTxt.setText(String.valueOf(part.getPrice()));
        partInvTxt.setText(String.valueOf(part.getStock()));

        if(part instanceof InHouse) {
            int machineID = (((InHouse) part).getMachineID());
            System.out.println(machineID);
            inHouseRBtn.setSelected(true);
            inHouseORoutLabel.setText("Machine ID");
            partMachIDorCompanyTxt.setText(Integer.toString(machineID));
        }
        else {
            String companyName = (((OutSourced) part).getCompanyName());
            System.out.println(companyName);

            outSourcedRBtn.setSelected(true);
            inHouseORoutLabel.setText("Company Name");
            partMachIDorCompanyTxt.setText(companyName);
        }
    }
}
