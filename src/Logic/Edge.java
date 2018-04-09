package Logic;

import java.io.Serializable;
import java.util.HashSet;

class Edge implements Serializable{
    private HashSet<Vertex> Adjacent;
    private Vertex reason;

    Edge() {
        Adjacent = new HashSet<>();
    }

    void addVertex(Vertex v) {
        Adjacent.add(v);
        v.addEdge(this);
    }

    void setReason(Vertex reason) {
        this.reason = reason;
    }

    Vertex getReason() {
        return reason;
    }

    void removeVertex(Vertex v) {
        Adjacent.remove(v);
    }

    int getSize() {
        return Adjacent.size();
    }

    HashSet<Vertex> getAdjacent() {
        return Adjacent;
    }
}
