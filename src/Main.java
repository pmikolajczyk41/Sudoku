import Logic.ComponentsListener;
import Logic.EndGameListener;
import Logic.Model;
import Logic.MoveListener;
import View.Viewer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Serializable;

public class Main extends Application implements Serializable {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //get fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View/sudoku.fxml"));
        Parent root = fxmlLoader.load();

        //set objects
        Viewer viewer = fxmlLoader.getController();
        Model model = new Model();
        viewer.setModel(model);
        model.addListener(new MoveListener(viewer));
        model.addListener(new EndGameListener(viewer));
        model.addListener(new ComponentsListener(viewer));

        //run
        primaryStage.setTitle("Super Sudoku");
        primaryStage.setScene(new Scene(root));
        primaryStage.getScene().getStylesheets().add("View/styles.css");
        primaryStage.show();
    }
}
