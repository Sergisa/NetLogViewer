public class Cat extends Animal implements DomesticAnimal{
    private String name;
    public Cat(String name){
        this.name = name;
    }

    @Override
    public void run() {

    }


    @Override
    public void talk() {

    }

    @Override
    public void look() {
        super.look();
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {

        return this.name;
    }
}
