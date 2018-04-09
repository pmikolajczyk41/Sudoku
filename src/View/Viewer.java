package View;

import Logic.Model;
import boards.BoardGetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Viewer implements Serializable {
    private Model model;
    private TextField[][] Fields;   //fields inside grid
    private MenuButton[][] MenuButtons;     //MenuButtons inside grid
    private Integer sizeofBoard = 1;
    private String oldValue = "";

    public Viewer() {
    }

    public void setModel(Model model) {
        this.model = model;
    }

    @FXML
    private RadioButton easyLevel;

    @FXML
    private RadioButton mediumLevel;

    @FXML
    private RadioButton hardLevel;

    //text field for displaying communicates
    @FXML
    private Label communicator;

    @FXML
    private GridPane grid;

    //one of the button was chosen
    @FXML
    void chosen(ActionEvent event) throws FileNotFoundException {
        loadBoard();
        communicator.setText("Good luck!");
        grid.setVisible(true);  //show board
    }

    //reset current board
    @FXML
    void clearBoard(ActionEvent event) throws FileNotFoundException {
        loadBoard();
        communicator.setText("Good luck!");
    }

    //reset grid settings
    private void prepareGrid(int sizeofBoard) {
        //remove old rules
        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();
        for (int i = 0; i < sizeofBoard; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100);    //equally distributed
            grid.getColumnConstraints().add(column);

            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100);    //equally distributed
            grid.getRowConstraints().add(row);
        }
    }

    //read board description, initialize model, create grid
    private void loadBoard() throws FileNotFoundException {
        //recognize game level
        String boardLevel = "";
        if (easyLevel.isSelected()) {
            sizeofBoard = 4;
            boardLevel = "easy";
        } else if (mediumLevel.isSelected()) {
            sizeofBoard = 6;
            boardLevel = "medium";
        } else if (hardLevel.isSelected()) {
            sizeofBoard = 9;
            boardLevel = "hard";
        }

        Fields = new TextField[sizeofBoard][sizeofBoard];
        MenuButtons = new MenuButton[sizeofBoard][sizeofBoard];
        ArrayList<Integer> Description = new ArrayList<>();     //for model

        BoardGetter in = boards.BoardGetter.get(boardLevel);
        Description.add(in.nextInt());   //ignore size

        prepareGrid(sizeofBoard);

        //read initial values, set blank fields
        for (int row = 0; row < sizeofBoard; row++) {
            for (int column = 0; column < sizeofBoard; column++) {
                Integer value = in.nextInt();
                Description.add(value);

                //fill field
                TextField textField = new TextField();
                textField.setPrefHeight(Double.MAX_VALUE);
                textField.setPrefWidth(Double.MAX_VALUE);
                textField.setEditable(false);
                Fields[row][column] = textField;
                grid.add(textField, column, row);

                GridPane.setHalignment(textField, HPos.CENTER);
                textField.setAlignment(Pos.CENTER);

                //don't use default css
                Fields[row][column].getStyleClass().clear();
                Fields[row][column].getStyleClass().add("text-field" + boardLevel);
                Fields[row][column].getStyleClass().add("text-fieldall");

                if (value == -1) {
                    int finalRow = row, finalColumn = column;

                    //MenuButton
                    MenuButton menuButton = new MenuButton();
                    menuButton.setOpacity(0.2);
                    MenuButtons[row][column] = menuButton;
                    grid.add(menuButton, column, row);
                    GridPane.setValignment(menuButton, VPos.BOTTOM);
                    for (int i = 0; i <= sizeofBoard; i++) {
                        MenuItem menuItem = new MenuItem((i > 0)? String.valueOf(i) : "" );
                        menuButton.getItems().add(menuItem);
                        menuItem.setOnAction(event -> {
                            String text = menuItem.getText();
                            if(text.equals("")) getMove(finalRow, finalColumn, 0);
                            else getMove(finalRow, finalColumn, Integer.parseInt(text));
                        });
                    }
                } else {
                    textField.setText(value.toString());
                }
            }
        }

        //finish description for model
        Integer components = in.nextInt();
        Description.add(components);
        for (int i = 0; i < components; i++) {
            Integer length = in.nextInt();
            Description.add(length);
            for (int j = 0; j < length; j++)
                Description.add(in.nextInt());
        }

        model.initializeModel(Description);
    }

    //action handler
    private void getMove(int row, int column, Integer newValue) {
        oldValue = Fields[row][column].getText();
        Fields[row][column].setText((newValue > 0)? newValue.toString() : "");
        if(oldValue.equals(Fields[row][column].getText())) return;
        model.move(row, column, newValue);
    }

    //no conflicts, no blank fields
    public void endGame() {
        communicator.setText("Congratulations!");
        //disable menuButtons
        for (MenuButton[] menuButtons : MenuButtons)
            for (int j = 0; j < MenuButtons.length; j++) {
                menuButtons[j].setDisable(false);
                menuButtons[j].setVisible(false);
            }
    }

    //conflict with initial value
    public void originConflict(int row, int column) {
        communicator.setText("Conflict with initial value");
        Fields[row][column].setText(oldValue);
    }

    //no new conflicts, still playing
    public void correct(int row, int column) {
        communicator.setText("Nice guess");
        Fields[row][column].setStyle("-fx-text-fill: black");
    }

    //new conflicts, still playing
    public void conflict() {
        communicator.setText("Ooops...");
    }

    //actualize red fields
    public void mark(boolean[][] Mark) {
        for (int row = 0; row < Mark.length; row++)
            for (int column = 0; column < Mark.length; column++) {
                if (Mark[row][column]) Fields[row][column].setStyle("-fx-text-fill: red");
                else Fields[row][column].setStyle("-fx-text-fill: black");
            }
    }

    //mark components
    public void setComponents(HashMap<Integer, Integer> Components) {
        for (Integer id : Components.keySet()) {
            int row = id / Fields.length, column = id % Fields.length;
            Fields[row][column].getStyleClass().add("text-field" + (Components.get(id)).toString());
        }
    }
}
