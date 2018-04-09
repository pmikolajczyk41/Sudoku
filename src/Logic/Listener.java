package Logic;

import View.Viewer;

import java.io.Serializable;

abstract class Listener implements Serializable{
    protected Board Board;
    protected Viewer viewer;

    Listener(Viewer viewer){
        this.viewer = viewer;
    }

    void receiveBoard(Board Board){
        this.Board = Board;
    }
}
