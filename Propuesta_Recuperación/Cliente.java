import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1"; // Dirección IP del servidor
        int serverPort = 44444; // Puerto del servidor
        
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress serverIP = InetAddress.getByName(serverAddress);
            byte[] buffer = new byte[500];
            
            // Conexión al servidor
            String connectCommand = "connect " + serverAddress;
            DatagramPacket connectPacket = new DatagramPacket(connectCommand.getBytes(), connectCommand.length(), serverIP, serverPort);
            clientSocket.send(connectPacket);
            
            // Recepción del nuevo puerto asignado por el servidor
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(responsePacket);
            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            int newPort = Integer.parseInt(response);
            
            // Envío de comandos
            String[] commands = {"list", "get archivo.txt", "remove archivo.txt", "disconnect"};
            for (String command : commands) {
                DatagramPacket commandPacket = new DatagramPacket(command.getBytes(), command.length(), serverIP, newPort);
                clientSocket.send(commandPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
