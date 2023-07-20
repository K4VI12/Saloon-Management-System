package lk.ijse.gsn.model;

import lk.ijse.gsn.db.DBConnection;
import lk.ijse.gsn.dto.Stock;
import lk.ijse.gsn.dto.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockModel {
    public static List<Stock> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM product_item_stock";

        List<Stock> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Stock(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            ) {
            });
        }

        return data;
    }
}
