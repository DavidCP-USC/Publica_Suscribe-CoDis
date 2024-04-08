module com.example.pruebaps {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.rmi;
    requires java.logging;

    opens com.example.pruebaps to javafx.fxml;
    exports com.example.pruebaps;
}