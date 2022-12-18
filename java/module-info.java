module IMS.Main {
    requires javafx.controls;
    requires javafx.fxml;





    exports IMS.Main;
    opens IMS.Main to javafx.fxml;
    exports IMS.Controller;
    opens IMS.Controller to javafx.fxml;
    exports IMS.Model;
    opens IMS.Model to javafx.fxml;
}