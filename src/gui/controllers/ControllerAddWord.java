package gui.controllers;

import gui.application.MerriamWebsterAPI;
import gui.application.SQLite;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerAddWord implements Initializable {

    private SQLite myDatabase;

    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private TextField boxEnter;
    @FXML
    private TextField boxPron;

    public void find() throws SQLException {
        String word = boxEnter.getText();
        if (word.equals("")) {
            return;
        }
        ResultSet resultSet = myDatabase.getResultSet(word);
        if (resultSet.next()) {
            htmlEditor.setHtmlText(resultSet.getString("html"));
            boxPron.setVisible(false);
        } else {
            boxPron.setVisible(true);
        }
    }

    public void add() throws SQLException {
        if (boxPron.isVisible()) {
            myDatabase.queryInsertHtml(boxEnter.getText(), htmlEditor.getHtmlText());
        } else {
            myDatabase.queryUpdateHtml(boxEnter.getText(), htmlEditor.getHtmlText());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            myDatabase = SQLite.getInstance();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        boxPron.setVisible(false);
    }
}
