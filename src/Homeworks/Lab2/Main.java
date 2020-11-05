package Homeworks.Lab2;

import Homeworks.Lab1.Order;
import Homeworks.Lab1.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception {

        Order order1 = new Order(1200, new Date(), 0, new ArrayList<Product>(){});
        Order order2 = new Order(2200, new Date(), 1, new ArrayList<Product>(){});
        Order order3 = new Order(800, new Date(), 2, new ArrayList<Product>(){});

        ArrayList<Order> orderList = new ArrayList<>();
        orderList.add(order1);
        orderList.add(order2);
        orderList.add(order3);

        SaveToDB.saveOrdersList(orderList);

        List<Order> orderList2 = LoadDB.loadOrdersList();

        orderList2.forEach(System.out::println);

        System.out.println("\n*************** Get order by price *****************");

        Order order = OrderService.findOrderByPrice(orderList2, 1200);
        System.out.println(order);

        System.out.println("\n********** Sorted array **********\n");
        OrderService.sortOrdersByPrice(orderList2).forEach(System.out::println);

    }
}
