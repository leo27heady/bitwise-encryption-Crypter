package sample;

import com.jfoenix.controls.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;



public class CryptUI {

    private File fileName = null;
    private Crypt crypt = new Crypt();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    static HBox cryptPane;

    @FXML
    private JFXTextArea enterTextArea;

    @FXML
    private JFXComboBox<String> chooseKeyCombo;

    @FXML
    private JFXTextArea CryptTextArea;

    @FXML
    private JFXButton safeDirectButton;

    @FXML
    private StackPane stackPane;

    @FXML
    private StackPane OneStack;

    @FXML
    void copyToClipboard(MouseEvent event) {
        if (!CryptTextArea.getText().isEmpty()){
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            clpbrd.setContents(new StringSelection(CryptTextArea.getText()), null);
        }

    }


    @FXML
    void CryptTextButton(MouseEvent event) throws IOException{


        String chosed = chooseKeyCombo.getSelectionModel().getSelectedItem();
        System.out.println(enterTextArea.getText());
        if ((chosed != null && fileName != null &&  enterTextArea.getText().isEmpty()) || (chosed != null && fileName == null &&  !enterTextArea.getText().isEmpty())){
            if (fileName != null) {
                crypt.cryptFile(fileName, Integer.parseInt(chosed), CryptTextArea, enterTextArea);
                safeDirectButton.setDisable(false);
            }
            else if(!enterTextArea.getText().isEmpty()){
                crypt.cryptText(enterTextArea.getText(), Integer.parseInt(chosed), CryptTextArea);
                safeDirectButton.setDisable(false);
            }
        }
        else if (chosed == null){
            createMessageDialog("Choose key!", "Choose the key from the presented options", "Okay. I'll choose");
        }
        else if (fileName == null && enterTextArea.getText().isEmpty()){
            createMessageDialog("Read please!", "Choose the file or enter text", "Okay");
        }
        else if(fileName != null && !enterTextArea.getText().isEmpty()){
            BoxBlur blur = new BoxBlur(5, 5, 1);

            JFXButton f_Button = new JFXButton("File");
            JFXButton t_Button = new JFXButton("Text");
            f_Button.getStyleClass().add("okButton");
            t_Button.getStyleClass().add("okButton");

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Label("Select one!"));
            dialogLayout.setBody(new Label("You selected a file and entered text."));

            dialogLayout.setActions(f_Button, t_Button);

            JFXDialog dialog = new JFXDialog(OneStack, dialogLayout, JFXDialog.DialogTransition.TOP);

            dialog.show();
            stackPane.setEffect(blur);

            f_Button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->{
                enterTextArea.setText("");
                dialog.close();
            });

            t_Button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) ->{
                fileName = null;
                dialog.close();
            });

            dialog.setOnDialogClosed(event1 -> {
                stackPane.setEffect(null);
            });

        }

    }

    @FXML
    void decryptIsClicked(MouseEvent event) throws IOException {
        Parent FXML = FXMLLoader.load(getClass().getResource("DecryptUI.fxml"));
        SignInDialog.stage.getScene().setRoot(FXML);
    }

    @FXML
    void homeIsClicked(MouseEvent event) throws IOException {
        Parent FXML = FXMLLoader.load(getClass().getResource("TestingMainProgram.fxml"));
        SignInDialog.stage.getScene().setRoot(FXML);
    }

    @FXML
    void cryptIsClicked(MouseEvent event) {

    }

    @FXML
    void openToCryptFileButton(MouseEvent event) throws IOException {

        Stage stage = (Stage)SignInDialog.stage.getScene().getWindow();

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File To Crypt");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")  + "\\Documents"));


        setExtFilters(fileChooser);
        fileName = fileChooser.showOpenDialog(stage);
        if (fileName != null) {

        }

    }

    private void setExtFilters(FileChooser chooser){
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("DOC", "*.doc"),
                new FileChooser.ExtensionFilter("DOCX", "*.docx")
        );
    }

    @FXML
    void safeFileAfterCryptButton(MouseEvent event) throws IOException{



        Stage stage = (Stage)SignInDialog.stage.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose directory to safe file");
        if (fileName != null) {
            fileChooser.setInitialFileName(crypt.getNewFileName().substring(crypt.getNewFileName().lastIndexOf('\\') + 1));
            fileChooser.setInitialDirectory(new File(crypt.getNewFileName().substring(0, crypt.getNewFileName().lastIndexOf('\\'))));
        }
        else {
            fileChooser.setInitialFileName("SampleFileName.txt");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")  + "\\Documents"));
        }

        setExtFilters(fileChooser);

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            //System.out.println(file.getAbsolutePath());
            try(BufferedOutputStream output = new BufferedOutputStream (new FileOutputStream(file.getAbsolutePath()))) {
                output.write(crypt.getBytes());
            }
            try(BufferedOutputStream out = new BufferedOutputStream (new FileOutputStream(crypt.createFileName(file.getAbsolutePath(), "_key")))) {
                out.write(crypt.getKeyBytes());
            }
        }

    }

    @FXML
    void initialize() {
        assert cryptPane != null : "fx:id=\"cryptPane\" was not injected: check your FXML file 'CryptUI.fxml'.";
        assert enterTextArea != null : "fx:id=\"enterTextArea\" was not injected: check your FXML file 'CryptUI.fxml'.";
        assert chooseKeyCombo != null : "fx:id=\"chooseKeyCombo\" was not injected: check your FXML file 'CryptUI.fxml'.";
        assert CryptTextArea != null : "fx:id=\"CryptTextArea\" was not injected: check your FXML file 'CryptUI.fxml'.";

        chooseKeyCombo.getItems().addAll("1", "2", "3", "4", "5", "6");
        chooseKeyCombo.setTooltip(new Tooltip("f"));


    }

    void createMessageDialog(String headText, String bodyText, String buttonText){
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
