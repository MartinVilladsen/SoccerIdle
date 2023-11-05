package Storage;


import Modul.Klub;
import Modul.Spiller;

import java.util.ArrayList;
import java.util.List;

public abstract class Storage {

    private static final List<Spiller> spillere = new ArrayList<>();
    private static final List<Klub> klubber = new ArrayList<>();

    public static void storeSpiller(Spiller spiller) {
        spillere.add(spiller);
    }

    public static void storeKlub(Klub klub) {
        klubber.add(klub);
    }

    public static List<Spiller> getSpillere() {
        return spillere;
    }

    public static ArrayList<String> getSpillernavne() {
        ArrayList<String> navne = new ArrayList<>();
        for (Spiller spiller : spillere) {
            navne.add(spiller.getNavn());
        }
        return navne;
    }

    public static List<Klub> getKlubber() {
        return klubber;
    }
}
