package Jeu;

import Jeu.Extra.Son;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Interface extends JFrame implements ActionListener {


    private Image img;
    private Son s = new Son("../dm_reseau_bataille/Jeu/Media/wano.wav");
    private JLabel lab_titre = new JLabel("La bataille navale");
    private int size = 2;
    private int size2 = 1;
    private GridLayout gl = new GridLayout(size, size2);
    private GridBagConstraints gbl = new GridBagConstraints();
    private JButton button1 = new JButton("Lancer une nouvelle partie");
    private JButton button2 = new JButton("Règles du Jeu");


    public Interface() throws IOException, UnsupportedAudioFileException, LineUnavailableException {


        // Création de la fenêtre
        this.setTitle("Bataille Navale");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);


        // Pannel / BorderLayout

        JPanel pan = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("../dm_reseau_bataille/Jeu/Media/bn2.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button1.setBackground(Color.lightGray);
        button2.setBackground(Color.lightGray);
        button1.setFont(new Font("Calibri", Font.ITALIC, 20));
        button2.setFont(new Font("Calibri", Font.ITALIC, 20));

        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        lab_titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        lab_titre.setForeground(Color.WHITE);
        lab_titre.setFont(new Font("Calibri", Font.PLAIN, 60));

        // Ajouter les boutons au panneau
        pan.add(Box.createRigidArea(new Dimension(0, 40)));
        pan.add(lab_titre);
        pan.add(Box.createRigidArea(new Dimension(0, 200)));
        pan.add(button1);
        pan.add(Box.createRigidArea(new Dimension(0, 20)));
        pan.add(button2);
        this.add(pan);

        // ActionListener
        button1.addActionListener(this);
        button2.addActionListener(this);

        this.setVisible(true);

        // Gestion du son

        s.loop();



    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button2) {
            String s = "La bataille navale est un jeu de stratégie pour deux joueurs. \n" +
                    "Chaque joueur dispose une flotte de navires sur une grille cachée de son adversaire. \n" +
                    "Le but est de deviner la position des navires ennemis en annonçant des coordonnées. \n" +
                    "Chaque tir peut être \"à l’eau\", \"touché\", ou \"coulé\", selon l’impact sur les navires adverses. \n" +
                    "Le vainqueur est le premier à couler tous les navires de l’adversaire.";
            JOptionPane.showMessageDialog(this, s, "Alert", JOptionPane.WARNING_MESSAGE);
        } else if (e.getSource() == button1) {



                this.dispose();
            try {
                s.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                    Parametres p = new Parametres(10);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

        }
    }


}
