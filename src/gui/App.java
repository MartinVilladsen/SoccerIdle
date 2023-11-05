package gui;

import Controller.ControllerArsenal;
import Controller.ControllerKlubber;
import Controller.ControllerLiverpool;
import Controller.ControllerTottenham;

public class App {

    public static void main(String[] args) {
        // initStorage();
        ControllerKlubber.tilføjKlubber();
        ControllerLiverpool.tilføjLiverpool();;
        Gui2.launch(Gui2.class);
    }

    public static void initStorage() {
        ControllerKlubber.tilføjKlubber();
        //
        ControllerArsenal.tilføjArsenal();
        ControllerTottenham.tilføjTottenham();
        ControllerLiverpool.tilføjLiverpool();
    }
}
