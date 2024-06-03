package com.example;

import com.example.Mascota;
import com.example.Inventario;
import com.example.Item;
import com.example.Estado;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.util.Duration;


import javafx.application.Platform;

public class MyController {
    public static Mascota mascota;
    private Inventario inventario;
    private boolean lightsOn=false;

    public MyController() {
    }

    @FXML
    private Label nameLabel, ageLabel, statusLabel;

    @FXML
    private ProgressBar healthBar, energyBar, happinessBar;

    @FXML
    private ListView<AnchorPane> foodList;
    
    @FXML
    private ListView<AnchorPane> medicineList;

    @FXML
    private MenuItem initAction;

    @FXML
    private ImageView backgroundImageView;

    private Timeline timeline, lightsTimeline;

    public void setPetName(String name) {
        nameLabel.setText(name);
    }

    public void setStatus(String status) {    
        statusLabel.setText(status);
    }

    public void setAge(double age) {
        ageLabel.setText(String.valueOf(age));
    }

    public void setHealth(float health) {
        healthBar.setProgress(health);
    }

    public void setHappiness(float happiness) {
        happinessBar.setProgress(happiness);
    }

    public void setEnergy(float energy) {
        energyBar.setProgress(energy);
    }

    public void setFoodList(String[] food) {
        foodList.getItems().clear();
        for (String f : food) {
            String[] parts = f.split(";");
            String name = parts[0];
            String quantity = parts[1];
    
            Button foodButton = new Button(name);
            Label quantityLabel = new Label("Cantidad: " + quantity);
    
            AnchorPane anchorPane = new AnchorPane();
            AnchorPane.setLeftAnchor(foodButton, 0.0);
            AnchorPane.setRightAnchor(quantityLabel, 0.0);
    
            anchorPane.getChildren().addAll(foodButton, quantityLabel);
            foodList.getItems().add(anchorPane);
        }
    }

    public void setMedicineList(String[] medicine) {
        medicineList.getItems().clear();
        for (String f : medicine) {
            String[] parts = f.split(";");
            String name = parts[0];
            String quantity = parts[1];
    
            Button medicineButton = new Button(name);
            Label quantityLabel = new Label("Cantidad: " + quantity);
    
            AnchorPane anchorPane = new AnchorPane();
            AnchorPane.setLeftAnchor(medicineButton, 0.0);
            AnchorPane.setRightAnchor(quantityLabel, 0.0);
    
            anchorPane.getChildren().addAll(medicineButton, quantityLabel);
            foodList.getItems().add(anchorPane);
        }
    }

    //Manejo de Menu
    @FXML
    private void handleIniciarAction(ActionEvent event) {
        startTimeline();
    }
    @FXML
    private void handleLightsAction(ActionEvent event) {
        if (lightsOn) {
            stopLightsTimeline();
            changeBackgroundImage("/background/background.png");
        } else {
            startLightsTimeline();
            changeBackgroundImage("/background/sleep.jpg");
        }
        lightsOn = !lightsOn;
    }

    private void changeBackgroundImage(String imagePath) {
        Platform.runLater(() -> {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            backgroundImageView.setImage(image);
        });
    }

    private void startTimeline() {
        if (timeline == null) {
            timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> executeOlder()));
            timeline.setCycleCount(Timeline.INDEFINITE);
        }
        timeline.play();
    }
    private void ActualizarLabels(){
        Platform.runLater(() -> {
            ageLabel.setText(String.valueOf(mascota.getEdad()));
            healthBar.setProgress(mascota.getSaludPorcentaje());
            energyBar.setProgress(mascota.getEnergiaPorcentaje());
            happinessBar.setProgress(mascota.getFelicidadPorcentaje());
            statusLabel.setText(mascota.getEstadoAsString());
        });
    }

    private void executeOlder() {
        mascota.envejecer();
        ActualizarLabels();
    }

    private void startLightsTimeline() {
        if (lightsTimeline == null) {
            lightsTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> increaseAttributes()));
            lightsTimeline.setCycleCount(Timeline.INDEFINITE);
        }
        lightsTimeline.play();
    }
    private void stopLightsTimeline() {
        if (lightsTimeline != null) {
            lightsTimeline.stop();
        }
    }

    private void increaseAttributes(){
        if(mascota.getEstado()!=Estado.Muerto){
            mascota.modificarEnergia(20);
            mascota.modificarSalud(2);
            mascota.modificarFelicidad(2);
            ActualizarLabels();
        }
        
    }

    public void printEstado(float step, PrintStream out) {
        out.printf("Tiempo simulado: %.1f%n", step);
        mascota.mostrarAtributos();
        inventario.mostrarItems();
    }

    public void readConfiguration(Scanner in) { // Mover a la etapa 4
        // Creación de mascota
        String nombreMascota = in.nextLine();
        mascota = new Mascota(nombreMascota);
        
        inventario = new Inventario();
        // Llenando inventario
        while (in.hasNextLine()) {
            String linea = in.nextLine();
            String[] item_csv = linea.split(";");
            int id = Integer.parseInt(item_csv[0]);
            String tipoItem = item_csv[1];
            String nombreItem = item_csv[2];
            int cantidad = Integer.parseInt(item_csv[3]);

            switch (tipoItem) {
                case "Comida":
                    inventario.agregarItem(new Comida(id, nombreItem, cantidad));
                    break;
                case "Medicina":
                    inventario.agregarItem(new Medicina(id, nombreItem, cantidad));
                    break;
                case "Juguete":
                    inventario.agregarItem(new Juguete(id, nombreItem, cantidad));
                    break;
            }
        }
    }

    public void CrearMascota(){
        InputStream inputStream = getClass().getResourceAsStream("/config.csv");
            if (inputStream == null) {
                System.out.println("El archivo config.csv no se encontró en los recursos.");
                System.exit(-1);
            }
            Scanner in = new Scanner(inputStream);
            this.readConfiguration(in); // Mover todo esto a la etapa 4 que lee la configuración


            setPetName(mascota.getNombre());
            setStatus(mascota.getEstadoAsString());
            setAge(mascota.getEdad());
            setHealth(mascota.getSaludPorcentaje());
            setHappiness(mascota.getFelicidadPorcentaje());
            setEnergy(mascota.getEnergiaPorcentaje());
            setFoodList(inventario.getComida());
            setMedicineList(inventario.getMedicina());
    }

}