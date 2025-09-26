public class KavurmaDecorator extends PilavDecorator { //Concrete Decorators (Somut Süsleyiciler)=MALZEMELER
    public KavurmaDecorator(Pilav pilav) {
        super(pilav);
    }

    @Override
    public String getDescription() {
        return pilav.getDescription() + ", Kavurma";
    }

    @Override
    public double getCost() {
        return pilav.getCost() + 10.0; // Kavurma fiyatı
    }
}
