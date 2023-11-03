package gui;

import Modul.Klub;
import Modul.Position;
import Modul.Spiller;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class Test2 {
    static String[] spillerNavn = new String[29];
    static int[] rygNummer = new int[29];
    static Position[] spillerPosition = new Position[29];
    static Map<String, Spiller> spillerMap = new HashMap<>();
    static Klub[] klubber = new Klub[20];
    static String[] alder = new String[29];
    static String[] land = new String[29];
    public static void main(String[] args) {
        final String url = "https://www.transfermarkt.com/tottenham-hotspur/startseite/verein/148";
        try {
            final Document document = Jsoup.connect(url).get();
            int landTal = 0;

            String html = "<td class=\"zentriert\"><img src=\"https://tmssl.akamaized.net/images/flagge/verysmall/75.png?lm=1520611569\" title=\"Italy\" alt=\"Italy\" class=\"flaggenrahmen\"></td>";

            Document doc = Jsoup.parse(html);

            Element imgElement = doc.select("img").first(); // Select the first 'img' element
            String title = imgElement.attr("title"); // Get the 'title' attribute

            System.out.println("Title: " + title);

            for (Element row : document.select(
                    "table.items td")) {
                if ((row.select(".flaggenrahmen").text().equals(""))) {
                    continue;
                } else {
                    final String landSpiller = row.select(".flaggenrahmen").text();
                    // System.out.println(landSpiller);
                    Elements images = document.select("https://tmssl.akamaized.net/images/flagge/verysmall/75.png?lm=1520611569");
                    System.out.println(images.attr("title"));

                    land[landTal] = landSpiller;
                    landTal++;
                }

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

    public static void tilføjNavnOgPosition() {
        final String url = "https://www.transfermarkt.com/tottenham-hotspur/startseite/verein/148";
        try {
            final Document document = Jsoup.connect(url).get();
            int rygnummerTal = 0;

            for (Element row : document.select(
                    "table.items td")) {
                if ((row.select("td.zentriert:nth-of-type(4)").text().equals(""))) {
                    continue;
                } else {
                    final String rygnummer = row.select("td.zentriert:nth-of-type(4)").text();
                    System.out.println(rygnummer);

                    rygNummer[rygnummerTal] = Integer.parseInt(rygnummer);
                    rygnummerTal++;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
