package lk.ijse.gsn.dao.custom.impl.util;

import lk.ijse.gsn.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlUtil {
    public static <T>T execute(String sql, Object... args) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            pstm.setObject((i + 1), args[i]);
        }

        if (sql.startsWith("SELECT") || sql.startsWith("select")) {
            return (T) pstm.executeQuery();
        } else {
            return (T) new Boolean(pstm.executeUpdate() > 0);
        }
    }
}
