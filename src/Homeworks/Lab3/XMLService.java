package Homeworks.Lab3;

import Homeworks.Lab1.Order;
import Homeworks.Lab1.Product;
import Homeworks.Lab1.Shop;
import Lessons.Lab3.model.Bank;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;

public class XMLService {

    public static void saveProductData(Shop shop) {

        try {
            StringWriter writer = new StringWriter();

            //создание объекта Marshaller, который выполняет сериализацию
            JAXBContext context = JAXBContext.newInstance(Shop.class, Product.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // сама сериализация
            marshaller.marshal(shop, writer);

            //преобразовываем в строку все записанное в StringWriter
            String result = writer.toString();

            try (OutputStream os = new FileOutputStream(new File("shop.xml"))) {
                os.write(result.getBytes(StandardCharsets.UTF_8));
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static Shop loadShopFromXML() {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Shop.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (Shop) un.unmarshal(new File("shop.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void searchByPrice(Shop shop, double price) {
        for (Product product: shop.getProducts()) {
            if (product.getProductPrice() == price)
                System.out.println(product.toString());
        }
    }

    public static void sortByPrice(Shop shop) {
        shop.getProducts().sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.compareTo(o2);
            }
        });

        shop.getProducts().forEach(System.out::println);
    }
}
