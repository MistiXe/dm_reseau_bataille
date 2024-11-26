import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;


public class Parametres extends JFrame implements ActionListener {
    // Variables Composants

    private final int SIZE;

    private final JPanel pan_main  = new JPanel();
    private final BorderLayout bl = new BorderLayout();
    private final JPanel main_panel = new JPanel();
    private final GridLayout gl_settings =  new GridLayout(5,3);
    private final Integer[] choix_coord = {null,0,1,2,3,4,5,6,7,8,9};
    private final String[] statement  =  {"Horizontal", "Vertical"};

    private final JLabel nom_du_pion = new JLabel("Porte-Avion (5 Cases) : ");
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
    private final JButton valider = new JButton("Valider");
    
    private int cpt = 0;


    //private JButton retour = new JButton("Retour");


    // Variables Controller

    ArrayList<ArrayList<Integer>> tableau_de_zero = new ArrayList<>();
    ArrayList<ArrayList<Integer>> liste_coord = new ArrayList<>();
    ArrayList<ArrayList<Integer>> liste_coord_final = new ArrayList<>();

    public Parametres(int taille){
        this.SIZE = taille;
        this.setVisible(true);
        this.setSize(new Dimension(600,200));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Paramètres");
        this.setLocationRelativeTo(null);
        this.setContentPane(pan_main);
        pan_main.setLayout(bl);
        pan_main.add(main_panel, BorderLayout.CENTER);
        pan_main.add(valider, BorderLayout.SOUTH);


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
        liste_combo.add(LISTE_PION1_1);
        liste_combo.add(LISTE_PION2);
        liste_combo.add(LISTE_PION2_1);
        liste_combo.add(LISTE_PION3);
        liste_combo.add(LISTE_PION3_1);
        liste_combo.add(LISTE_PION4);
        liste_combo.add(LISTE_PION4_1);

        valider.addActionListener(this);
        valider.setEnabled(false);

        // Actions

        for (int i = 0; i < liste_combo.size(); i++) {
            liste_combo.get(i).addActionListener(this);

        }

      
        
    }

    public ArrayList<ArrayList<Integer>> genererGrilleVide(){
        for (int i = 0; i < SIZE ; i++) {
            ArrayList<Integer> ligne_tableau = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                ligne_tableau.add(0);
            }
            tableau_de_zero.add(ligne_tableau);
        }
        return tableau_de_zero;
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
            ArrayList<Integer> sous_liste =  new ArrayList<>();
            sous_liste.add(x);
            sous_liste.add(y);
            coordinates.add(sous_liste);
            this.liste_combo.get(i).setEnabled(false);
            this.liste_combo.get(i + 1).setEnabled(false);
           
            System.out.println(cpt);
            if(cpt == 7){
                valider.setEnabled(true);
                
            }
           
            
        }

        cpt++;

        if(e.getSource() == valider){
           
           


            generateIndex(coordinates, 5, 0);
            generateIndex(coordinates, 4, 1);
            generateIndex(coordinates, 1, 2);
            generateIndex(coordinates, 2, 3);
           if(estValide(coordinates)){
               this.dispose();
               Joueur1 j = new Joueur1(coordinates);

           }else{
               JOptionPane.showMessageDialog(null, "Erreur dans la saisie de données , veuillez réessayer. ", "Erreur", JOptionPane.ERROR_MESSAGE);
               this.dispose();
               new Parametres(10);
           }
        }

        
        
        


        
 
    
    }


    public ArrayList<ArrayList<Integer>> generateIndex(ArrayList<ArrayList<Integer>> ar, int dupp, int indice){
        for(int i = 0 ; i< ar.size(); i++){
           if(i == indice){
            for (int j = 1; j < dupp; j++) {
                ArrayList<Integer> ar_ind = new ArrayList<>();
                ar_ind.add(ar.get(i).get(0));
                ar_ind.add(ar.get(i).get(1)+j);
                ar.add(ar_ind);   
            }
           }
         }

         return ar;
        }


        public boolean estValide(ArrayList<ArrayList<Integer>> ar){
            HashSet<ArrayList<Integer>> H = new HashSet();
            for(ArrayList<Integer> ar_bis : ar){
                H.add(ar_bis);
            }
            return H.size() == ar.size();
    }

        
    }



