package pe.edu.utp.Chat;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente {
    private Fcliente frameCliente;
    private Socket socket;
    private HiloCliente hiloCliente;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Cliente (Fcliente frameCliente){
        this.frameCliente = frameCliente;
    }
    public boolean conectar (String ip, String nombre){
        try {
            this.socket = new Socket(InetAddress.getByName(ip), 5000);
            this.output = new ObjectOutputStream(this.socket.getOutputStream());
            this.input = new ObjectInputStream(this.socket.getInputStream());
            this.output.writeObject(nombre);
            this.output.flush();
            this.hiloCliente = new HiloCliente(this.frameCliente, this.input);
            this.hiloCliente.start();
        }catch (IOException exception){
            return false;
        }
        return true;
    }
    public void enviar (String mensaje){
        try {
            this.output.writeObject(mensaje);
            this.output.flush();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
