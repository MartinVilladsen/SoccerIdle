package gui;

import Controller.ControllerKlubber;
import Controller.ControllerLiverpool;
import Modul.Spiller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.util.List;

public class Gui extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("SoccerIdle");
        GridPane pane = new GridPane();
        this.initContent(pane);

        TextField textField = new TextField();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    //-----------------------------------------------------------
    //

    private final Spiller gætSpilleren = ControllerKlubber.randomSpiller(ControllerKlubber.getAlleSpillere());
    private final TextField txfGuessPlayer = new TextField();
    private final Button btnGuess = new Button("OK");
    private final TextField txfPlayerGuessed1 = new TextField();
    private final TextField txfPlayerGuessed2 = new TextField();
    private final TextField txfPlayerGuessed3 = new TextField();
    private final TextField txfPlayerGuessed4 = new TextField();
    private final TextField txfPlayerGuessed5 = new TextField();
    private final TextField txfPlayerGuessed6 = new TextField();
    private final TextField txfPlayerGuessed7 = new TextField();
    private final TextField txfPlayerGuessed8 = new TextField();

    // --------------------------------------------------------

    private final TextArea clubGuessed = new TextArea();
    private final TextArea positionGuessed = new TextArea();
    private final TextArea ageGuessed = new TextArea();
    private final TextArea shirtNumberGuessed = new TextArea();



    private void initContent(GridPane pane) {
        // show or hide grid lines
        pane.setGridLinesVisible(false);
        // set padding of the pane
        pane.setPadding(new Insets(20));
        // set horizontal gap between components
        pane.setHgap(10);
        // set vertical gap between components
        pane.setVgap(10);


        int i = 1;
        txfGuessPlayer.setPromptText("Guess: " + i + " out of 8");
        Label lblGuessPlayer = new Label();
        pane.add(txfGuessPlayer, 1, 1, 4, 1);

        Label lblbtnGuess = new Label();
        pane.add(btnGuess, 5, 1);

        Label lblclubGuess = new Label();
        pane.add(clubGuessed, 1, 2);
        clubGuessed.setPrefSize(50, 50);
        clubGuessed.setEditable(false);

        Label lblpositionGuess = new Label();
        pane.add(positionGuessed, 2, 2);
        positionGuessed.setPrefSize(50, 50);
        positionGuessed.setEditable(false);

        Label lblalderGuess = new Label();
        pane.add(ageGuessed, 3, 2);
        ageGuessed.setPrefSize(50, 50);
        ageGuessed.setEditable(false);

        Label lblShirtGuess = new Label();
        pane.add(shirtNumberGuessed, 4, 2);
        shirtNumberGuessed.setPrefSize(50, 50);
        shirtNumberGuessed.setEditable(false);

        // Set up auto-suggestions for the TextField
        TextFields.bindAutoCompletion(txfGuessPlayer, ControllerKlubber.getAlleSpillere());

        btnGuess.setOnAction(event -> gætSpiller());
    }
    int i = 0;
    private void gætSpiller() {
        String guess = txfGuessPlayer.getText();
        if (guess.equals(gætSpilleren.getNavn())) {
            System.out.println("Du har vundet!");
        } else {
            i++;
            System.out.println("Du har ikke vundet! " + i);

            // Check if the guessed trait matches with any other Spiller's trait
            List<Spiller> alleSpillere = ControllerKlubber.getAlleSpillere();
            for (Spiller spiller : alleSpillere) {
                if (spiller != gætSpilleren) {
                    if (guess.equals(spiller.getNavn())) {
                        if (shareSameTrait(gætSpilleren, spiller)) {
                            // Display the correct trait in the corresponding TextArea
                            clubGuessed.setText(spiller.getKlub().getKlubnavn());
                            positionGuessed.setText(spiller.getPosition().name());
                            ageGuessed.setText(Integer.toString(spiller.getAlder()));
                            shirtNumberGuessed.setText(Integer.toString(spiller.getTrøjenummer()));
                        }
                    }
                }
            }
        }
    }

    private boolean shareSameTrait(Spiller spiller1, Spiller spiller2) {
        // Check if the two Spillers share the same trait (shirt number, club, or position)
        return spiller1.getTrøjenummer() == spiller2.getTrøjenummer() ||
                spiller1.getKlub().equals(spiller2.getKlub()) ||
                spiller1.getPosition().equals(spiller2.getPosition());
    }
}