package org.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GuiRegistrar extends JFrame {

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
        Color azulColor = new Color(12, 106, 225, 186);
        Color errorRojo = new Color(255, 0, 0, 100);


        public GuiRegistrar() {

            initComponentes();

            setContentPane(panelMadre);
            setTitle("Login");
            setSize(600, 550);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            configurarDiseño();

            registrarBtn.addActionListener(e -> {
                GuiCreate registro = new GuiCreate();
                registro.setVisible(true);
                registro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            });

            ingresarBtn.addActionListener(e -> {
                SqlConnection sqlConnection = new SqlConnection();
                String usuario = usuarioTF.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (usuario.equals("") || password.equals("")) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese todos los datos");
                } else {
                    if (sqlConnection.login(usuario, password)) {
                        GuiMostrar mostrar = new GuiMostrar();
                        mostrar.setVisible(true);
                        mostrar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                    }
                }
            });


        }


        public static void main(String[] args) {
            org.GUI.Login frame = new org.GUI.Login();
            frame.setVisible(true);

        }

        private void initComponentes() {

            panelMadre = new JPanel(new BorderLayout());
            panelContenido = new JPanel(new BorderLayout());
            panelImagen = new JPanel(new GridLayout());
            panelDatos = new JPanel(new GridBagLayout());
            panelBotones = new JPanel();
            panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS)); //para que los botones queden uno al lado del otro


            ImageIcon icon = new ImageIcon("src/resources/Imagenes/login.png");
            imagenLabel = new JLabel(icon);
            inicioLabel = new JLabel("Inicio de sesion");
            usuarioLabel = new JLabel("Usuario");
            usuarioTF = new JTextField();
            errorUserLabel = new JLabel("");
            passwordLabel = new JLabel("Contraseña");
            passwordField = new JPasswordField();
            errorPassLabel = new JLabel("");
            ingresarBtn = new JButton("Ingresar");
            registrarBtn = new JButton("Registrarse");





            panelMadre.setBackground(grisColor);
            panelContenido.setBackground(blancoColor);

            panelMadre.add(panelContenido, BorderLayout.CENTER);
            panelMadre.setBorder(new EmptyBorder(50, 50, 50, 50));

            panelImagen.add(imagenLabel);
            panelImagen.setBackground(blancoColor);


            panelBotones.add(registrarBtn);
            panelBotones.add(Box.createVerticalStrut(35));
            panelBotones.add(Box.createHorizontalGlue());
            panelBotones.add(ingresarBtn);

            panelContenido.add(panelImagen, BorderLayout.NORTH);
            panelContenido.add(panelDatos, BorderLayout.CENTER);
            panelContenido.add(panelBotones, BorderLayout.SOUTH);

        }

        private void configurarDiseño() {

            panelImagen.setBackground(blancoColor);
            panelDatos.setBackground(blancoColor);
            panelBotones.setBackground(blancoColor);

            inicioLabel.setHorizontalAlignment(JLabel.CENTER);
            inicioLabel.setFont(new Font("Arial", Font.BOLD, 20));
            usuarioLabel.setHorizontalAlignment(JLabel.CENTER);
            usuarioLabel.setFont(new Font("Arial", Font.BOLD, 15));
            passwordLabel.setHorizontalAlignment(JLabel.CENTER);
            passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));
            usuarioTF.setFont(new Font("Arial", Font.PLAIN, 13));
            passwordField.setFont(new Font("Arial", Font.PLAIN, 13));
            usuarioTF.setPreferredSize(new Dimension(0, 30));
            passwordField.setPreferredSize(new Dimension(0, 30));

            errorUserLabel.setForeground(errorRojo);
            errorUserLabel.setFont(new Font("Arial", Font.BOLD, 11));

            errorPassLabel.setForeground(errorRojo);
            errorPassLabel.setFont(new Font("Arial", Font.BOLD, 11));

            ingresarBtn.setBackground(azulColor);
            ingresarBtn.setForeground(Color.WHITE);
            ingresarBtn.setFont(new Font("Arial", Font.BOLD, 15));
            ingresarBtn.setFocusPainted(false);

            registrarBtn.setContentAreaFilled(false);
            registrarBtn.setBorderPainted(false);
            registrarBtn.setOpaque(false);
            registrarBtn.setForeground(azulColor);
            registrarBtn.setFont(new Font("Arial", Font.BOLD, 15));
            registrarBtn.setFocusPainted(false);

            //Configuracion del panelDatos
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 0, 5, 0); //espacio entre los elementos del panelDatos
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1; //ancho de los elementos del panelDatos
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            panelDatos.add(inicioLabel, gbc);

            gbc.gridy = 1;
            gbc.gridwidth = 1;
            panelDatos.add(usuarioLabel, gbc);

            gbc.insets = new Insets(5, 0, 0, 0);

            gbc.gridy = 2;
            panelDatos.add(usuarioTF, gbc);

            gbc.insets = new Insets(0, 0, 5, 0);
            gbc.gridy = 3;
            panelDatos.add(errorUserLabel, gbc);

            gbc.insets = new Insets(5, 0, 5, 0);
            gbc.gridy = 4;
            panelDatos.add(passwordLabel, gbc);

            gbc.insets = new Insets(5, 0, 0, 0);

            gbc.gridy = 5;
            panelDatos.add(passwordField, gbc);
            gbc.insets = new Insets(0, 0, 0, 0);

            gbc.gridy = 6;
            panelDatos.add(errorPassLabel, gbc);

            panelImagen.setBorder(new EmptyBorder(22, 0, 0, 0));
            panelDatos.setBorder(new EmptyBorder(0, 22, 0, 22));
            panelBotones.setBorder(new EmptyBorder(11, 22, 22, 22));


        }


    }


