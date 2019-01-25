package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class Main extends Application {

    public static Stage Stage = null;
    public static User user;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("SignInDialog.fxml"));
        primaryStage.setTitle("CDP");
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        Stage = primaryStage;
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
