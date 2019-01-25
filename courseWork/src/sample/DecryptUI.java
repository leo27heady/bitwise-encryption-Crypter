package sample;

import com.jfoenix.controls.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DecryptUI {

    private File fileName = null;
    private File fileKeyName = null;
    private Decrypt decrypt = new Decrypt();

    @FXML
    void decryptIsClicked(MouseEvent event) {

    }

    @FXML
    void cryptIsClicked(MouseEvent event) throws IOException {
        Parent FXML = FXMLLoader.load(getClass().getResource("CryptUI.fxml"));
        SignInDialog.stage.getScene().setRoot(FXML);
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXComboBox<String> chooseKeyCombo;

    @FXML
    private JFXTextArea DecryptTextArea;

    @FXML
    private JFXButton safeDirectButton;

    @FXML
    private StackPane OneStack;

    @FXML
    private StackPane stackPane;

    @FXML
    void copyToClipboard(MouseEvent event) {
        if (!DecryptTextArea.getText().isEmpty()){
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            clpbrd.setContents(new StringSelection(DecryptTextArea.getText()), null);
        }
    }

    @FXML
    void DecryptTextButton(MouseEvent event) throws IOException {
        String chosed = chooseKeyCombo.getSelectionModel().getSelectedItem();
        if (chosed != null && fileName != null && fileKeyName != null){
            decrypt.decrypt(fileName, fileKeyName, Integer.parseInt(chosed), DecryptTextArea);
            safeDirectButton.setDisable(false);
        }
        else if (fileName == null){
            createMessageDialog("Choose file!", "Choose the file to decrypt", "Okay");
        }
        else if (fileKeyName == null){
            createMessageDialog("Choose file!", "Choose the File-Key", "Okay");
        }
        else if (chosed == null){
            createMessageDialog("Choose key!", "Choose the key from the presented options", "Okay. I'll choose");
        }
        /*else if (chosed == null && fileName == null){
            createMessageDialog("Choose file!", "Choose the file to dec", "Okay");
        }
        else if (chosed == null && fileKeyName == null){
            System.out.println("chose and file_key");
        }
        else if (fileName == null && fileKeyName == null){
            System.out.println("file and file_key");
        }*/
    }

    @FXML
    void openKey_FileButton(MouseEvent event) {
        Stage stage = (Stage)SignInDialog.stage.getScene().getWindow();

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Key-File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")  + "\\Documents"));


        setExtFilters(fileChooser);
        fileKeyName = fileChooser.showOpenDialog(stage);
        if (fileKeyName != null) {
            System.out.println(fileKeyName.getAbsolutePath());
        }
    }

    @FXML
    void openToDecryptFileButton(MouseEvent event) {
        Stage stage = (Stage)SignInDialog.stage.getScene().getWindow();

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File To Decrypt");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")  + "\\Documents"));


        setExtFilters(fileChooser);
        fileName = fileChooser.showOpenDialog(stage);
        if (fileName != null) {
            System.out.println(fileName.getAbsolutePath());
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
    void safeFileAfterDecryptButton(MouseEvent event) throws IOException{
        Stage stage = (Stage)SignInDialog.stage.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose directory to safe file");
        fileChooser.setInitialFileName(decrypt.getNewFileName().substring(decrypt.getNewFileName().lastIndexOf('\\') + 1));
        fileChooser.setInitialDirectory(new File(decrypt.getNewFileName().substring(0, decrypt.getNewFileName().lastIndexOf('\\'))));

        setExtFilters(fileChooser);

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            System.out.println(file.getAbsolutePath());

            try(BufferedOutputStream output = new BufferedOutputStream (new FileOutputStream(file.getAbsolutePath()))) {
                output.write(decrypt.getBytes());
            }

        }

    }

    @FXML
    void homeIsClicked(MouseEvent event) throws IOException {
        Parent FXML = FXMLLoader.load(getClass().getResource("TestingMainProgram.fxml"));
        SignInDialog.stage.getScene().setRoot(FXML);
    }

    @FXML
    void initialize() {
        assert chooseKeyCombo != null : "fx:id=\"chooseKeyCombo\" was not injected: check your FXML file 'DecryptUI.fxml'.";
        assert DecryptTextArea != null : "fx:id=\"DecryptTextArea\" was not injected: check your FXML file 'DecryptUI.fxml'.";

        chooseKeyCombo.getItems().addAll("1", "2", "3", "4", "5", "6");
    }

    void createMessageDialog(String headText, String bodyText, String buttonText){
        BoxBlur blur = new BoxBlur(5, 5, 1);

        JFXButton okButton = new JFXButton(buttonText);
        okButton.getStyleClass().add("okButton");

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new javafx.scene.control.Label(headText));
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
