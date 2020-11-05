package Homeworks.Lab4;

import Homeworks.Lab1.Order;
import Homeworks.Lab1.Product;
import Homeworks.Lab2.OrderService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        Lab4LoadFromDB.loadOrdersFromJsonCol();
        Lab4LoadFromDB.loadOrdersFromJsonbCol();


//        List<Order> orderList = Lab4LoadFromDB.loadOrdersFromJsonCol();
//        OrderService.findOrderByPrice(orderList, 500);
//        OrderService.sortOrdersByPrice(orderList);

    }

    public static void insertValuesIntoDb() throws SQLException {
        Order order1 = new Order(1200, new Date(), 0, new ArrayList<Product>(){});
        Order order2 = new Order(2200, new Date(), 1, new ArrayList<Product>(){});
        Order order3 = new Order(800, new Date(), 2, new ArrayList<Product>(){});

        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);

        Lab4SaveToDB.saveJsonToDB(orderList);
    }
}
