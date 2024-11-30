package Jeu.Connexion;

import java.util.ArrayList;

public class Bateau {

    private ArrayList<ArrayList<Integer>> coord;
    private ArrayList<String> etat;


    public Bateau(){
        this.coord = new ArrayList<>();
        this.etat = new ArrayList<>();
    }

    // Ajouter une coordonnée
    public void addCoordinate(ArrayList<ArrayList<Integer>> a_i) {
        coord = a_i;

    }

    // Ajouter un état
    public void addState(String state) {
        etat.add(state);
    }

    // Obtenir la liste des coordonnées
    public ArrayList<ArrayList<Integer>> getCoordinates() {
        return coord;
    }

    // Obtenir la liste des états
    public ArrayList<String> getStates() {
        return etat;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Coordinates: ");
        for (ArrayList<Integer> coord : coord) {
            sb.append(coord).append(" ");
        }
        sb.append("States: ").append(etat);
        return sb.toString();
    }
}

