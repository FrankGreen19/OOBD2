package Homeworks.Lab8;

import Homeworks.Lab8.domain.Order;
import Homeworks.Lab8.domain.Product;

import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception {
//        EntityManager entityManager = new EntityManagerImpl();
        Product product = new Product("Shirt", "Brand", 1200);
        product.setId(1);
        Product product2 = new Product("Shirt", "Brand", 1400);
        product2.setId(2);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);
        Order order = new Order(2300, new Date(), 1, products);
        order.setId(7);

        EntityManagerFactory entityManagerFactory = new EntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        System.out.println(entityManager.find(Order.class, order).toString());
//        System.out.println(entityManager.merge(order).toString());
        entityManager.persist(order);
//        entityManager.remove(order);

    }
}
