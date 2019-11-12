package au.edu.unimelb.cis.swen90007.itsms.database;

import java.sql.*;

/**
 * The DBConnection provides methods for creating SQL statements
 * for the connected database and managing database-level transactions.
 * The DBConnection.java itself is a singleton class
 */
public class DBConnection {

    private static DBConnection instance;
    private Connection connection;
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "xindefengbao12";


    /**
     * Creates a connection to the database at the URL set by the
     * "JDBC_DATABASE_URL" system variable.
     */
    private DBConnection() throws SQLException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());

            //Local Deployment
            this.connection = DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);
            // Heroku Deployment
//            String dbUrl = System.getenv("JDBC_DATABASE_URL");
//            this.connection = DriverManager.getConnection(dbUrl);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the current Connection
     */
    private Connection getDBConnection() {
        return connection;
    }

    /**
     * Create and return the singleton instance of the DBConnection
     */
    public static DBConnection getInstance() throws SQLException {
        if (instance==null) {
            instance = new DBConnection();
        } else if (instance.getDBConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    /**
     * Prepares the provided SQL statement string into a PreparedStatement
     * that can be populated with values and executed by the connected
     * database.
     * @param stm the SQL statement string.
     * @return the PreparedStatement of the provided SQL statement.
     */
    public static PreparedStatement prepare(String stm) {
        PreparedStatement preparedStatement = null;
        try {
            Connection dbConnection = getInstance().getDBConnection();
            preparedStatement = dbConnection.prepareStatement(stm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Prepares the provided SQL statement string into a PreparedStatement
     * that can be populated with values and executed by the connected
     * database, including whether to return new generated keys.
     * @param stm the SQL statement string.
     * @param returnGenKeys flag for if to return generated keys.
     * @return the PreparedStatement of the provided SQL statement.
     */
    public static PreparedStatement prepare(String stm, int returnGenKeys) {
        PreparedStatement preparedStatement = null;
        try {
            Connection dbConnection = getInstance().getDBConnection();
            preparedStatement = dbConnection.prepareStatement(stm, returnGenKeys);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Commits all non-commited transaction statements to the database.
     * @throws SQLException
     */
    public static void commit() throws SQLException {
        instance.getDBConnection().commit();
        System.out.println("Commit block executed");
    }

    /**
     * Rollsback all non-commited transaction statements.
     * @throws SQLException
     */
    public static void rollback() throws SQLException {
        getInstance().getDBConnection().rollback();
    }

}
