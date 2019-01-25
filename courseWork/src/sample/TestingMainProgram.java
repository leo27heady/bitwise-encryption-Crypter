package sample;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TestingMainProgram {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox sideBar;

    @FXML
    private HBox globalPane;

    @FXML
    private Label userLabel;

    @FXML
    private AnchorPane swapPane;

    @FXML
    void cryptIsClicked(MouseEvent event) throws IOException {
        Parent FXML = FXMLLoader.load(getClass().getResource("CryptUI.fxml"));
        SignInDialog.stage.getScene().setRoot(FXML);

    }

    @FXML
    void decryptIsClicked(MouseEvent event) throws IOException {
        Parent FXML = FXMLLoader.load(getClass().getResource("DecryptUI.fxml"));
        SignInDialog.stage.getScene().setRoot(FXML);
    }

    @FXML
    void settingsIsClicked(MouseEvent event) throws IOException {
        Stage stage;
        Parent root = FXMLLoader.load(getClass().getResource("SettingsUI.fxml"));

        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("CDP Settings");
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }

    @FXML
    void initialize() {
        assert globalPane != null : "fx:id=\"globalPane\" was not injected: check your FXML file 'TestingMainProgram.fxml'.";
        assert sideBar != null : "fx:id=\"sideBar\" was not injected: check your FXML file 'TestingMainProgram.fxml'.";
        assert swapPane != null : "fx:id=\"swapPane\" was not injected: check your FXML file 'TestingMainProgram.fxml'.";
        assert userLabel != null : "fx:id=\"userLabel\" was not injected: check your FXML file 'TestingMainProgram.fxml'.";

        userLabel.setText(SignInDialog.userName);
    }
}
