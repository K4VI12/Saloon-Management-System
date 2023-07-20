package lk.ijse.gsn.controller;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class LoginFormController{

    private static final String URL = "jdbc:mysql://localhost:3306/gsn";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXButton btnsignup;
    public Button checkpasswordbtn;
    public AnchorPane pane;
    public TextField usernametxt;
    public PasswordField passwordtxt;

    String username ;
    String passwrd ;

    public void setSignupOnAction(ActionEvent event) throws IOException, SQLException {


            Stage stage = (Stage) pane.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/SignupForm.fxml"))));
            stage.setTitle("Signup");
            stage.centerOnScreen();
            stage.show();

    }

    public void btncheckpasswordbtnonaction(ActionEvent event) {
    }

    public void btninvisiblebtnonaction(ActionEvent event) {
    }


    public void setSigninOnAction(ActionEvent event) throws IOException {

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "SELECT *FROM password " ;
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                username = resultSet.getString(1);
                passwrd = resultSet.getString(2);
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,
                        "oops)")
                        .show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println(username+""+passwrd);

        if(username.equals(usernametxt.getText())&&passwrd.equals(passwordtxt.getText())) {

            Stage stage = (Stage) pane.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.setTitle("Dashboard");
            stage.centerOnScreen();
            stage.show();

        }else {
            new Alert(Alert.AlertType.WARNING,"Please enter correct username & password").show();
        }

    }
}


