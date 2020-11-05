package Homeworks.Lab5;

import Homeworks.Lab1.Shop;
import Homeworks.Lab4.DatabaseHandler;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Lab5ShopService {

    public static void insertShop(Shop shop) throws SQLException {
        Connection connection = DatabaseHandler.getDbConnection();

        PreparedStatement statement = connection.
                prepareStatement("INSERT INTO shop_shop(concrete_shop) VALUES " +
                        "(ROW(cast(? as text), cast(? as order_type[]), cast(? as product[])," +
                        "cast(? as client[])))");
        statement.setString(1, shop.getName());
        statement.setString(2, shop.getSqlOrders());
        statement.setString(3, shop.getSqlProducts());
        statement.setString(4, shop.getSqlClients());

        System.out.println(statement.executeUpdate());
    }
}
