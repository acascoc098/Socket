import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        final int PUERTO = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO);
            System.out.println("Servidor esperando conexiones en el puerto " + PUERTO + "...");

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clienteSocket.getInetAddress());

                BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                PrintWriter salida = new PrintWriter(clienteSocket.getOutputStream(), true);

                String mensajeCliente = entrada.readLine();
                System.out.println("Mensaje del cliente: " + mensajeCliente);

                // Procesar el mensaje del cliente y enviar la respuesta
                if (mensajeCliente.startsWith("Hola, soy")) {
                    String nombreCliente = mensajeCliente.substring(10, mensajeCliente.indexOf("y quiero saludarle") - 1);
                    String respuesta = "Muchas gracias, yo también le saludo a usted, " + nombreCliente + ".";
                    salida.println(respuesta);
                } else {
                    salida.println("No entiendo el mensaje del cliente.");
                }

                clienteSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
