package Servidor.logic;

import java.net.InetAddress;

/**
 * @author Andrea Castilla Cocera
 */
public class Request {
    private InetAddress ipCliente;
    private String fecha;

    public Request(InetAddress ipCliente, String fecha) {
        this.ipCliente = ipCliente;
        this.fecha = fecha;
    }

    public InetAddress getIpCliente() {
        return ipCliente;
    }

    public void setIpCliente(InetAddress ipCliente) {
        this.ipCliente = ipCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
}
