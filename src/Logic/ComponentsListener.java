package Logic;

import View.Viewer;

import java.io.Serializable;
import java.util.HashMap;

public class ComponentsListener extends Listener implements Serializable {
    public ComponentsListener(Viewer viewer) {
        super(viewer);
    }

    //give each vertex id of its enclosing component
    void mark(){
        HashMap<Integer, Integer> Colors = new HashMap<>();
        for(int row = 0; row < Board.getSize(); row++){
            for(int column = 0; column < Board.getSize(); column++){
                Integer id = row * Board.getSize() + column;
                Colors.put(id, Board.getElement(row,column).getComponent().getId());
            }
        }
        viewer.setComponents(Colors);
    }
}
