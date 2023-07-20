package lk.ijse.gsn.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.gsn.dto.Payment;
import lk.ijse.gsn.dto.tm.CustomerTM;
import lk.ijse.gsn.dto.tm.PaymentTM;
import lk.ijse.gsn.model.CustomerModel;
import lk.ijse.gsn.model.PaymentModel;

import java.net.URL;
import java.sql.Time;
import java.sql.Date;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {

    private static final String URL = "jdbc:mysql://localhost:3306/gsn";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXTextField paymenttxt;
    public JFXTextField appoimenttxt;
    public JFXTextField pricetxt;
    public JFXTextField datetxt;
    public JFXButton savebtn;
    public JFXTextField timetxt;
    public JFXComboBox apoiid;
    public JFXButton deletebtn;
    public JFXButton update;
    public JFXTextField searchtxt;
    public Button searchbtn;

    public TableView tableview;
    public TableColumn paymentid;
    public TableColumn appid;
    public TableColumn price;
    public TableColumn date;
    public TableColumn time;

    public void savebtnonaction(ActionEvent event) {

        System.out.println("first");

        String payment_id = paymenttxt.getText();
        String apoi = String.valueOf(apoiid.getValue());
        double price = Double.parseDouble(pricetxt.getText());



        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "INSERT INTO payment(payment_id, appointment_id, price, time, Date)" +
                    "VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, payment_id );
            pstm.setString(2, apoi);
            pstm.setDouble(3, price);
            pstm.setTime(4, new Time(System.currentTimeMillis()));
            pstm.setDate(5, new Date(System.currentTimeMillis()));

            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,
                        " Payement added successfully")
                        .show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,
                        "oops)")
                        .show();
            }

            paymenttxt.setText("");
            pricetxt.setText("");
            datetxt.setText("");
            timetxt.setText("");
            apoiid.setValue(null);


        } catch (SQLException throwables) {

        }

    }

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {

        ObservableList<String> options = FXCollections.observableArrayList();

        try (Connection con = DriverManager.getConnection(URL, props)){
            String sql = "SELECT *FROM appointment " ;
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                options.add(id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        apoiid.setItems(options);

        getAll(); setCellValueFactory();
    }

    public void deletebtnonaction(ActionEvent event) throws SQLException {

        String id = paymenttxt.getText();

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "DELETE FROM payment WHERE  payment_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully customer remove !").show();
            }
            paymenttxt.setText("");
            pricetxt.setText("");
            datetxt.setText("");
            timetxt.setText("");
            apoiid.setValue(null);

            searchtxt.setText("");

        }

    }

    public void updatebtn(ActionEvent event) {

        String apoi = String.valueOf(apoiid.getValue());
        double price = Double.parseDouble(pricetxt.getText());
        String payment_id = paymenttxt.getText();
        String date = datetxt.getText();
        Time starttime = Time.valueOf(timetxt.getText());

        try (Connection con = DriverManager.getConnection(URL,props)){
            String sql = "UPDATE payment SET appointment_id = ?, price = ?, time = ?, Date = ? WHERE payment_id  = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, apoi);
            pstm.setDouble(2, price);
            pstm.setTime(3, starttime);
            pstm.setDate(4, Date.valueOf(date));
            pstm.setString(5, payment_id);

            if(pstm.executeUpdate()>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer update successfully").show();
            }

            paymenttxt.setText("");
            pricetxt.setText("");
            datetxt.setText("");
            timetxt.setText("");
            apoiid.setValue(null);

            searchtxt.setText("");
            savebtn.setDisable(false);

        } catch (SQLException throwables) {

        }

    }

    public void searchbtnonaction(ActionEvent event) throws SQLException {
        String dearch = searchtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT * FROM payment WHERE payment_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, dearch);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String paymentid = resultSet.getString(1);
                String apoid = resultSet.getString(2);
                String price = resultSet.getString(3);
                String time = resultSet.getString(4);
                String date = resultSet.getString(5);


                paymenttxt.setText(paymentid);
                pricetxt.setText(price);
                datetxt.setText(date);
                timetxt.setText(time);
                apoiid.setValue(null);

            }else{
                new Alert(Alert.AlertType.WARNING, "Check and enter correct id").show();
            }
        }
    }

    private void setCellValueFactory() {
        paymentid.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        appid.setCellValueFactory(new PropertyValueFactory<>("appointment_id"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        date.setCellValueFactory(new PropertyValueFactory<>("time"));
        time.setCellValueFactory(new PropertyValueFactory<>("Date"));
    }

    private void getAll() {
        try {
            ObservableList<PaymentTM> obList = FXCollections.observableArrayList();
            List<Payment> cusList = PaymentModel.getAll();

            for (Payment customer : cusList) {
                obList.add(new PaymentTM(
                        customer.getPayment_id(),
                        customer.getAppointment_id(),
                        customer.getPrice(),
                        customer.getTime(),
                        customer.getDate()
                ));
            }
            tableview.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
}
