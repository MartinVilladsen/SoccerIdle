package gui;

import Controller.ControllerKlubber;
import Modul.Spiller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.util.Arrays;
import java.util.List;

public class SoccleIdleGame extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("SoccerIdle");
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    //-----------------------------------------------------------
    //
    private final Button btnGuess = new Button("OK");
    private final Spiller gætSpilleren = ControllerKlubber.randomSpiller(ControllerKlubber.getAlleSpillere());
    private final TextField txfGuessPlayer = new TextField();
    private final Spiller[] guessedPlayers = new Spiller[8];

    // --------------------------------------------------------

    private final TextArea clubGuessed = new TextArea();
    private final TextArea positionGuessed = new TextArea();
    private final TextArea ageGuessed = new TextArea();
    private final TextArea shirtNumberGuessed = new TextArea();

    // --------------------------------------------------------
    private final Label lblErrorTrøjenummer = new Label();

    private final Label lblErrorAlder = new Label();





    private void initContent(GridPane pane) {
        // show or hide grid lines
        pane.setGridLinesVisible(false);
        // set padding of the pane
        pane.setPadding(new Insets(20));
        // set horizontal gap between components
        pane.setHgap(10);
        // set vertical gap between components
        pane.setVgap(10);

        Label lblbtnGuess = new Label();
        pane.add(btnGuess, 5, 1);

        int i = 1;
        txfGuessPlayer.setPromptText("Guess: " + i + " out of 8");
        Label lblGuessPlayer = new Label();
        pane.add(txfGuessPlayer, 1, 1, 4, 1);

        Label lblclubGuess = new Label();
        pane.add(clubGuessed, 1, 2);
        clubGuessed.setPrefSize(200, 50);
        clubGuessed.setEditable(false);

        Label lblpositionGuess = new Label();
        pane.add(positionGuessed, 2, 2);
        positionGuessed.setPrefSize(80, 50);
        positionGuessed.setEditable(false);

        Label lblalderGuess = new Label();
        pane.add(ageGuessed, 3, 2);
        ageGuessed.setPrefSize(30, 50);
        ageGuessed.setEditable(false);

        Label lblShirtGuess = new Label();
        pane.add(shirtNumberGuessed, 4, 2);
        shirtNumberGuessed.setPrefSize(30, 50);
        shirtNumberGuessed.setEditable(false);

        pane.add(lblErrorAlder,3,3);
        lblErrorAlder.setVisible(false);

        pane.add(lblErrorTrøjenummer,4,3);
        lblErrorTrøjenummer.setVisible(false);

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
                lblErrorAlder.setVisible(false);
                lblErrorTrøjenummer.setVisible(false);

                NytSpilWindow dialog = new NytSpilWindow(new SoccleIdleGame());
                dialog.showAndWait();
                
            } else {
                System.out.println("Du har ikke vundet! " + i);

                // Check if the guessed traits (position, shirt number, age, and club) match the "correct" Spiller's traits
                if (shareSameTrait(gætSpilleren.getPosition(), playerGuess.getPosition())) {
                    displayPositionTraitTrue(playerGuess.getPosition());

                } else {
                    displayPositionTraitFalse(playerGuess.getPosition());
                }

                if (shareSameTrait(gætSpilleren.getTrøjenummer(), playerGuess.getTrøjenummer())) {
                    displayShirtNumberTraitTrue(playerGuess.getTrøjenummer());
                } else {
                    displayShirtNumberTraitFalse(playerGuess.getTrøjenummer());
                    if (gætSpilleren.getTrøjenummer() > playerGuess.getTrøjenummer()) {
                        lblErrorTrøjenummer.setText(playerGuess.getTrøjenummer() + " ↑");
                        lblErrorTrøjenummer.setVisible(true);
                    } else {
                        lblErrorTrøjenummer.setText(playerGuess.getTrøjenummer() + " ↓");
                        lblErrorTrøjenummer.setVisible(true);
                    }
                }

                if (shareSameTrait(gætSpilleren.getAlder(), playerGuess.getAlder())) {
                    displayAgeTraitTrue(playerGuess.getAlder());
                } else {
                    displayAgeTraitFalse(playerGuess.getAlder());
                    if (gætSpilleren.getAlder() > playerGuess.getAlder()) {
                        lblErrorAlder.setText(playerGuess.getAlder() + " ↑");
                        lblErrorAlder.setVisible(true);
                    } else {
                        lblErrorAlder.setText(playerGuess.getAlder() + " ↓");
                        lblErrorAlder.setVisible(true);
                    }
                }

                if (shareSameTrait(gætSpilleren.getKlub(), playerGuess.getKlub())) {
                    displayClubTraitTrue(playerGuess.getKlub());
                } else {
                    displayClubTraitFalse(playerGuess.getKlub());
                }
            }
        } else {
            System.out.println("Player not found: " + guess);
        }
        System.out.println(Arrays.toString(guessedPlayers));
    }

    private void displayAllCorrectTraits(Spiller spiller) {
        displayPositionTraitTrue(spiller.getPosition());
        displayShirtNumberTraitTrue(spiller.getTrøjenummer());
        displayAgeTraitTrue(spiller.getAlder());
        displayClubTraitTrue(spiller.getKlub());
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

    private void displayPositionTraitTrue(Object position) {
        positionGuessed.setText(position.toString());
        positionGuessed.setStyle("-fx-text-fill: green;");
    }

    private void displayPositionTraitFalse(Object position) {
        positionGuessed.setText(position.toString());
        positionGuessed.setStyle("-fx-text-fill: red;");
    }

    private void displayShirtNumberTraitTrue(Object shirtNumber) {
        shirtNumberGuessed.setText(shirtNumber.toString());
        shirtNumberGuessed.setStyle("-fx-text-fill: green;");
    }
    private void displayShirtNumberTraitFalse(Object shirtNumber) {
        shirtNumberGuessed.setText(shirtNumber.toString());
        shirtNumberGuessed.setStyle("-fx-text-fill: red;");
    }

    private void displayAgeTraitTrue(Object age) {
        ageGuessed.setText(age.toString());
        ageGuessed.setStyle("-fx-text-fill: green;");
    }
    private void displayAgeTraitFalse(Object age) {
        ageGuessed.setText(age.toString());
        ageGuessed.setStyle("-fx-text-fill: red;");
    }

    private void displayClubTraitTrue(Object club) {
        clubGuessed.setText(club.toString());
        clubGuessed.setStyle("-fx-text-fill: green;");
    }
    private void displayClubTraitFalse(Object club) {
        clubGuessed.setText(club.toString());
        clubGuessed.setStyle("-fx-text-fill: red;");
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
