package Cliente;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Clase para el cliente
 * @author Andrea Castilla Cocera
 */
public class Client {
    public static void main(String[] args) {
        int numPuerto;
        String hostServer;
        Socket sComunicacion;
        InetAddress ipServer;
        String fecha;
        Scanner sc = new Scanner(System.in);//Scanner para pedir la fecha por teclado
        System.out.println("acascoc098 Andrea Castilla Cocera");

        if (args.length < 2) {//Comprobamos que se pasen los argumentos necesarios
            System.out.println("ERROR: debes pasar el ip y el puerto del servidor");
            System.exit(1);
        }

        numPuerto = Integer.parseInt(args[1]);
        hostServer = args[0];

        try {
            sComunicacion = new Socket(hostServer,numPuerto);
            ipServer = sComunicacion.getInetAddress();
            System.out.println("----------------------------------------");
            System.out.println("Indicca uno de los siguintes formatos:");
            System.out.println("yyyyMMddHHmmss");
            System.out.println("EEE, MM d, yy");
            System.out.println("h:mm a");
            System.out.println("hh a, zzzz");
            System.out.println("k:mm a, z");
            System.out.println("yyyy.MMM.dd GGG hh:mm aaa");
            System.out.println("----------------------------------------");
            System.out.printf("Client connect server: ip...%s%n",ipServer.getHostAddress());

            Scanner gis = new Scanner(sComunicacion.getInputStream());//Scanner para pedir la respuesta
            PrintWriter pw = new PrintWriter(sComunicacion.getOutputStream());
            System.out.print("fechaTCP>");

            while (true) {
                while ((fecha = sc.nextLine()).length() > 0) {//Comprobamos que se escribe algo por teclado
                    pw.println(fecha);
                    pw.flush();
                    System.out.printf("fecha: %s%n", gis.nextLine());
                    System.out.print("fechaTCP>");
                }
            }

        }catch (UnknownHostException n){
            System.out.printf("ERROR: no se encuentra el servidor...%s%n", hostServer);
            System.exit(2);
        }catch (IOException e) {
            System.out.println("ERROR: flujo de E/S");
            System.exit(1);
        } 
    }
}
