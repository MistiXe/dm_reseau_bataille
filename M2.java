import java.io.*;
import java.net.*;

public class M2 {
    public static char[][] grille = new char[3][3];

    public static void main(String[] args) {
        initialiserGrille();

        try {
            ServerSocket serveurSocket = new ServerSocket(12345);
            System.out.println("En attente de connexion...");
            Socket socket = serveurSocket.accept();
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            boolean tourServeur = false;
            while (true) {
                afficherGrille();

                // Informer le joueur dont c'est le tour
                if (tourServeur) {
                    System.out.println("Votre tour (Joueur 2) !");
                    jouerTour('O'); // Serveur joue avec 'O'
                } else {
                    output.println("VotreTour");
                    recevoirTour(input, 'X'); // Joueur 1 joue avec 'X'
                }

                // Envoyer la grille mise à jour au client
                for (int i = 0; i < 3; i++) {
                    output.println(new String(grille[i]));
                }

                // Vérifier la fin du jeu
                if (verifierVictoire('X')) {
                    output.println("Perdu"); // Joueur 2 (Serveur) a perdu
                    System.out.println("Le joueur X a gagné !");
                    break;
                } else if (verifierVictoire('O')) {
                    output.println("Gagne"); // Joueur 2 a gagné
                    System.out.println("Vous avez gagné !");
                    break;
                } else if (estMatchNul()) {
                    output.println("Egalite");
                    System.out.println("Match nul !");
                    break;
                } else {
                    output.println("Ta gueule");
                }

                tourServeur = !tourServeur;  // Changer de tour
            }
            socket.close();
            serveurSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initialiserGrille() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                grille[i][j] = ' ';
    }

    public static void afficherGrille() {
        for (char[] ligne : grille) {
            for (char cellule : ligne) {
                System.out.print("|" + cellule);
            }
            System.out.println("|");
        }
    }

    public static void recevoirTour(BufferedReader input, char symbole) throws IOException {
        while (true) {
            String[] move = input.readLine().split(" ");
            int ligne = Integer.parseInt(move[0]);
            int colonne = Integer.parseInt(move[1]);
            if (grille[ligne][colonne] == ' ') {
                grille[ligne][colonne] = symbole;
                break;
            } else {
                System.out.println("Case occupée, demande de rejouer.");
            }
        }
    }

    public static void jouerTour(char symbole) throws IOException {
        BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Entrez votre mouvement (ligne colonne) : ");
            String[] move = clavier.readLine().split(" ");
            int ligne = Integer.parseInt(move[0]);
            int colonne = Integer.parseInt(move[1]);
            if (grille[ligne][colonne] == ' ') {
                grille[ligne][colonne] = symbole;
                break;
            } else {
                System.out.println("Case déjà occupée, rejouez.");
            }
        }
    }

    public static boolean verifierVictoire(char symbole) {
        for (int i = 0; i < 3; i++) {
            if ((grille[i][0] == symbole && grille[i][1] == symbole && grille[i][2] == symbole) || // Ligne
                    (grille[0][i] == symbole && grille[1][i] == symbole && grille[2][i] == symbole)) { // Colonne
                return true;
            }
        }
        return (grille[0][0] == symbole && grille[1][1] == symbole && grille[2][2] == symbole) || // Diagonale
                (grille[0][2] == symbole && grille[1][1] == symbole && grille[2][0] == symbole);   // Diagonale inverse
    }

    public static boolean estMatchNul() {
        for (char[] ligne : grille) {
            for (char cellule : ligne) {
                if (cellule == ' ') return false;
            }
        }
        return true;
    }
}
