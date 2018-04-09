package Logic;

import java.io.Serializable;
import java.util.ArrayList;

class Component implements Serializable {
    private final int id;
    private final ArrayList<Vertex> Elements;

    Component(int id) {
        this.id = id;
        Elements = new ArrayList<>();
    }

    int getId() {
        return id;
    }

    ArrayList<Vertex> getElements() {
        return Elements;
    }

    void addElement(Vertex v) {
        Elements.add(v);
        v.setComponent(this);
    }
}
