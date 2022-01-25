module com.example.conciflex {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.conciflex to javafx.fxml;
    exports com.example.conciflex;
    exports com.example.conciflex.controller;
    opens com.example.conciflex.controller to javafx.fxml;
    exports com.example.conciflex.model.classes;
    opens com.example.conciflex.model.classes to javafx.fxml;
    exports com.example.conciflex.model.classes.ben;
    opens com.example.conciflex.model.classes.ben to javafx.fxml;
}