package gui.application;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GoogleTranslate {

    private final static String myKey = "a1eee83ba1msh94476e54672bd76p13321cjsn31948ee29ba1";
    private final static String baseURI = "https://google-translate1.p.rapidapi.com/language/translate/v2";

    public GoogleTranslate() {

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        GoogleTranslate a = new GoogleTranslate();
        System.out.println(a.getTranslationFromJSON("con mèo meo meo"));
    }

    private static String handleString(String input, String srcISOCode, String dstISOCode) {
        return new String("q=" + input + "&target=" + dstISOCode + "&source=" + srcISOCode);
    }

    public static String request(String inputText) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURI))
                .header("content-type", "application/x-www-form-urlencoded")
                .header("accept-encoding", "application/gzip")
                .header("x-rapidapi-host", "google-translate1.p.rapidapi.com")
                .header("x-rapidapi-key", myKey)
                .method("POST", HttpRequest.BodyPublishers.ofString(handleString(inputText, "vi", "en")))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        return new String(response.body().toString());
    }

    public static String getTranslationFromJSON(String inputText) throws IOException, InterruptedException {
        JSONObject jsonObject = new JSONObject(request(inputText));
        return jsonObject.getJSONObject("data")
                .getJSONArray("translations")
                .getJSONObject(0)
                .getString("translatedText");
    }

    public static String translate(String text, String srcISO, String desISO) throws IOException {
        String urlStr = "https://script.google.com/macros/s/AKfycbzzPDVP6IUDO2Pd-UW2OmrzHsA11MM7sCe4BDIZJyqsLVk4dHDpJXe7a67LCEFScc0/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + desISO +
                "&source=" + srcISO;
        URL url = new URL(urlStr);

        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
