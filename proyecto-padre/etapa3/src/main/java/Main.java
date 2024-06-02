import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.MyController;


public class Main extends Application{

    private MyController controller;


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("interfaz.fxml"));
            Parent root = loader.load();
            controller = loader.getController();

            controller.CrearMascota();


            


            primaryStage.setTitle("Simulación Mascota Virtual");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        
        launch(args);
        //stage1.executeAction(new Scanner(System.in), System.out);
    }

    

}
