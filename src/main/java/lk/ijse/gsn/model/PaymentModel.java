package lk.ijse.gsn.model;

import lk.ijse.gsn.db.DBConnection;
import lk.ijse.gsn.dto.Payment;
import lk.ijse.gsn.dto.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {
    public static List<Payment> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM payment";

        List<Payment> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Payment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            ) {
            });
        }

        return data;
    }
}
