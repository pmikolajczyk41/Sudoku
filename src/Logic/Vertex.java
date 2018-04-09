package Logic;

import java.io.Serializable;
import java.util.HashSet;

class Vertex implements Serializable {
    private final int x, y;
    private Component component;
    private Integer value;
    private final boolean original;
    private final HashSet<Edge> Edges;

    Vertex(int x, int y, Integer value, boolean original) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.original = original;
        this.Edges = new HashSet<>();
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    Component getComponent() {
        return component;
    }
    
    void setComponent(Component component) {
        if (this.component == null)
            this.component = component;
    }

    Integer getValue() {
        return value;
    }

    void setValue(Integer value) {
        this.value = value;
    }

    boolean isOriginal() {
        return original;
    }

    void addEdge(Edge e) {   //append new conflict
        Edges.add(e);
    }

    boolean isConflicted() {   //returns if it has any conflicted neighbour
        return Edges.size() > 0;
    }

    public HashSet<Edge> getEdges() {
        return Edges;
    }

    void removeEdge(Edge e) {
        Edges.remove(e);
    }

    void resetEdges() {
        Edges.clear();
    }
}
