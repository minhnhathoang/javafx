package gui.application;

import org.json.JSONArray;

import javax.json.JsonArray;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MerriamWebsterAPI {
    private final static String KEY_DICTIONARY = "481c6b37-6a3e-4fcb-a1f6-7e84751a86fc";
    private final static String KEY_THERAURUS = "a897e3af-96b3-4e3a-960c-0f2b8672da4e";
    public static final MerriamWebsterAPI api;

    static {
        api = new MerriamWebsterAPI();
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        // System.out.println(sb);
        return sb.toString();
    }

    private URL url(String word, String ref) throws MalformedURLException {
        String keyAPI;
        if (ref.equals("collegiate")) {
            keyAPI = KEY_DICTIONARY;
        } else {
            keyAPI = KEY_THERAURUS;
        }
        return new URL("https://dictionaryapi.com/api/v3/references/" + ref + "/json/" + word + "?key=" + keyAPI);

    }

    private URL urlDict(String word) throws MalformedURLException {
        return url(word, "collegiate");
    }

    private URL urlThes(String word) throws MalformedURLException {
        return url(word, "thesaurus");
    }

    public JSONArray requestJsonDict(String word) throws IOException {
        URL url = urlDict(word);
        try (InputStream is = url.openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String jsonText = readAll(rd);
            return new JSONArray(jsonText);
        }
    }

    public JSONArray requestJsonThes(String word) throws IOException {
        URL url = urlThes(word);
        try (InputStream is = url.openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String jsonText = readAll(rd);
            return new JSONArray(jsonText);
        }
    }

    //https://media.merriam-webster.com/audio/prons/[language_code = en]/[country_code = es]/[format]/[subdirectory]/[base filename].[format]
    public void show(String myWord) throws IOException {
        JSONArray jsonArray = requestJsonDict(myWord);
    }

    public String getSubdirectory(String word) throws IOException {
        return requestJsonDict(word)
                .getJSONObject(0)
                .getJSONObject("hwi")
                .getJSONArray("prs")
                .getJSONObject(0)
                .getJSONObject("sound")
                .getString("audio");
    }

    public String urlPronounce(String word) throws IOException {
        String fileName = getSubdirectory(word);
        String subdirectory = "number";
        char firstChar = fileName.charAt(0);
        if (fileName.startsWith("bix")) {
            subdirectory = "bix";
        } else if (fileName.startsWith("gg")) {
            subdirectory = "gg";
        } else if ((firstChar >= 'a' && firstChar <= 'z') || (firstChar >= 'A' && firstChar <= 'Z')) {
            subdirectory = Character.toString(firstChar);
        }
        return "https://media.merriam-webster.com/audio/prons/en/us/" + "mp3/" + subdirectory + "/" + fileName + ".mp3";
    }

    public List<String> getListSymonymsFromJson(JSONArray json) {
        System.out.println(json.toString());
        String request = json.getJSONObject(0)
                .getJSONObject("meta")
                .getJSONArray("syns")
                .getJSONArray(0)
                .toString();
        request = request.replace("[", "");
        request = request.replace("]", "");
        request = request.replace("\"", "");
        return new ArrayList<String>(Arrays.asList(request.split(",")));
    }

    public List<String> getListAntonymsJson(JSONArray json) {
        System.out.println(json.toString());
        String request = json.getJSONObject(0)
                .getJSONObject("meta")
                .getJSONArray("ants")
                .getJSONArray(0)
                .toString();
        request = request.replace("[", "");
        request = request.replace("]", "");
        request = request.replace("\"", "");
        return new ArrayList<String>(Arrays.asList(request.split(",")));
    }

    public static void main(String[] args) throws IOException {

    }

}
