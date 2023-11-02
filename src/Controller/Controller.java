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

public abstract class Controller {
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
        String[] Klubnavn = new String[20];
        String[] KlubInit = new String[20];
        Map<String, Klub> klubMap = new HashMap<>();

        final String url = "https://www.premierleague.com/tables";

        try {
            final Document document = Jsoup.connect(url).get();
            int sizeNavn = 0;
            int sizeInit = 0;

            for (Element row : document.select(
                    "tbody.league-table__tbody.isPL td")) {
                if (row.select("td:nth-of-type(2)").text().equals("")) {
                    continue;
                } else {
                    final String ticker =
                            row.select("td:nth-of-type(2)").text();
                    System.out.println(ticker);


                    int sidsteMellem = ticker.lastIndexOf(" ");
                    if (sidsteMellem != -1) {
                        String klubNavn = ticker.substring(0, sidsteMellem);
                        String initial = ticker.substring(sidsteMellem + 1);
                        Klubnavn[sizeNavn] = klubNavn;
                        sizeNavn++;
                        KlubInit[sizeInit] = initial;
                        sizeInit++;

                    }
                }
            }
            System.out.println("-------ASDASD---");

            for (int i = 0; i < Klubnavn.length; i++) {
                Klub club = Controller.opretKlub(Klubnavn[i], KlubInit[i]);
                klubMap.put(KlubInit[i], club);
            }
            System.out.println("----------");

            System.out.println("Hashmap");
            System.out.println(klubMap);
            System.out.println("Hashmap test");
            System.out.println(klubMap.get(KlubInit[1]));

            Spiller moSalah = Controller.opretSpiller("Mo Salah", "Egypt", klubMap.get(KlubInit[3]), Position.Angriber, 30, 11);
            Spiller Trent = Controller.opretSpiller("Trent Alexander Arnold", "England", klubMap.get(KlubInit[3]), Position.Forsvar, 25, 66);
            Spiller Son = Controller.opretSpiller("Son Heung-min", "Korea", klubMap.get(KlubInit[0]), Position.Angriber, 28, 7);


            Controller.tilføjSpillerTilKlub(moSalah, klubMap.get(KlubInit[3]));
            System.out.println("Mo Salah Klub!!!");
            System.out.println(moSalah.getKlub());
            System.out.println("Mo Salahs klubs spillere");
            System.out.println(moSalah.getKlub().getSpillere());
            System.out.println("Alle Spillere!!!");
            System.out.println(Controller.getAlleSpillere());
            System.out.println("Alle klubber!!!");
            System.out.println(Controller.getAlleKlubber());

            System.out.println(klubMap.get(KlubInit[3]).getSpillere());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<Klub>getAlleKlubber() {
        return Storage.getKlubber();
    }

    public static List<Spiller> getAlleSpillere() {
        return Storage.getSpillere();
    }

    public static void initStorage() {

    }
}
