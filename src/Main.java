import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class Main {
    static  Cat kitty;
    public static void main(String[] args){
        String[] array = new String[]{"one", "two", "three"};

        String ddd = new String();

        List<String> listing = new ArrayList<>(Arrays.asList(array));

        ListIterator<String> iterator = listing.listIterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }




        kitty = new Cat();
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
