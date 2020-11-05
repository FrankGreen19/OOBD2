package Homeworks.Lab4;

import Homeworks.Lab1.Order;
import Homeworks.Lab1.Product;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Lab4LoadFromDB {

    public static List<Order> loadOrdersFromJsonCol() throws SQLException {

        long start = System.currentTimeMillis();

        Connection connection = DatabaseHandler.getDbConnection();
        String ordersStr = "";

        PreparedStatement statement =
                connection.prepareStatement("select json from json_table");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            ordersStr = resultSet.getString("json");
//            System.out.println(ordersStr);
        }

        statement.close();

        Gson gson = new Gson();

        Order[] orders = gson.fromJson(ordersStr, Order[].class);
//        Arrays.asList(orders).forEach(System.out::println);

        System.out.println("Чтение поля JSON: " + (double) (System.currentTimeMillis() - start)/1000 + "сек");
        return Arrays.asList(orders);
    }

    public static List<Order> loadOrdersFromJsonbCol() throws SQLException {

        long start = System.currentTimeMillis();

        Connection connection = DatabaseHandler.getDbConnection();
        String ordersStr = "";

        PreparedStatement statement =
                connection.prepareStatement("select jsonb from json_table");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            ordersStr = resultSet.getString("jsonb");
//            System.out.println(ordersStr);
        }

        statement.close();

        Gson gson = new Gson();

        Order[] orders = gson.fromJson(ordersStr, Order[].class);
//        Arrays.asList(orders).forEach(System.out::println);

        System.out.println("Чтение поля JSONB: " + (double) (System.currentTimeMillis() - start)/1000 + "сек");
        return Arrays.asList(orders);
    }

}
