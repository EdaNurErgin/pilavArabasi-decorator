public class SadePilav implements Pilav {//Concrete Component (Somut Bileşen)=SADE PİLAV
    @Override
    public String getDescription() {
        return "Sade Pilav";
    }

    @Override
    public double getCost() {
        return 15.0; // Sade pilav fiyatı
    }
}
