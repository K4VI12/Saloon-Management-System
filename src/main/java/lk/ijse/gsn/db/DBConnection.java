package lk.ijse.gsn.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {


        private static DBConnection dbConnection;
        private Connection con;

        private DBConnection() throws SQLException {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/gsn",
                    "root",
                    "1234"
            );
        }

        public static DBConnection getInstance() throws SQLException {
            return (null == dbConnection) ? dbConnection = new DBConnection()
                    : dbConnection;
        }

        public Connection getConnection() {
            return con;
}
    }

