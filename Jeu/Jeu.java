package Jeu;

import Jeu.Connexion.Bateau;
import Jeu.Extra.Etats_bataille_Navale;
import Jeu.Extra.Son;
import Jeu.Extra.Timer3min;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
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
    private JLabel score = new JLabel("Pseudo : " + InetAddress.getLocalHost().getHostAddress() + "  Score : 1");
    private int points = 1;

    private boolean monTour = true; // Indique si c'est le tour du joueur local
    private ServerSocket serveurSocket;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Jeu(Map<String, Bateau> liste_du_serveur, String pseudo,String reseau) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        this.setTitle("Bataille Navale - Serveur");
        this.dico_b = liste_du_serveur;
        this.setSize(550, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        Timer3min t = new Timer3min(this);

        // Initialisation des sockets
        serveurSocket = new ServerSocket(12345);
        socket = serveurSocket.accept();
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(socket.getOutputStream(), true);

        // Échanger les dictionnaires des bateaux avec l'adversaire
        envoyerDictionnaire();
        recevoirDictionnaire();

        // Création de la grille
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton bouton = new JButton();
                boutons[i][j] = bouton;
                bouton.setBackground(Color.CYAN);
                ArrayList<Integer> ar = new ArrayList<>();
                ar.add(i);
                ar.add(j);

                bouton.addActionListener(e -> {
                    if (!monTour) {
                        return;
                    }

                    if (estdanslaGrille(ar)) {
                        bouton.setEnabled(false);
                        bouton.setBackground(Color.RED);
                        bouton.setText("X");
                        bouton.setForeground(Color.WHITE);
                        s.play();
                        output.println("TOUR");
                        monTour = false;
                        setGrilleActive(false);

                        points++;

                        score.setText("Pseudo : " + pseudo + " Score : " + points);

                        try {
                            verifierFinDeJeu();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        bouton.setEnabled(false);
                        bouton.setBackground(Color.DARK_GRAY);

                        // Passer le tour
                        output.println("TOUR");
                        monTour = false;
                        setGrilleActive(false);
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
                        if (message.equals("TOUR")) {
                            SwingUtilities.invokeLater(() -> {
                                monTour = true;
                                setGrilleActive(true);
                            });
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        this.setVisible(true);
    }









    private void envoyerDictionnaire() {
        try {
            for (Map.Entry<String, Bateau> entry : dico_b.entrySet()) {
                StringBuilder bateauData = new StringBuilder(entry.getKey());
                bateauData.append(",");
                for (ArrayList<Integer> coord : entry.getValue().getCoordinates()) {
                    bateauData.append(coord.get(0)).append(",").append(coord.get(1)).append(";");
                }
                output.println(bateauData.toString());
            }
            output.println("END"); // Indiquer la fin de l'envoi
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recevoirDictionnaire() {
        try {
            dico_b.clear(); // Vider le dictionnaire local
            String line;
            while (!(line = input.readLine()).equals("END")) {
                String[] parts = line.split(",", 2);
                String key = parts[0];
                Bateau bateau = new Bateau();

                if (parts.length > 1) {
                    String[] coordGroups = parts[1].split(";");
                    ArrayList<ArrayList<Integer>> coordinates = new ArrayList<>();

                    for (String group : coordGroups) {
                        if (!group.isEmpty()) {
                            String[] coordParts = group.split(",");
                            ArrayList<Integer> coord = new ArrayList<>();
                            coord.add(Integer.parseInt(coordParts[0]));
                            coord.add(Integer.parseInt(coordParts[1]));
                            coordinates.add(coord);
                        }
                    }

                    bateau.addCoordinate(coordinates);
                }

                dico_b.put(key, bateau);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void verifierFinDeJeu() throws InterruptedException {
        if (points == 12) {
            JOptionPane.showMessageDialog(this, "Félicitations ! Vous avez gagné !", "Fin du jeu", JOptionPane.INFORMATION_MESSAGE);
            Thread.sleep(5000);
            try {
                socket.close();
                if (serveurSocket != null) {
                    serveurSocket.close();
                }
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
        for (Map.Entry<String, Bateau> map : dico_b.entrySet()) {
            if (map.getValue().getCoordinates().contains(testeur)) {
                return true;
            }
        }
        return false;
    }
}