package Jeu.Extra;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Temps extends JPanel {

    private static final int TIMER_DURATION = 2 * 60; // Durée en secondes (3 minutes)
    private int timeRemaining = TIMER_DURATION;      // Temps restant en secondes
    private final JLabel timerLabel;
    private Timer timer;
    private Son s = new Son("../dm_reseau_bataille/Jeu/Media/coultdown.wav");


    public Temps(JFrame f, Son a_terminer, Son a_lancer) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        // Configurer le panel
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(100, 50));

        // Ajouter un JLabel pour afficher le temps restant
        timerLabel = new JLabel(formatTime(timeRemaining), SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 12));
        this.add(timerLabel, BorderLayout.CENTER);

        // Démarrer le timer
        startTimer(f, a_terminer, a_lancer);
    }

    private void startTimer(JFrame f, Son a_terminer, Son a_lancer) {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timeRemaining <20){
                    setBackground(Color.RED);
                    setForeground(Color.WHITE);
                    try {
                        a_terminer.close();
                        a_lancer.loop();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }








                if (timeRemaining > 0) {
                    timeRemaining--; // Réduire le temps restant
                    timerLabel.setText(formatTime(timeRemaining)); // Mettre à jour l'affichage
                } else {
                    timer.stop(); // Arrêter le timer
                    timerLabel.setText("Temps écoulé !");
                    JOptionPane.showMessageDialog(null, "Le temps est écoulé !");
                    f.dispose();
                }
            }
        });
        timer.start();
    }


    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("Temps restant  : %02d:%02d", minutes, secs);
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }
}
