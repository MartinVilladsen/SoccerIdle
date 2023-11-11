package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NytSpilWindow extends Stage {

    private SoccleIdleGame soccleIdleGame;

    public NytSpilWindow(SoccleIdleGame soccleIdleGame) {
        this.soccleIdleGame = soccleIdleGame;
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        // this.setWidth(300); --> Nødvendig hvis hidden items
        this.setTitle("Nyt spil");
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    //-----------------------------------------------------------
    private final Label lblVelkommen = new Label("Vil du spille igen?");
    private final Button btnSpil = new Button("Spil");
    private final Button btnAnnuller = new Button("Annuller");

    private void initContent(GridPane pane) {
        // show or hide grid lines
        pane.setGridLinesVisible(false);
        // set padding of the pane
        pane.setPadding(new Insets(20));
        // set horizontal gap between components
        pane.setHgap(10);
        // set vertical gap between components
        pane.setVgap(10);
        // Backgrounds styling & Header Label


        pane.add(lblVelkommen, 2, 1);
        pane.add(btnSpil, 1, 2);
        pane.add(btnAnnuller, 3, 2);

        btnSpil.setOnAction(event -> this.spilOnAction());
        btnAnnuller.setOnAction(event -> this.exitOnAction());
    }

    private void spilOnAction() {
        SoccleIdleGame dialog = new SoccleIdleGame();
        dialog.start(new Stage());
        Stage stage = (Stage) btnSpil.getScene().getWindow();
        stage.close();
    }

    private void exitOnAction() {
        Platform.exit();
    }


}
