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

public abstract class ControllerArsenal {
    static String[] spillerNavn = new String[25];
    static Position[] spillerPosition = new Position[25];
    static int[] rygNummer = new int[25];
    static int[] alder = new int[25];
    static Map<String, Spiller> spillerMap = new HashMap<>();



    public static void tilføjSpillereArsenal() {
        final String url = "https://www.transfermarkt.com/fc-arsenal/startseite/verein/11";

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
                    spillerNavn[spillerNavnTal] = navn;
                    spillerNavnTal++;
                    spillerPosition[spillerPositionTal] = playerPosition;
                    spillerPositionTal++;
                }
            }
            for (int i = 0; i < spillerNavn.length; i++) {
                Spiller spiller = ControllerKlubber.opretSpiller(spillerNavn[i], "England", ControllerKlubber.klubMap.get("ARS"), spillerPosition[i], (alder[i]), rygNummer[i]);
                spillerMap.put(spillerNavn[i], spiller);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void tilføjRygnummerArsenal() {
        final String url = "https://www.transfermarkt.com/fc-arsenal/startseite/verein/11";
        try {
            final Document document = Jsoup.connect(url).get();
            int rygnummerTal = 0;
            for (Element row : document.select(
                    "table.items td")) {
                if ((row.select(".rn_nummer").text().equals(""))) {
                    continue;
                } else {
                    final String rygnummer = row.select(".rn_nummer").text();
                    // System.out.println(rygnummer);

                    rygNummer[rygnummerTal] = Integer.parseInt(rygnummer);
                    rygnummerTal++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void tilføjAlderArsenal() {
        final String url = "https://www.transfermarkt.com/fc-arsenal/startseite/verein/11";
        try {
            final Document document = Jsoup.connect(url).get();
            int alderTal = 0;

            for (Element row : document.select(
                    "table.items td")) {
                if ((row.select("td.zentriert:nth-of-type(3)").text().equals(""))) {
                    continue;
                } else {
                    final String alderSpiller = row.select("td.zentriert:nth-of-type(3)").text();
                    int sidsteMellem = alderSpiller.lastIndexOf("(");
                    if (sidsteMellem != -1) {
                        String deresAlder = alderSpiller.substring(sidsteMellem + 1, sidsteMellem + 3);
                        alder[alderTal] = Integer.parseInt(deresAlder);
                        alderTal++;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void tilføjArsenal() {
        tilføjRygnummerArsenal();
        tilføjAlderArsenal();
        tilføjSpillereArsenal();
    }
}
