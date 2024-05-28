package com.example;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class Controller {
    @FXML
    private ProgressBar hydrationBar;
    @FXML
    private Label stateLabel;
    @FXML
    private Button waterButton;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button quitButton;

    private Plant plant;
    private Timeline timeline;

    public void initialize() {
        plant = new Plant();
        updateUI();

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            plant.passTime();
            updateUI();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        waterButton.setOnAction(event -> {
            plant.water();
            updateUI();
        });

        startButton.setOnAction(event -> {
            timeline.play();
        });

        stopButton.setOnAction(event -> {
            timeline.stop();
        });

        quitButton.setOnAction(event -> {
            System.exit(1);
        });
    }

    private void updateUI() {
        stateLabel.setText("Estado: " + plant.getState());
        hydrationBar.setProgress(plant.getHydration());
    }
}
