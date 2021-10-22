package gui.application;

import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonManagement {

    private static final String keyAPI = "481c6b37-6a3e-4fcb-a1f6-7e84751a86fc";

    private static URL url(String word, String ref, String key) throws MalformedURLException {
        return new URL("https://dictionaryapi.com/api/v3/references/" + ref + "/json/" + word + "?key=" + key);
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        System.out.println(sb);
        return sb.toString();
    }

    public static JSONArray request(String word, String ref, String key) throws IOException {
        URL url = url(word, ref, key);
        try (InputStream is = url.openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String jsonText = readAll(rd);
            return new JSONArray(jsonText);
        }
    }

    public static void show(String word, String ref, String key) throws IOException {
        JSONArray json = request(word, ref, key);
        Text u = new Text();
        u.setText((String) json.getJSONObject(0).get("meta"));


    }

    public static void main(String[] args) throws IOException {
        JSONArray u = request("cool", "collegiate", keyAPI);
        String cur = u.getJSONObject(0).getJSONObject("hwi").getString("hw");
        //JSONObject cur = u.getJSONArray(0).getJSONObject("hwi").getJSONObject("hw");
        System.out.println(cur);
    }
}
