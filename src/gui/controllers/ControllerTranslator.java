package gui.controllers;

import gui.application.AudioManagement;
import gui.application.GoogleTranslate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerTranslator implements Initializable {

    String srcLanguage = "en";
    String desLanguage = "vi";

    @FXML
    private TextArea textInput;
    @FXML
    private TextArea textOutput;
    @FXML
    private VBox boxTransition;
    @FXML
    private Button exchangeLanguage;
    @FXML
    private HBox english, vietnamese;

    @FXML
    public void exchange(ActionEvent event) {
        String temp = desLanguage;
        desLanguage = srcLanguage;
        srcLanguage = temp;

        if (srcLanguage.equals("en")) {
            english.setTranslateX(0);
            vietnamese.setTranslateX(0);
        } else {
            english.setTranslateX(350);
            vietnamese.setTranslateX(-350);
        }
    }

    @FXML
    public void copy() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(textOutput.getText());
        clipboard.setContent(content);
    }

    @FXML
    public void actionTranslate(ActionEvent event) throws InterruptedException, IOException {
        String text = textInput.getText();
        if (text.equals("")) {
            return;
        }

        textOutput.setText(GoogleTranslate.translate(text, srcLanguage, desLanguage));
        boxTransition.setVisible(true);
    }

    @FXML
    public void actionPronounceOutput(ActionEvent event) throws Exception {
        AudioManagement.textToSpeechFromRSS(textInput.getText(), "tts_rss_text");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boxTransition.setVisible(false);
        textOutput.setEditable(false);
    }
}
