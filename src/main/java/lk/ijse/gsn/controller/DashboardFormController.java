package lk.ijse.gsn.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class DashboardFormController{



    public void setOnActiondsh(ActionEvent event){


    }

    public void setOnActionapmt(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AppoinmentForm.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Appoiment");
        Image img = new Image("image/GSN.1png.png");
        stage.getIcons().add(img);

    }

    public void setOnActioncust(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CustomerForm.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Customer");
        Image img = new Image("image/GSN.1png.png");
        stage.getIcons().add(img);

    }

    public void setOnActionpymt(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/PaymentForm.fxml "));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Payment");
        Image img = new Image("image/GSN.1png.png");
        stage.getIcons().add(img);


    }

    public void setOnActionemply(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/EmployeeForm.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Employ");
        Image img = new Image("image/GSN.1png.png");
        stage.getIcons().add(img);

    }

    public void setOnActionsrvis(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ServiceForm.fxml "));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Service");
        Image img = new Image("image/GSN.1png.png");
        stage.getIcons().add(img);

    }

    public void setOnActionStok(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/StockForm.fxml "));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Stock");
        Image img = new Image("image/GSN.1png.png");
        stage.getIcons().add(img);

    }

    public void setOnActionstng(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SupplierForm.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setTitle("Supplier");
        Image img = new Image("image/GSN.1png.png");
        stage.getIcons().add(img);

    }

}
