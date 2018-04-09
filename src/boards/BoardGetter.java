package boards;

import java.io.Serializable;

public class BoardGetter implements Serializable{
    private Integer[] board;
    private int iterator;

    private static Integer[] easy = {4, -1, -1, -1, -1, -1, -1, -1, -1, 1, 2, -1, -1, -1, -1, 3, -1, 4, 4, 0, 1, 4, 8, 4, 5, 9, 12, 13, 4, 2, 3, 7, 11, 4, 6, 10, 14, 15};
    private static Integer[] medium = {6, -1, -1, 3, -1, -1, -1, -1, 2, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 6, -1, -1, -1, -1, 5, -1, -1, -1, -1, 4, -1, -1, 6, 6, 0, 1, 2, 3, 4, 6, 6, 7, 8, 13, 14, 19, 20, 6, 12, 18, 24, 30, 31, 25, 6, 32, 33, 26, 27, 21, 22, 6, 34, 35, 28, 29, 23, 17, 6, 5, 11, 10, 9, 15, 16};
    private static Integer[] hard = {9, -1, 4, 1, -1, 8, -1, -1, 6, -1, 2, -1, -1, -1, -1, -1, -1, -1, 5, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 3, -1, -1, -1, -1, -1, -1, -1, 9, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9, -1, -1, -1, -1, -1, -1, -1, -1, 7, -1, -1, -1, -1, -1, -1, -1, 2, -1, 3, -1, -1, 6, -1, 2, 4, -1, 9, 9, 0, 1, 9, 18, 19, 28, 29, 30, 21, 9, 2, 3, 4, 5, 10, 11, 12, 13, 20, 9, 6, 7, 8, 17, 14, 15, 23, 32, 33, 9, 16, 24, 25, 26, 34, 35, 43, 44, 53, 9, 27, 36, 45, 54, 37, 46, 55, 64, 56, 9, 63, 72, 73, 74, 65, 66, 57, 48, 47, 9, 75, 76, 77, 78, 67, 68, 69, 70, 60, 9, 79, 80, 71, 62, 61, 52, 51, 50, 59, 9, 38, 39, 40, 41, 42, 31, 22, 49, 58};

    public static BoardGetter get(String board) {
        if (board.equals("easy")) return new BoardGetter(easy);
        else if (board.equals("medium")) return new BoardGetter(medium);
        return new BoardGetter(hard);
    }

    private BoardGetter(Integer[] board){
        this.board = board;
        iterator = 0;
    }

    public Integer nextInt() {
        return board[iterator++];
    }
}
