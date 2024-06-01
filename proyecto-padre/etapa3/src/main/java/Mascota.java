
public class Mascota {
    private String nombre;
    private float edad;
    private int salud;
    private int energia;
    private int felicidad;
    private Estado estado;

    // Constructor
    public Mascota(String nombre) {
        this.nombre = nombre;
        this.edad = 0;
        this.salud = 100;
        this.energia = 100;
        this.felicidad = 50;
        this.estado = Estado.Neutro; // Estado neutro inicial
    }

    // Métodos para obtener información de la mascota
    public void mostrarAtributos() {
        System.out.println("Mascota Virtual\n");
        System.out.println("Atributos\n---------");
        System.out.println("Nombre: " + nombre);
        System.out.println("Edad: " + edad);
        System.out.println("Salud: " + salud);
        System.out.println("Energía: " + energia);
        System.out.println("Felicidad: " + felicidad);
        System.out.println("Estado: " + getEstadoString() + "\n");
    }

    // Método para modificar la salud
    public void modificarSalud(int puntos) {
        salud = Math.min(100, salud + puntos);
        actualizarEstado();
    }

    // Método para modificar la energía
    public void modificarEnergia(int puntos) {
        energia = Math.min(100, energia + puntos);
        actualizarEstado();
    }

    // Método para modificar la felicidad
    public void modificarFelicidad(int puntos) {
        felicidad = Math.min(100, felicidad + puntos);
        actualizarEstado();
    }

    // Simular el paso del tiempo
    public void envejecer() {
        edad += 0.5;
        salud -= 5;
        energia -= 5;
        felicidad -= 5;
        actualizarEstado();
    }

    // Actualizar el estado de la mascota basado en sus atributos
    private void actualizarEstado() {
        if (salud == 0 || energia == 0 || edad >= 15) {
            estado = Estado.Muerto;
        } else if (energia <= 15) {
            estado = Estado.Cansado;
        } else if (edad > 5 && salud <= 30 && energia <= 30) {
            estado = Estado.Enojado;
        } else if ((edad <= 5 && salud <= 20) || (edad > 5 && edad <= 10 && salud <= 50)) {
            estado = Estado.Hambriento;
        } else if (felicidad <= 20) {
            estado = Estado.Triste;
        } else if (felicidad >= 60) {
            estado = Estado.Feliz;
        } else {
            estado = Estado.Neutro;
        }
    }

    public String getEstadoString() {
        switch (estado) {
            case Neutro:
                return "(-_-) Meh....";
            case Feliz:
                return "\\(^_^)/ Weeeeeh!";
            case Triste:
                return "(._.) snif....";
            case Hambriento:
                return "(0o0) hambre hambre!";
            case Enojado:
                return "(ôwô) grrrr....";
            case Cansado:
                return "(=_=) zzzz....";
            case Muerto:
                return "(x_x) fin del juego";
            default:
                return "Estado desconocido";
        }
    }
    
    public Estado getEstado() {
        return estado;
    }

    public String getEstadoAsString() {
        return estado.toString();
    }

    public String getNombre() {
        return nombre;

    }

    public float getEdad() {
        return edad;
    }

    public float getSaludPorcentaje() {
        return salud/100.0f;
    }

    public float getEnergiaPorcentaje() {
        return energia/100.0f;
    }

    public float getFelicidadPorcentaje() {
        return felicidad/100.0f;
    }
}