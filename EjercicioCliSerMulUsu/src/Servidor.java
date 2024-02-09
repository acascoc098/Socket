import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Servidor {

    public static void main(String[] args) {

        List<Usuarios> list = new ArrayList<>();
        AtomicInteger ai = new AtomicInteger(0);
        int puerto;
        Socket socket = null;
        if (args.length < 1){
            System.out.println("Error, debes pasar el puerto del servidor");
            System.exit(1);
        }
        puerto = Integer.parseInt(args[0]);

        try{
            ServerSocket sersoc = new ServerSocket(puerto);
            while(true) {
                socket = sersoc.accept();
                ServerHilo serhil = new ServerHilo(socket, list, ai);
                serhil.start();
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class ServerHilo extends Thread{
    private Socket socket;
    private List<Usuarios> list;
    private AtomicInteger ai;

    public ServerHilo(Socket socket, List<Usuarios> list, AtomicInteger ai) {
        this.socket = socket;
        this.list = list;
        this.ai = ai;
    }
    @Override
    public void run(){
        try{
            String lineaRecibida;
            String operacion;
            Boolean iniciadoSesion = false;
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            while((lineaRecibida = br.readLine())!=null && lineaRecibida.length()>0){
                String[] split = lineaRecibida.split(" ");
                if (split.length != 2){
                    operacion = split[0];
                    if(operacion.equals("crear")) {
                        Usuarios user = new Usuarios(ai.incrementAndGet(), split[1], split[2]);
                        list.add(user);
                        pw.println("Usuario creado");
                        pw.flush();
                    } else if (operacion.equals("iniciar")) {
                        for (int i = 0; i<list.size();i++){
                            if (split[1].equals(list.get(i).getUser()) && split[2].equals(list.get(i).getPass())){
                                iniciadoSesion= true;
                                pw.println("Usuario logeado");
                                pw.flush();
                            }
                        }
                    } else if (operacion.equals("list")) {
                        if (iniciadoSesion==true){
                            for (int i = 0; i<list.size();i++){
                                pw.println("ID: "+list.get(i).getId()+" Usuario: "+list.get(i).getUser()+" Password: "+list.get(i).getPass());
                                pw.flush();
                            }
                        }else{
                            pw.println("No tienes permisos");
                            pw.flush();
                        }
                    } else if (operacion.equals("salir")) {
                        iniciadoSesion = false;
                        pw.println("Sesion cerrada");
                        pw.flush();
                    }else {
                        pw.println("Comando no reconocido");
                        pw.flush();
                    }

                }else{
                    pw.println("Comando no valido");
                    pw.flush();
                }
                pw.println("-------------------------------------");
                pw.flush();
                pw.println("Eliga una opcion:");
                pw.flush();
                pw.println("Crear user: crear usuario password");
                pw.flush();
                pw.println("Iniciar user: iniciar usuario password");
                pw.flush();
                pw.println("Listar usuarios(solo disponible para users): list");
                pw.flush();
                pw.println("Logout: salir");
                pw.flush();
                pw.println("-------------------------------------");
                pw.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}