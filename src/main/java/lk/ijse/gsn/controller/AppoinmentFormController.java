package lk.ijse.gsn.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gsn.dto.Appoinment;
import lk.ijse.gsn.dto.Customer;
import lk.ijse.gsn.dto.tm.AppointmentTM;
import lk.ijse.gsn.dto.tm.CustomerTM;
import lk.ijse.gsn.model.AppointmentModel;
import lk.ijse.gsn.model.CustomerModel;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class AppoinmentFormController implements Initializable {

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
    public DatePicker sellecttxt;
    public JFXButton savebtn;
    public JFXComboBox servicecobo;
    public JFXComboBox custid;
    public JFXTextField searchtxt;
    public Button searchbtn;
    public JFXButton updatebtn;
    public JFXTextField timetxt;
    public JFXTextField endtimetxt;
    public JFXButton deletebtn;

    public TableView tableview;
    public TableColumn appid;
    public TableColumn cusid;
    public TableColumn date;
    public TableColumn time;
    public TableColumn number;

    public void savebtnonaction(ActionEvent event) {

        String appoitmenid = appoimentidtxt.getText();
        String customer_id = String.valueOf(custid.getValue());
        String service = String.valueOf(servicecobo.getValue());
        String address = addresstxt.getText();
        String phonenumber = phonenumbertxt.getText();
        String date = String.valueOf(sellecttxt.getValue());
        String endtime = endtimetxt.getText();
        String starttime =timetxt.getText();
        //****

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "INSERT INTO appointment(appointment_id, time, date, customer_id, address, pnnumber, service, endtime)" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, appoitmenid  );
            pstm.setString(2, starttime);
            pstm.setDate(3, Date.valueOf(date));
            pstm.setString(4,customer_id);
            pstm.setString(5, address);
            pstm.setString(6, phonenumber);
            pstm.setString(7, service);
            pstm.setString(8, endtime);


            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,
                        " Appoiment added successfully")
                        .show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,
                        "oops)")
                        .show();
            }

            appoimentidtxt.setText("");
            custid.setValue(null);
            servicecobo.setValue(null);
            addresstxt.setText("");
            phonenumbertxt.setText("");
            sellecttxt.setValue(null);
            endtimetxt.setText("");
            timetxt.setText("");



        } catch (SQLException throwables) {

        }
    }

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        ObservableList<String> options = FXCollections.observableArrayList();

        servicecobo.getItems().addAll("Hair-cutting", "Waxing", "Nail treatments","Facials and skin care","Massages");

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "SELECT *FROM customer " ;
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                String username = resultSet.getString(1);
                options.add(username);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        custid.setItems(options);


        getAll();setCellValueFactory();

    }

    public void searchbtnonaction(ActionEvent event) throws SQLException {
        String search = searchtxt.getText();

        try (Connection con = DriverManager.getConnection(URL , props)){
            String sql = "SELECT * FROM appointment WHERE customer_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, search);

            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()) {
                String id = resultSet.getString(1);
                String time = resultSet.getString(2);
                String date = resultSet.getString(3);
                String cusid = resultSet.getString(4);
                String address = resultSet.getString(5);
                String pnumber = resultSet.getString(6);
                String service = resultSet.getString(7);
                String endtime = resultSet.getString(8);

                appoimentidtxt.setText(id);
                custid.setValue(cusid);
                servicecobo.setValue(service);
                addresstxt.setText(address);
                phonenumbertxt.setText(pnumber);
                sellecttxt.setValue(LocalDate.parse(date));
                endtimetxt.setText(endtime);
                timetxt.setText(time);

            }


        }
    }

    public void updatebtnonaction(ActionEvent event) {
        String appoitmenid = appoimentidtxt.getText();
        String customer_id = String.valueOf(custid.getValue());
        String service = String.valueOf(servicecobo.getValue());
        String address = addresstxt.getText();
        String phonenumber = phonenumbertxt.getText();
        String date = String.valueOf(sellecttxt.getValue());
        String endtime = endtimetxt.getText();
        Time starttime = Time.valueOf(timetxt.getText());

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE appointment SET  time = ?, date = ?, customer_id = ?, address = ?, pnnumber = ?, service = ?, endtime = ? WHERE appointment_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setTime(1, starttime);
            pstm.setDate(2, Date.valueOf(date));
            pstm.setString(3,customer_id);
            pstm.setString(4, address);
            pstm.setString(5, phonenumber);
            pstm.setString(6, service);
            pstm.setTime(7, Time.valueOf(endtime));
            pstm.setString(8, appoitmenid  );


            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,
                        " Appointment updated succsessfully !")
                        .show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,
                        "oops)")
                        .show();
            }

            appoimentidtxt.setText("");
            custid.setValue(null);
            servicecobo.setValue(null);
            addresstxt.setText("");
            phonenumbertxt.setText("");
            sellecttxt.setValue(null);
            endtimetxt.setText("");
            timetxt.setText("");

            searchtxt.setText("");



        } catch (SQLException throwables) {

        }
    }

    public void deletebtnonaction(ActionEvent event) {
        String id = appoimentidtxt.getText();

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "DELETE FROM appointment WHERE  appointment_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully appointment remove !").show();
            }


            appoimentidtxt.setText("");
            custid.setValue(null);
            servicecobo.setValue(null);
            addresstxt.setText("");
            phonenumbertxt.setText("");
            sellecttxt.setValue(null);
            endtimetxt.setText("");
            timetxt.setText("");

            searchtxt.setText("");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        appid.setCellValueFactory(new PropertyValueFactory<>("appointmentid"));
        cusid.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        number.setCellValueFactory(new PropertyValueFactory<>("pnnumber"));

    }

    private void getAll() {
        try {
            ObservableList<AppointmentTM> obList = FXCollections.observableArrayList();
            List<Appoinment> cusList = AppointmentModel.getAll();

            for (Appoinment customer : cusList) {
                obList.add(new AppointmentTM(
                        customer.getAppointmentid(),
                        customer.getTime(),
                        customer.getDate(),
                        customer.getCustomer_id(),
                        customer.getAddress(),
                        customer.getPnnumber(),
                        customer.getService(),
                        customer.getEndtime()

                ));
            }
            tableview.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
}
