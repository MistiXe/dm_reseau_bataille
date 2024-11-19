package hmar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {
    public static void main(String[] args) {
        Socket client = null;
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in)); // Pour lire depuis le terminal

        try {
            // Connexion au serveur
            client = new Socket("localhost", 4444);
            System.out.println("Connecté au serveur.");

            // Création des flux d'entrée et de sortie
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            // Interaction avec l'utilisateur et le serveur
            String userMessage;
            while (true) {
                System.out.print("Entrez un message à envoyer au serveur : ");
                userMessage = userInput.readLine();

                if (userMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Déconnexion du client...");
                    break;
                }

                // Envoi du message au serveur
                out.println(userMessage);
                
                // Réception et affichage de la réponse du serveur
                String serverResponse = in.readLine();
                System.out.println("Réponse du serveur: " + serverResponse);
            }

        } catch (UnknownHostException e) {
            System.out.println("Erreur d'adresse inconnue : " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erreur de communication avec le serveur : " + e.getMessage());
        } finally {
            // Fermeture des ressources
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (client != null) client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
