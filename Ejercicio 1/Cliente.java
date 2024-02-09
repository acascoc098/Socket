import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

    public static void main(String[] args) {
        final String SERVIDOR = "localhost";
        final int PUERTO = 12345;

        try {
            Socket socket = new Socket(SERVIDOR, PUERTO);

            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            // Enviar saludo al servidor
            String nombreCliente = "Andrea (acascoc098)";
            String saludo = "Hola, soy " + nombreCliente + " y quiero saludarle";
            salida.println(saludo);

            // Recibir respuesta del servidor
            String respuesta = entrada.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
