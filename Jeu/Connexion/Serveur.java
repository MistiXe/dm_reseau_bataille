package Jeu.Connexion;



import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Serveur {

   public Serveur(Map<String, Bateau> liste_j1) throws IOException, ClassNotFoundException {
       int port  = 666;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Serveur en attente de connexion sur le port " + port);

           // Accepter la connexion du client
           Socket socket = serverSocket.accept();
           System.out.println("Client connecté : " + socket.getInetAddress());

           // Flux pour échanger les tableaux
           ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
           ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

           // Envoyer le tableau du serveur au client
           System.out.println("Envoi du tableau serveur : " + liste_j1);
           out.writeObject(liste_j1);

           // Recevoir le tableau du client
           Map<String, Bateau> ar = (Map) in.readObject();
           System.out.println("Tableau reçu du client : " + ar);

           // Mise à jour du tableau serveur
           liste_j1 = ar;

           // Fermer les flux et la connexion
           in.close();
           out.close();
           socket.close();
           System.out.println("Serveur terminé.");


   }
    }

