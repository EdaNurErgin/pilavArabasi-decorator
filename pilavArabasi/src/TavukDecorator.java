public class TavukDecorator extends PilavDecorator {//Concrete Decorators (Somut Süsleyiciler)=MALZEMELER
    public TavukDecorator(Pilav pilav) {
        super(pilav);
    }

    @Override
    public String getDescription() {
        return pilav.getDescription() + ", Tavuk";
    }

    @Override
    public double getCost() {
        return pilav.getCost() + 7.0; // Tavuk fiyatı
    }
}
