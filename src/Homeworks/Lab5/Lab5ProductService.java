package Homeworks.Lab5;

import Homeworks.Lab1.Product;
import Homeworks.Lab4.DatabaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lab5ProductService {

    public static void insertProduct(Product product) throws SQLException {
        Connection connection = DatabaseHandler.getDbConnection();

        PreparedStatement statement = connection.
                prepareStatement("insert into shop_product(concrete_product) values " +
                        "(ROW(cast(? as character varying), cast(? as character varying), cast(? as double precision)))");
        statement.setString(1, product.getProductType());
        statement.setString(2, product.getProductBrand());
        statement.setDouble(3, product.getProductPrice());

        int count = statement.executeUpdate();
        System.out.println(count + " records added!");

        statement.close();
        connection.close();
    }

    public static void updateProduct(int productId) throws SQLException {
        Connection connection = DatabaseHandler.getDbConnection();

        PreparedStatement statement = connection.
                prepareStatement("SELECT concrete_product FROM shop_product WHERE product_id=cast(? as bigint)");
        statement.setInt(1, productId);

        ResultSet resultSet = statement.executeQuery();

        Object object = null;
        while (resultSet.next()) {
            object = resultSet.getObject("concrete_product");
        }

        Product product = new Product();
        product.setValue(object.toString());
        System.out.println(product.toString());

        String answer;
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nИзменить тип продукта?[y/n]");
        answer = scanner.nextLine();
        if (answer.equals("y")) {
            System.out.println("Введите тип:");
            product.setProductType(scanner.nextLine());
        }

        System.out.println("\nИзменить брэнд продукта?[y/n]");
        answer = scanner.nextLine();
        if (answer.equals("y")) {
            System.out.println("Введите брэнд:");
            product.setProductBrand(scanner.nextLine());
        }

        System.out.println("\nИзменить цену продукта?[y/n]");
        answer = scanner.nextLine();
        if (answer.equals("y")) {
            System.out.println("Введите цену:");
            product.setProductPrice(scanner.nextDouble());
        }

        System.out.println(product.toString());
        System.out.println(product.getValue());
        statement = connection.prepareStatement("UPDATE shop_product SET concrete_product = cast(? as product)" +
                "WHERE product_id = cast(? as bigint)");
        statement.setString(1, product.getValue());
        statement.setInt(2, productId);

        int count = statement.executeUpdate();
        System.out.println(count + " records added!");

        statement.close();
        connection.close();
    }

    public static void getProductsByPrice(double price) throws SQLException {
        Connection connection = DatabaseHandler.getDbConnection();

        PreparedStatement statement = connection.
                prepareStatement("SELECT concrete_product FROM shop_product");

        ResultSet resultSet = statement.executeQuery();

        List<Product> productList = new ArrayList<>();
        while (resultSet.next()) {
            Object object = resultSet.getObject("concrete_product");
            Product product = new Product();
            product.setValue(object.toString());
            if (product.getProductPrice() == price) {
                productList.add(product);
            }
        }

        for (Product product: productList) {
            System.out.println(product.toString());
        }

        statement.close();
        connection.close();
    }

}
