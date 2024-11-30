package Jeu.Connexion;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Client {

    public Client(Map<String, Bateau> liste_j1) throws IOException, ClassNotFoundException {

        String host = InetAddress.getLocalHost().getHostAddress();
        int port = 666;
        Socket socket = new Socket(host, port);
        System.out.println("Connecté au serveur.");

            // Flux pour échanger les tableaux
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Recevoir le tableau du serveur
        Map<String, Bateau> ar = (Map) in.readObject();
        System.out.println("Tableau reçu du client : " + ar);

        // Envoyer le tableau du client
        System.out.println("Envoi du tableau client : " + ar);
        out.writeObject(ar);

        // Mise à jour du tableau client
        liste_j1 = ar;


    }
}
