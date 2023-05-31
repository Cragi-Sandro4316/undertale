package utilities;
import javax.sound.sampled.*;
import java.io.File;

public class AudioPlayer {

    public static void playAudio(String audioFilePath) {
        try {
            File audioFile = new File(audioFilePath);

            if (!audioFile.exists()) {
                System.out.println("Audio file not found!");
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip) AudioSystem.getLine(info);

            clip.open(audioInputStream);

            clip.start();

            // Wait for the audio to finish playing
            while (!clip.isRunning())
                Thread.sleep(10);
            while (clip.isRunning())
                Thread.sleep(10);

            clip.close();
            audioInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


