import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    static Scanner mt = new Scanner(System.in);
    public static void main(String[] args) {

        String ip;
        int puerto;
        Socket socket;
        String echo;
        String echo2;
        if (args.length < 2){
            System.out.println("Error, debes pasar el puerto del servidor y host servidor");
            System.exit(1);
        }
        puerto = Integer.parseInt(args[0]);
        ip = args[1];
        try{
            socket = new Socket(ip, puerto);
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Conexion establecida");
            System.out.println("Eliga una opcion:");
            System.out.println("Crear user: crear usuario password");
            System.out.println("Iniciar user: iniciar usuario password");
            System.out.println("Listar usuarios(solo disponible para users): list");
            System.out.println("Logout: salir");
            System.out.println("-------------------------------------");

            while (true){

                System.out.print(">");
                echo = mt.nextLine();
                if (echo.equals("")) break;
                pw.println(echo);
                pw.flush();
                System.out.println("Mensaje enviado");
                do {
                    echo2 = in.readLine();
                    System.out.println(echo2);
                }while (in.ready() && echo2!=null);
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
