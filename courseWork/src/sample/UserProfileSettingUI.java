package sample;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class UserProfileSettingUI {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField answerCartoonLabel;

    @FXML
    private JFXTextField previousLoginLabel;

    @FXML
    private JFXTextField newLoginLabel;

    @FXML
    private JFXTextField previousPasswordLabel;

    @FXML
    private JFXTextField newPasswordLabel;

    @FXML
    private ImageView avatarImage;

    @FXML
    void chooseAvatarImage(MouseEvent event) {

    }

    @FXML
    void doneAvatar(MouseEvent event) {

    }

    @FXML
    void doneLogin(MouseEvent event) {

    }

    @FXML
    void donePassword(MouseEvent event) {

    }

    @FXML
    void doneQuestion(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert answerCartoonLabel != null : "fx:id=\"answerCartoonLabel\" was not injected: check your FXML file 'UserProfileSettingUI.fxml'.";
        assert previousLoginLabel != null : "fx:id=\"previousLoginLabel\" was not injected: check your FXML file 'UserProfileSettingUI.fxml'.";
        assert newLoginLabel != null : "fx:id=\"newLoginLabel\" was not injected: check your FXML file 'UserProfileSettingUI.fxml'.";
        assert previousPasswordLabel != null : "fx:id=\"previousPasswordLabel\" was not injected: check your FXML file 'UserProfileSettingUI.fxml'.";
        assert newPasswordLabel != null : "fx:id=\"newPasswordLabel\" was not injected: check your FXML file 'UserProfileSettingUI.fxml'.";
        assert avatarImage != null : "fx:id=\"avatarImage\" was not injected: check your FXML file 'UserProfileSettingUI.fxml'.";

    }
}
