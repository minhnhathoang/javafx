
package gui.application;

import com.voicerss.tts.*;

import java.io.FileOutputStream;

public class VoiceRSS {

    private static String myKey = "40ccd1f320c549f3afc53b26046c49a4";
    public static String ACCENT = Languages.English_GreatBritain;

    public static void setACCENT(String accent) {
        if (accent.equals("us")) {
            ACCENT = Languages.English_UnitedStates;
        } else {
            ACCENT = Languages.English_GreatBritain;
        }
    }

    public static void requestDownload(String input, String nameFile) throws Exception {
        VoiceProvider tts = new VoiceProvider(myKey);

        System.out.println(ACCENT);
        VoiceParameters params = new VoiceParameters(input, ACCENT);
        params.setCodec(AudioCodec.MP3);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        byte[] voice = tts.speech(params);

        FileOutputStream fos = new FileOutputStream("src/resources/audio/" + nameFile + ".mp3");
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
    }
}

