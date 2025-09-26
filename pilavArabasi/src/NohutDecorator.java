public class NohutDecorator extends PilavDecorator {// Concrete Decorators (Somut Süsleyiciler)=MALZEMELER
    public NohutDecorator(Pilav pilav) {
        super(pilav);
    }

    @Override
    public String getDescription() {
        return pilav.getDescription() + ", Nohut";
    }

    @Override
    public double getCost() {
        return pilav.getCost() + 5.0; // Nohut fiyatı
    }
}
