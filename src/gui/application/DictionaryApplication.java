package gui.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class DictionaryApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.setProperty("prism.lcdtext", "false");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/views/ui.fxml")));
        //stage.getIcons().add(new Image(DictionaryApplication.class.getResourceAsStream("../gui/graphics/logo.png")));
        stage.setTitle("Koko's dictionary");
        stage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = new Scene(root, 1023, 733);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

}
