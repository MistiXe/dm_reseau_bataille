import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Parametres extends JFrame implements ActionListener {
    // Variables Composants

    private int SIZE;
    private JPanel main_panel = new JPanel();
    private GridLayout gl_settings =  new GridLayout(5,3);
    private Integer[] choix_coord = {null,0,1,2,3,4,5,6,7,8,9};
    private String[] statement  =  {"Horizontal", "Vertical"};

    private JLabel nom_du_pion = new JLabel("Porte-Avion (5 Cases) : ");
    private JLabel nom_du_pion2 = new JLabel("Croiseur (4 Cases) : ");
    private JLabel nom_du_pion3 = new JLabel("Torpilleur (1 Case) : ");
    private JLabel nom_du_pion4 = new JLabel("Sous-Marins (2 Cases) : ");

    private JComboBox<Integer> LISTE_PION1 = new JComboBox<>(choix_coord);
    private JComboBox<Integer> LISTE_PION2 = new JComboBox<>(choix_coord);
    private JComboBox<Integer> LISTE_PION3 = new JComboBox<>(choix_coord);
    private JComboBox<Integer> LISTE_PION4 = new JComboBox<>(choix_coord);

    private JComboBox<Integer> LISTE_PION1_1 = new JComboBox<>(choix_coord);
    private JComboBox<Integer> LISTE_PION2_1 = new JComboBox<>(choix_coord);
    private JComboBox<Integer> LISTE_PION3_1 = new JComboBox<>(choix_coord);
    private JComboBox<Integer> LISTE_PION4_1 = new JComboBox<>(choix_coord);


    private JComboBox<String> LISTE_PION_AL1 = new JComboBox<>(statement);
    private JComboBox<String> LISTE_PION_AL2 = new JComboBox<>(statement);
    private JComboBox<String> LISTE_PION_AL3 = new JComboBox<>(statement);
    private JComboBox<String> LISTE_PION_AL4 = new JComboBox<>(statement);

    private ArrayList<JComboBox> liste_combo = new ArrayList<>();
    private JButton valider = new JButton("Valider");
    private JButton retour = new JButton("Retour");


    // Variables Controller

    ArrayList<ArrayList<Integer>> tableau_de_zero = new ArrayList<>();
    ArrayList<ArrayList<Integer>> liste_coord = new ArrayList<>();

    public Parametres(int taille){
        this.SIZE = taille;
        this.setVisible(true);
        this.setSize(new Dimension(600,200));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Paramètres");
        this.setContentPane(main_panel);

        // Position des Composants dans un GridLayout
        this.main_panel.setBackground(Color.lightGray);
        this.main_panel.setLayout(gl_settings);
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
        liste_combo.add(LISTE_PION2_1);
        liste_combo.add(LISTE_PION2);
        liste_combo.add(LISTE_PION2_1);
        liste_combo.add(LISTE_PION3);
        liste_combo.add(LISTE_PION3_1);
        liste_combo.add(LISTE_PION4);
        liste_combo.add(LISTE_PION4_1);


        // Actions

        for (int i = 0; i < liste_combo.size(); i++) {
            liste_combo.get(i).addActionListener(this);

        }

        genererGrilleVide();
        System.out.println(tableau_de_zero);
    }

    public ArrayList<Integer[]> genererGrilleVide(){
        for (int i = 0; i < SIZE ; i++) {
            ArrayList<Integer> ligne_tableau = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                ligne_tableau.add(0);
            }
            tableau_de_zero.add(ligne_tableau);
        }
        return null;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<int[]> coordinates = new ArrayList<>();
        boolean allValuesSelected = true; // Vérifie si toutes les valeurs sont sélectionnées

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
            coordinates.add(new int[]{x, y});
        }



        // Afficher les coordonnées
        System.out.println("Coordonnées :");
        for (int[] coordinate : coordinates) {
            System.out.println("(" + coordinate[0] + ", " + coordinate[1] + ")");
        }
    }


}
