package mh;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static final Scanner s = new Scanner(System.in);//Para meter la linea

    public static void main(String[] args) {
        int numPuertoServer;
        String hostServidor;
        Socket socketComun;
        InetAddress ipServer;
        String echo; //Línea a pasar

        //Comprobamos los argumentos
        if (args.length < 2) {
            System.out.println("ERROR: hace falta el puerto del servidor y el host");
            System.exit(1);
        }

        numPuertoServer = Integer.parseInt(args[0]); //Puerto donde se lanza
        hostServidor = args[1]; //Ip del servidor

        try {
            socketComun = new Socket(hostServidor, numPuertoServer);
            ipServer = socketComun.getInetAddress();
            System.out.printf("Cliente connect server %s...%n",ipServer.getHostAddress());

            Scanner gis = new Scanner(socketComun.getInputStream());//Para la respuesta del servidor
            PrintWriter pw = new PrintWriter(socketComun.getOutputStream());
            System.out.print(">");

            while (true) {
                //echo = s.nextLine();
                while ((echo = s.nextLine()).length() > 0) {
                    pw.println(echo);
                    pw.flush();//Mandamos lo escrito
                    System.out.println("Socket mandado!!!");
                    System.err.printf("Respuesta: %s%n", gis.nextLine());
                    System.out.print(">");
                }
            }

            //Cerramos los flujos
            //socketComun.close();
            //pw.close();
        } catch (UnknownHostException ex) {
            System.out.printf("ERROR: no se reconoce el servidor %s%n", hostServidor);
            ex.printStackTrace();
            System.exit(2);
        } catch (IOException e) {
            System.out.println("ERROR: flujo E/S");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
