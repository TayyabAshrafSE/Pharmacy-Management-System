package com.example.pharmacymanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import com.example.pharmacymanagementsystem.GetDataForUser.*;

public class LoginController implements Initializable {

    @FXML
    private Button btn_login;

    @FXML
    private PasswordField tf_password;

    @FXML
    private TextField tf_email;

    // Database Tools
    private PreparedStatement prepare;
    private Connection connect;
    private ResultSet result;

    public void loginUser() {

        String sql = "Select * from user where email=? and password=?";
        connect = DbConnection.connectDb();
        int temp = 0;
        try {
            prepare = connect.prepareStatement((sql));
            prepare.setString(1, tf_email.getText());
            prepare.setString(2, tf_password.getText());

            result = prepare.executeQuery();

            Alert alert;
            if (tf_email.getText().isEmpty() || tf_password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please Fill all blank fields");
                alert.showAndWait();
            } else {
                while (result.next()) {
                    temp = 1;
                    GetDataForUser.email = tf_email.getText();
                    if (result.getString("Role").equals("Admin")) {
                        // Hide login form
                        btn_login.getScene().getWindow().hide();
                        // Redirect to Dashboard
                        Parent root = FXMLLoader.load(getClass().getResource("AdminDashboard.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        stage.setScene(scene);
                        stage.show();
                    } else {
                        btn_login.getScene().getWindow().hide();
                        // Redirect to Dashboard
                        Parent root = FXMLLoader.load(getClass().getResource("PharmacistDashboard.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        stage.setScene(scene);
                        stage.show();
                    }
                }
                if (temp == 0) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Email or Password");
                    alert.showAndWait();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public void close() {
        System.exit(0);
    }

    public void initialize(URL url, ResourceBundle rb) {

    }
}
