package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ListView;

public class MyController {

    public MyController() {
    }

    @FXML
    private Label nameLabel, ageLabel, statusLabel;

    @FXML
    private ProgressBar healthBar, energyBar, happinessBar;

    @FXML
    private ListView<String> foodList, medicineList;

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
            foodList.getItems().add(f);
        }
    }

    public void setMedicineList(String[] medicine) {
        medicineList.getItems().clear();
        for (String m : medicine) {
            medicineList.getItems().add(m);
        }
    }

}