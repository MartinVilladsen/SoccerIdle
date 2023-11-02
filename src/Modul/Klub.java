package Modul;

import java.util.ArrayList;

public class Klub {
    private String klubnavn;
    private String initaler;
    private ArrayList<Spiller> spillere = new ArrayList<>();

    public Klub(String klubnavn, String initialer) {
        this.klubnavn = klubnavn;
        this.initaler = initialer;
    }

    public void addSpillere(Spiller spiller) {
        spillere.add(spiller);
    }

    public String getKlubnavn() {
        return klubnavn;
    }

    public ArrayList<Spiller> getSpillere() {
        return spillere;
    }

    public String getInitaler() {
        return initaler;
    }

    @Override
    public String toString() {
        return klubnavn + " (" + initaler +")";
    }
}
