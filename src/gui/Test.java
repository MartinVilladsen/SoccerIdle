package gui;

import Controller.ControllerArsenal;
import Controller.ControllerKlubber;
import Controller.ControllerLiverpool;
import Controller.ControllerTottenham;
import Modul.Spiller;

public class Test {
    public static void main(String[] args) {
        ControllerKlubber.tilføjKlubber();
        //
        ControllerArsenal.tilføjArsenal();
        ControllerTottenham.tilføjTottenham();
        ControllerLiverpool.tilføjLiverpool();


        for (Spiller spiller : ControllerKlubber.getAlleSpillere()) {
            System.out.println(spiller.toString());
        }




    }

}
