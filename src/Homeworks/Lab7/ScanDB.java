package Homeworks.Lab7;

import Homeworks.Lab4.DatabaseHandler;
import Homeworks.Lab6.Lab6Main2;
import Homeworks.Lab6.annotation.Entity;
import Homeworks.Lab6.graph.EntityAttribute;
import Homeworks.Lab6.graph.EntityNode;
import Homeworks.Lab6.graph.GraphModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ScanDB {

    private static Connection connection;

    public static void main(String[] args) {
        // Структура для хранения имен таблиц и полей (в HashSet)
        HashMap<String, HashSet<String>> tables = new HashMap<>();
        GraphModel graphModel = Lab6Main2.getGraph();

        try(Connection connection = getConnection()) {

            System.out.println("***** База данных *****");
            List<String> tbls = getTables(connection);
            System.out.println("\nКоличество таблиц: " + tbls.size());
            System.out.println("\nСписок таблиц:\n");
            tbls.forEach(System.out::println);
            System.out.println();
            for (String table : tbls) {
                System.out.println("Список полей таблицы "+table+":");
                List<String> fields = getFields(connection, table);

                HashSet<String> hashSetFields = new HashSet<>();
                fields.forEach(f->{
                    System.out.println(f);
                    hashSetFields.add(f);
                });

                tables.put(table, hashSetFields);
            }

            System.out.println("\n***** Сущности *****");
            System.out.println("\nКоличество сущностей: " + graphModel.getEntityNodeList().size());
            System.out.println("\nСписок сущностей:\n");
            for (EntityNode entityNode : graphModel.getEntityNodeList()) {
                System.out.println(entityNode.getEntityName());
            }
            for (EntityNode entityNode : graphModel.getEntityNodeList()) {
                System.out.println("Поля сущности: " + entityNode.getEntityName() + "\n");
                for (EntityAttribute entityAttribute : entityNode.getAtributes()) {
                    System.out.println(entityAttribute.getAttributeName());
                }
                System.out.println();
            }


            for (String table : tbls) {
                for (EntityNode entityNode : graphModel.getEntityNodeList()) {
                    String[] fieldToString = entityNode.getEntityName().split("\\.");
                    String className = fieldToString[2].toLowerCase();

                    if (table.equals(className)) {
                        System.out.println(table + " -> " + className);
                    }
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        return DatabaseHandler.getDbConnection();
    }

    public static List<String> getTables(Connection connection) throws SQLException {

        List<String> lst = new ArrayList<>();

        PreparedStatement st = connection.prepareStatement(
                "SELECT table_name FROM information_schema.tables " +
                        "WHERE table_type = 'BASE TABLE' AND" +
                        " table_schema NOT IN ('pg_catalog', 'information_schema')");

        ResultSet resultSet = st.executeQuery();

        while (resultSet.next()) {
            String s = resultSet.getString("table_name");
            lst.add(s);
        }

        st.close();
        return lst;
    }

    public static List<String> getFields(Connection connection, String tableName) throws SQLException {

        List<String> lst = new ArrayList<>();

        PreparedStatement st = connection.prepareStatement(
                "SELECT a.attname " +
                        "FROM pg_catalog.pg_attribute a " +
                        "WHERE a.attrelid = (SELECT c.oid FROM pg_catalog.pg_class c " +
                        "LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace " +
                        " WHERE pg_catalog.pg_table_is_visible(c.oid) AND c.relname = ? )" +
                        " AND a.attnum > 0 AND NOT a.attisdropped");

        st.setString(1, tableName);
        ResultSet resultSet = st.executeQuery();

        while (resultSet.next()) {
            String s = resultSet.getString("attname");
            lst.add(s);
        }

        st.close();
        return lst;
    }
}
