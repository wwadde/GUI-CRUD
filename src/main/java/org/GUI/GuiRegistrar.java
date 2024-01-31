package org.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
    private JButton regresarBtn;


    Color blancoColor = new Color(255, 255, 255, 202);
    Color grisColor = new Color(255, 255, 255, 89);
    Color azulColor = new Color(12, 106, 225, 186);
    Color errorRojo = new Color(255, 0, 0, 100);

    private static final int numFilas = 6;

    public GuiRegistrar() {

        initComponentes();

        setContentPane(panelMadre);
        setTitle("Registrar");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

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
        regresarBtn = new JButton();

        panelMadre.add(panelContenido, BorderLayout.CENTER);
        panelMadre.setBorder(new EmptyBorder(50, 50, 50, 50));

        panelContenido.add(panelBotonRegresar, BorderLayout.NORTH);
        panelContenido.add(panelDatos, BorderLayout.CENTER);
        panelContenido.add(panelBotonRegistrar, BorderLayout.SOUTH);

        panelBotonRegresar.add(regresarBtn);
        panelBotonRegistrar.add(registrarBtn);


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
        regresarBtn.setIcon(imagenRegresar);
        // Deshabilitar el pintado del fondo
        regresarBtn.setContentAreaFilled(false);
        // Deshabilitar el pintado del borde
        regresarBtn.setBorderPainted(false);


    }


    private void configurarEventos() {

        regresarBtn.addActionListener(e -> {
            GuiRegistrar.this.dispose();
            Login login = new Login();
        });

        registrarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SqlConnection sqlConnection = new SqlConnection();
                // Verificar que los campos no estén vacíos
                if (nombreTF.getText().isEmpty() || apellidoTF.getText().isEmpty() || correoTF.getText().isEmpty() || codigoTF.getText().isEmpty() || passwordField.getPassword().length == 0 || confirmarPasswordField.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null, "Por favor llene todos los campos");
                // Verificar que las contraseñas coincidan
                } else if (!Arrays.equals(passwordField.getPassword(), confirmarPasswordField.getPassword())) {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                // Verificar que el correo no esté registrado
                } else if (sqlConnection.usuarioExistente(correoTF.getText())){
                    JOptionPane.showMessageDialog(null, "El correo ya está registrado, escoja uno nuevo");
                }else {

                    // Instancio la clase Usuario. getPassword devuelve un arreglo de char, por eso se usa Arrays.toString
                    Usuario usuario = new Usuario(nombreTF.getText(), apellidoTF.getText(), correoTF.getText(), codigoTF.getText(), String.valueOf(passwordField.getPassword()));
                    sqlConnection.insertarDatos(usuario);
                    GuiRegistrar.this.dispose();
                    Login login = new Login();


                }



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
                helpLabel.setBounds(2, 0, width-45, height-10);
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
}


