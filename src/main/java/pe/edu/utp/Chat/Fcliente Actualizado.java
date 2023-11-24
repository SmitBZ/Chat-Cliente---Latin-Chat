package pe.edu.utp.Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Fcliente extends JFrame {
    private JPanel panelConexion;
    private JTextField textIP;
    private JTextField textNombre;
    private JButton buttonConectar;
    private JButton buttonLimpiar;
    private JButton buttonEnviarMensaje;
    private JButton buttonEnviarArchivo;
    private JTextField textMensaje;
    private JLabel labelNombre;
    private JLabel labelIP;
    private JTextArea textMensajes;
    private JPanel panelMensaje;
    private Cliente cliente;

    public static void main(String[] args) {
        Fcliente frame = new Fcliente();
        frame.setVisible(true);
    }

    public Fcliente() {
        initGUI();
        this.cliente = new Cliente(this);
    }

    public JTextArea getTextMensajes() {
        return this.textMensajes;
    }

    private void initGUI() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mini chat. Cliente");
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0, 0, 0));

        panelConexion = new JPanel();
        panelConexion.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelConexion.setBackground(new Color(0, 0, 0));
        add(panelConexion, BorderLayout.NORTH);

        labelIP = new JLabel();
        panelConexion.add(labelIP);
        labelIP.setText("IP:");
        labelIP.setForeground(new Color(255, 0, 0));

        textIP = new JTextField();
        panelConexion.add(textIP);
        textIP.setText("127.0.0.1");

        labelNombre = new JLabel();
        panelConexion.add(labelNombre);
        labelNombre.setText("Nombre Cliente");
        labelNombre.setForeground(new Color(255, 0, 0));

        textNombre = new JTextField();
        panelConexion.add(textNombre);
        textNombre.setPreferredSize(new Dimension(100, 20));

        buttonConectar = new JButton();
        panelConexion.add(buttonConectar);
        buttonConectar.setText("Conectar");
        buttonConectar.setForeground(new Color(255, 0, 0));
        buttonConectar.setBackground(new Color(0, 0, 0));
        buttonConectar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                BConectarActionPerformed(evt);
            }
        });

        panelConexion.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonLimpiar = new JButton();
        panelConexion.add(buttonLimpiar);
        buttonLimpiar.setText("Limpiar Mensaje");
        buttonLimpiar.setEnabled(true);
        buttonLimpiar.setBackground(new Color(0, 0, 0));
        buttonLimpiar.setForeground(new Color(255, 0, 0));
        buttonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonLimpiarActionPerformed(e);
            }
        });

        panelMensaje = new JPanel();
        BorderLayout PMensajeLayout = new BorderLayout();
        panelMensaje.setLayout(PMensajeLayout);
        panelMensaje.setBackground(new Color(0, 0, 0));
        add(panelMensaje, BorderLayout.SOUTH);

        textMensaje = new JTextField();
        panelMensaje.add(textMensaje);
        textMensaje.setEnabled(false);
        textMensaje.setPreferredSize(new Dimension(100, 21));

        buttonEnviarMensaje = new JButton();
        panelMensaje.add(buttonEnviarMensaje, BorderLayout.LINE_END);
        buttonEnviarMensaje.setText("Enviar Mensaje");
        buttonEnviarMensaje.setForeground(new Color(255, 0, 0));
        buttonEnviarMensaje.setBackground(new Color(0, 0, 0));
        buttonEnviarMensaje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonEnviarMensajeActionPerformed(evt);
            }
        });
        buttonEnviarMensaje.setEnabled(false);

        buttonEnviarArchivo = new JButton();
        panelMensaje.add(buttonEnviarArchivo, BorderLayout.BEFORE_LINE_BEGINS);
        buttonEnviarArchivo.setText("Enviar Archivo");
        buttonEnviarArchivo.setForeground(new Color(255, 0, 0));
        buttonEnviarArchivo.setBackground(new Color(0, 0, 0));
        buttonEnviarArchivo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                buttonEnviarArchivoActionPerformed(evt);
            }
        });
        buttonEnviarArchivo.setEnabled(false);

        textMensajes = new JTextArea();
        textMensajes.setBackground(new Color(0, 0, 0));
        textMensajes.setForeground(new Color(204, 0, 0));
        add(new JScrollPane(textMensajes), BorderLayout.CENTER);
        setSize(800, 600);
    }

    private void BConectarActionPerformed(ActionEvent evt) {
        if (!this.textNombre.getText().equals("")) {
            if (this.cliente.conectar(this.textIP.getText(), this.textNombre.getText())) {
                this.buttonConectar.setEnabled(false);
                this.textNombre.setEnabled(false);
                this.textIP.setEnabled(false);
                this.textMensaje.setEnabled(true);
                this.buttonEnviarMensaje.setEnabled(true);
                this.buttonEnviarArchivo.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Error de Servidor", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Inserte Nombre", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buttonEnviarMensajeActionPerformed(ActionEvent evt) {
        this.cliente.enviar(this.textMensaje.getText());
        this.textMensaje.setText("");
    }

    private void buttonEnviarArchivoActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            textMensajes.append("Enviado archivo: " + selectedFile.getName() + "\n");
        }
    }

    private void buttonLimpiarActionPerformed(ActionEvent e){
        textMensajes.setText("");
    }

}
