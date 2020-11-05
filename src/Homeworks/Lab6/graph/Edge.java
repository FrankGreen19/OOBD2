package Homeworks.Lab6.graph;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

public class Edge {
    private EntityNode nodeSource;
    private String nodeTarget;
    private RelationType relationType;

    public EntityNode getNodeSource() {
        return nodeSource;
    }

    public void setNodeSource(EntityNode nodeSource) {
        this.nodeSource = nodeSource;
    }

    public String getNodeTarget() {
        return nodeTarget;
    }

    public void setNodeTarget(String nodeTarget) {
        this.nodeTarget = nodeTarget;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    @Override
    public String toString() {
        return "\nnodeSource: " + nodeSource.getEntityName() +
                "\nnodeTarget: " + nodeTarget;
    }
}
