package com.example;
public abstract class Item {
    protected int id;
    protected String nombre;
    protected int cantidad;

    public Item(int id, String nombre, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public abstract void usar(Mascota mascota);

    public void mostrarInfo() {
        System.out.println(id + ": " + nombre + ", cantidad: " + cantidad);
    }

    public abstract void printItemAplicado();
}

class Comida extends Item {
    public Comida(int id, String nombre, int cantidad) {
        super(id, nombre, cantidad);
    }

    @Override
    public void usar(Mascota mascota) {
        mascota.modificarSalud(20);
        mascota.modificarEnergia(20);
        cantidad--;
    }

    @Override
    public void printItemAplicado() {
        System.out.println("Comida aplicada: " + nombre);
    }
}

class Medicina extends Item {
    public Medicina(int id, String nombre, int cantidad) {
        super(id, nombre, cantidad);
    }

    @Override
    public void usar(Mascota mascota) {
        mascota.modificarSalud(40);
        cantidad--;
    }

    @Override
    public void printItemAplicado() {
        System.out.println("Medicina aplicada: " + nombre);
    }
}

class Juguete extends Item {
    public Juguete(int id, String nombre, int cantidad) {
        super(id, nombre, cantidad);
    }

    @Override
    public void usar(Mascota mascota) {
        mascota.modificarFelicidad(30);
        cantidad--;
    }

    @Override
    public void printItemAplicado() {
        System.out.println("Juguete usado: " + nombre);
    }
}
