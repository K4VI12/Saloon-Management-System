package lk.ijse.gsn.model;

import lk.ijse.gsn.db.DBConnection;
import lk.ijse.gsn.dto.Service;
import lk.ijse.gsn.dto.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceModel {
    public static List<Service> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM service";

        List<Service> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Service(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)

            ) {
            });
        }

        return data;
    }
}
