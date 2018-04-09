package Logic;

import View.Viewer;

public class EndGameListener extends Listener {
    public EndGameListener(Viewer viewer) {
        super(viewer);
    }

    void listen() {
        for (int i = 0; i < Board.getSize(); i++)
            for (int j = 0; j < Board.getSize(); j++)
                if (Board.getElement(i, j).isConflicted()
                        || Board.getElement(i, j).getValue() == null) return;
        viewer.endGame();
    }
}
