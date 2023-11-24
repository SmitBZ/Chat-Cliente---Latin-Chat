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
    private ObjectOutputStream outputStream;

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

    public boolean enviarArchivo(File file) {
        try {
            outputStream.writeObject("ARCHIVO");
            outputStream.flush();
            outputStream.writeObject(file.getName());
            outputStream.flush();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                outputStream.flush();
            }
            fileInputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

