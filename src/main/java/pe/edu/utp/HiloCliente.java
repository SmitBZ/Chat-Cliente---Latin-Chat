package pe.edu.utp.Chat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HiloCliente extends Thread {
    private ObjectInputStream input;
    private Fcliente cliente;
    private boolean activo = true;
    private Servidor servidor;
    private ObjectOutputStream output;
    private String nombreUsuario;

    public HiloCliente (Fcliente cliente, ObjectInputStream input){
        this.cliente = cliente;
        this.input = input;
    }
    public void run (){
        while (this.activo){
            String mensaje;
            try {
                mensaje = (String)this.input.readObject();
                this.cliente.getTextMensajes().append(mensaje + "\n");
            }catch (IOException exception){
                this.activo = false;
            }catch (ClassNotFoundException exception){
                exception.printStackTrace();
            }
        }
    }
    private void recibirArchivo() {
        try {
            String nombreArchivo = (String) input.readObject();
            output.writeObject("OK");
            output.flush();
            FileOutputStream fileOutputStream = new FileOutputStream("ruta/del/servidor/" + nombreArchivo);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.close();
            servidor.enviarMensaje(nombreUsuario + " ha enviado el archivo: " + nombreArchivo);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

