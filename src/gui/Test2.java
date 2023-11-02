package gui;

import Controller.Controller;
import Modul.Klub;
import Modul.Position;
import Modul.Spiller;
import Storage.Storage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test2 {
    static String[] spillerNavn = new String[29];
    static int[] rygNummer = new int[29];
    static Position[] spillerPosition = new Position[29];
    static Map<String, Spiller> spillerMap = new HashMap<>();
    static Klub[] klubber = new Klub[20];
    public static void main(String[] args) {
        String[] spillerNavn = new String[29];
        String[] spillerPosition = new String[29];
        // Map<String, Spiller> spillerMap = new HashMap<>();
        tilføjRygnummer();
        tilføjNavnOgPosition();
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

    public static void tilføjNavnOgPosition() {

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
                Spiller spiller = Controller.opretSpiller(spillerNavn[i], "England", klubber[0], spillerPosition[i], 30, rygNummer[0]);
                spillerMap.put(spillerNavn[i], spiller);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
