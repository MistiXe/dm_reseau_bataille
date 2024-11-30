package Jeu.Connexion;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

public class Serveur {

   public Serveur(Map<String, Bateau> liste_j1) throws IOException, ClassNotFoundException {
       ServerSocket serveurSocket = new ServerSocket(12345);
       System.out.println("En attente de connexion...");
       Socket socket = serveurSocket.accept();
       BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

       // Envoyer chaque entrée de la HashMap
       output.println(liste_j1.size()); // Envoyer la taille de la HashMap
       for (Map.Entry<String, Bateau> entry : liste_j1.entrySet()) {
           output.println(entry.getKey());         // Envoyer la clé
           output.println(entry.getValue().toString()); // Envoyer la valeur
       }

       // Recevoir la HashMap du client
       int clientMapSize = Integer.parseInt(input.readLine()); // Taille de la HashMap client
       Map<String, Bateau> receivedMap = new HashMap<>();
       for (int i = 0; i < clientMapSize; i++) {
           String key = input.readLine();              // Recevoir la clé
           String bateauStr = input.readLine();        // Recevoir la valeur (toString)
           receivedMap.put(key, Bateau.fromString(bateauStr)); // Reconstruire l'objet
       }

       System.out.println("HashMap reçu du client : " + receivedMap);



   }




}

