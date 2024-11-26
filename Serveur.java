

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket client = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            // Création du serveur sur le port 4444
            server = new ServerSocket(4444);
            System.out.println("Serveur démarré, en attente de connexion...");

            // Attente de la connexion du client
            client = server.accept();
            System.out.println("Client connecté : " + client.getInetAddress());

            // Création des flux d'entrée et de sortie
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);

            // Communication avec le client
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Message reçu du client: " + message);
                // Répondre au client
                out.println("Message reçu: " + message);
            }

        } catch (IOException e) {
            System.out.println("Impossible d'écouter sur le port 4444: " + e.getMessage());
        } finally {
            // Fermeture des ressources
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (client != null) client.close();
                if (server != null) server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
