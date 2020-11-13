package Homeworks.Lab8;

import Homeworks.Lab1.Order;
import Homeworks.Lab1.Product;
import Homeworks.Lab4.DatabaseHandler;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

public class EntityManagerImpl implements EntityManager {

    @Override
    public void persist(Object var1) throws Exception {
        // Задача этого метода сформировать и выполнить запрос
        // INSERT INTO ...
        // Для этого мы должны получить имя таблицы, имена полей и их значения

        // 1. Проверяем наличие аннотации @Entity
        Annotation entity = var1.getClass().getAnnotation(Entity.class);
        // Если аннотации @Entity нет, то прекращаем работу, генерируя исключение
        if (entity == null)
            throw new Exception("Class " + var1.getClass().getCanonicalName() + " is not Entity!");

        // 1. Получаем имя таблицы
        String tableName = var1.getClass().getSimpleName().toLowerCase();

        // Получаем список членов класса
        Field[] fields = var1.getClass().getDeclaredFields();

        HashMap<String, String> fieldsAndValues = new HashMap<>();

        for (Field field : fields) {
            String[] parts = field.toString().split("\\.");

            if (!parts[parts.length - 1].toLowerCase().equals("id")) {

                String fieldKey = parts[parts.length-1].toLowerCase();
                String fieldValue = "";

                Annotation[] annotations = field.getAnnotations();
                for (Annotation a : annotations) {
                    if (a.annotationType().equals(OneToMany.class)) {
                        String[] fieldToString = field.getGenericType().toString().split("\\.");
                        String className = fieldToString[4].substring(0, fieldToString[4].length() - 1).toLowerCase();

                        String middleTableName = tableName + "_" + className;
                        System.out.println(middleTableName);

                        continue;
                    }
                    if (a.annotationType().equals(Column.class) && !a.annotationType().equals(Id.class)) {
                        try {
                            Method method = var1.getClass().getMethod(
                                    "get"+field.getName().substring(0,1).toUpperCase()+
                                            field.getName().substring(1),null);

                            fieldValue = String.valueOf(method.invoke(var1));

                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }

                fieldsAndValues.put(fieldKey, fieldValue);
            }
        }


        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + "(");

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

        sql.append(")");

        System.out.println(sql);
//
//        Connection connection = DatabaseHandler.getDbConnection();
//        PreparedStatement statement = connection.prepareStatement(String.valueOf(sql));
//        statement.execute();
//        connection.close();

    }

    public static void main(String[] args) throws Exception {
        EntityManager entityManager = new EntityManagerImpl();
        Product product = new Product("Shirt", "Brand", 1200);
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);
        Order order = new Order();

        entityManager.persist(order);
    }
    @Override
    public <T> T merge(T var1) {
        return null;
    }

    @Override
    public void remove(Object var1) {

    }

    @Override
    public <T> T find(Class<T> var1, Object var2) {
        return null;
    }

    @Override
    public void refresh(Object var1) {

    }
}
