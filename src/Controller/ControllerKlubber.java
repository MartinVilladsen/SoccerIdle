package Controller;

import Modul.Klub;
import Modul.Position;
import Modul.Spiller;
import Storage.Storage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class ControllerKlubber {

    static String[] Klubnavn = new String[20];
    static String[] KlubInit = new String[20];
    static Klub[] klubber = new Klub[20];
    static Map<String, Klub> klubMap = new HashMap<>();

    public static Spiller opretSpiller(String navn, String land, Klub klub, Position position, int alder,
                                       int trøjenummer) {
        Spiller spiller = new Spiller(navn, land, klub, position, alder, trøjenummer);
        klub.addSpillere(spiller);
        Storage.storeSpiller(spiller);
        return spiller;
    }

    public static void tilføjSpillerTilKlub(Spiller spiller, Klub klub) {
        if (!spiller.getKlub().equals(klub)) {
            klub.addSpillere(spiller);
            spiller.addKlub(klub);
        }
    }

    public static Klub opretKlub(String klubnavn, String initaler) {
        Klub klub = new Klub(klubnavn, initaler);
        Storage.storeKlub(klub);
        return klub;
    }

    public static void tilføjKlubber() {
        final String url = "https://www.premierleague.com/tables";

        try {
            final Document document = Jsoup.connect(url).get();
            int sizeNavn = 0;
            int sizeInit = 0;
            int sizeKlub = 0;

            for (Element row : document.select(
                    "tbody.league-table__tbody.isPL td")) {
                if (row.select("td:nth-of-type(2)").text().equals("")) {
                    continue;
                } else {
                    final String ticker =
                            row.select("td:nth-of-type(2)").text();

                    int sidsteMellem = ticker.lastIndexOf(" ");
                    if (sidsteMellem != -1) {
                        String klubNavn = ticker.substring(0, sidsteMellem);
                        String initial = ticker.substring(sidsteMellem + 1);
                        Klubnavn[sizeNavn] = klubNavn;
                        sizeNavn++;
                        KlubInit[sizeInit] = initial;
                        sizeInit++;
                        klubber[sizeKlub] = new Klub(klubNavn, initial);
                        sizeKlub++;
                    }
                }
            }

            for (int i = 0; i < Klubnavn.length; i++) {
                Klub club = ControllerKlubber.opretKlub(Klubnavn[i], KlubInit[i]);
                klubMap.put(KlubInit[i], club);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<Spiller> getAlleSpillere() {
        return Storage.getSpillere();
    }

    public static List<String> getAlleSpillerNavne() {
        return Storage.getSpillernavne();
    }

    public static Spiller randomSpiller(List<Spiller> list) {
        Random random = new Random();
        int randomSpiller = random.nextInt(list.size());
        Spiller spiller = list.get(randomSpiller);
        return spiller;
    }

    public static String[] navne() {
        String[] navn = new String[29];
        int i = 0;
        for (Spiller s : getAlleSpillere()) {
            navn[i] = s.getNavn();
            i++;
        }
        return navn;
    }
}

