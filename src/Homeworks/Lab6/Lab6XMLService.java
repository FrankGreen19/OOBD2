package Homeworks.Lab6;

import Homeworks.Lab1.Product;
import Homeworks.Lab1.Shop;
import Homeworks.Lab6.graph.Edge;
import Homeworks.Lab6.graph.EntityNode;
import Homeworks.Lab6.graph.GraphModel;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Lab6XMLService {

    public static void makeGraph(GraphModel graph) {

        try {
            StringWriter writer = new StringWriter();

            //создание объекта Marshaller, который выполняет сериализацию
            JAXBContext context = JAXBContext.newInstance(GraphModel.class, EntityNode.class, Edge.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // сама сериализация
            marshaller.marshal(graph, writer);

            //преобразовываем в строку все записанное в StringWriter
            String result = writer.toString();

            try (OutputStream os = new FileOutputStream(new File("graph.xml"))) {
                os.write(result.getBytes(StandardCharsets.UTF_8));
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
