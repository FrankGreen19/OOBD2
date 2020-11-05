package Homeworks.Lab5;

import Homeworks.Lab1.*;
import Homeworks.Lab4.DatabaseHandler;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        Product product = new Product("Свитер", "Quiksilver", 1000);
        Product product2 = new Product("Футболка", "DC_Shoes", 1000);

        List<Product> productList = new ArrayList<>();
        productList.add(product);
//        productList.add(product2);
        Order order = new Order(2000, new Date(), 1, productList);
        Order order2 = new Order(2000, new Date(), 1, productList);
//        Lab5OrderService.insertOrder(order);

        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
//
        Client client = new Client();
        client.setEmail("@gmail.com");
        client.setPassword("1234");
        client.setFirstName("Bob");
        client.setLastName("Black");
        client.setPhoneNumber("223344");
        client.setDiscount(5);
//
////        Lab5ClientService.insertClient(client);
//
////        Lab5ClientService.updateClient(4);

        List<Client> clients = new ArrayList<>();
        clients.add(client);

        Shop shop = new Shop();
        shop.setName("Just Shop");
        shop.setProducts(productList);
        shop.setClients(clients);
        shop.setOrders(orderList);


        System.out.println(shop.getSqlProducts());
        System.out.println(
        );
        System.out.println(shop.getSqlOrders());
        System.out.println();
        System.out.println(shop.getSqlClients());

        Lab5ShopService.insertShop(shop);

    }
}
