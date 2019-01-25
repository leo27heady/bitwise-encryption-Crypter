package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXToggleButton;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignInDialog {

    private User signUser;

    public static String userName = null;

    public static boolean fileNotFound = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane OneStack;

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane parent;

    @FXML
    private Pane contentArea;

    @FXML
    private TextField signLoginField;

    @FXML
    private PasswordField signPasswordField;

    @FXML
    private Label forgotField;

    @FXML
    private JFXButton signButton;

    @FXML
    private JFXToggleButton checkRemember;

    @FXML
    private Label registerField;

    @FXML
    void closeApp(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void openForgPass(MouseEvent event) {

    }
    static Stage stage;

    @FXML
    void signButton(MouseEvent event) throws IOException {

        if (!signLoginField.getText().isEmpty() && !signPasswordField.getText().isEmpty()) {
            if (signLoginField.getText().equals(signUser.getLogin()) && signPasswordField.getText().equals(signUser.getPassword())) {
                Parent root = FXMLLoader.load(getClass().getResource("TestingMainProgram.fxml"));

                Main.Stage.close();

                Scene scene = new Scene(root);
                stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("CDP");
                stage.initStyle(StageStyle.DECORATED);
                stage.show();
            }
            else if(!signLoginField.getText().equals(signUser.getLogin())) {
                createMessageDialog("Wrong Login!", "Login is Wrong", "Okay");
            }
            else if(!signPasswordField.getText().equals(signUser.getPassword())) {
                createMessageDialog("Wrong Password!", "Password is Wrong", "Okay");
            }
        }
        else if(signLoginField.getText().isEmpty()){
            createMessageDialog("Enter Login!", "Login label is Empty", "Okay");
        }
        else if(signPasswordField.getText().isEmpty()){
            createMessageDialog("Enter Password!", "Password label is Empty", "Okay");
        }
    }
    
    @FXML
    void openRegistration(MouseEvent event) throws IOException {
        Parent FXML = FXMLLoader.load(getClass().getResource("RegistrationPane.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(FXML);
    }

    @FXML
    void initialize() throws IOException {
        assert parent != null : "fx:id=\"parent\" was not injected: check your FXML file 'SignInDialog.fxml'.";
        assert contentArea != null : "fx:id=\"contentArea\" was not injected: check your FXML file 'SignInDialog.fxml'.";
        assert signLoginField != null : "fx:id=\"signLoginField\" was not injected: check your FXML file 'SignInDialog.fxml'.";
        assert signPasswordField != null : "fx:id=\"signPasswordField\" was not injected: check your FXML file 'SignInDialog.fxml'.";
        assert forgotField != null : "fx:id=\"forgotField\" was not injected: check your FXML file 'SignInDialog.fxml'.";
        assert signButton != null : "fx:id=\"signButton\" was not injected: check your FXML file 'SignInDialog.fxml'.";
        assert checkRemember != null : "fx:id=\"checkRemember\" was not injected: check your FXML file 'SignInDialog.fxml'.";
        assert registerField != null : "fx:id=\"registerField\" was not injected: check your FXML file 'SignInDialog.fxml'.";
        makeStageDraggable();

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(new File(System.getProperty("user.home") + "\\Documents\\CDP_userData.txt")));
            signUser = (User)objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            fileNotFound = true;
            FileWriter writer = new FileWriter(new File(System.getProperty("user.home") + "\\Documents\\CDP_userData.txt"));

            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(fileNotFound);

        if (!fileNotFound) {
            userName = signUser.getLogin();

            if (signUser.isRemember()) {
                signLoginField.setText(signUser.getLogin());
                signPasswordField.setText(signUser.getPassword());
                checkRemember.setSelected(signUser.isRemember());
            }
        }

    }

    private double xOffset = 0;
    private double yOffset = 0;
    public void makeStageDraggable() {
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        parent.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Main.Stage.setX(event.getScreenX() - xOffset);
                Main.Stage.setY(event.getScreenY() - yOffset);
                Main.Stage.setOpacity(0.75f);
            }
        });
        parent.setOnMouseReleased((event) -> {
            Main.Stage.setOpacity(1.0f);
        });
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
