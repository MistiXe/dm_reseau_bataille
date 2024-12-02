package Jeu.Connexion;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Serveur {

    private Map<String, Bateau> recu = new HashMap<>();

    public Serveur(Map<String, Bateau> liste_j1) throws IOException {
        ServerSocket serveurSocket = new ServerSocket(12345);
        System.out.println("En attente de connexion...");
        Socket socket = serveurSocket.accept();
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("Client connecté.");

        // Boucle de jeu en mode tour par tour
        boolean jeuEnCours = true;
        int tour = 1;

        while (jeuEnCours) {
            System.out.println("Tour " + tour++);

            // 1. Envoyer l'état actuel au client
            output.println(liste_j1.size()); // Envoyer la taille de la HashMap
            for (Map.Entry<String, Bateau> entry : liste_j1.entrySet()) {
                output.println(entry.getKey()); // Envoyer la clé
                output.println(entry.getValue().toString()); // Envoyer la valeur
            }

            // 2. Recevoir l'état mis à jour du client
            int clientMapSize = Integer.parseInt(input.readLine()); // Taille de la HashMap client
            recu.clear();
            for (int i = 0; i < clientMapSize; i++) {
                String key = input.readLine(); // Recevoir la clé
                String bateauStr = input.readLine(); // Recevoir la valeur
                recu.put(key, Bateau.fromString(bateauStr)); // Reconstruire l'objet
            }
            System.out.println("État reçu du client : " + recu);

            // 3. Simuler une action du serveur (par exemple, mise à jour des bateaux)
            mettreAJourEtatServeur();

            // 4. Vérifier si le jeu doit se terminer
            if (verifierFinDuJeu()) {
                jeuEnCours = false;
                // Affiche une popup
                JOptionPane.showMessageDialog(null, "Vous avez gagné !", "Félicitations", JOptionPane.INFORMATION_MESSAGE);
                // Redémarre l'application
                relancerProgramme();
                try {
                    Thread.sleep(1000); // Pause pour simuler la boucle de jeu
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                output.println("CONTINUER"); // Indiquer au client que le jeu continue
            }
        }

        System.out.println("Le jeu est terminé !");
        socket.close();
        serveurSocket.close();
    }

    private void relancerProgramme() throws IOException {
        // Récupère le chemin de l'exécutable Java
        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        // Récupère le chemin du fichier jar ou classe principale en cours d'exécution
        String currentJar = new File(Serveur.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath()).getAbsolutePath();
        // Construire la commande pour redémarrer
        ProcessBuilder builder = new ProcessBuilder(javaBin, "-jar", currentJar);
        builder.start(); // Lance le nouveau processus
        System.exit(0); // Arrête le programme actuel
    }

    private void mettreAJourEtatServeur() {
        // Exemple : Action simulée sur les bateaux du serveur
        System.out.println("Action du serveur (simulation)...");
        // Ajouter ici la logique d'attaque ou de déplacement
    }

    private boolean verifierFinDuJeu() {
        // if  score de (soit serveur soit client) == 12
                // return True
        return false;
    }

    public Map<String, Bateau> getRecu() {
        return recu;
    }
}
