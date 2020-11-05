package Homeworks.Lab5;

import Homeworks.Lab1.Order;
import Homeworks.Lab1.Product;
import Homeworks.Lab4.DatabaseHandler;

import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;

public class Lab5OrderService {

    public static void insertOrder(Order order) throws SQLException {
        Connection connection = DatabaseHandler.getDbConnection();

        PreparedStatement statement = connection.
                prepareStatement("insert into shop_order(concrete_order) values " +
                        "(ROW(cast(? as double precision ), cast(? as date ), cast(? as integer ), cast(? as product[])))");
        statement.setDouble(1, order.getOrderPrice());
        statement.setString(2, String.valueOf(order.getOrderDate()));
        statement.setInt(3, order.getClientID());

        StringBuilder s= new StringBuilder("{\"");
        for (Product product: order.getProductList()) {
            s.append(product.getValue()).append("\",\"");
        }
        s = new StringBuilder(s.substring(0, s.length() - 2));
        s.append("}");

        statement.setString(4, s.toString());

        int count = statement.executeUpdate();
        System.out.println(count + " records added!");

        statement.close();
        connection.close();
    }

//    public static void updateOrder(int orderId) throws SQLException, ParseException {
//        Connection connection = DatabaseHandler.getDbConnection();
//
//        PreparedStatement statement = connection.
//                prepareStatement("SELECT concrete_order FROM shop_order WHERE order_id=cast(? as bigint)");
//        statement.setInt(1, orderId);
//
//        ResultSet resultSet = statement.executeQuery();
//
//        Object object = null;
//        while (resultSet.next()) {
//            object = resultSet.getObject("concrete_order");
//        }
//
//        Order order = new Order();
//        order.setValue(object.toString());
//        System.out.println(order.toString());
//
//        System.out.println("Изменить стоимость заказа?[y/n]");
//
//        Scanner scanner = new Scanner(System.in);
//        String answer = scanner.nextLine();
//
//        if (answer.equals("y")) {
//            System.out.println("Введите стоимость заказа:\n");
//            order.setOrderPrice(scanner.nextDouble());
//        }
//
//        statement = connection.prepareStatement("UPDATE shop_order SET concrete_order = cast()")
//
//
//    }

}
