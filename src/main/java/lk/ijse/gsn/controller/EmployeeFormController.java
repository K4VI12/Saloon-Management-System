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
import lk.ijse.gsn.dto.Employee;
import lk.ijse.gsn.dto.tm.CustomerTM;
import lk.ijse.gsn.dto.tm.EmployeeTM;
import lk.ijse.gsn.model.CustomerModel;
import lk.ijse.gsn.model.EmployeeModel;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

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
    public JFXTextField customeridtxt;
    public JFXButton savebtn;
    public JFXTextField nictxt;
    public JFXTextField searchtxt;
    public Button searchbtn;
    public JFXButton updatebtn;
    public JFXButton deletebtn;

    public TableView tableview;
    public TableColumn id;
    public TableColumn nic;
    public TableColumn name;
    public TableColumn secname;
    public TableColumn contactno;
    public TableColumn city;
    public TableColumn employee;

    public void savebtnonaction(ActionEvent event) {

        String name = firstnametxt.getText();
        String sename = secondnametxt.getText();
        String nic = nictxt.getText();
        String employeeid = emailtxt.getText();
        String contactnum = phonenumbertxt.getText();
        String city = customeridtxt.getText();
        String nul = "null";

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "INSERT INTO employee(fist_name, second_name, employee_id, contact_number, NIC, Street, Lane, City)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, sename);
            pstm.setString(3, employeeid);
            pstm.setString(4, contactnum);
            pstm.setString(5, nic);
            pstm.setString(6, nul);
            pstm.setString(7,nul);
            pstm.setString(8, city);


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




        } catch (SQLException throwables) {

        }
    }

    public void searchbtnonaction(ActionEvent event) throws SQLException {

        String search = searchtxt.getText();

        try (Connection con = DriverManager.getConnection(URL , props)){
            String sql = "SELECT * FROM employee WHERE employee_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, search);

            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()) {
                String name = resultSet.getString(1);
                String secname = resultSet.getString(2);
                String id = resultSet.getString(3);
                String conumber = resultSet.getString(4);
                String nic = resultSet.getString(5);
                String city = resultSet.getString(8);

                firstnametxt.setText(name);
                secondnametxt.setText(secname);
                nictxt.setText(nic);
                emailtxt.setText(id);
                phonenumbertxt.setText(conumber);
                customeridtxt.setText(city);
            }


        }
    }

    public void updatebtnonaction(ActionEvent event) throws SQLException {

        String nam = firstnametxt.getText();
        String secnam = secondnametxt.getText();
        String nic = nictxt.getText();
        String email = emailtxt.getText();
        String phonenum = phonenumbertxt.getText();
        String id  = customeridtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "UPDATE employee SET fist_name = ?, second_name = ?, contact_number = ?, NIC = ?, City = ? WHERE employee_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, nam);
            pstm.setString(2, secnam);
            pstm.setString(3, phonenum);
            pstm.setString(4, nic);
            pstm.setString(5, id);
            pstm.setString(6, email);


            firstnametxt.setText("");
            secondnametxt.setText("");
            nictxt.setText("");
            emailtxt.setText("");
            phonenumbertxt.setText("");
            customeridtxt.setText("");

            boolean isupdate = pstm.executeUpdate() > 0;
            if(isupdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee updated successfully !").show();
            }
        }

    }

    public void deletebtnonaction(ActionEvent event) {
        String id = customeridtxt.getText();

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "DELETE FROM employee WHERE  employee_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully employee remove !").show();
            }


            firstnametxt.setText("");
            secondnametxt.setText("");
            nictxt.setText("");
            emailtxt.setText("");
            phonenumbertxt.setText("");
            customeridtxt.setText("");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getAll(); setCellValueFactory();
    }

    private void setCellValueFactory() {
        id.setCellValueFactory(new PropertyValueFactory<>("fist_name"));
        nic.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        name.setCellValueFactory(new PropertyValueFactory<>("fist_name"));
        secname.setCellValueFactory(new PropertyValueFactory<>("second_name"));
        contactno.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        employee.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
    }

    private void getAll() {
        try {
            ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();
            List<Employee> cusList = EmployeeModel.getAll();

            for (Employee emp : cusList) {
                obList.add(new EmployeeTM(
                        emp.getFist_name(),
                        emp.getSecond_name(),
                        emp.getEmployee_id(),
                        emp.getContact_number(),
                        emp.getNIC(),
                        emp.getStreet(),
                        emp.getLane(),
                        emp.getCity()

                ));
            }
            tableview.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
}
