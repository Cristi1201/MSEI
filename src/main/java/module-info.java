module com.example.msei.managementsystemforeducationalinstitution {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.msei.managementsystemforeducationalinstitution to javafx.fxml;
    exports com.example.msei.managementsystemforeducationalinstitution;
}