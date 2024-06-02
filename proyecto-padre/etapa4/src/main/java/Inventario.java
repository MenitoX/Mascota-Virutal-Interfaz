package com.example;
import java.util.ArrayList;

public class Inventario {
    private ArrayList<Item> items;

    public Inventario() {
        this.items = new ArrayList<>();
    }

    public void agregarItem(Item item) {
        items.add(item);
    }

    public void mostrarItems() {
        System.out.println("Acciones\n--------");
        System.out.println("0: dormir");
        for (Item item : items) {
            item.mostrarInfo();
        }
    }

    public void usarItem(int id, Mascota mascota) {
        if (id < items.size() && items.get(id) != null) {
            items.get(id).usar(mascota);
            if (items.get(id).cantidad <= 0) {
                items.remove(id);
            }
        }
    }

    public String[] getComida() {
        ArrayList<String> comida = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Comida) {
                comida.add(item.nombre);
            }
        }
        return comida.toArray(new String[0]);
    }

    public String[] getMedicina() {
        ArrayList<String> medicina = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Medicina) {
                medicina.add(item.nombre);
            }
        }
        return medicina.toArray(new String[0]);
    }
}
