import java.util.ArrayList;

public class Test {

    public Test(){

    }

    public ArrayList<ArrayList<Integer>> generateIndex(ArrayList<Integer> integ, int dupp, Etat_Pion e) {
        ArrayList<ArrayList<Integer>> liste_genere = new ArrayList<>();
        liste_genere.add(integ);
        for (int i = 1; i < dupp; i++) {
            if (e.equals(Etat_Pion.HORIZONTAL)) {
                ArrayList<Integer> sous_liste = new ArrayList<>(integ);
                integ.set(0, integ.get(0) + 1);
                liste_genere.add(sous_liste);

            } else {
                ArrayList<Integer> sous_liste = new ArrayList<>(integ);
                integ.set(1, integ.get(1) + 1);
                liste_genere.add(sous_liste);
            }

        }

        return liste_genere;
    }


    public static void main(String[] args){
        Test t = new Test();
        ArrayList<Integer> i  = new ArrayList<>();
        i.add(2);
        i.add(2);
        System.out.println(t.generateIndex(i, 5, Etat_Pion.VERTICAL));
    }
}

