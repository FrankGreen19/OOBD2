package Homeworks.Lab2;

import Homeworks.Lab1.Order;
import Homeworks.Lab1.Person;

import java.util.*;

public class OrderService {

    public static Order findOrderByPrice(List<Order> orders, double searchPrice) {
        Order result = null;

        for (Order order : orders) {
            if (order.getOrderPrice() == searchPrice) {
                result = order;
            }
        }

        return result;
    }

    public static List<Order> sortOrdersByPrice(List<Order> orders) {
        orders.sort(new Comparator<Order>() {
            public int compare(Order o1, Order o2) {
                return o1.compareTo(o2);
            }
        });

        return orders;
    }

}
