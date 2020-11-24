package Homeworks.Lab8;

import Homeworks.Lab4.DatabaseHandler;
import Homeworks.Lab6.Lab6Main2;
import Homeworks.Lab6.graph.EntityNode;
import Homeworks.Lab8.domain.BaseEntity;
import Homeworks.Lab8.domain.Order;
import Homeworks.Lab8.domain.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.beans.Statement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EntityManagerImpl implements EntityManager {

    private List<BaseEntity> entityList = new ArrayList<>();

    @Override
    public void persist(Object var1) throws Exception {
        // 1. Проверяем наличие аннотации @Entity
        Annotation entity = var1.getClass().getAnnotation(Entity.class);
        // Если аннотации @Entity нет, то прекращаем работу, генерируя исключение
        if (entity == null)
            throw new Exception("Class " + var1.getClass().getCanonicalName() + " is not Entity!");
        entityList.add((BaseEntity) var1);

        // 1. Получаем имя таблицы
        String tableName = var1.getClass().getSimpleName().toLowerCase();

        // Получаем список членов класса
        HashMap<String, String> fieldsAndValues = EntityManagerImpl.getFieldsValues(var1);

        StringBuilder sql = new StringBuilder("INSERT INTO \"" + tableName + "\"(");
        Iterator<Map.Entry<String, String>> entries = fieldsAndValues.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            sql.append(entry.getKey());
            if (entries.hasNext()) {
                sql.append(", ");
            }
        }
        sql.append(") VALUES(");
        entries = fieldsAndValues.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            sql.append("'").append(entry.getValue()).append("'");
            if (entries.hasNext()) {
                sql.append(", ");
            }
        }
        sql.append(") RETURNING id");

        System.out.println(sql);

        Connection connection = DatabaseHandler.getDbConnection();
        PreparedStatement statement = connection.prepareStatement(String.valueOf(sql));
        ResultSet resultSet = statement.executeQuery();

        int id = 0;
        while (resultSet.next()) {
            id = resultSet.getInt("id");
        }

        Field[] fields = var1.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType().equals(OneToMany.class)) {
                    String[] fieldToString = field.getGenericType().toString().split("\\.");
                    String className = fieldToString[5].substring(0, fieldToString[5].length() - 1).toLowerCase();

                    String middleTableName = tableName + "_" + className;

                    Method method = var1.getClass().getMethod(
                            "get"+field.getName().substring(0,1).toUpperCase()+
                                    field.getName().substring(1),null);

                    List<BaseEntity> list = (List<BaseEntity>) method.invoke(var1);

                    String query = "INSERT INTO " + middleTableName + "(" + tableName + "id, " +
                             className + "id) VALUES (?, ?)";

                    System.out.println(query);

                    for (BaseEntity item : list) {
                        statement = connection.prepareStatement(query);
                        statement.setInt(1, id);
                        statement.setLong(2, item.getId());
                        statement.execute();
                    }

                }
            }
        }
        connection.close();
    }


    @Override
    public BaseEntity merge(BaseEntity var1) throws Exception {
        Connection connection = DatabaseHandler.getDbConnection();

        Annotation entity = var1.getClass().getAnnotation(Entity.class);
        // Если аннотации @Entity нет, то прекращаем работу, генерируя исключение
        if (entity == null)
            throw new Exception("Class " + var1.getClass().getCanonicalName() + " is not Entity!");
        entityList.add((BaseEntity) var1);

        // 1. Получаем имя таблицы
        String tableName = var1.getClass().getSimpleName().toLowerCase();

        HashMap<String, String> fieldsAndValues = EntityManagerImpl.getFieldsValues(var1);

        StringBuilder sql = new StringBuilder("UPDATE \"" + tableName + "\" SET ");
        Iterator<Map.Entry<String, String>> entries = fieldsAndValues.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            sql.append(entry.getKey()).append(" = '").append(entry.getValue()).append("'");
            if (entries.hasNext()) {
                sql.append(", ");
            }
        }
        sql.append(" WHERE id = ").append(var1.getId()).append(" RETURNING ");

        entries = fieldsAndValues.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            sql.append(entry.getKey());
            if (entries.hasNext()) {
                sql.append(", ");
            }
        }

        System.out.println(sql);

        PreparedStatement statement = connection.prepareStatement(String.valueOf(sql), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery();

        connection.close();
        return (BaseEntity) EntityManagerImpl.getInstance(resultSet, var1);
    }

    @Override
    public void remove(BaseEntity var1) throws Exception {
        Connection connection = DatabaseHandler.getDbConnection();

        Annotation entity = var1.getClass().getAnnotation(Entity.class);
        // Если аннотации @Entity нет, то прекращаем работу, генерируя исключение
        if (entity == null)
            throw new Exception("Class " + var1.getClass().getCanonicalName() + " is not Entity!");

        entityList.add((BaseEntity) var1);
        // 1. Получаем имя таблицы
        String tableName = var1.getClass().getSimpleName().toLowerCase();

        // Получаем список членов класса
        Field[] fields = var1.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType().equals(OneToMany.class)) {
                    String[] fieldToString = field.getGenericType().toString().split("\\.");
                    String className = fieldToString[5].substring(0, fieldToString[5].length() - 1).toLowerCase();

                    String middleTableName = tableName + "_" + className;

                    Method method = var1.getClass().getMethod(
                            "get"+field.getName().substring(0,1).toUpperCase()+
                                    field.getName().substring(1),null);

                    List<BaseEntity> list = (List<BaseEntity>) method.invoke(var1);

                    String query = "DELETE FROM " + middleTableName + " WHERE " + tableName + "id = ?";

                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setLong(1, var1.getId());
                    statement.execute();
                }
            }
        }

        String sql = "DELETE FROM \"" + tableName + "\" WHERE id = ? RETURNING id";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, var1.getId());
        statement.execute();

        connection.close();
    }

    @Override
    public <T> T find(Class<T> var1, BaseEntity var2) throws Exception {
        Connection connection = DatabaseHandler.getDbConnection();

        Annotation entity = var2.getClass().getAnnotation(Entity.class);
        // Если аннотации @Entity нет, то прекращаем работу, генерируя исключение
        if (entity == null)
            throw new Exception("Class " + var2.getClass().getCanonicalName() + " is not Entity!");
        entityList.add(var2);
        // 1. Получаем имя таблицы
        String tableName = var2.getClass().getSimpleName().toLowerCase();

        String sql = "SELECT * FROM \"" + tableName + "\" WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        statement.setLong(1, var2.getId());
        ResultSet resultSet = statement.executeQuery();

        connection.close();

        return (T) EntityManagerImpl.getInstance(resultSet, var2);
    }

    @Override
    public void refresh(Object var1) {

    }

    private static Object getInstance(ResultSet resultSet, Object var1) {
        Field[] fields = var1.getClass().getDeclaredFields();

        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType().equals(Column.class) && !a.annotationType().equals(Id.class)
                        && !a.annotationType().equals(OneToMany.class)) {
                    try {

                        Method method = var1.getClass().getMethod(
                                "set" + field.getName().substring(0, 1).toUpperCase() +
                                        field.getName().substring(1), field.getType());

                        Object value = null;

                        while (resultSet.next()) {
                            if (field.getType().getSimpleName().toLowerCase().equals("long"))
                                value = Long.parseLong(String.valueOf(resultSet.getObject(field.getName().toLowerCase())));
                            else
                                value = resultSet.getObject(field.getName().toLowerCase());
                        }
                        resultSet.beforeFirst();

                        method.invoke(var1, value);

                    } catch (NoSuchMethodException | SQLException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return var1;
    }

    private static HashMap<String, String> getFieldsValues(Object var1) {
        Field[] fields = var1.getClass().getDeclaredFields();

        HashMap<String, String> fieldsAndValues = new HashMap<>();

        for (Field field : fields) {
            String fieldKey = "";
            String fieldValue = "";

            Annotation[] annotations = field.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType().equals(Column.class) && !a.annotationType().equals(Id.class)
                        && !a.annotationType().equals(OneToMany.class)) {
                    try {
                        String[] parts = field.toString().split("\\.");

                        fieldKey = parts[parts.length - 1].toLowerCase();

                        Method method = var1.getClass().getMethod(
                                "get" + field.getName().substring(0, 1).toUpperCase() +
                                        field.getName().substring(1), null);

                        fieldValue = String.valueOf(method.invoke(var1));

                        fieldsAndValues.put(fieldKey, fieldValue);

                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return fieldsAndValues;
    }
}
