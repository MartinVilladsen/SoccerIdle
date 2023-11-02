package Modul;

public class Spiller {
    private String navn;
    private String land;
    private Klub klub;
    private Position position;
    private int alder;
    private int trøjenummer;

    public Spiller(String navn, String land, Klub klub, Position position, int alder, int trøjenummer) {
        this.navn = navn;
        this.land = land;
        this.klub = klub;
        this.position = position;
        this.alder = alder;
        this.trøjenummer = trøjenummer;
    }

    public void addKlub(Klub klub) {
        this.klub = klub;
    }

    public String getNavn() {
        return navn;
    }

    public String getLand() {
        return land;
    }

    public Klub getKlub() {
        return klub;
    }

    public Position getPosition() {
        return position;
    }

    public int getAlder() {
        return alder;
    }

    public int getTrøjenummer() {
        return trøjenummer;
    }

    @Override
    public String toString() {
        return navn + " | " + land + " | " + klub.getKlubnavn() + " | " + position + " | " + alder + " | " + trøjenummer;
    }
}
