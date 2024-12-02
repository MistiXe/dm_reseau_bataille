package Jeu.Connexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Client {

    Map<String, Bateau> ar_envoye = new HashMap<>();


    public Client(Map<String, Bateau> liste_j1) throws IOException, ClassNotFoundException {
        this.ar_envoye = liste_j1;
        Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 12345);
        System.out.println("Connecté au serveur.");
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


        int serverMapSize = Integer.parseInt(in.readLine()); // Taille de la HashMap serveur
        Map<String, Bateau> receivedMap = new HashMap<>();
        for (int i = 0; i < serverMapSize; i++) {
            String key = in.readLine();              // Recevoir la clé
            String bateauStr = in.readLine();        // Recevoir la valeur (toString)
            receivedMap.put(key, Bateau.fromString(bateauStr)); // Reconstruire l'objet
        }
        System.out.println("HashMap reçu du serveur : " + receivedMap);


        // Envoyer chaque entrée de la HashMap client
        out.println(liste_j1.size()); // Envoyer la taille de la HashMap
        for (Map.Entry<String, Bateau> entry : liste_j1.entrySet()) {
            out.println(entry.getKey());         // Envoyer la clé
            out.println(entry.getValue().toString()); // Envoyer la valeur
        }


    }


    public Map<String, Bateau> getAr_envoye() {
        return ar_envoye;
    }
}
