package Jeu.Extra;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Son {

    private Clip c;
    private  AudioInputStream audioIn;
    public Son(String chemin_de_donnees) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        File f = new File(chemin_de_donnees);
        audioIn = AudioSystem.getAudioInputStream(f);
        c = AudioSystem.getClip();
        c.open(audioIn);
        this.setVolume(-20);
        
    }

    // Méthode pour jouer le son
    public void play() {
        if (c != null) {
            c.setFramePosition(0); // Revenir au début
            c.start();
        }
    }

    // Méthode pour jouer en boucle
    public void loop() {
        if (c != null) {
            c.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    // Méthode pour arrêter le son
    public void stop() {
        if (c != null && c.isRunning()) {
            c.stop();
        }
    }

    // Méthode pour libérer les ressources
    public void close() throws IOException {
        if (c != null) {
            c.close();

        }
    }

    // Vérifie si le son est en cours de lecture
    public boolean isPlaying() {
        return c != null && c.isRunning();
    }

    public void setVolume(float volume) {
        if (c != null) {
            if (c != null) {
                FloatControl volumeControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
                volume = Math.min(volumeControl.getMaximum(), Math.max(volumeControl.getMinimum(), volume)); // Clamping
                volumeControl.setValue(volume);
            }
        }
    }


}
