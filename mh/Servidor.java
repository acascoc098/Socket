package mh;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Servidor {
    public static void main(String[] args) {
        int numPuertoServer;
        Socket socketComun = null;//Al principio estrá a null
        ServerSocket socketServer;
        InetAddress ipCliente;
        int puertoCliente;

        //Comprobamos que se pasa el puerto del servidor
        if (args.length < 1) {
            System.out.println("ERROR: debes pasar el puerto del servidor");
            System.exit(1);
        }

        numPuertoServer = Integer.parseInt(args[0]);

        try {
            socketServer = new ServerSocket(numPuertoServer);//Deber ser Server Socket para el servidor
            while (true) {
                socketComun = socketServer.accept();//Se acepta el enlace
                ipCliente = socketComun.getInetAddress();//Sacamos la ip del cliente
                puertoCliente = socketComun.getPort();//Sacamos el puerto del cliente
                System.out.printf("Conexión establecida con cliente IP ....%s ; Puerto: %d%n", ipCliente,puertoCliente);
                ServerMH svr = new ServerMH(socketComun);
                svr.start();//Llamamos al hilo y lo inicamos
            }

        } catch (IOException e) {
            System.out.println("ERROR: en el flujo E/S");
            e.printStackTrace();
        }
    }
}

class ServerMH extends Thread{
    private Socket socketComunicacion;

    public ServerMH(Socket mSocket){
        socketComunicacion = mSocket;//Establecemos el socket
    }
    
    @Override
    public void run(){
        InetAddress ipCliente;

        try {
            Scanner gis = new Scanner(socketComunicacion.getInputStream());
            PrintWriter pw = new PrintWriter(socketComunicacion.getOutputStream());

            String linea;

            while (true) {
                
                while ((linea = gis.nextLine()) != null && linea.length() > 0) {
                    ipCliente = socketComunicacion.getInetAddress();
                    System.out.printf("Recibo desde %s por puerto %d> %s%n", ipCliente.getHostAddress(),socketComunicacion.getPort(),linea);
                    linea = "$"+linea+"$";
                    pw.println(linea);//Mandamos la linea modificada
                    pw.flush();//Limpiamos buffer
                }
                /*//while (gis.hasNextLine()) {
                    linea = gis.nextLine();
                    if (linea.length() > 0) {
                        ipCliente = socketComunicacion.getInetAddress();
                        System.out.printf("Recibo desde %s por puerto %d> %s%n", ipCliente.getHostAddress(),socketComunicacion.getPort(),linea);
                        linea = "$" + linea + "$";
                        pw.println(linea); // Mandamos la línea modificada
                        pw.flush(); // Limpiamos buffer
                    }
                //}*/
                
            }

            //socketComunicacion.close();
            //pw.close();
        } catch (IOException e) {
            System.out.println("ERROR: en el flujo E/S");
            e.printStackTrace();
        } catch (NoSuchElementException e){
            System.out.println("El Cliente ha cerrado su conexión....");
            if (socketComunicacion!=null)
                try{
                    System.out.printf("Socket servidor Multihilo, cerrado%n");
                     socketComunicacion.close();
                }catch (IOException ex){
                    System.out.println("ERROR: flujo de E/S al cerrar el Socket una vez desconectado con cliente");
                    ex.printStackTrace();
                }
        }
    }
    
}