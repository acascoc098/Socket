package Servidor.resource;

import java.util.ArrayList;

import Servidor.logic.Request;

/**
 * @author Andrea Castilla Cocera
 */
public class History {
    private ArrayList<Request> peticiones;
    private int contador = 0;
    
    public History() {
    }

    public ArrayList<Request> getPeticiones() {
        return peticiones;
    }

    public void setPeticiones(ArrayList<Request> peticiones) {
        this.peticiones = peticiones;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public void info() {
        contador = peticiones.size();
        System.out.println("Nº peticiones: " + contador);
    }
}
