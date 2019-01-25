package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class SettingsUI {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane lowerPane;

    @FXML
    void OpenFolderButton(MouseEvent event) {

    }

    @FXML
    void openCodeButton(MouseEvent event) {

    }

    @FXML
    void openSettingsButton(MouseEvent event) {

    }

    @FXML
    void openUserProfileButton(MouseEvent event) throws IOException {
        Parent FXML = FXMLLoader.load(getClass().getResource("UserProfileSettings.fxml"));
        lowerPane.getChildren().removeAll();
        lowerPane.getChildren().setAll(FXML);
    }

    @FXML
    void initialize() {
        assert lowerPane != null : "fx:id=\"lowerPane\" was not injected: check your FXML file 'SettingsUI.fxml'.";

    }
}
