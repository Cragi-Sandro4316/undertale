package utilities;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;


class Music {


    /**
     * @param musicLoc a java non piacciono le directory relative, fare ctrl c ctrl v dal gestore di file.
     * (certified java moment)
     * ah si, gli piacciono solo i file .wav ..
     * voglio imparare il rust.
     */
    void playMusic(String musicLoc) {

        try {
            File musicPath = new File(musicLoc);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                // JOptionPane.showMessageDialog(null,"Press ok to stop playing");
                clip.drain();
                clip.flush();
                clip.stop();
            } else {
                System.out.println("Couldn't find Music file");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



}
