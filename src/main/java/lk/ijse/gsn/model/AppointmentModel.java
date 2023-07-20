package lk.ijse.gsn.model;

import lk.ijse.gsn.db.DBConnection;
import lk.ijse.gsn.dto.Appoinment;
import lk.ijse.gsn.dto.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentModel {
    public static List<Appoinment> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM appointment";

        List<Appoinment> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Appoinment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)

            ) {
            });
        }

        return data;
    }
}
