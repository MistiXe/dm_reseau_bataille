package Jeu.Connexion;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    private Map<String, Bateau> ar_envoye = new HashMap<>();

    public Client(Map<String, Bateau> liste_j1) throws IOException {
        this.ar_envoye = liste_j1;
        Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 12345);
        System.out.println("Connecté au serveur.");
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        boolean jeuEnCours = true;
        int tour = 1;

        while (jeuEnCours) {
            System.out.println("Tour " + tour++);

            // 1. Recevoir l'état actuel du serveur
            int serverMapSize = Integer.parseInt(in.readLine());
            Map<String, Bateau> receivedMap = new HashMap<>();
            for (int i = 0; i < serverMapSize; i++) {
                String key = in.readLine();
                String bateauStr = in.readLine();
                receivedMap.put(key, Bateau.fromString(bateauStr));
            }
            System.out.println("État reçu du serveur : " + receivedMap);

            // 2. Effectuer une action du client
            mettreAJourEtatClient();

            // 3. Envoyer l'état mis à jour au serveur
            out.println(ar_envoye.size());
            for (Map.Entry<String, Bateau> entry : ar_envoye.entrySet()) {
                out.println(entry.getKey());
                out.println(entry.getValue().toString());
            }

            // 4. Vérifier les instructions du serveur pour savoir si le jeu continue
            String instruction = in.readLine();
            if ("FIN".equals(instruction)) {
                System.out.println("Le jeu est terminé !");
                jeuEnCours = false;
            }
        }
        socket.close();
    }

    private void mettreAJourEtatClient() {
        // Exemple : Modifier l'état des bateaux pour simuler une action du client
        System.out.println("Action du client (simulation)...");
        // Ajoutez ici la logique de modification de l'état des bateaux
    }

    public Map<String, Bateau> getAr_envoye() {
        return ar_envoye;
    }
}
