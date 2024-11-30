package Jeu.Connexion;

import java.util.ArrayList;

public class Bateau {

    private ArrayList<ArrayList<Integer>> coord;
    private ArrayList<String> etat;

    public Bateau() {
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

        // Convertir les coordonnées en chaîne
        sb.append("coord=");
        for (ArrayList<Integer> subList : coord) {
            sb.append("[");
            for (Integer val : subList) {
                sb.append(val).append(",");
            }
            sb.deleteCharAt(sb.length() - 1); // Supprimer la dernière virgule
            sb.append("];");
        }

        // Convertir les états en chaîne
        sb.append("etat=");
        for (String state : etat) {
            sb.append(state).append(",");
        }
        if (!etat.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1); // Supprimer la dernière virgule
        }

        return sb.toString();
    }

    // Méthode pour reconstruire un objet Bateau à partir d'une chaîne
    public static Bateau fromString(String str) {
        Bateau bateau = new Bateau();

        String[] parts = str.split("etat="); // Séparer les coordonnées des états
        if (parts.length != 2) return null; // Format invalide

        // Traiter les coordonnées
        String coordPart = parts[0].replace("coord=", "").trim();
        String[] coordGroups = coordPart.split(";");
        for (String group : coordGroups) {
            if (group.isEmpty()) continue;

            group = group.replace("[", "").replace("]", ""); // Retirer les crochets
            String[] numbers = group.split(",");
            ArrayList<Integer> subList = new ArrayList<>();
            for (String num : numbers) {
                subList.add(Integer.parseInt(num.trim()));
            }
            bateau.coord.add(subList);
        }

        // Traiter les états
        String etatPart = parts[1].trim();
        String[] states = etatPart.split(",");
        for (String state : states) {
            bateau.etat.add(state.trim());
        }

        return bateau;
    }
}
