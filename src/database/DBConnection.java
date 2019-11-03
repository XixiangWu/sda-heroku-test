package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {

    private static String DB_CONNECTION = "jdbc:postgresql://localhost:5432/postgres";

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "xindefengbao12";

    static Connection dbConnection = null;

    public static PreparedStatement prepare(String stm) throws SQLException {

        PreparedStatement preparedStatement = null;

        try {
            Connection dbConnection = getDbConnection();
            preparedStatement = dbConnection.prepareStatement(stm);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return preparedStatement;
    }

    private static Connection getDbConnection() {

        try {
            // Register JDBC driver
            DriverManager.registerDriver(new org.postgresql.Driver());

            dbConnection = DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);

            dbConnection.setAutoCommit(false);

            return dbConnection;
        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
        System.out.println("Connection Problem");
        return null;
    }

}
