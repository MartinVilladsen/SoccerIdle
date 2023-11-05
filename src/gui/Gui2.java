package gui;

import Controller.ControllerKlubber;
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

public class Gui2 extends Application {

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
    private final Spiller[] guessedPlayers = new Spiller[8];

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
        Spiller playerGuess = getPlayerByName(guess);

        if (playerGuess != null) {
            guessedPlayers[i] = playerGuess;
            i++;

            if (i < guessedPlayers.length) {
                txfGuessPlayer.setPromptText("Guess: " + (i + 1) + " out of 8");
            }

            if (playerGuess.equals(gætSpilleren)) {
                System.out.println("Du har vundet!");

                // Display all the correct traits when the player wins
                displayAllCorrectTraits(gætSpilleren);
            } else {
                System.out.println("Du har ikke vundet! " + i);

                // Check if the guessed traits (position, shirt number, age, and club) match the "correct" Spiller's traits
                if (shareSameTrait(gætSpilleren.getPosition(), playerGuess.getPosition())) {
                    displayPositionTrait(playerGuess.getPosition());
                } else {
                    clearPositionTextArea();
                }

                if (shareSameTrait(gætSpilleren.getTrøjenummer(), playerGuess.getTrøjenummer())) {
                    displayShirtNumberTrait(playerGuess.getTrøjenummer());
                } else {
                    clearShirtNumberTextArea();
                }

                if (shareSameTrait(gætSpilleren.getAlder(), playerGuess.getAlder())) {
                    displayAgeTrait(playerGuess.getAlder());
                } else {
                    clearAgeTextArea();
                }

                if (shareSameTrait(gætSpilleren.getKlub(), playerGuess.getKlub())) {
                    displayClubTrait(playerGuess.getKlub());
                } else {
                    clearClubTextArea();
                }
            }
        } else {
            System.out.println("Player not found: " + guess);
        }
    }

    private void displayAllCorrectTraits(Spiller spiller) {
        displayPositionTrait(spiller.getPosition());
        displayShirtNumberTrait(spiller.getTrøjenummer());
        displayAgeTrait(spiller.getAlder());
        displayClubTrait(spiller.getKlub());
    }

    private Spiller getPlayerByName(String name) {
        List<Spiller> alleSpillere = ControllerKlubber.getAlleSpillere();
        for (Spiller spiller : alleSpillere) {
            if (spiller.getNavn().equals(name)) {
                return spiller;
            }
        }
        return null;
    }


    private boolean shareSameTrait(Object trait1, Object trait2) {
        // Check if the two traits are the same
        return trait1.equals(trait2);
    }

    private void displayPositionTrait(Object position) {
        positionGuessed.setText(position.toString());
    }

    private void displayShirtNumberTrait(Object shirtNumber) {
        shirtNumberGuessed.setText(shirtNumber.toString());
    }

    private void displayAgeTrait(Object age) {
        ageGuessed.setText(age.toString());
    }

    private void displayClubTrait(Object club) {
        clubGuessed.setText(club.toString());
    }

    private void clearPositionTextArea() {
        positionGuessed.clear();
    }

    private void clearShirtNumberTextArea() {
        shirtNumberGuessed.clear();
    }

    private void clearAgeTextArea() {
        ageGuessed.clear();
    }

    private void clearClubTextArea() {
        clubGuessed.clear();
    }
}
