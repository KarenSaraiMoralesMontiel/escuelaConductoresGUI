package gov.peru.escuela.conducir.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static gov.peru.escuela.conducir.util.DbCredentials.*;

public class DbConnection {
    public Connection getConnection() throws SQLException {
        try {
            Connection cn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return cn;
        } catch (SQLException e) {
            throw e;
        }
    }
}
