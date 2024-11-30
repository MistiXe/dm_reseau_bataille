package Jeu;



import Jeu.Connexion.Bateau;
import Jeu.Extra.Timer3min;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.SwingConstants.CENTER;

public class Jeu extends JFrame {

    private JButton[][] boutons = new JButton[10][10];
    private Map<String , Bateau> dico_b = new HashMap<>();

    private BorderLayout bl =  new BorderLayout();
    private JPanel panMain = new JPanel();
    private JPanel zoneJoueur = new JPanel();
    private GridLayout gl = new GridLayout(1,3);
    private JLabel pseudo =  new JLabel(" Pseudo : " + InetAddress.getLocalHost().getHostAddress());
    private int points = 1;
    private JLabel score = new JLabel("Score : " + points);



    public Jeu(Map<String, Bateau> dico) throws IOException {
        this.dico_b =dico;
        afficherDico(dico_b);
        this.setTitle("Partie Joueur");
        this.setSize(550, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        int lignes = 10;
        int colonnes = 10;
        System.out.println(dico);
        JPanel gridPanel = new JPanel(new GridLayout(lignes, colonnes));

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                JButton bouton = new JButton();
                bouton.setFont(new Font("Arial", Font.BOLD, 20)); // Définit une grande police
                bouton.setBackground(Color.DARK_GRAY);
                ArrayList<Integer> ar = new ArrayList<>();
                ar.add(i);
                ar.add(j);
                for(Map.Entry<String , Bateau> m : dico_b.entrySet()){
                    if(m.getValue().getCoordinates().contains(ar)){

                        bouton.addActionListener(e -> points++);
                        bouton.addActionListener(e -> score.setText("Score : " + (points)));
                        bouton.addActionListener(e -> bouton.setBackground(Color.RED));
                        bouton.addActionListener(e -> bouton.setText("O"));
                    }
                }
                gridPanel.add(bouton);
            }
        }

            this.add(panMain);
            this.panMain.setLayout(bl);
            panMain.add(gridPanel, BorderLayout.CENTER);
            panMain.add(zoneJoueur, BorderLayout.SOUTH);
            zoneJoueur.setLayout(gl);
            zoneJoueur.add(pseudo);
            pseudo.setHorizontalAlignment(CENTER);
            score.setHorizontalAlignment(CENTER);
            zoneJoueur.add(score);
            zoneJoueur.add(new Timer3min(this));
            this.setVisible(true);


        }

        public void afficherDico(Map h){
            for (Map.Entry<String, Bateau> entry : dico_b.entrySet()) {
                System.out.println("Nom du pion: " + entry.getKey());
                System.out.println("Coordonnées :  " + entry.getValue().getCoordinates());
                System.out.println("Etat : " + entry.getValue().getStates());
                System.out.println("##############");
            }
        }


}


