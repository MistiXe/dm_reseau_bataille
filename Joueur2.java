import java.io.*;
import java.net.*;

public class Joueur2 {

    public static void main(String[] args) {
        try {
            // Le serveur écoute sur le port 12345
            ServerSocket serveurSocket = new ServerSocket(12345);
            System.out.println("Serveur démarré, en attente de connexion sur le port 12345...");

            // Attendre qu'un client se connecte
            Socket socket = serveurSocket.accept();
            System.out.println("Client connecté : " + socket.getInetAddress());

            // Flux de communication
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

            // Thread pour recevoir les messages du client
            Thread recevoirMessagesClient = new Thread(() -> {
                try {
                    String messageRecu;
                    while ((messageRecu = input.readLine()) != null) {
                        System.out.println( messageRecu);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            recevoirMessagesClient.start();

            // Envoyer des messages au client
            String messageEnvoye;
            while ((messageEnvoye = clavier.readLine()) != null) {
                output.println("Hodheyfa : " + messageEnvoye);
                if (messageEnvoye.equals("exit")) {
                    break; // Si le message est "exit", on termine
                }
            }

            // Fermer les ressources
            input.close();
            output.close();
            socket.close();
            serveurSocket.close();
            System.out.println("Serveur arrêté.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
