package org.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Login extends JFrame{
    private JPanel panelMadre;
    private JPanel panelContenido;
    private JPanel panelBotones;
    private JPanel panelImagen;
    private JPanel panelDatos;
    private JPasswordField passwordField;
    private JTextField usuarioTF;
    private JButton ingresarBtn;
    private JButton registrarBtn;
    private JLabel inicioLabel;
    private JLabel imagenLabel;
    private JLabel usuarioLabel;
    private JLabel errorUserLabel;
    private JLabel passwordLabel;
    private JLabel errorPassLabel;
    Color blancoColor = new Color(255, 255, 255, 202);
    Color grisColor = new Color(255, 255, 255, 89);

    private void initComponentes(){

        panelMadre = new JPanel(new BorderLayout());
        panelContenido = new JPanel(new GridBagLayout());
        panelImagen = new JPanel(new GridLayout());
        panelDatos = new JPanel(new GridLayout(7, 1));
        panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        passwordField = new JPasswordField();
        usuarioTF = new JTextField();
        ingresarBtn = new JButton("Ingresar");
        registrarBtn = new JButton("Registrarse");
        inicioLabel = new JLabel("Inicio de sesion");
        usuarioLabel = new JLabel("Usuario");
        passwordLabel = new JLabel("Contraseña");

        errorUserLabel = new JLabel("aaaaaaaaa");
        errorPassLabel = new JLabel("aaaaaaaaa");


        ImageIcon icon = new ImageIcon("src/resources/Imagenes/login.png");
        imagenLabel = new JLabel(icon);


        panelMadre.setBackground(grisColor);
        panelContenido.setBackground(blancoColor);

        panelMadre.add(panelContenido, BorderLayout.CENTER);
        panelMadre.setBorder(new EmptyBorder(50, 50, 50, 50));

        panelImagen.add(imagenLabel);
        panelImagen.setBackground(blancoColor);

        panelDatos.add(inicioLabel);
        panelDatos.add(usuarioLabel);
        panelDatos.add(usuarioTF);
        panelDatos.add(errorUserLabel);
        panelDatos.add(passwordLabel);
        panelDatos.add(passwordField);
        panelDatos.add(errorPassLabel);
        panelDatos.setBackground(blancoColor);

        panelBotones.add(registrarBtn);
        panelBotones.add(Box.createVerticalStrut(35));
        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(ingresarBtn);
        panelBotones.setBackground(blancoColor);

    }

    private void configurarDiseño(){

        inicioLabel.setHorizontalAlignment(JLabel.CENTER);
        inicioLabel.setFont(new Font("Arial", Font.BOLD, 20));
        usuarioLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);

        errorUserLabel.setForeground(new Color(255, 0, 0, 100));
        errorUserLabel.setFont(new Font("Arial", Font.BOLD, 13));

        errorPassLabel.setForeground(new Color(255, 0, 0, 100));
        errorPassLabel.setFont(new Font("Arial", Font.BOLD, 13));

        errorUserLabel.setOpaque(true);
        errorPassLabel.setOpaque(true);
        errorPassLabel.setBackground(Color.BLACK);
        errorUserLabel.setBackground(Color.BLACK);


        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panelContenido.add(panelImagen, gbc);

        gbc.gridy = 1;
        gbc.gridheight = 2;
        panelContenido.add(panelDatos, gbc);

        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelContenido.add(panelBotones, gbc);
        panelContenido.setBorder(new EmptyBorder(0, 20, 0, 20));


    }

    public Login(){

        initComponentes();

        setContentPane(panelMadre);
        setTitle("Login");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        configurarDiseño();




    }



    public static void main(String[] args) {
        Login frame = new Login();
        frame.setVisible(true);

    }
}
