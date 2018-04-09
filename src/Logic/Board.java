package Logic;

import java.io.Serializable;
import java.util.ArrayList;

class Board implements Serializable {
    private final int size;     //length of side
    private final Vertex[][] Elements;
    private final ArrayList<Component> Components;

    Board(int size) {
        this.size = size;
        this.Elements = new Vertex[size][size];
        this.Components = new ArrayList<>();
    }

    int getSize() {
        return size;
    }

    Vertex[][] getElements() {
        return Elements;
    }

    Vertex getElement(int x, int y) {
        return Elements[x][y];
    }

    void setElement(int x, int y, Vertex v) {
        Elements[x][y] = v;
    }

    ArrayList<Component> getComponents() {
        return Components;
    }

    void addComponent(Component component) {
        Components.add(component);
    }
}
