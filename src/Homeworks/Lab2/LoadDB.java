package Homeworks.Lab2;

import Homeworks.Lab1.Order;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class LoadDB {

    public static List<Order> loadOrdersList() throws IOException, JsonSyntaxException {

        String ordersStr = "";
        File file = new File("orders.json");

        if (file.exists()) {
            ordersStr = new String(Files.readAllBytes(file.toPath()));
        } else {
            System.out.println("File orders.json not found!");
        }

        Gson gson = new Gson();

        Order[] ordersList = gson.fromJson(ordersStr, Order[].class);

        return Arrays.asList(ordersList);
    }
}
