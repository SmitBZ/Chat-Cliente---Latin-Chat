package pe.edu.utp.Chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    private Socket socket;
    private ServerSocket serverSocket;
    private String NombreUsuarioConectado;
    private ArrayList<HiloServidor> hilosServidor = new
            ArrayList<HiloServidor>();

    public void iniciarServidor(){
        try {
            this.serverSocket = new ServerSocket(5000);
            int contador = 1;
            while (true){
                System.out.println("Esperando nueva conexion");
                this.socket = serverSocket.accept();
                System.out.println("Conexion" + contador + "desde:" +
                        this.socket.getInetAddress().getHostName() + "IP:" +
                        this.socket.getInetAddress().getHostAddress());
                contador++;
                ObjectInputStream input = new ObjectInputStream(this.socket.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(this.socket.getOutputStream());
                this.NombreUsuarioConectado = (String)input.readObject();
                System.out.println(this.NombreUsuarioConectado + "se ha conectado");
                HiloServidor hiloServidor = new HiloServidor(this.NombreUsuarioConectado, input, output, this);
                hiloServidor.start();
                this.hilosServidor.add(hiloServidor);
                this.enviarClienteConectado();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException exception){
            exception.printStackTrace();
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void enviarClienteConectado(){
        for (HiloServidor hilo : this.hilosServidor){
            hilo.enviar(this.NombreUsuarioConectado + "se ha conectado");
        }
    }
    public void enviarMensaje(String mensaje){
        for (HiloServidor hilo : this.hilosServidor){
            hilo.enviar(mensaje);
        }
    }
    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciarServidor();
    }
}

