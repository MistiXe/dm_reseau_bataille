import java.io.*;
import java.net.*;

public class Joueur1 {

    public static void main(String[] args) {
        try {
            // Connexion au serveur (localhost et port 12345)
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connecté au serveur.");

            // Flux de communication
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

            // Thread pour recevoir les messages du serveur
            Thread recevoirMessagesServeur = new Thread(() -> {
                try {
                    String messageRecu;
                    while ((messageRecu = input.readLine()) != null) {
                        System.out.println( messageRecu);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            recevoirMessagesServeur.start();

            // Envoyer des messages au serveur
            String messageEnvoye;
            while ((messageEnvoye = clavier.readLine()) != null) {
                output.println("Alexis : " + messageEnvoye);
                if (messageEnvoye.equals("exit")) {
                    break; // Si le message est "exit", on termine
                }
            }

            // Fermer les ressources
            input.close();
            output.close();
            socket.close();
            System.out.println("Connexion fermée.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
