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
import java.util.NoSuchElementException;

public abstract class Controller {
    static String[] Klubnavn = new String[20];
    static String[] KlubInit = new String[20];
    static Klub[] klubber = new Klub[20];
    static Map<String, Klub> klubMap = new HashMap<>();
    //
    static String[] spillerNavn = new String[29];
    static Position[] spillerPosition = new Position[29];
    static int[] rygNummer = new int[29];
    static Map<String, Spiller> spillerMap = new HashMap<>();

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
                    System.out.println(ticker);


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

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void tilføjSpillere() {
        final String url = "https://www.transfermarkt.com/tottenham-hotspur/startseite/verein/148";

        try {
            final Document document = Jsoup.connect(url).get();
            int spillerNavnTal = 0;
            int spillerPositionTal = 0;

            for (Element row : document.select(
                    "table.items td")) {
                if (row.select("tr:nth-of-type(1)").text().equals("")) {
                    continue;
                } else {
                    final String navn =
                            row.select("tr:nth-of-type(1)").text();
                    final String position =
                            row.select("tr:nth-of-type(2)").text();

                    Position playerPosition = null;

                    if (position.equals("Goalkeeper")) {
                        playerPosition = Position.Målmand;
                    } else if (position.equals("Centre-Back")) {
                        playerPosition = Position.Forsvar;
                    } else if (position.equals("Left-Back")) {
                        playerPosition = Position.Forsvar;
                    } else if (position.equals("Right-Back")) {
                        playerPosition = Position.Forsvar;
                    } else if (position.equals("Defensive Midfield")) {
                        playerPosition = Position.Midtbane;
                    } else if (position.equals("Central Midfield")) {
                        playerPosition = Position.Midtbane;
                    } else if (position.equals("Left Midfield")) {
                        playerPosition = Position.Midtbane;
                    } else if (position.equals("Attacking Midfield")) {
                        playerPosition = Position.Midtbane;
                    } else if (position.equals("Left Winger")) {
                        playerPosition = Position.Angriber;
                    } else if (position.equals("Right Winger")) {
                        playerPosition = Position.Angriber;
                    } else if (position.equals("Centre-Forward")) {
                        playerPosition = Position.Angriber;
                    }

                    System.out.println(navn + " | " + position);
                    spillerNavn[spillerNavnTal] = navn;
                    spillerNavnTal++;
                    spillerPosition[spillerPositionTal] = playerPosition;
                    spillerPositionTal++;
                }
            }

            for (int i = 0; i < spillerNavn.length; i++) {
                Spiller spiller = Controller.opretSpiller(spillerNavn[i], "England", klubber[0], spillerPosition[i], 30, rygNummer[i]);
                spillerMap.put(spillerNavn[i], spiller);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void tilføjRygnummer() {
        final String url = "https://www.transfermarkt.com/tottenham-hotspur/startseite/verein/148";
        try {
            final Document document = Jsoup.connect(url).get();
            int rygnummerTal = 0;

            for (Element row : document.select(
                    "table.items td")) {
                if ((row.select(".rn_nummer").text().equals(""))) {
                    continue;
                } else {
                    final String rygnummer = row.select(".rn_nummer").text();
                    System.out.println(rygnummer);

                    rygNummer[rygnummerTal] = Integer.parseInt(rygnummer);
                    rygnummerTal++;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static List<Klub> getAlleKlubber() {
        return Storage.getKlubber();
    }

    public static List<Spiller> getAlleSpillere() {
        return Storage.getSpillere();
    }

    public static void initStorage() {

    }


}
