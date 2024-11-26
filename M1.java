import java.io.*;
import java.net.*;

public class M1 {
    public static void main(String[] args) {
        char[][] grille = new char[3][3];
        initialiserGrille(grille);

        try {
            Socket socket = new Socket("localhost", 12345);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                afficherGrille(grille);

                // Recevoir l'indication si c'est au tour du joueur
                String tour = input.readLine();
                if (tour.equals("VotreTour")) {
                    // Joueur 1 entre son mouvement
                    System.out.print("Entrez votre mouvement (ligne colonne) : ");
                    String move = clavier.readLine();
                    output.println(move); // Envoi du mouvement au serveur
                }

                // Recevoir la grille mise à jour
                for (int i = 0; i < 3; i++) {
                    String ligne = input.readLine();
                    grille[i] = ligne.toCharArray();
                }

                // Vérifier si le jeu est terminé
                String statut = input.readLine();
                if (statut.equals("Gagne")) {
                    afficherGrille(grille);
                    System.out.println("Vous avez gagné !");
                    break;
                } else if (statut.equals("Perdu")) {
                    afficherGrille(grille);
                    System.out.println("Vous avez perdu !");
                    break;
                } else if (statut.equals("Egalite")) {
                    afficherGrille(grille);
                    System.out.println("Match nul !");
                    break;
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initialiserGrille(char[][] grille) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                grille[i][j] = ' ';
    }

    public static void afficherGrille(char[][] grille) {
        for (char[] ligne : grille) {
            for (char cellule : ligne) {
                System.out.print("|" + cellule);
            }
            System.out.println("|");
        }
    }
}
