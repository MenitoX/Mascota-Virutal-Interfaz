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

public class Main extends Application{
    private Mascota mascota;
    private Inventario inventario;
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    static {
        try {
            FileHandler fileHandler = new FileHandler("app.log", true);
            fileHandler.setLevel(Level.ALL);
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al configurar el handler de archivo de log", e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOGGER.info("Registrando en archivo y consola 1 ");
        try {
            LOGGER.info("Registrando en archivo y consola 2");
            Parent root = FXMLLoader.load(getClass().getResource("interfaz.fxml"));
            primaryStage.setTitle("Simulación Mascota Virtual");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al iniciar la aplicación", e);
            // Manejo de error adecuado
        }
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = Main.class.getResourceAsStream("/config.csv");
        if (inputStream == null) {
            System.out.println("El archivo config.csv no se encontró en los recursos.");
            System.exit(-1);
        }

        Scanner in = new Scanner(inputStream);
        Main stage1 = new Main();
        stage1.readConfiguration(in);
        System.out.println("HOLA MUNDO");
        launch(args);
        //stage1.executeAction(new Scanner(System.in), System.out);
    }

    public void readConfiguration(Scanner in) {
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

    public void executeAction(Scanner in, PrintStream out) {
        String seleccion;
        float tiempoSimulado = 0;

        do {
            printEstado(tiempoSimulado, out);
            out.println("Seleccione un elemento del inventario, 'c' para continuar, y 'x' para salir:");
            seleccion = in.nextLine();

            if (seleccion.matches("\\d+")) {
                if (seleccion.equals("0")) {
                    //domir
                    mascota.modificarEnergia(100);
                    mascota.modificarSalud(15);
                    mascota.modificarFelicidad(15);
                    tiempoSimulado += 0.5;
                    mascota.envejecer();
                    continue;
                }
                int seleccionNum = Integer.parseInt(seleccion);
                inventario.usarItem(seleccionNum-1, mascota);
                tiempoSimulado += 0.5;
                mascota.envejecer();
            } else if (seleccion.equals("c")) {
                tiempoSimulado += 0.5;
                mascota.envejecer();
            }
        } while (!seleccion.equals("x") && mascota.getEstado() != Estado.Muerto);
        printEstado(tiempoSimulado, out);
    }

    public void printEstado(float step, PrintStream out) {
        out.printf("Tiempo simulado: %.1f%n", step);
        mascota.mostrarAtributos();
        inventario.mostrarItems();
    }
}
