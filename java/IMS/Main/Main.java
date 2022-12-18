package IMS.Main;

import IMS.Model.Inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
/**
 * This class runs the application.
 * */
public class Main extends Application {
    /**
     * This start method loads the application's main screen.
     * @param primaryStage
     * @throws IOException
     */

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/IMS/MainScreen.fxml"));
        Scene scene = new Scene(root, 1100, 500 );
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main method launches the application.
     * FUTURE ENHANCEMENT: Adding a "Remove All Associated Parts" function for the Modify Product window would improve efficiency.
     * If the user wants to delete a product that contains parts, they must first go into the Modify Product window and individually remove each part.
     * A Remove All function could save a user some time, especially if Products tend to contain many parts.
     * However, if products are rarely deleted and tend to have just a few parts, this enhancement may prove to be trivial.
     *
     * The "javadocs" folder is located inside the Inventory folder in the project zip file.
     * @param args
     */
    public static void main(String[] args) {
        Inventory.addTestData();
        launch(args);

    }
}