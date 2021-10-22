package gui.controllers;

import gui.application.AudioManagement;
import gui.application.MerriamWebsterAPI;
import gui.application.SQLite;
import gui.application.VoiceRSS;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.json.JSONArray;

import javax.json.JsonArray;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerSearchWord implements Initializable {

    private SQLite myDadabase;
    @FXML
    private TextField searchBox;
    @FXML
    private WebView webView;
    @FXML
    private HBox boxWord1, boxWord2;
    @FXML
    private WebView wordView;
    @FXML
    private VBox boxEdit;
    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private Pane pane;
    @FXML
    private ComboBox accents;


    @FXML
    private ListView<String> synonyms = null, antonyms = null;

    public void addFavorites(ActionEvent event) {

    }

    public void delete(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm delete");
        alert.setHeaderText("");
        alert.setContentText("Are you sure want to delete this word?");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.TRANSPARENT);
        alert.getDialogPane().getScene().setFill(Color.TRANSPARENT);
        alert.getDialogPane().getStylesheets().add("resources/css/alert.css");

        if (alert.showAndWait().get() == ButtonType.OK) {
            myDadabase.queryDelete(searchBox.getText());
            System.out.println("Deleted!!!");
        }
    }

    @FXML
    public void modify(ActionEvent event) {
        if (boxEdit.isVisible() == false) {
            boxEdit.setVisible(true);
            webView.setVisible(false);
        } else {
            boxEdit.setVisible(false);
            webView.setVisible(true);
        }
    }

    public void update(ActionEvent event) throws SQLException {
        myDadabase.queryUpdateHtml(searchBox.getText(), htmlEditor.getHtmlText().replace("'", "''"));
    }

    @FXML
    public void search(ActionEvent event) {

        if (synonyms == null || antonyms == null) {
            Scene stage = searchBox.getScene();
            synonyms = (ListView<String>) stage.lookup("#synonyms");
            antonyms = (ListView<String>) stage.lookup("#antonyms");
        }

        synonyms.getItems().clear();
        antonyms.getItems().clear();

        boxWord1.setVisible(true);
        boxWord2.setVisible(true);

        try {
            String[] result = myDadabase.selectAll(searchBox.getText());

            if (result == null) {

                pane.setVisible(true);
                boxWord1.setVisible(false);
                boxWord2.setVisible(false);

                pane.getChildren().clear();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/suggestion.fxml"));
                Node translatorBox = loader.load();
                pane.getChildren().add(translatorBox);
                System.out.println(MerriamWebsterAPI.api.requestJsonDict(searchBox.getText()));
                Label label = (Label) pane.lookup("#suggestion");
                String sug = MerriamWebsterAPI.api.requestJsonDict(searchBox.getText()).toString();
                sug = sug.replace("[", "");
                sug = sug.replace("]", "");
                sug = sug.replace("\"", "");
                sug = sug.replace(",", "\n");
                if (sug.charAt(0) == '{') {
                    label.setText("");
                } else {
                    label.setText(sug.toLowerCase());
                }
                return;
            }

            boxWord1.setVisible(true);
            pane.setVisible(false);

            String word = result[0];
            String pronunciation = result[1];
            String html = result[3];

            htmlEditor.setHtmlText(html);

            final URL engineStyleUrl = Controller.class.getResource("/resources/css/styles-html.css");
            final URL scrollBarStyleUrl = Controller.class.getResource("/resources/css/styles-scrollbar.css");

            webView.getEngine().setUserStyleSheetLocation(engineStyleUrl.toExternalForm());

            wordView.getEngine().setUserStyleSheetLocation(scrollBarStyleUrl.toExternalForm());


            String rev = "<h1>" + word + "</h1>" + "<h3><i>/" + pronunciation + "/</i></h3>";
            String x = html.replace(rev, "");

            webView.getEngine().loadContent(x);
            wordView.getEngine().loadContent("<h1>" + word + "</h1>" + "<h3><i>/ " + pronunciation + " /</i></h3>");

            boxWord1.setVisible(true);
            boxWord2.setVisible(true);

            webView.getChildrenUnmodifiable().addListener(new ListChangeListener<Node>() {
                @Override
                public void onChanged(Change<? extends Node> change) {
                    Set<Node> deadSeaScrolls = webView.lookupAll(".scroll-bar");
                    for (Node scroll : deadSeaScrolls) {
                        scroll.setVisible(false);
                    }
                }
            });

            // thesaurus
            JSONArray jsonData = MerriamWebsterAPI.api.requestJsonThes(searchBox.getText());

            List<String> lists = MerriamWebsterAPI.api.getListSymonymsFromJson(jsonData);
            if (lists != null) {
                for (String value : lists) {
                    synonyms.getItems().add(value);
                }
            }
            lists = MerriamWebsterAPI.api.getListAntonymsJson(jsonData);
            if (lists != null) {
                for (String value : lists) {
                    antonyms.getItems().add(value);
                }
            }
            //
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void chooseAccent() {
        VoiceRSS.setACCENT(accents.getValue().toString());
        System.out.println("AAAAAAAAAAACEN");
    }

    @FXML
    public void pronounce(ActionEvent event) throws Exception {
        String u = searchBox.getText();
        if (!u.equals("")) {
            AudioManagement.textToSpeechFromRSS(u, "tts_rss_word");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        accents.getItems().addAll("us", "uk");
        VoiceRSS.setACCENT("uk");

        try {
            myDadabase = SQLite.getInstance();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        boxWord1.setVisible(false);
        boxWord2.setVisible(false);
        boxEdit.setVisible(false);
        pane.setVisible(false);

        AutoCompletionBinding<String> autoCompletionBinding = TextFields.bindAutoCompletion(searchBox, e -> {
            try {
                return myDadabase.getListWord().stream().filter(element -> {
                    return element.startsWith(e.getUserText().toLowerCase());
                }).collect(Collectors.toList());
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            return null;
        });

        autoCompletionBinding.getAutoCompletionPopup().setStyle(
                ".auto-complete-popup {"
              + "-fx-control-inner-background:WHITE;"
              + "-fx-accent: #213191;"
              + "-fx-selection-bar-non-focused:#213191;"
              + "-fx-font:17px Roboto;}"
              + ".auto-complete-popup > .list-view > .virtual-flow > .clipped-container > .sheet > .list-cell { "
              + "-fx-padding: 100 0 4 5; -fx-background: -fx-control-inner-background;}");
    }
}
