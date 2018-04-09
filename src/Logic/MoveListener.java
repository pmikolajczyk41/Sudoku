package Logic;

import View.Viewer;

import java.io.Serializable;

public class MoveListener extends Listener implements Serializable {

    public MoveListener(Viewer viewer) {
        super(viewer);
    }

    boolean listen(int x, int y, Integer value){
        //if(Board.getElement(x,y).getValue() != null && Board.getElement(x,y).getValue().equals(value)) return false;

        if(!checkOrigins(x, y, value)){
            viewer.originConflict(x, y);
            return false;
        }

        eraseEdges(x, y);
        boolean created = createEdge(x, y, value);

        boolean Mark[][] = new boolean[Board.getSize()][Board.getSize()];
        for(int i = 0; i < Board.getSize(); i++)
            for(int j = 0; j < Board.getSize(); j++)
                Mark[i][j] = Board.getElement(i,j).isConflicted();
        viewer.mark(Mark);

        if(created) viewer.conflict();
        else viewer.correct(x,y);
        return true;
    }

    private boolean createEdge(int x, int y, Integer value){
        Edge edge = new Edge();

        for(int i = 0; i < Board.getSize(); i++){
            Vertex v1 = Board.getElement(i, y);
            Vertex v2 = Board.getElement(x, i);

            if(v1.getValue() != null && v1.getValue().equals(value)) edge.addVertex(v1);
            if(v2.getValue() != null && v2.getValue().equals(value)) edge.addVertex(v2);
        }

        Component component = Board.getElement(x,y).getComponent();
        if(component != null){
            for(Vertex adj : component.getElements())
                if(adj.getValue() != null && adj.getValue().equals(value))
                    edge.addVertex(adj);
        }

        if(edge.getSize() > 0){
            Vertex v = Board.getElement(x,y);
            edge.addVertex(v);
            edge.setReason(v);
            return true;
        }

        return false;
    }

    private void eraseEdges(int x, int y){
        Vertex v = Board.getElement(x,y);
        if(v.getValue() == null) return;
        if(!v.isConflicted()) return;
        for(Edge e : v.getEdges()){
            if(e.getReason() == v || e.getSize() == 2){
                for(Vertex u : e.getAdjacent())
                    if (u != v) u.removeEdge(e);
            }
            else e.removeVertex(v);
        }
        v.resetEdges();
    }

    private boolean checkOrigins(int x, int y, Integer value) {
        if(Board.getElement(x,y).isOriginal()) return false;
        for(int i = 0; i < Board.getSize(); i++) {
            Vertex v1 = Board.getElement(i, y);
            Vertex v2 = Board.getElement(x, i);

            if (v1.isOriginal() && v1.getValue().equals(value))
                return false;

            if (v2.isOriginal() && v2.getValue().equals(value))
                return false;
        }

        Component component = Board.getElement(x, y).getComponent();
        if(component != null){
            for(Vertex adj : component.getElements())
                if(adj.isOriginal() && adj.getValue().equals(value))
                    return false;
        }
        return true;
    }
}
