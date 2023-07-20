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
import lk.ijse.gsn.dto.Supplier;
import lk.ijse.gsn.dto.tm.CustomerTM;
import lk.ijse.gsn.dto.tm.SupplierTM;
import lk.ijse.gsn.model.CustomerModel;
import lk.ijse.gsn.model.SupplierModel;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {

    private static final String URL = "jdbc:mysql://localhost:3306/gsn";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXTextField suplierid;
    public JFXTextField locationtxt;
    public JFXTextField emailtxt;
    public JFXButton savebtn;
    public JFXTextField nametxt;
    public JFXTextField contactnumbertxt;
    public JFXTextField searchtxt;
    public Button searchbtn;
    public JFXButton updatebtn;
    public JFXButton deltebtn;

    public TableView tableview;
    public TableColumn supid;
    public TableColumn location;
    public TableColumn email;
    public TableColumn name;
    public TableColumn conumber;

    public void savebtnonaction(ActionEvent event) {



        String suplier_id = suplierid.getText();
        String location = locationtxt.getText();
        String email = emailtxt.getText();
        String name = nametxt.getText();
        String contactnumber = contactnumbertxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "INSERT INTO supplier(Supplier_Id, location, email, name, contact_number)" +
                    "VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, suplier_id);
            pstm.setString(2, location);
            pstm.setString(3, email);
            pstm.setString(4, name);
            pstm.setString(5, contactnumber);


            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,
                        " Supplier added successfully")
                        .show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,
                        "oops)")
                        .show();
            }

        } catch (SQLException throwables) {

        }

        suplierid.setText("");
        locationtxt.setText("");
        emailtxt.setText("");
        nametxt.setText("");
        contactnumbertxt.setText("");



    }

    public void searchbtnonaction(ActionEvent event) throws SQLException {
        String dearch = searchtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT * FROM supplier WHERE Supplier_Id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, dearch);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString(1);
                String location = resultSet.getString(2);
                String email = resultSet.getString(3);
                String name = resultSet.getString(4);
                String conumber = resultSet.getString(5);


                nametxt.setText(name);
                suplierid.setText(id);
                emailtxt.setText(email);
                locationtxt.setText(location);
                contactnumbertxt.setText(conumber);

            }else{
                new Alert(Alert.AlertType.WARNING, "Check and enter correct id").show();
            }
        }

    }

    public void updatebtnonaction(ActionEvent event) {

        String location = locationtxt.getText();
        String email = emailtxt.getText();
        String name = nametxt.getText();
        String contact_number = contactnumbertxt.getText();
        String Supplier_Id = suplierid.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE supplier SET  location = ?, email = ?, name = ?, contact_number = ? WHERE Supplier_Id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, location);
            pstm.setString(2, email);
            pstm.setString(3, name);
            pstm.setString(4, contact_number);
            pstm.setString(5, Supplier_Id);

            boolean isUpdated = pstm.executeUpdate() > 0;
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "yes! updated!!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        nametxt.setText("");
        suplierid.setText("");
        emailtxt.setText("");
        locationtxt.setText("");
        contactnumbertxt.setText("");

        searchtxt.setText("");


    }

    public void deletebtnonaction(ActionEvent event) {
        String id = suplierid.getText();

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "DELETE FROM supplier WHERE Supplier_ID = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully employee remove !").show();
            }


            nametxt.setText("");
            suplierid.setText("");
            emailtxt.setText("");
            locationtxt.setText("");
            contactnumbertxt.setText("");

            searchtxt.setText("");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getAll();setCellValueFactory();
    }

    private void setCellValueFactory() {
        supid.setCellValueFactory(new PropertyValueFactory<>("Supplier_Id"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        conumber.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
    }

    private void getAll() {
        try {
            ObservableList<SupplierTM> obList = FXCollections.observableArrayList();
            List<Supplier> cusList = SupplierModel.getAll();

            for (Supplier customer : cusList) {
                obList.add(new SupplierTM(
                        customer.getSupplier_Id(),
                        customer.getLocation(),
                        customer.getEmail(),
                        customer.getName(),
                        customer.getContact_number()

                ));
            }
            tableview.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

}
