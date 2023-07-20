package lk.ijse.gsn.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gsn.dto.Customer;
import lk.ijse.gsn.dto.Service;
import lk.ijse.gsn.dto.tm.CustomerTM;
import lk.ijse.gsn.dto.tm.ServiceTM;
import lk.ijse.gsn.model.CustomerModel;
import lk.ijse.gsn.model.ServiceModel;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class ServiceFormController implements Initializable {

    private static final String URL = "jdbc:mysql://localhost:3306/gsn";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXTextField firstnametxt;
    public JFXTextField emailtxt;
    public JFXButton savebtn;
    public JFXTextField name;
    public JFXTextField serviceidtxt;
    public JFXTextField pricetxt;
    public JFXTextField nametxt;
    public JFXTextField searchtxt;
    public Button searchbtn;
    public JFXButton updatebtn;

    public TableView tableview;
    public TableColumn serviceid;
    public TableColumn servicetype;
    public TableColumn price;

    public void savebtnonaction(ActionEvent event) {

        String servicename = nametxt.getText();
        String service_id = serviceidtxt.getText();
        Double price = Double.valueOf(pricetxt.getText());


        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "INSERT INTO service(Service_id, Service_type, price)" +
                    "VALUES(?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, service_id);
            pstm.setString(2, servicename);
            pstm.setDouble(3, price);


            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,
                        " Customer added successfully")
                        .show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,
                        "oops)")
                        .show();
            }

            nametxt.setText("");
            serviceidtxt.setText("");
            pricetxt.setText("");



        } catch (SQLException throwables) {

        }

    }

    public void searchbtnonaction(ActionEvent event) throws SQLException {
        String search = searchtxt.getText();

        if(search != null){
            try (Connection con = DriverManager.getConnection(URL , props)){
                String sql = "SELECT * FROM service WHERE Service_Id = ?";
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, search);

                ResultSet resultSet = pstm.executeQuery();
                if(resultSet.next()) {
                    String id = resultSet.getString(1);
                    String type = resultSet.getString(2);
                    String price = resultSet.getString(3);

                    serviceidtxt.setText(id);
                    nametxt.setText(type);
                    pricetxt.setText(price);
                }


            }
        }else{
            new Alert(Alert.AlertType.WARNING, "Please enter id !").show();
        }
    }

    public void updatebtnonaction(ActionEvent event) throws SQLException {
        String id = serviceidtxt.getText();
        String type = nametxt.getText();
        String price = pricetxt.getText();


        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "UPDATE service SET Service_type = ?, price = ? WHERE Service_Id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, type);
            pstm.setString(2, price);
            pstm.setString(3, id);

            if(pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Service update successfully !").show();
            }


        }

    }

    public void deletebtnonaction(ActionEvent event) throws SQLException {
        String id = serviceidtxt.getText();

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "DELETE FROM service WHERE  Service_ID = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully service remove !").show();
            }


            nametxt.setText("");
            serviceidtxt.setText("");
            pricetxt.setText("");
            searchtxt.setText("");

        }
    }

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getAll();setCellValueFactory();
    }

    private void setCellValueFactory() {
        serviceid.setCellValueFactory(new PropertyValueFactory<>("Service_ID"));
        servicetype.setCellValueFactory(new PropertyValueFactory<>("Service_type"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    private void getAll() {
        try {
            ObservableList<ServiceTM> obList = FXCollections.observableArrayList();
            List<Service> cusList = ServiceModel.getAll();

            for (Service customer : cusList) {
                obList.add(new ServiceTM(
                        customer.getService_ID(),
                        customer.getService_type(),
                        customer.getPrice()


                ));
            }
            tableview.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

}
