import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface extends JFrame implements ActionListener {


    private JPanel pan1 = new JPanel();
    private JPanel pan2 = new JPanel();
    private Image img;
    private BorderLayout bl =  new BorderLayout();

    private int size = 2;
    private int size2 = 1;
    private GridLayout gl = new GridLayout(size,size2);
    private JButton[][] buttons = new JButton[size][size2];


    public Interface()  {


        // Création de la fenêtre
        this.setTitle("Bataille Navale");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(500, 500));
        this.setVisible(true);


        // Pannel / BorderLayout
        pan1.setBackground(new Color(Color.OPAQUE));
        this.setContentPane(pan1);
        pan1.setLayout(bl);
        JLabel labTitre = new JLabel("Bataille navale");
        Font font=new Font("Fira Code",Font.PLAIN,50);
        labTitre.setForeground(Color.WHITE);
        labTitre.setFont(font);
        labTitre.setHorizontalAlignment(SwingConstants.CENTER);

        // GridLayout

        pan1.add(pan2, bl.SOUTH);
        pan2.setLayout(gl);
        pan1.add(labTitre, bl.NORTH);
        addButtons(pan2);


        // ActionListener

        buttons[0][0].addActionListener(this);
        buttons[1][0].addActionListener(this);



    }

    public void addButtons(JPanel panel) {
        for (int k = 0; k < size; k++) {
            for (int j = 0; j < size2; j++) {
                buttons[k][j] = new JButton();
                if(k ==1 ){
                    buttons[k][j].setText("Jouer");
                }else{
                    buttons[k][j].setText("Règles du jeu");
                }
                panel.add(buttons[k][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttons[0][0]){
            String s  = "La bataille navale est un jeu de stratégie pour deux joueurs. \n" +
                    "Chaque joueur dispose une flotte de navires sur une grille cachée de son adversaire. \n" +
                    "Le but est de deviner la position des navires ennemis en annonçant des coordonnées. \n" +
                    "Chaque tir peut être \"à l’eau\", \"touché\", ou \"coulé\", selon l’impact sur les navires adverses. \n" +
                    "Le vainqueur est le premier à couler tous les navires de l’adversaire.";
            JOptionPane.showMessageDialog(this,s,"Alert",JOptionPane.WARNING_MESSAGE);
        }else if(e.getSource() == buttons[1][0]){
            this.dispose();
            Parametres p = new Parametres(10);
        }
    }
}
