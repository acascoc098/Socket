import java.io.*;
import java.net.*;

/**
 * Clase para el cliente, donde tenemos los comandos
 * @author Andrea Castilla Cocera
 * acasoc098@g.educand.es
 */
public class Cliente {
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1";
        int serverPort = 3000;
        String archivo = "listado.txt";
        
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress serverIP = InetAddress.getByName(serverAddress);
            byte[] buffer = new byte[500];
            
            String connectCommand = "connect " + serverAddress;
            DatagramPacket connectPacket = new DatagramPacket(connectCommand.getBytes(), connectCommand.length(), serverIP, serverPort);
            clientSocket.send(connectPacket);
            
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(responsePacket);
            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            int newPort = Integer.parseInt(response);
            
            String[] commands = {"list", "get archivo.txt", "remove "+ archivo, "disconnect"};
            for (String command : commands) {
                DatagramPacket commandPacket = new DatagramPacket(command.getBytes(), command.length(), serverIP, newPort);
                clientSocket.send(commandPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
