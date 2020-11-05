package Homeworks.Lab2;

import Homeworks.Lab1.Order;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class SaveToDB {

    public static void saveOrdersList(List<Order> orders) throws Exception {

        if (orders != null && orders.size() > 0) {

            Gson gson = new Gson();

            String ordersAsJson = gson.toJson(orders);
            System.out.println(ordersAsJson);
            try (OutputStream os = new FileOutputStream(new File("orders.json"))) {
                    os.write(ordersAsJson.getBytes(StandardCharsets.UTF_8));
                    os.flush();
            }

        } else
            System.out.println("Список пуст!");
    }
}
