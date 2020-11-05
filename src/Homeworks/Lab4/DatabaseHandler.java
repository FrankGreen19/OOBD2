package Homeworks.Lab4;

import java.sql.*;

public class DatabaseHandler {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/oodb";
    static final String USER = "postgres";
    static final String PASS = "root";

    public static Connection getDbConnection() {

        Connection dbConnection = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC not found:(");
            e.printStackTrace();
        }

        try {

            dbConnection = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("DB not found(((");
            System.out.println(e.getMessage());
        }

        return dbConnection;
    }

//    public static void getTablesList() {
//        ResultSet rs = null;
//        try {
//            Statement statement = getDbConnection().createStatement();
//            rs = statement.executeQuery("select * from users");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }


}


