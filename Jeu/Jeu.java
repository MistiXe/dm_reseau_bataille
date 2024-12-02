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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.SwingConstants.CENTER;

public class Jeu extends JFrame {

    private JButton[][] boutons = new JButton[10][10];
    private Map<String, Bateau> dico_b = new HashMap<>();
    private Son s = new Son("../dm_reseau_bataille/Jeu/Media/eau.wav");

    private BorderLayout bl = new BorderLayout();
    private JPanel panMain = new JPanel();
    private JPanel zoneJoueur = new JPanel();
    private GridLayout gl = new GridLayout(1, 3);
    private JLabel pseudo = new JLabel(" Pseudo : " + InetAddress.getLocalHost().getHostAddress());
    private int points = 1;
    private JLabel score = new JLabel("Score : " + points);
    private Map<String, Bateau> recu = new HashMap<>();



    public Jeu(Map<String, Bateau> liste_du_serveur) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        // Configuration initiale
        this.setTitle("Bataille Navale - Serveur");
        this.setSize(550, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel gridPanel = new JPanel(new GridLayout(10, 10));
        JButton[][] boutons = new JButton[10][10];
        boolean[] monTour = {true}; // Commence avec le serveur

        ServerSocket serveurSocket = new ServerSocket(12345);
        Socket socket = serveurSocket.accept();
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        // Création des boutons de la grille
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton bouton = new JButton();
                boutons[i][j] = bouton;
                bouton.setBackground(Color.CYAN);
                int x = i, y = j;
                boolean estCaseOccupee = false;

                bouton.addActionListener(e -> {
                    if (monTour[0]) {
                        bouton.setEnabled(false); // Désactiver le bouton
                        output.println("A ton tour"); // Envoyer la position au client
                        bouton.setBackground(Color.RED);
                        setEnabled(false); // Désactiver la fenêtre (c'est au tour de l'autre joueur)
                        monTour[0] = false; // Passer le tour
                        // Configuration des actions pour une case occupée par un bateau
                    }
                });



                gridPanel.add(bouton);
            }
        }


        new Thread(() -> {
                while (true) {
                    monTour[0] = true; // Tour au joueur actuel
                    setEnabled(true); // Réactiver la fenêtre
                }


        }).start();

        this.add(gridPanel);
        this.setVisible(true);
    }


    public void afficherDico (Map h){
            for (Map.Entry<String, Bateau> entry : dico_b.entrySet()) {
                System.out.println("Nom du pion: " + entry.getKey());
                System.out.println("Coordonnées :  " + entry.getValue().getCoordinates());
                System.out.println("Etat : " + entry.getValue().getStates());
                System.out.println("##############");
            }
        }



    }



