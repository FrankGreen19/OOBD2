package Homeworks.Lab6.graph;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

public class EntityNode {

    private Class entityClass;
    private String entityName;
    private List<EntityAttribute> atributes = new ArrayList<>();

    public void setEntityAttribute(EntityAttribute entityAttribute) {
        this.atributes.add(entityAttribute);
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<EntityAttribute> getAtributes() {
        return atributes;
    }

    public void setAtributes(List<EntityAttribute> atributes) {
        this.atributes = atributes;
    }

    @Override
    public String toString() {
        return "\nEntityName: " + this.entityName +
                "\n EntityClass: " + this.entityClass;
    }
}
