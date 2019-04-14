package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;
        window.setTitle("XSS Detector");

        userInterface();

        window.setOnCloseRequest(e -> System.exit(0));

    }

    private void userInterface() {

        Test test = new Test();

        GridPane pane = new GridPane();
        Button button = new Button("Run Test");
        pane.add(button, 1, 1);

        button.setOnAction(e -> {
            try {

                test.formTest();

            }catch (Exception e1) {
                e1.printStackTrace();
                System.exit(0);
            }
            });

        Scene scene = new Scene(pane,300, 300);
        window.setScene(scene);
        window.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
