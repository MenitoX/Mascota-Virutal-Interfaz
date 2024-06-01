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
    private Mascota mascota;
    private Inventario inventario;
    private MyController controller;


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("interfaz.fxml"));
            Parent root = loader.load();
            controller = loader.getController();


            InputStream inputStream = Main.class.getResourceAsStream("/config.csv");
            if (inputStream == null) {
                System.out.println("El archivo config.csv no se encontró en los recursos.");
                System.exit(-1);
            }
            Scanner in = new Scanner(inputStream);
            this.readConfiguration(in); // Mover todo esto a la etapa 4 que lee la configuración


            controller.setPetName(mascota.getNombre());
            controller.setStatus(mascota.getEstadoAsString());
            controller.setAge(mascota.getEdad());
            controller.setHealth(mascota.getSaludPorcentaje());
            controller.setHappiness(mascota.getFelicidadPorcentaje());
            controller.setEnergy(mascota.getEnergiaPorcentaje());
            controller.setFoodList(inventario.getComida());
            controller.setMedicineList(inventario.getMedicina());


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

    public void printEstado(float step, PrintStream out) {
        out.printf("Tiempo simulado: %.1f%n", step);
        mascota.mostrarAtributos();
        inventario.mostrarItems();
    }
}
