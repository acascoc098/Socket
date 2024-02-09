package Servidor.ppal;

import java.io.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Servidor.logic.Request;
import Servidor.resource.History;

import java.text.*;

/**
 * Clase del servidor
 * @author Andrea Castilla Cocera
 */
public class Server {
    public static void main(String[] args) {
        ServerSocket socketServer;
        Socket sComunicacion;
        InetAddress ipCliente;
        int numPuertoServer;
        int numPuertoCliente;
        System.out.println("acascoc098 Andrea Castilla Cocera");

        if (args.length < 0) {
            System.out.println("ERROR: se debe pasar el puerto");
            System.exit(1);
        }

        numPuertoServer = Integer.parseInt(args[0]);

        try {
            socketServer = new ServerSocket(numPuertoServer);
            while (true) {
                sComunicacion = socketServer.accept();
                numPuertoCliente = sComunicacion.getPort();
                ipCliente = sComunicacion.getInetAddress();
                System.out.printf("Conexión establecida: IP...%s; Puerto...%d%n",ipCliente,numPuertoCliente);
                ServerThread hiloServer = new ServerThread(sComunicacion);
                hiloServer.start();
            }

        } catch (Exception e) {
            System.out.println("ERROR: no se ha podido crearla conexión");
        }
    }
}


/**
 * Clase del hilo
 * @author Andrea Castilla Cocera
 */
class ServerThread extends Thread{
    private Socket socketComun;

    public ServerThread(Socket mSocket){
        this.socketComun = mSocket;
    }

    @Override
    public void run(){
        InetAddress ipCliente;

        try {
            Scanner gis = new Scanner(socketComun.getInputStream());
            PrintWriter pw = new PrintWriter(socketComun.getOutputStream());
            String fecha;

            while (true) {
                while ((fecha = gis.nextLine()) != null && fecha.length() > 0) {
                    ipCliente = socketComun.getInetAddress();

                    System.out.printf("Desde %s por puerto %d -> %s%n", ipCliente.getHostAddress(),socketComun.getPort(), fecha);
                    fecha = "@" + new SimpleDateFormat(fecha).format(new Date()) + "@";
                    Request pet = new Request(ipCliente, fecha);
                    History his = new History();
                    //his.getPeticiones().add(pet);
                    //his.info();
                    pw.println(fecha);
                    pw.flush();
                }
            }

        } catch (IOException e) {
            System.out.println("ERROR: flujos E/S");
        }catch(NoSuchElementException e){
            System.out.println("Conexión con el cliente cerrada....");
            if (socketComun != null)
                try{
                    System.out.printf("Cerramos socket del Servidor....%n");
                    socketComun.close();
                }catch (IOException ex){
                    System.out.println("ERROR: flujos E/S al cerrar el Socket sin cliente");
                    ex.printStackTrace();
                }
        }catch(Exception e){
            System.out.println("ERROR: formato de fecha no permitido");
            e.printStackTrace();
        }
    }
}
