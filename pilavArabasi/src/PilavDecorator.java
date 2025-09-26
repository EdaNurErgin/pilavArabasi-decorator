public abstract class PilavDecorator implements Pilav {//Decorator (Süsleyici): Pilav Malzemesi Ekleyici

    protected Pilav pilav; // Temel pilav nesnesi

    public PilavDecorator(Pilav pilav) {
        this.pilav = pilav; // Pilav nesnesini referans al
    }

    @Override
    public String getDescription() {
        return pilav.getDescription(); // Temel pilav açıklamasını döndür
    }

    @Override
    public double getCost() {
        return pilav.getCost(); // Temel pilav fiyatını döndür
    }
}
