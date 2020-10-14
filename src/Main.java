import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class Main {
    static  Cat kitty;

    final private static String[][] countryList = {
            {"USA", "images/us.png"},
            {"India", "images/in.png"},
            {"Vietnam", "images/vn.png"},
            {"Germany", "images/de.png"},
            {"Canada", "images/ca.png"},
            {"Japan", "images/jp.png"},
            {"Great Britain", "images/gb.png"},
            {"France", "images/fr.png"}
    };

    public static void main(String[] args){
        String[] array = new String[]{"one", "two", "three"};

        String ddd = new String();

        List<String> listing = new ArrayList<>(Arrays.asList(array));

        ListIterator<String> iterator = listing.listIterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyForm().updateCompnents(countryList).setVisible(true);
            }
        });

        kitty = new Cat("www");
        feed(kitty);
        walkWith(kitty);

        DomesticAnimal biggle = new Dog();
    }

    static void feed(Animal anToFeed){
        anToFeed.eat();
    }

    static void walkWith(DomesticAnimal anToWalkWith){
        anToWalkWith.run();
    }

}
