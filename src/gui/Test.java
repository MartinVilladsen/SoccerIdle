package gui;

import Controller.Controller;
import Modul.Klub;
import Modul.Position;
import Modul.Spiller;
import Storage.Storage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Controller.tilføjKlubber();
        Controller.tilføjRygnummer();
        Controller.tilføjAlder();
        Controller.tilføjSpillereTottenham();

        System.out.println(" --- ");
        for (Spiller spiller : Controller.getAlleSpillere()) {
            System.out.println(spiller.toString());
        }


    }

}
