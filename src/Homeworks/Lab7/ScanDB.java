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
import java.util.*;

public class ScanDB {

    private static Connection connection;

    public static void main(String[] args) {
        // Структура для хранения имен таблиц и полей (в HashSet)
        HashMap<String, HashSet<String>> tables = new HashMap<>();
        GraphModel graphModel = Lab6Main2.getGraph();

        try(Connection connection = DatabaseHandler.getDbConnection()) {

            List<String> tbls = getTables(connection);

            for (String table : tbls) {
                List<String> fields = getFields(connection, table);

                HashSet<String> hashSetFields = new HashSet<>();
                fields.forEach(f->{
                    hashSetFields.add(f);
                });

                tables.put(table, hashSetFields);
            }

            for (Map.Entry<String, HashSet<String>> item : tables.entrySet()) {

                for (EntityNode entityNode : graphModel.getEntityNodeList()) {

                    String[] fieldToString = entityNode.getEntityName().split("\\.");
                    String className = fieldToString[2].toLowerCase();

                    if (item.getKey().equals(className)) {
                        System.out.println("\nТаблица " + item.getKey() + " -> Класс " + className + "\n");

                        int classFieldNum = entityNode.getAtributes().size();
                        int counter = 0;

                        for (EntityAttribute field : entityNode.getAtributes()) {
                            if (item.getValue().contains(field.getAttributeName().toLowerCase())) {
                                counter++;
                                System.out.println(field.getAttributeName() + " ✓");
                            } else
                                System.out.println(field.getAttributeName() + " ✗");
                        }

                        if (counter != classFieldNum) {
                            System.out.println("\nПроцент совпадений: " + (100 * counter)/classFieldNum + "%");
                        } else
                            System.out.println("\nПроцент совпадений: 100%");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
