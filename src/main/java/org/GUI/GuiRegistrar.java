package org.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.util.Arrays;

public class GuiRegistrar extends JFrame {

    private JPanel panelMadre;
    private JPanel panelContenido;
    private JPanel panelBotonRegresar;
    private JPanel panelBotonRegistrar;
    private JPanel panelDatos;
    private JLayeredPane nombreJLayer;
    private JLayeredPane apellidoJLayer;
    private JLayeredPane correoJLayer;
    private JLayeredPane codigoJLayer;
    private JLayeredPane passwordJLayer;
    private JLayeredPane confirmarpasswordJLayer;
    private JTextField nombreTF;
    private JTextField apellidoTF;
    private JTextField correoTF;
    private JTextField codigoTF;
    private JPasswordField passwordField;
    private JPasswordField confirmarPasswordField;
    private JButton registrarBtn;
    private JLabel regresarLabel;


    Color blancoColor = new Color(255, 255, 255, 202);
    Color grisColor = new Color(255, 255, 255, 89);
    Color azulColor = new Color(12, 106, 225, 186);

    private static final int numFilas = 6;

    public GuiRegistrar() {

        initComponentes();

        setContentPane(panelMadre);
        setTitle("Registrar");
        setSize(650, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        configurarDesign();

        configurarEventos();


    }

    private void initComponentes() {

        panelMadre = new JPanel(new BorderLayout());
        panelContenido = new JPanel(new BorderLayout());
        panelBotonRegresar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotonRegistrar = new JPanel();
        panelDatos = new JPanel(new GridLayout(numFilas, 1, 0, 20));

        nombreTF = new JTextField();
        apellidoTF = new JTextField();
        correoTF = new JTextField();
        codigoTF = new JTextField();
        passwordField = new JPasswordField();
        confirmarPasswordField = new JPasswordField();

        nombreJLayer = createLayeredTextField(nombreTF, "Nombre: ");
        apellidoJLayer = createLayeredTextField(apellidoTF, "Apellido: ");
        correoJLayer = createLayeredTextField(correoTF, "Correo: ");
        codigoJLayer = createLayeredTextField(codigoTF, "Codigo: ");
        passwordJLayer = createLayeredTextField(passwordField, "Contraseña: ");
        confirmarpasswordJLayer = createLayeredTextField(confirmarPasswordField, "Confirmar Contraseña: ");

        registrarBtn = new JButton("Registrar");
        regresarLabel = new JLabel();

        panelMadre.add(panelContenido, BorderLayout.CENTER);
        panelMadre.setBorder(new EmptyBorder(50, 50, 50, 50));

        panelContenido.add(panelBotonRegresar, BorderLayout.NORTH);
        panelContenido.add(panelDatos, BorderLayout.CENTER);
        panelContenido.add(panelBotonRegistrar, BorderLayout.SOUTH);

        panelBotonRegresar.add(regresarLabel);
        panelBotonRegresar.setBorder(new EmptyBorder(10, 22, 10, 22));
        panelBotonRegistrar.add(registrarBtn);
        panelBotonRegistrar.setBorder(new EmptyBorder(10, 22, 10, 22));


        panelDatos.add(nombreJLayer);
        panelDatos.add(apellidoJLayer);
        panelDatos.add(correoJLayer);
        panelDatos.add(codigoJLayer);
        panelDatos.add(passwordJLayer);
        panelDatos.add(confirmarpasswordJLayer);
        panelDatos.setBorder(new EmptyBorder(0, 22, 0, 22));


    }

    private void configurarDesign() {

        panelMadre.setBackground(grisColor);
        panelContenido.setBackground(blancoColor);
        panelDatos.setBackground(blancoColor);
        panelBotonRegresar.setBackground(blancoColor);
        panelBotonRegistrar.setBackground(blancoColor);

        registrarBtn.setBackground(azulColor);
        registrarBtn.setForeground(Color.WHITE);
        registrarBtn.setPreferredSize(new Dimension(100, 30));
        registrarBtn.setFont(new Font("Arial", Font.BOLD, 15));

        ImageIcon imagenRegresar = new ImageIcon("src/resources/Imagenes/regresarbtn.png");
        regresarLabel.setIcon(imagenRegresar);


    }


    private void configurarEventos() {

        regresarLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GuiRegistrar.this.dispose();
                new Login();
            }
        });

        registrarBtn.addActionListener(e -> {

            SqlConnection sqlConnection = new SqlConnection();
            // Verificar que los campos no estén vacíos
            if (nombreTF.getText().isEmpty() || apellidoTF.getText().isEmpty() || correoTF.getText().isEmpty() || codigoTF.getText().isEmpty() || passwordField.getPassword().length == 0 || confirmarPasswordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "Por favor llene todos los campos");
                // Verificar que las contraseñas coincidan
            } else if (!Arrays.equals(passwordField.getPassword(), confirmarPasswordField.getPassword())) {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
            } else {

                // Rol 2 es para usuarios, asi que se asigna por defecto
                int rol = 2;
                // Instancio la clase Usuario. getPassword devuelve un arreglo de char, por eso se usa String.valueOf
                Usuario usuario = new Usuario(nombreTF.getText(), apellidoTF.getText(), correoTF.getText(), codigoTF.getText(), String.valueOf(passwordField.getPassword()), rol);
                // Devuelve true si el usuario ya existe
                if (sqlConnection.insertarDatos(usuario)) {
                    return;
                }
                GuiRegistrar.this.dispose();
                new Login();


            }


        });


    }

    // Método para crear un JLayeredPane con un JTextField y un JLabel
    // Funciona con passwordField porque es una subclase de JTextField
    private JLayeredPane createLayeredTextField(JTextField textField, String labelText) {

        JLayeredPane layeredPane = new JLayeredPane();
        JLabel helpLabel = new JLabel(labelText);
        helpLabel.setForeground(Color.GRAY);

        layeredPane.add(textField, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(helpLabel, JLayeredPane.POPUP_LAYER);

        textField.setFont(new Font("Arial", Font.PLAIN, 13));
        helpLabel.setFont(new Font("Arial", Font.PLAIN, 13));

        panelDatos.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = panelDatos.getSize();
                int width = size.width;
                // Suma 1 para que los componentes queden bien centrados
                int height = size.height / (numFilas + 1);

                // Establecer el tamaño y la posición del JTextField y JLabel
                textField.setBounds(0, 0, width - 45, height - 10);
                helpLabel.setBounds(2, 0, width - 45, height - 10);
                // Establecer el tamaño preferido del JLayeredPane
                layeredPane.setPreferredSize(new Dimension(width, height));


            }
        });

        // Agregar un DocumentListener al JTextField para mostrar u ocultar el JLabel
        // Se terminan creando 6 DocumentListener, uno por cada JTextField
        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            public void removeUpdate(DocumentEvent e) {
                update();
            }

            public void insertUpdate(DocumentEvent e) {
                update();
            }

            public void update() {

                if (textField.getText().isEmpty()) {
                    helpLabel.setVisible(true);
                } else {
                    helpLabel.setVisible(false);
                }
            }
        });

        return layeredPane;
    }

    public JPanel getPanel(String correo) {

        SqlConnection sqlConnection = new SqlConnection();

        JComboBox<String> rolCB = new JComboBox<>();
        rolCB.addItem("Administrador");
        rolCB.addItem("Usuario");
        rolCB.setFocusable(false);

        Usuario usuario = sqlConnection.obtenerUsuario(correo);

        nombreTF.setText(usuario.getNombre());
        apellidoTF.setText(usuario.getApellido());
        correoTF.setText(usuario.getCorreo());
        codigoTF.setText(usuario.getCodigo());
        passwordField.setText(usuario.getPassword());
        confirmarPasswordField.setText(usuario.getPassword());
        rolCB.setSelectedIndex(usuario.getRol() - 1);

        panelBotonRegresar.remove(regresarLabel);
        panelBotonRegistrar.remove(registrarBtn);
        panelContenido.remove(panelDatos);
        JPanel nuevoPanelDatos = new JPanel(new GridBagLayout());
        nuevoPanelDatos.setBackground(blancoColor);
        JLabel nombreLabel = new JLabel("Nombre: ");
        JLabel apellidoLabel = new JLabel("Apellido: ");
        JLabel correoLabel = new JLabel("Correo: ");
        JLabel codigoLabel = new JLabel("Codigo: ");
        JLabel passwordLabel = new JLabel("Contraseña: ");
        JLabel confirmarPasswordLabel = new JLabel("Confirmar Contraseña: ");
        JLabel rolLabel = new JLabel("Rol: ");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 10, 5, 10); // Para agregar un margen entre los componentes
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.weightx = 0.20;

        nuevoPanelDatos.add(nombreLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.80;
        nuevoPanelDatos.add(nombreTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.20;
        nuevoPanelDatos.add(apellidoLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.90;
        nuevoPanelDatos.add(apellidoTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.20;
        nuevoPanelDatos.add(correoLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.80;
        nuevoPanelDatos.add(correoTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.20;
        nuevoPanelDatos.add(codigoLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.80;
        nuevoPanelDatos.add(codigoTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.20;
        nuevoPanelDatos.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.80;
        nuevoPanelDatos.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.20;
        nuevoPanelDatos.add(confirmarPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.80;
        nuevoPanelDatos.add(confirmarPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.20;
        nuevoPanelDatos.add(rolLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.80;
        nuevoPanelDatos.add(rolCB, gbc);

        panelContenido.add(nuevoPanelDatos, BorderLayout.CENTER);

        JButton confirmarEdicionBtn = new JButton("Confirmar Cambios");
        confirmarEdicionBtn.setBackground(azulColor);
        confirmarEdicionBtn.setForeground(Color.WHITE);
        confirmarEdicionBtn.setPreferredSize(new Dimension(200, 30));
        panelBotonRegistrar.add(confirmarEdicionBtn);
        passwordField.setEchoChar((char) 0);
        confirmarPasswordField.setEchoChar((char) 0);

        confirmarEdicionBtn.addActionListener(e -> {
            if (nombreTF.getText().isEmpty() || apellidoTF.getText().isEmpty() || correoTF.getText().isEmpty() || codigoTF.getText().isEmpty() || passwordField.getPassword().length == 0 || confirmarPasswordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "Por favor llene todos los campos");
            } else if (!Arrays.equals(passwordField.getPassword(), confirmarPasswordField.getPassword())) {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
            } else {

                usuario.setNombre(nombreTF.getText());
                usuario.setApellido(apellidoTF.getText());
                usuario.setCorreo(correoTF.getText());
                usuario.setCodigo(codigoTF.getText());
                usuario.setPassword(String.valueOf(passwordField.getPassword()));
                usuario.setRol(rolCB.getSelectedIndex() + 1);

                sqlConnection.editarUsuario(usuario);
                Inicio.panelContenido.removeAll();
                Inicio.instanciaInicio.revalidate();
                Inicio.instanciaInicio.repaint();

                GuiMostrarTabla panel = new GuiMostrarTabla();
                panel.tablaRegistros.setModel(sqlConnection.selectDatos());
                Inicio.panelCentralCambiante = panel.getPanel();
                Inicio.panelContenido.add(Inicio.panelCentralCambiante, BorderLayout.CENTER);
                Inicio.instanciaInicio.revalidate();
                Inicio.instanciaInicio.repaint();


            }
        });
        return panelMadre;
    }
}


