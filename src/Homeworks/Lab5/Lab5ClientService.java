package Homeworks.Lab5;

import Homeworks.Lab1.Client;
import Homeworks.Lab1.Order;
import Homeworks.Lab1.Product;
import Homeworks.Lab4.DatabaseHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Lab5ClientService {

    public static void insertClient(Client client) throws SQLException {
        Connection connection = DatabaseHandler.getDbConnection();

        String sql = "insert into shop_client(concrete_client) values (ROW(" + client.getValue() + ',' + client.getDiscount() + "))";
        System.out.println(sql);

        PreparedStatement statement = connection.
                prepareStatement(sql);

        int count = statement.executeUpdate();
        System.out.println(count + " records added!");

        statement.close();
        connection.close();
    }

    public static void updateClient(int clientId) throws SQLException {
        Connection connection = DatabaseHandler.getDbConnection();
        Scanner scanner = new Scanner(System.in);

        PreparedStatement statement = connection.
                prepareStatement("SELECT concrete_client FROM shop_client WHERE client_id = cast(? as integer)");
        statement.setInt(1, clientId);

        ResultSet resultSet = statement.executeQuery();

        Object object = null;
        while (resultSet.next()) {
            object = resultSet.getObject("concrete_client");
        }

        System.out.println(object);

        Client client1 = new Client();
        client1.setValue(object.toString());

        String answer;

        System.out.println("Изменить email?[y/n]");
        answer = scanner.nextLine();

        if (answer.equals("y")) {
            System.out.println("Введите email");
            client1.setEmail(scanner.nextLine());
        }

        System.out.println("Изменить пароль?[y/n]");
        answer = scanner.nextLine();

        if (answer.equals("y")) {
            System.out.println("Введите пароль");
            client1.setPassword(scanner.nextLine());
        }

        System.out.println("Изменить имя?[y/n]");
        answer = scanner.nextLine();

        if (answer.equals("y")) {
            System.out.println("Введите имя");
            client1.setFirstName(scanner.nextLine());
        }

        System.out.println("Изменить фамилию?[y/n]");
        answer = scanner.nextLine();

        if (answer.equals("y")) {
            System.out.println("Введите фамилию");
            client1.setLastName(scanner.nextLine());
        }

        System.out.println("Изменить телефон?[y/n]");
        answer = scanner.nextLine();

        if (answer.equals("y")) {
            System.out.println("Введите телефон");
            client1.setPhoneNumber(scanner.nextLine());
        }

        System.out.println("Изменить скидку?[y/n]");
        answer = scanner.nextLine();

        if (answer.equals("y")) {
            System.out.println("Введите скидку");
            client1.setDiscount(Integer.parseInt(scanner.nextLine()));
        }

        statement = connection.
                prepareStatement("UPDATE shop_client SET concrete_client = (ROW(cast(? as person), " +
                        "cast(? as integer ))) WHERE client_id = cast(? as integer)");
        statement.setString(1, client1.getValue());
        statement.setInt(2, client1.getDiscount());
        statement.setInt(3, clientId);

        System.out.println(statement.executeUpdate() + " запись обновлена!");

        connection.close();
    }
}
