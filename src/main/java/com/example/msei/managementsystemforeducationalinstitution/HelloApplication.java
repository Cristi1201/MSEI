package com.example.msei.managementsystemforeducationalinstitution;

import DB_Connection.ConnectionBD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getClassLoader().getResource("app.fxml"));
            Scene scene = new Scene(root, 860, 560);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("IPLT Ion Luca Caragiale");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Connection c = ConnectionBD.getConnection();
        try {
            if (c != null) {
                launch(args); //start app
                c.close(); //close connection to db
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }
    }
}