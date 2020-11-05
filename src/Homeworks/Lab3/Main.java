package Homeworks.Lab3;

import Homeworks.Lab1.Product;
import Homeworks.Lab1.Shop;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JAXBException {

        Shop shop = new Shop();

        Product product1 = new Product("Cake", "SomeBrand", 500);
        Product product2 = new Product("Car", "SomeBrand", 500000);
        Product product3 = new Product("Ball", "SomeBrand", 600);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        shop.setProducts(products);

        XMLService.saveProductData(shop);

        Shop shop1 = XMLService.loadShopFromXML();
        assert shop1 != null;
        shop1.showProducts();

        System.out.println("\n********** Searching product by price **********\n");
        XMLService.searchByPrice(shop1, 500);

        System.out.println("\n********** Sorting product by price **********\n");
        XMLService.sortByPrice(shop1);
    }
}
