package Homeworks.Lab4;

import Homeworks.Lab1.Order;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Lab4SaveToDB {

    public static void saveJsonToDB(List<Order> orders) throws SQLException {

        if (orders.size() != 0) {

            long start = System.currentTimeMillis();

            Gson gson = new Gson();

            String ordersAsJson = gson.toJson(orders);
            Connection connection = DatabaseHandler.getDbConnection();

            PreparedStatement statement = connection.
                    prepareStatement("insert into json_table(json, jsonb) values ( cast( ? as json) , cast( ? as json) )");
            statement.setString(1, ordersAsJson);
            statement.setString(2, ordersAsJson);

            int count = statement.executeUpdate();

            System.out.println(count + " records added!");

            System.out.println("Запись строки: " + (double) (System.currentTimeMillis() - start)/1000 + "сек");
            statement.close();
        }

    }
}
