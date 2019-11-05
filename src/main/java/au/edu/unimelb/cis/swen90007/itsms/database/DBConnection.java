package au.edu.unimelb.cis.swen90007.itsms.database;

import java.sql.*;

/**
 * The DBConnection provides methods for creating SQL statements
 * for the connected database and managing database-level transactions.
 */
public class DBConnection {

    private static DBConnection instance;
    private Connection connection;
    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "xindefengbao12";

    private DBConnection() throws SQLException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());

            //Local Deployment
//            this.connection = DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);
            // Heroku Deployment
            String dbUrl = System.getenv("JDBC_DATABASE_URL");
            this.connection = DriverManager.getConnection(dbUrl);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getDBConnection() {
        return connection;
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance==null) {
            instance = new DBConnection();
        } else if (instance.getDBConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    /**
     * Creates and returns a connection to the database at the URL set by the
     * "JDBC_DATABASE_URL" system variable.
     * @return Connection to the PostgreSQL Database
     */
//    private static Connection getDBConnection() {
//        // Local Version
//        try {
//            DriverManager.registerDriver(new org.postgresql.Driver());
//            Connection dbConnection = DriverManager.getConnection(DB_CONNECTION,DB_USER,DB_PASSWORD);
//            dbConnection.setAutoCommit(false);
//
//            dbConnection.prepareStatement("INSERT INTO APP.issues (reporter_id, title, status, description, stickied, time_submitted)\n" +
//                    "VALUES (4, 'dbConnection', 'open', 'My laptop has frozen and doesn''t work anymore', false, '2019-09-10 12:42:10');").executeUpdate();
//
//            return dbConnection;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//         Deploy Version
//        try {
//            DriverManager.registerDriver(new org.postgresql.Driver());
//            String dbUrl = System.getenv("JDBC_DATABASE_URL");
//            Connection dbConnection = DriverManager.getConnection(dbUrl);
//            dbConnection.setAutoCommit(false);
//            return dbConnection;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Problem connecting to DB");
//        return null;
//    }

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
//        getDBConnection().prepareStatement("INSERT INTO APP.issues (reporter_id, title, status, description, stickied, time_submitted)\n" +
//                "VALUES (4, 'Commit', 'open', 'My laptop has frozen and doesn''t work anymore', false, '2019-09-10 12:42:10');").executeUpdate();
//        getDBConnection().commit();
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
