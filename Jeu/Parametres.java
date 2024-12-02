package Jeu;


import Jeu.Connexion.Bateau;
import Jeu.Extra.Etat_Pion;
import Jeu.Extra.Son;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Parametres extends JFrame implements ActionListener {
    // Variables Composants

    private final int SIZE;
    private final JPanel pan_main = new JPanel();
    private final BorderLayout bl = new BorderLayout();
    private final JPanel main_panel = new JPanel();
    private final GridLayout gl_settings = new GridLayout(6, 3);
    private final Integer[] choix_coord = {null, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final String[] statement = {"Horizontal", "Vertical"};
    private final JLabel nom_du_pion = new JLabel("Porte-Avion (5 Cases) : ");
    private final JLabel type = new JLabel("Type de bateau");
    private final JLabel type_de_X = new JLabel("Coordonnées en X");
    private final JLabel type_de_Y = new JLabel("Coordonnées en Y");
    private final JLabel type_de_Etat = new JLabel("Orientation");
    private final JLabel nom_du_pion2 = new JLabel("Croiseur (4 Cases) : ");
    private final JLabel nom_du_pion3 = new JLabel("Torpilleur (1 Case) : ");
    private final JLabel nom_du_pion4 = new JLabel("Sous-Marins (2 Cases) : ");
    private final JComboBox<Integer> LISTE_PION1 = new JComboBox<>(choix_coord);
    private final JComboBox<Integer> LISTE_PION2 = new JComboBox<>(choix_coord);
    private final JComboBox<Integer> LISTE_PION3 = new JComboBox<>(choix_coord);
    private final JComboBox<Integer> LISTE_PION4 = new JComboBox<>(choix_coord);
    private final JComboBox<Integer> LISTE_PION1_1 = new JComboBox<>(choix_coord);
    private final JComboBox<Integer> LISTE_PION2_1 = new JComboBox<>(choix_coord);
    private final JComboBox<Integer> LISTE_PION3_1 = new JComboBox<>(choix_coord);
    private final JComboBox<Integer> LISTE_PION4_1 = new JComboBox<>(choix_coord);
    private final JComboBox<String> LISTE_PION_AL1 = new JComboBox<>(statement);
    private final JComboBox<String> LISTE_PION_AL2 = new JComboBox<>(statement);
    private final JComboBox<String> LISTE_PION_AL3 = new JComboBox<>(statement);
    private final JComboBox<String> LISTE_PION_AL4 = new JComboBox<>(statement);
    private final ArrayList<JComboBox> liste_combo = new ArrayList<>();
    private final ArrayList<Etat_Pion> liste_etat = new ArrayList<>();
    private final ArrayList<JComboBox> liste_combo_etat = new ArrayList<>();
    private final JButton valider = new JButton("Valider");
    private final int cpt = 0;
    ArrayList<ArrayList<Integer>> tableau_de_zero = new ArrayList<>();
    Map<String, Bateau> dico_pion = new HashMap<>();
    private Son s;

    public Parametres(int taille) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.SIZE = taille;
        this.setVisible(true);
        this.setSize(new Dimension(600, 200));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Paramètres");
        this.setLocationRelativeTo(null);
        this.setContentPane(pan_main);
        s = new Son("../dm_reseau_bataille/Jeu/Media/draft.wav");
        s.loop();
        pan_main.setLayout(bl);
        pan_main.add(main_panel, BorderLayout.CENTER);
        pan_main.add(valider, BorderLayout.SOUTH);


        // Position des Composants dans un GridLayout
        this.main_panel.setBackground(Color.lightGray);
        this.main_panel.setLayout(gl_settings);
        this.main_panel.add(type);
        this.main_panel.add(type_de_X);
        this.main_panel.add(type_de_Y);
        this.main_panel.add(type_de_Etat);
        this.main_panel.add(nom_du_pion);
        this.main_panel.add(LISTE_PION1);
        this.main_panel.add(LISTE_PION1_1);
        this.main_panel.add(LISTE_PION_AL1);
        this.main_panel.add(nom_du_pion2);
        this.main_panel.add(LISTE_PION2);
        this.main_panel.add(LISTE_PION2_1);
        this.main_panel.add(LISTE_PION_AL2);
        this.main_panel.add(nom_du_pion3);
        this.main_panel.add(LISTE_PION3);
        this.main_panel.add(LISTE_PION3_1);
        this.main_panel.add(LISTE_PION_AL3);
        this.main_panel.add(nom_du_pion4);
        this.main_panel.add(LISTE_PION4);
        this.main_panel.add(LISTE_PION4_1);
        this.main_panel.add(LISTE_PION_AL4);


        liste_combo.add(LISTE_PION1);
        liste_combo.add(LISTE_PION1_1);
        liste_combo.add(LISTE_PION2);
        liste_combo.add(LISTE_PION2_1);
        liste_combo.add(LISTE_PION3);
        liste_combo.add(LISTE_PION3_1);
        liste_combo.add(LISTE_PION4);
        liste_combo.add(LISTE_PION4_1);

        liste_combo_etat.add(LISTE_PION_AL1);
        liste_combo_etat.add(LISTE_PION_AL2);
        liste_combo_etat.add(LISTE_PION_AL3);
        liste_combo_etat.add(LISTE_PION_AL4);

        valider.addActionListener(this);
        valider.setEnabled(false);

        // Actions

        for (int i = 0; i < liste_combo.size(); i++) {
            liste_combo.get(i).addActionListener(this);

        }

        for (int i = 0; i < liste_combo_etat.size(); i++) {
            liste_combo_etat.get(i).addActionListener(this);

        }

    }

    public static boolean testComboDescative(ArrayList<JComboBox> liste_de_combo_indice) {

        boolean combo_indice = true;

        for (JComboBox cb : liste_de_combo_indice) {
            if (cb.isEnabled()) {
                combo_indice = false;
            }
        }

        return combo_indice;
    }

    public static boolean testComboDescativeEtat(ArrayList<JComboBox> liste_de_combo_etat) {

        boolean combo_etat = true;

        for (JComboBox cb : liste_de_combo_etat) {
            if (cb.isEnabled()) {
                combo_etat = false;
            }
        }

        return combo_etat;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        boolean allValuesSelected = true;
        ArrayList<ArrayList<Integer>> coordinates = new ArrayList<>();

        // Parcourir les JComboBox par paires pour former des couples (x, y)
        for (int i = 0; i < liste_combo.size(); i += 2) {
            // Récupérer les éléments sélectionnés dans chaque JComboBox
            Object xObj = liste_combo.get(i).getSelectedItem();
            Object yObj = liste_combo.get(i + 1).getSelectedItem();

            // Vérifier que les deux valeurs sont sélectionnées
            if (xObj == null || yObj == null) {
                allValuesSelected = false; // Marque qu'une sélection est manquante
                break;
            }
            // Ajouter le couple (x, y) aux coordonnées
            int x = (int) xObj;
            int y = (int) yObj;
            ArrayList<Integer> sous_liste = new ArrayList<>();
            sous_liste.add(x);
            sous_liste.add(y);
            coordinates.add(sous_liste);
            this.liste_combo.get(i).setEnabled(false);
            this.liste_combo.get(i + 1).setEnabled(false);

        }


        if (e.getSource() == valider) {
            for (int i = 0; i < coordinates.size(); i++) {
                switch (i) {
                    case 0:
                        Bateau b = new Bateau();
                        b.addState(liste_etat.get(0).toString());
                        b.addCoordinate(generateIndex(coordinates.get(i), 5, liste_etat.get(0)));
                        dico_pion.put("Bateau à 5 cases", b);
                    case 1:
                        Bateau b1 = new Bateau();
                        b1.addState(liste_etat.get(1).toString());
                        b1.addCoordinate(generateIndex(coordinates.get(i), 4, liste_etat.get(1)));
                        dico_pion.put("Bateau à 4 cases : ", b1);
                    case 2:
                        Bateau b2 = new Bateau();
                        b2.addState(liste_etat.get(2).toString());
                        b2.addCoordinate(generateIndex(coordinates.get(i), 2, liste_etat.get(2)));
                        dico_pion.put("Bateau à 2 cases : ", b2);
                    case 3:
                        Bateau b3 = new Bateau();
                        b3.addState(liste_etat.get(3).toString());
                        b3.addCoordinate(generateIndex(coordinates.get(i), 1, liste_etat.get(3)));
                        dico_pion.put("Bateau à 1 cases : ", b3);

                }

            }

            // ZONE A EDITER

            if (estValide(dico_pion)) {
                try {
                    s.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                this.dispose();
                try {
                    //Client c = new Client(dico_pion);
                    Jeu j = new Jeu(dico_pion);


                } catch (UnknownHostException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);


                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Erreur dans la saisie de données , veuillez réessayer. ", "Erreur", JOptionPane.ERROR_MESSAGE);
                this.dispose();
                try {
                    new Parametres(10);
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }


        }


        for (JComboBox cbe : liste_combo_etat) {
            if (e.getSource() == cbe) {
                cbe.setEnabled(false);
                if (cbe.getSelectedItem().toString().equalsIgnoreCase("HORIZONTAL")) {
                    liste_etat.add(Etat_Pion.HORIZONTAL);

                } else {
                    liste_etat.add(Etat_Pion.VERTICAL);

                }
            }

        }


        if (testComboDescative(liste_combo) && testComboDescativeEtat(liste_combo_etat)) {
            valider.setEnabled(true);
        }
    }

    public ArrayList<ArrayList<Integer>> generateIndex(ArrayList<Integer> integ, int dupp, Etat_Pion e) {
        ArrayList<ArrayList<Integer>> liste_genere = new ArrayList<>();
        for (int i = 0; i < dupp; i++) {
            if (e.equals(Etat_Pion.VERTICAL)) {
                ArrayList<Integer> sous_liste = new ArrayList<>(integ);
                integ.set(0, integ.get(0) + 1);
                liste_genere.add(sous_liste);

            } else {
                ArrayList<Integer> sous_liste = new ArrayList<>(integ);
                integ.set(1, integ.get(1) + 1);
                liste_genere.add(sous_liste);
            }
        }

        return liste_genere;
    }

    public boolean estValide(Map<String, Bateau> dico_a_verifier) {
        boolean valide = true;
        ArrayList<ArrayList<Integer>> liste_de_coordonees = new ArrayList<>();
        for (Map.Entry<String, Bateau> m : dico_a_verifier.entrySet()) {
            ArrayList<ArrayList<Integer>> sous_liste = m.getValue().getCoordinates();
            for (ArrayList<Integer> a : sous_liste) {
                if (liste_de_coordonees.contains(a) || a.get(0) > 10 || a.get(1) > 10) {
                    valide = false;
                } else {
                    liste_de_coordonees.add(a);
                }

            }
        }
        return valide;
    }


}



