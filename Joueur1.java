import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Joueur1 extends JFrame {

    private JButton[][] boutons = new JButton[10][10];


    public Joueur1(ArrayList<ArrayList<Integer>> ar_coord) {
        this.setTitle("Partie Joueur 1 ");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        int lignes = 10;
        int colonnes = 10;
        JPanel gridPanel = new JPanel(new GridLayout(lignes, colonnes));
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                JButton button = new JButton();
                button.setBackground(Color.LIGHT_GRAY);
                boutons[i][j] = button;
                final int x_i = i;
                final int y_i = j;


                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        ArrayList<Integer> ar_ou_est_il = new ArrayList<>();
                        ar_ou_est_il.add(x_i);
                        ar_ou_est_il.add(y_i);

                        if(ar_coord.contains(ar_ou_est_il)){
                            button.setBackground(Color.RED);
                        }else {

                            button.setBackground(Color.BLUE);

                        }

                    }
                });

                // Ajouter le bouton au panneau de la grille
                gridPanel.add(button);
            }

            this.add(gridPanel);
            this.setVisible(true);


        }
    }

        public static void main (String[]args){
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
                            System.out.println(messageRecu);
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
