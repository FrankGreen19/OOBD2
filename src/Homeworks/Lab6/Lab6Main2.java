package Homeworks.Lab6;

import Homeworks.Lab1.Client;
import Homeworks.Lab1.Order;
import Homeworks.Lab1.Person;
import Homeworks.Lab6.annotation.Column;
import Homeworks.Lab6.annotation.Entity;
import Homeworks.Lab6.graph.*;

import javax.persistence.OneToMany;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Програма демонстрирует формирование графа сущностей и связей между ними
 */
public class Lab6Main2 {

    // Пакет в котором расположены классы-сущности
    public static String PATH_FOR_SCAN = "Homeworks.Lab1";

    public static void main(String[] args) {

        GraphModel graph = new GraphModel();
        ArrayList<EntityNode> entityNodesList = new ArrayList<>();
        ArrayList<Edge> edgesList = new ArrayList<>();

        /* Сканируем пакет PATH_FOR_SCAN для поиска классов-сущностей  */
        List<Class<?>> classList = PathScan.find(PATH_FOR_SCAN);
        if (classList != null)
            classList.stream().filter(c -> classIsEntity(c)).forEach(System.out::println);

        for (Class<?> cl : classList) {
            EntityNode entityNode = new EntityNode();

            entityNode.setEntityClass(cl);
            entityNode.setEntityName(cl.getName());

            Field[] fields = cl.getDeclaredFields();
            for (Field field : fields) {
                EntityAttribute entityAttribute = new EntityAttribute();
                entityAttribute.setAttributeName(field.getName());
                entityAttribute.setAttributeType("Field");

//                entityNode.setEntityAttribute(entityAttribute);

                Annotation[] fannotations = field.getAnnotations();
                for (Annotation a : fannotations) {
                    if (a.annotationType().equals(OneToMany.class)) {
                        String[] fieldToString = field.getGenericType().toString().split("\\.");
                        String className = fieldToString[4].substring(0, fieldToString[4].length() - 1);

                        Edge edge = new Edge();
                        edge.setNodeSource(entityNode);
                        System.out.println(className);
                        edge.setNodeTarget(className);
                        edge.setRelationType(RelationType.OneToMany);

                        edgesList.add(edge);
                    }
                }
            }

//            Method[] methods = cl.getMethods();
//            for (Method method : methods) {
//                EntityAttribute entityAttribute = new EntityAttribute();
//                entityAttribute.setAttributeName(method.getName());
//                entityAttribute.setAttributeType("Method");
//
//                entityNode.setEntityAttribute(entityAttribute);
//            }

            entityNodesList.add(entityNode);
        }

        graph.setEntityNodeList(entityNodesList);
        graph.setEdges(edgesList);

        Lab6XMLService.makeGraph(graph);

    }

    private static boolean classIsEntity(Class<?> c) {
        Annotation[] annotations = c.getAnnotations();
        for (Annotation a : annotations) {
            if (a.equals(Entity.class)) {
                return true;
            }
        }
        return false;
    }

}
