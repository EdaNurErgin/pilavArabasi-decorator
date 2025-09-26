public class TursuDecorator extends PilavDecorator {//Concrete Decorators (Somut Süsleyiciler)=MALZEMELER
    public TursuDecorator(Pilav pilav) {
        super(pilav);
    }

    @Override
    public String getDescription() {
        return pilav.getDescription() + ", Turşu";
    }

    @Override
    public double getCost() {
        return pilav.getCost() + 3.0; // Turşu fiyatı
    }
}
