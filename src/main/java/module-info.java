module com.example.tms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.tms to javafx.fxml;
    exports com.example.tms;
}