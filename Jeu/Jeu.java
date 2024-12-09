package Jeu;

import Jeu.Extra.Etats_bataille_Navale;
import Jeu.Extra.Son;
import Jeu.Extra.Temps;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Jeu extends JFrame {

    private final JButton[][] boutons = new JButton[10][10];
    private Map<String, Bateau> dico_b = new HashMap<>();
    private final Son s = new Son("../dm_reseau_bataille/Jeu/Media/eau.wav");

    private final JPanel gridPanel = new JPanel(new GridLayout(11, 11)); // 11x11 pour inclure les en-têtes
    private final JLabel pseudo_i = new JLabel();
    private final JLabel passe_tour = new JLabel("Au tour de : ");
    private int points = 1;
    private final JPanel pan_south = new JPanel(new GridLayout(1, 4));
    private final JLabel p = new JLabel("Votre score est de " + points);

    private boolean monTour = true;
    private ServerSocket serveurSocket;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String pseudoLocal; // Pseudo du joueur local
    private String pseudoAdversaire; // Pseudo de l'adversaire

    public Jeu(Map<String, Bateau> liste_du_serveur, Etats_bataille_Navale.Etat reseau)
            throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        this.setTitle("Bataille Navale - " + reseau);
        this.dico_b = liste_du_serveur;
        this.setSize(600, 600);  // Ajustement de la taille pour accueillir l'entête
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        Temps t = new Temps(this);
        this.add(pan_south, BorderLayout.SOUTH);
        pan_south.add(this.pseudo_i);
        pan_south.add(p);
        pan_south.add(passe_tour);
        pan_south.add(t);


        if (reseau.equals(Etats_bataille_Navale.Etat.SERVEUR)) {
            serveurSocket = new ServerSocket(12345);
            socket = serveurSocket.accept();
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true); // Initialisation de output
            pseudoLocal = JOptionPane.showInputDialog(this, "Entrez votre pseudo :");
            pseudoAdversaire = input.readLine();
            output.println(pseudoLocal);
        } else {
            String adresseIP = JOptionPane.showInputDialog(null,
                    "Entrez l'adresse IP du serveur :",
                    "Connexion au serveur",
                    JOptionPane.QUESTION_MESSAGE);
            socket = new Socket(adresseIP, 12345);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            pseudoLocal = JOptionPane.showInputDialog(this, "Entrez votre pseudo :");
            output.println(pseudoLocal);
            pseudoAdversaire = input.readLine();
        }

        // Mise à jour des labels après la saisie des pseudos
        pseudo_i.setText("Joueur : " + pseudoLocal);
        updateLabelTour();

        envoyerDictionnaire();
        recevoirDictionnaire();


        JPanel lettres_grille = new JPanel(new GridLayout(1, 11));
        lettres_grille.add(new JLabel("")); // Coin supérieur gauche vide
        for (char c = 'A'; c <= 'J'; c++) {
            lettres_grille.add(new JLabel(String.valueOf(c), SwingConstants.CENTER));
        }


        for (int i = 0; i < 10; i++) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 11));
            rowPanel.add(new JLabel(String.valueOf(i), SwingConstants.CENTER));
            for (int j = 0; j < 10; j++) {
                JButton bouton = new JButton();
                boutons[i][j] = bouton;
                bouton.setBackground(Color.BLUE);
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
                        bouton.setFont(new Font("Arial", Font.BOLD, 30));  
                        s.play();
                        output.println("TOUR");
                        monTour = false;

                        // Mise à jour du label après avoir cliqué
                        updateLabelTour();
                        setGrilleActive(false);
                        points++;
                        p.setText("Votre score est de " + points);

                        try {
                            verifierFinDeJeu();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        bouton.setEnabled(false);
                        bouton.setBackground(Color.DARK_GRAY);
                        output.println("TOUR");
                        monTour = false;

                        // Mise à jour du label après avoir cliqué
                        updateLabelTour();
                        setGrilleActive(false);
                    }
                });

                rowPanel.add(bouton);
            }
            gridPanel.add(rowPanel);  // Ajout de chaque ligne à la grille
        }


        this.add(lettres_grille, BorderLayout.NORTH);
        this.add(gridPanel, BorderLayout.CENTER);



        new Thread(() -> {
            try {
                while (true) {
                    String message = input.readLine();
                    if (message != null) {
                        if (message.equals("TOUR")) {
                            SwingUtilities.invokeLater(() -> {
                                monTour = true;
                                updateLabelTour();
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

    // Met à jour le label qui affiche le tour du joueur
    private void updateLabelTour() {
        if (monTour) {
            passe_tour.setText("Au tour de : " + pseudoLocal);
        } else {
            passe_tour.setText("Au tour de : " + pseudoAdversaire);
        }
    }

    // Envoie le dictionnaire des bateaux à l'adversaire
    private void envoyerDictionnaire() {
        try {
            for (Map.Entry<String, Bateau> entry : dico_b.entrySet()) {
                StringBuilder bateauData = new StringBuilder(entry.getKey());
                bateauData.append(",");
                for (ArrayList<Integer> coord : entry.getValue().getCoordinates()) {
                    bateauData.append(coord.get(0)).append(",").append(coord.get(1)).append(";");
                }
                output.println(bateauData);
            }
            output.println("END");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Reçoit le dictionnaire des bateaux de l'adversaire
    private void recevoirDictionnaire() {
        try {
            dico_b.clear();
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

    // Vérifie si un joueur a gagné
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

    // Active ou désactive la grille de boutons
    private void setGrilleActive(boolean active) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                boutons[i][j].setEnabled(active && boutons[i][j].getBackground() == Color.BLUE);  // Seules les cases bleues sont activées
            }
        }
    }

    // Vérifie si les coordonnées sont valides dans la grille
    public boolean estdanslaGrille(ArrayList<Integer> testeur) {
        for (Map.Entry<String, Bateau> map : dico_b.entrySet()) {
            if (map.getValue().getCoordinates().contains(testeur)) {
                return true;
            }
        }
        return false;
    }
}
