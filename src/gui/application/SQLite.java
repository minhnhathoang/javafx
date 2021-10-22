package gui.application;

import java.sql.*;
import java.util.SortedSet;
import java.util.TreeSet;

public class SQLite {

    private static final String url = "jdbc:sqlite:src\\gui\\en-vi.db";
    public static SQLite instance = null;
    private Connection connection;

    public static SQLite getInstance() throws SQLException {
        if (instance == null) {
            instance = new SQLite();
            instance.connection = DriverManager.getConnection(url);
            System.out.println("SQLite is connected!");
        }
        return instance;
    }

    public SortedSet<String> getListWord() throws SQLException {
        String querySql = "SELECT word FROM av";

        Statement statement = connection.createStatement();
        statement.executeUpdate(querySql);
        ResultSet resultSet = statement.executeQuery(querySql);

        SortedSet<String> list = new TreeSet<String>();

        while (resultSet.next()) {
            list.add(resultSet.getString("word"));
        }
        return list;
    }

    public void queryDelete(String word) throws SQLException {
        String querySql = "DELETE FROM av WHERE word = '" + word + "'";

        Statement statement = connection.createStatement();
        statement.executeUpdate(querySql);

        System.out.println(querySql);
    }

    public void queryUpdateHtml(String word, String html) throws SQLException {
        String querySql = "UPDATE av SET html = '" + html + "' WHERE word ='" + word + "'";

        Statement statement = connection.createStatement();
        statement.executeUpdate(querySql);

        System.out.println(querySql);
    }

    public void queryInsertHtml(String word, String html) throws SQLException {
        String querySql = "INSERT INTO av(word, html) VALUES('" + word + "', '" + html + "')";

        Statement statement = connection.createStatement();
        statement.executeUpdate(querySql);

        System.out.println(querySql);
    }

    public ResultSet getResultSet(String word) throws SQLException {
        String querySql = "SELECT all word, description, pronunciation, html FROM av WHERE word='" + word + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(querySql);
        return resultSet;
    }

    public String[] selectAll(String word) throws SQLException {
        String querySql = "SELECT all word, description, pronunciation, html FROM av WHERE word='" + word + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(querySql);

        if (!resultSet.next()) {
            System.out.println("Result Set is null!");
            return null;
        }

        return new String[]{
                resultSet.getString("word"),
                resultSet.getString("pronunciation"),
                resultSet.getString("description"),
                resultSet.getString("html")
        };
    }

    public void finalize() throws SQLException {
        connection.close();
    }
}
