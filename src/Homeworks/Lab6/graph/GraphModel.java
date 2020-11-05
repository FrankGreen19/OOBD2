package Homeworks.Lab6.graph;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "graph")
@XmlRootElement(name = "graph")
public class GraphModel {

    private List<EntityNode> entityNodeList = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    public EntityNode findEntityByName(String entityName) {
        for (EntityNode entityNode : entityNodeList) {
            if (entityNode.getEntityName().equals("Homeworks.Lab1." + entityName))
                return entityNode;
        }
        return null;
    }

    public void addEntity(EntityNode entityNode) {
        this.entityNodeList.add(entityNode);
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public List<EntityNode> getEntityNodeList() {
        return entityNodeList;
    }

    @XmlElementWrapper(name="nodes", nillable = true)
    @XmlElement(name = "node")
    public void setEntityNodeList(List<EntityNode> entityNodeList) {
        this.entityNodeList = entityNodeList;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    @XmlElementWrapper(name="edges", nillable = true)
    @XmlElement(name = "edge")
    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }
}
