package pe.edu.utp.Chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HiloServidor extends Thread{
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Servidor servidor;
    boolean activo = true;
    private String mensajeRecibido;
    public HiloServidor (String nombre, ObjectInputStream input, ObjectOutputStream output,
                         Servidor servidor){
        super(nombre);
        this.input = input;
        this.output = output;
        this.servidor = servidor;
    }
    public void run (){
        while (this.activo){
            try {
                this.mensajeRecibido = (String) this.input.readObject();
                System.out.println(this.getName() + "dice: " + this.mensajeRecibido);
                this.servidor.enviarMensaje(this.mensajeRecibido);
            }catch (IOException exception){
                this.activo = false;
            }catch (ClassNotFoundException exception){
                exception.printStackTrace();
            }
        }
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
