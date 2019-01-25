package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXToggleButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pane;

    @FXML
    private StackPane OneStack;

    @FXML
    private StackPane stackPane;

    @FXML
    private TextField regLoginField;

    @FXML
    private TextField emailLabel;

    @FXML
    private PasswordField regPasswordField;

    @FXML
    private PasswordField regConPasswordField;

    @FXML
    private JFXToggleButton checkRemember;

    @FXML
    private TextField secretAnswer;

    @FXML
    void backButton(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignInDialog.fxml"));
        Main.Stage.getScene().setRoot(root);
    }

    @FXML
    void closeApp(MouseEvent event) {
        System.exit(0);
    }

    static Stage stage;
    @FXML
    void registerButton(MouseEvent event) throws IOException {

        if (!regLoginField.getText().isEmpty() && !emailLabel.getText().isEmpty() && !regPasswordField.getText().isEmpty() && !regConPasswordField.getText().isEmpty() && !secretAnswer.getText().isEmpty()){
            Main.user = new User(regLoginField.getText(), emailLabel.getText(), regPasswordField.getText(), secretAnswer.getText(), checkRemember.isSelected());

            SignInDialog.userName = regLoginField.getText();

            try(ObjectOutputStream serial = new ObjectOutputStream(new FileOutputStream(new File(System.getProperty("user.home") + "\\Documents\\CDP_userData.txt")))) {
                serial.writeObject(Main.user);
            }

            Main.Stage.close();

            Parent root = root = FXMLLoader.load(getClass().getResource("TestingMainProgram.fxml"));

            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("CDP");
            stage.initStyle(StageStyle.DECORATED);
            stage.show();

            SignInDialog.stage = RegistrationController.stage;
        }
        else if(!regPasswordField.getText().equals(regConPasswordField)){
            createMessageDialog("Passwords do not match!", "Password and Confirm Password is not Equals", "Okay");
        }
        else if(regLoginField.getText().isEmpty()){
            createMessageDialog("Enter Login!", "Login label is Empty", "Okay");
        }
        else if(emailLabel.getText().isEmpty()){
            createMessageDialog("Enter Email!", "Email label is Empty", "Okay");
        }
        else if(regPasswordField.getText().isEmpty()){
            createMessageDialog("Enter Password!", "Password label is Empty", "Okay");
        }
        else if(regConPasswordField.getText().isEmpty()){
            createMessageDialog("Confirm Password!", "Confirm Password label is Empty", "Okay");
        }
        else if(secretAnswer.getText().isEmpty()){
            createMessageDialog("Enter Secret Answer!", "Secret Answer label is Empty", "Okay");
        }

    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'RegistrationPane.fxml'.";
        assert regLoginField != null : "fx:id=\"regLoginField\" was not injected: check your FXML file 'RegistrationPane.fxml'.";
        assert emailLabel != null : "fx:id=\"emailLabel\" was not injected: check your FXML file 'RegistrationPane.fxml'.";
        assert regPasswordField != null : "fx:id=\"regPasswordField\" was not injected: check your FXML file 'RegistrationPane.fxml'.";
        assert regConPasswordField != null : "fx:id=\"regConPasswordField\" was not injected: check your FXML file 'RegistrationPane.fxml'.";
        assert checkRemember != null : "fx:id=\"checkRemember\" was not injected: check your FXML file 'RegistrationPane.fxml'.";
        assert secretAnswer != null : "fx:id=\"secretAnswer\" was not injected: check your FXML file 'RegistrationPane.fxml'.";

    }

    public void createMessageDialog(String headText, String bodyText, String buttonText){
        BoxBlur blur = new BoxBlur(5, 5, 1);

        JFXButton okButton = new JFXButton(buttonText);
        okButton.getStyleClass().add("okButton");

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Label(headText));
        dialogLayout.setBody(new Label(bodyText));

        dialogLayout.setActions(okButton);

        JFXDialog dialog = new JFXDialog(OneStack, dialogLayout, JFXDialog.DialogTransition.TOP);

        dialog.show();
        stackPane.setEffect(blur);

        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->{
            dialog.close();
        });

        dialog.setOnDialogClosed(event1 -> {
            stackPane.setEffect(null);
        });
    }
}

