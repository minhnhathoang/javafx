package gui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private AnchorPane scene;
    @FXML
    private VBox options;
    @FXML
    private Button menu;

    @FXML
    private Button buttonLogout;
    @FXML
    private WebView webView;

    @FXML
    private HBox boxTranslator;

    @FXML
    private VBox boxSearch;
    @FXML
    private VBox dashboard;
    @FXML
    private ComboBox setDictionary;

    @FXML
    private HBox thesaurus;


    @FXML
    public void actionLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm exit");
        alert.setHeaderText("");
        alert.setContentText("Are you sure you want to exit?");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.initStyle(StageStyle.TRANSPARENT);
        alert.getDialogPane().getScene().setFill(Color.TRANSPARENT);
        alert.getDialogPane().getStylesheets().add("resources/css/alert.css");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    @FXML
    public void closeWindow(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    public void minimizeWindow(MouseEvent event) {
        Stage stage = (Stage) scene.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void add(ActionEvent event) throws IOException {
        dashboard.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/add-word.fxml"));
        Node addBox = loader.load();
        dashboard.getChildren().add(addBox);
    }


    private double x_offset = 0;
    private double y_offset = 0;

    @FXML
    public void dragWindow(MouseEvent event) {
        Stage stage = (Stage) scene.getScene().getWindow();

        scene.setOnMousePressed(mouseEvent -> {
            x_offset = mouseEvent.getSceneX();
            y_offset = mouseEvent.getSceneY();
            System.out.println(x_offset);
            System.out.println(y_offset);
        });

        scene.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x_offset);
            stage.setY(mouseEvent.getScreenY() - y_offset);
            stage.setOpacity(0.5);
        });

        scene.setOnDragDone(mouseEvent -> {
            stage.setOpacity(1);
        });

        scene.setOnMouseReleased(mouseEvent -> {
            stage.setOpacity(1);
        });

    }

    @FXML
    public void actionMenu(ActionEvent event) {

    }


    @FXML
    void actionViews(ActionEvent event) throws IOException {
        dashboard.getChildren().clear();
        String cur = setDictionary.getValue().toString();
        System.out.println(cur);
        if (cur.equals("English - Vietnamese")) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/search-word.fxml"));
            Node searchBox = loader.load();
            dashboard.getChildren().add(searchBox);

            thesaurus.getChildren().clear();
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/resources/views/thesaurus.fxml"));
            Node thes = loader1.load();
            thesaurus.getChildren().add(thes);


        } else if (cur.equals("Translator")) {

            thesaurus.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/views/translator.fxml"));
            Node translatorBox = loader.load();
            dashboard.getChildren().add(translatorBox);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDictionary.getItems().addAll("English - Vietnamese", "Translator");
    }
}
