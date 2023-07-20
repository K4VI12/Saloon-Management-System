package lk.ijse.gsn.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupFormController {

    @FXML
    private AnchorPane pane;


    public void btncheckpasswordbtnonaction(ActionEvent event) {
    }

    public void btninvisiblebtnonaction(ActionEvent event) {
    }

    public void setAigninOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginpageForm.fxml"))));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
        stage.show();
    }


    public void setSignupOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
        stage.show();
    }
}

