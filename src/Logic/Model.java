package Logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Model implements Serializable {
    private Board Board;
    private MoveListener moveListener;
    private EndGameListener endGameListener;
    private ComponentsListener componentsListener;

    public Model() {}

    public void initializeModel(ArrayList<Integer> Description) {
        int size = Description.get(0);
        Board = new Board(size);

        //write initial values into Board
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                Integer value = Description.get(row * size + column + 1);
                boolean original = true;
                if (value.equals(-1)) {
                    value = null;
                    original = false;
                }
                Vertex newborn = new Vertex(row, column, value, original);
                Board.setElement(row, column, newborn);
            }
        }

        int numOfComponents = Description.get(size * size + 1);
        int iterator = size * size + 2;
        for (int i = 0; i < numOfComponents; i++) {
            int componentSize = Description.get(iterator++);
            Component component = new Component(i);
            Board.addComponent(component);
            for (int j = 0; j < componentSize; j++) {
                int vertexId = Description.get(iterator++);
                int row = vertexId / size, column = vertexId % size;
                component.addElement(Board.getElement(row, column));
            }
        }

        moveListener.receiveBoard(Board);
        endGameListener.receiveBoard(Board);
        componentsListener.receiveBoard(Board);

        componentsListener.mark();
    }

    public void addListener(Listener listener) {
        if (listener instanceof MoveListener) moveListener = (MoveListener) listener;
        else if (listener instanceof EndGameListener) endGameListener = (EndGameListener) listener;
        else if (listener instanceof ComponentsListener) componentsListener = (ComponentsListener) listener;
    }

    public void move(int x, int y, Integer value) {
        if (moveListener.listen(x, y, value)) {
            if(value.equals(0)) Board.getElement(x,y).setValue(null);
            else Board.getElement(x, y).setValue(value);
            endGameListener.listen();
        }
    }

    public MoveListener getMoveListener() {
        return moveListener;
    }

    public EndGameListener getEndGameListener() {
        return endGameListener;
    }

    public ComponentsListener getComponentsListener() {
        return componentsListener;
    }
}
