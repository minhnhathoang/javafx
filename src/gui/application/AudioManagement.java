package gui.application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class AudioManagement {

    public static void download(String path) throws IOException {
        URLConnection conn = new URL(path).openConnection();
        InputStream is = conn.getInputStream();

        OutputStream outstream = new FileOutputStream(new File("src/resources/audio/merriam_webster_api.mp3"));
        byte[] buffer = new byte[4096];
        int len;
        while ((len = is.read(buffer)) > 0) {
            outstream.write(buffer, 0, len);
        }
        outstream.close();
    }


    private static Media media;
    private static MediaPlayer mediaPlayer;

    public static void playAudio(String path) throws InterruptedException {
        media = new Media(path);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void textToSpeechFromRSS(String text, String nameFile) throws Exception {
        VoiceRSS.requestDownload(text, nameFile);
        String path = new File("src/resources/audio/" + nameFile + ".mp3").toURI().toString();
        System.out.println(path);
        AudioManagement.playAudio(path);
    }
}
