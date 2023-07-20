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
import lk.ijse.gsn.dto.Stock;
import lk.ijse.gsn.dto.tm.CustomerTM;
import lk.ijse.gsn.dto.tm.StockTM;
import lk.ijse.gsn.model.CustomerModel;
import lk.ijse.gsn.model.StockModel;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class StockFormController implements Initializable {

    private static final String URL = "jdbc:mysql://localhost:3306/gsn";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    public JFXTextField productidtxt;
    public JFXTextField qtytxt;
    public JFXTextField descriptiontxt;
    public JFXButton savebtn;
    public JFXTextField brandtxt;
    public JFXTextField searchtxt;
    public Button searchbtn;
    public JFXButton updatebtn;
    public JFXButton deletebtn;

    public TableView tableview;
    public TableColumn productid;
    public TableColumn qty;
    public TableColumn brand;
    public TableColumn description;

    public void savebtnonaction(ActionEvent event) {

        String product_id = productidtxt.getText();
        int quantity = Integer.parseInt(qtytxt.getText());
        String description = descriptiontxt.getText();
        String brand = brandtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "INSERT INTO product_item_stock(Product_Item_Stock_Id, qty, Description, brand)" +
                    "VALUES(?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, product_id);
            pstm.setInt(2, quantity);
            pstm.setString(3, description);
            pstm.setString(4, brand);


            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.CONFIRMATION,
                        " Stock added successfully")
                        .show();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,
                        "oops)")
                        .show();
            }

            productidtxt.setText("");
            qtytxt.setText("");
            descriptiontxt.setText("");
            brandtxt.setText("");



        } catch (SQLException throwables) {

        }

    }

    public void searchbtnonaction(ActionEvent event) throws SQLException {
        String dearch = searchtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT * FROM product_item_stock WHERE Product_Item_Stock_Id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, dearch);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString(1);
                int qty = resultSet.getInt(2);
                String description = resultSet.getString(3);
                String brand = resultSet.getString(4);


                productidtxt.setText(id);
                qtytxt.setText(String.valueOf(qty));
                descriptiontxt.setText(description);
                brandtxt.setText(brand);

            }else{
                new Alert(Alert.AlertType.WARNING, "Check and enter correct id").show();
            }
        }
    }

    public void updatebtnonation(ActionEvent event) {

        String id = productidtxt.getText();
        int qty = Integer.parseInt(qtytxt.getText());
        String description = descriptiontxt.getText();
        String brand = brandtxt.getText();

        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "UPDATE product_item_stock SET  qty = ?, Description = ?, brand = ? WHERE Product_Item_Stock_Id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, qty);
            pstm.setString(2, description);
            pstm.setString(3, brand);
            pstm.setString(4, id);

            boolean isUpdated = pstm.executeUpdate() > 0;
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "yes! updated!!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        productidtxt.setText("");
        qtytxt.setText("");
        descriptiontxt.setText("");
        brandtxt.setText("");

        searchtxt.setText("");


    }

    public void deletebtnonaction(ActionEvent event) {

        String id = productidtxt.getText();

        try(Connection con = DriverManager.getConnection(URL, props)){
            String sql = "DELETE FROM product_item_stock WHERE  Product_Item_Stock_Id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, id);

            if (pstm.executeUpdate() > 0){
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully employee remove !").show();
            }

            productidtxt.setText("");
            qtytxt.setText("");
            descriptiontxt.setText("");
            brandtxt.setText("");

            searchtxt.setText("");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void setCellValueFactory() {
        productid.setCellValueFactory(new PropertyValueFactory<>("Product_Item_Stock"));
        qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        brand.setCellValueFactory(new PropertyValueFactory<>("Description"));
        description.setCellValueFactory(new PropertyValueFactory<>("brand"));

    }

    private void getAll() {
        try {
            ObservableList<StockTM> obList = FXCollections.observableArrayList();
            List<Stock> cusList = StockModel.getAll();

            for (Stock customer : cusList) {
                obList.add(new StockTM(
                        customer.getProduct_Item_Stock(),
                        customer.getQty(),
                        customer.getBrand(),
                        customer.getDescription()

                ));
            }
            tableview.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getAll();setCellValueFactory();
    }
}

