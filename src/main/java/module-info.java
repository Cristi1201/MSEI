module com.example.msei.managementsystemforeducationalinstitution {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.msei.managementsystemforeducationalinstitution to javafx.graphics, javafx.fxml, javafx.base, org.controlsfx.control;

    exports com.example.msei.managementsystemforeducationalinstitution;
    exports Tables;
    opens Tables to javafx.base, javafx.fxml, javafx.graphics, org.controlsfx.control;
}