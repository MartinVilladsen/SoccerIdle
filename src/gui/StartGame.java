package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartGame extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("SoccerIdle");
        GridPane pane = new GridPane();
        this.initContent(pane);
        pane.setMinHeight(600);
        pane.setMinWidth(750);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    //-----------------------------------------------------------
    private final Label lblVelkommen = new Label("Velkommen til SoccerIdle!");
    private final Label lblSpil = new Label("Vil du spille?");
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
        pane.add(lblSpil, 2, 2);
        pane.add(btnSpil, 1, 3);
        pane.add(btnAnnuller, 3, 3);

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
