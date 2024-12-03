package Jeu;

import Jeu.Connexion.Bateau;
import Jeu.Extra.Son;
import Jeu.Extra.Timer3min;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Jeu extends JFrame {

    private JButton[][] boutons = new JButton[10][10];
    private Map<String, Bateau> dico_b = new HashMap<>();
    private Son s = new Son("../dm_reseau_bataille/Jeu/Media/eau.wav");

    private JPanel gridPanel = new JPanel(new GridLayout(10, 10));
    private JLabel score = new JLabel("Pesudo : + " + InetAddress.getLocalHost().getHostAddress() + "  Score : 1");
    private int points = 1;

    private boolean monTour = true; // Indique si c'est le tour du joueur local
    private ServerSocket serveurSocket;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Jeu(Map<String, Bateau> liste_du_serveur) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        this.setTitle("Bataille Navale - Serveur");
        this.dico_b =liste_du_serveur;
        this.setSize(550, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        Timer3min t = new Timer3min(this);

        // Initialisation des sockets
        serveurSocket = new ServerSocket(12345);
        socket = serveurSocket.accept();
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);

        // Création de la grille
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton bouton = new JButton();
                boutons[i][j] = bouton;
                bouton.setBackground(Color.CYAN);
                int x = i, y = j;
                ArrayList<Integer> ar = new ArrayList<>();
                ar.add(i);
                ar.add(j);

                bouton.addActionListener(e -> {
                    if (monTour) {

                        if(estdanslaGrille(ar)) {
                            bouton.setEnabled(false);
                            bouton.setBackground(Color.RED);
                            s.play();
                            output.println("C'est a ton tour");
                            monTour = false;
                            setGrilleActive(false);
                            points++;
                            try {
                                score.setText("Pesudo : + " + InetAddress.getLocalHost().getHostAddress() + " Score : " + points);
                            } catch (UnknownHostException ex) {
                                throw new RuntimeException(ex);
                            }
                            try {
                                verifierFinDeJeu();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }

                        }else{
                            bouton.setEnabled(false);
                            monTour = false;
                            setGrilleActive(false);
                        }
                    }
                });


                gridPanel.add(bouton);
            }

        }

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(score, BorderLayout.SOUTH);
        this.add(t, BorderLayout.NORTH);

        // Ce que j'obtiens de l'adversaire
        new Thread(() -> {
            try {
                while (true) {
                    String message = input.readLine();
                    if (message != null) {
                        SwingUtilities.invokeLater(() -> {
                            monTour = true;
                            setGrilleActive(true);
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        this.setVisible(true);
    }

    private void verifierFinDeJeu() throws InterruptedException {
        if (points == 12) {
            JOptionPane.showMessageDialog(this, "Félicitations ! Vous avez gagné !", "Fin du jeu", JOptionPane.INFORMATION_MESSAGE);
            Thread.sleep(5000);
            try {
                socket.close();
                serveurSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    private void setGrilleActive(boolean active) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                boutons[i][j].setEnabled(active && boutons[i][j].getBackground() == Color.CYAN);
            }
        }
    }

    public boolean estdanslaGrille(ArrayList<Integer> testeur) {
        boolean estValide =  false;
        for (Map.Entry<String, Bateau> map : dico_b.entrySet()) {
            if (map.getValue().getCoordinates().contains(testeur)) {
                estValide = true;
            }
        }

        return estValide;
    }


}

