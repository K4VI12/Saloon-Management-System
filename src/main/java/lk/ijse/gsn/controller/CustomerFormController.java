package lk.ijse.gsn.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gsn.dto.Customer;
import lk.ijse.gsn.dto.tm.CustomerTM;
import lk.ijse.gsn.model.CustomerModel;

import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    private static final String URL = "jdbc:mysql://localhost:3306/gsn";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXTextField firstnametxt;
    public JFXTextField secondnametxt;
    public JFXTextField emailtxt;
    public JFXTextField addresstxt;
    public JFXTextField phonenumbertxt;
    public JFXTextField appoimentidtxt;
    public JFXButton savebtn;
    public JFXTextField customeridtxt;
    public JFXTextField nictxt;
    public JFXTextField searchtxt;
    public Button searchbtn;
    public JFXButton updatebtn;
    public JFXButton deletebtn;

    public TableView tableview;
    public TableColumn customerid;
    public TableColumn firstname;
    public TableColumn secondname;
    public TableColumn nic;
    public TableColumn phonenumber;
    public TableColumn address;
    public TableColumn city;

    public void savebtnonaction(ActionEvent event) throws SQLException {
        String custmer_id = customeridtxt.getText();
        String first_name = firstnametxt.getText();
        String second_name = secondnametxt.getText();
        String contact_number = phonenumbertxt.getText();
        String Nic = nictxt.getText();
        String address = addresstxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "INSERT INTO customer(customer_id, fist_name, second_name, contact_number, NIC, address)" +
                    "VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,custmer_id );
            pstm.setString(2, first_name);
            pstm.setString(3, second_name);
            pstm.setString(4, contact_number);
            pstm.setString(5, Nic);
            pstm.setString(6, address);


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

            firstnametxt.setText("");
            customeridtxt.setText("");
            secondnametxt.setText("");
            phonenumbertxt.setText("");
            nictxt.setText("");
            addresstxt.setText("");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void searchbtnonaction(ActionEvent event) throws SQLException {
        String dearch = searchtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT * FROM customer WHERE customer_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, dearch);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String secname = resultSet.getString(3);
                String contact_num = resultSet.getString(4);
                String nic = resultSet.getString(5);
                String address = resultSet.getString(6);


                firstnametxt.setText(name);
                customeridtxt.setText(id);
                secondnametxt.setText(secname);
                phonenumbertxt.setText(contact_num);
                nictxt.setText(nic);
                addresstxt.setText(address);
                savebtn.setDisable(true);

            }else{
                new Alert(Alert.AlertType.WARNING, "Check and enter correct id").show();
            }
        }
    }

    public void updatebtnonaction(ActionEvent event) {

        String id = customeridtxt.getText();
        String name = firstnametxt.getText();
        String secname = secondnametxt.getText();
        String contact_num = phonenumbertxt.getText();
        String nic = nictxt.getText();
        String address = addresstxt.getText();

        try (Connection con = DriverManager.getConnection(URL,props)){
            String sql = "UPDATE customer SET fist_name = ?, second_name = ?, contact_number = ?, NIC = ?, address = ? WHERE customer_id  = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, secname);
            pstm.setString(3, contact_num);
            pstm.setString(4, nic);
            pstm.setString(5, address);
            pstm.setString(6, id);

            if(pstm.executeUpdate()>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer update successfully").show();
            }

            customeridtxt.setText("");
            firstnametxt.setText("");
            secondnametxt.setText("");
            phonenumbertxt.setText("");
            nictxt.setText("");
            addresstxt.setText("");

            searchtxt.setText("");
            savebtn.setDisable(false);

        } catch (SQLException throwables) {

        }

    }

    public void deletebtnonaction(ActionEvent event) throws SQLException {

        String id = customeridtxt.getText();

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "DELETE FROM customer WHERE  customer_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully customer remove !").show();
            }
            customeridtxt.setText("");
            firstnametxt.setText("");
            secondnametxt.setText("");
            phonenumbertxt.setText("");
            nictxt.setText("");
            addresstxt.setText("");

            searchtxt.setText("");

        }

    }

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getAll(); setCellValueFactory();
    }

    private void setCellValueFactory() {
        customerid.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("fist_name"));
        secondname.setCellValueFactory(new PropertyValueFactory<>("second_name"));
        nic.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
        phonenumber.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void getAll() {
        try {
            ObservableList<CustomerTM> obList = FXCollections.observableArrayList();
            List<Customer> cusList = CustomerModel.getAll();

            for (Customer customer : cusList) {
                obList.add(new CustomerTM(
                        customer.getCustomer_id(),
                        customer.getFist_name(),
                        customer.getSecond_name(),
                        customer.getContact_number(),
                        customer.getNIC(),
                        customer.getAddress()

                ));
            }
            tableview.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

}


