package org.GUI;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Color;

public class GuiRegistrar extends JFrame{
    private JPanel RegistroPanel;
    private JTextField ApellidoJText;
    private JTextField NombreJText;
    private JTextField CorreoJText;
    private JTextField CodigoJText;
    private JButton EnviarRegistroButton;
    private JButton RetrocederButton;

    private final Color placeholderColor = Color.gray; // Color del placeholder
    private final Color textColor = Color.BLACK; // Color del texto

    public static ArrayList<DatosEstudiante> listaEstudiantes = new ArrayList<>();

    public GuiRegistrar(){

        setContentPane(RegistroPanel);
        setTitle("Registro de Estudiantes");
        setBounds(500,300,500,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        NombreJText.setText("Nombre");
        ApellidoJText.setText("Apellido");
        CorreoJText.setText("Correo");
        CodigoJText.setText("Codigo");

        NombreJText.setForeground(placeholderColor);
        ApellidoJText.setForeground(placeholderColor);
        CorreoJText.setForeground(placeholderColor);
        CodigoJText.setForeground(placeholderColor);

        NombreJText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(NombreJText.getText().equals("Nombre")){
                    NombreJText.setText("");
                    NombreJText.setForeground(textColor);

                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(NombreJText.getText().equals("")){
                    NombreJText.setForeground(placeholderColor);
                    NombreJText.setText("Nombre");

                }
            }
        });
        ApellidoJText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(ApellidoJText.getText().equals("Apellido")){
                    ApellidoJText.setForeground(textColor);
                    ApellidoJText.setText("");

                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(ApellidoJText.getText().equals("")){
                    ApellidoJText.setForeground(placeholderColor);
                    ApellidoJText.setText("Apellido");

                }
            }
        });
        CorreoJText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(CorreoJText.getText().equals("Correo")){
                    CorreoJText.setForeground(textColor);
                    CorreoJText.setText("");

                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(CorreoJText.getText().equals("")){
                    CorreoJText.setForeground(placeholderColor);
                    CorreoJText.setText("Correo");

                }
            }
        });
        CodigoJText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if(CodigoJText.getText().equals("Codigo")){
                    CodigoJText.setForeground(textColor);
                    CodigoJText.setText("");

                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if(CodigoJText.getText().equals("")){
                    CodigoJText.setForeground(placeholderColor);
                    CodigoJText.setText("Codigo");


                }
            }
        });
        RetrocederButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); //usa el dispose para cerrar la ventana JFrame.dispose() - no llama JFrame por la herencia
            }
        });
        EnviarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(NombreJText.getText().equals("") || ApellidoJText.getText().equals("") || CorreoJText.getText().equals("") || CodigoJText.getText().equals("") || NombreJText.getText().equals("Nombre") || ApellidoJText.getText().equals("Apellido") || CorreoJText.getText().equals("Correo") || CodigoJText.getText().equals("Codigo")){
                    JOptionPane.showMessageDialog(GuiRegistrar.this,"Por favor llene todos los campos");
                }
                else{
                    String nombre = NombreJText.getText();
                    String apellido = ApellidoJText.getText();
                    String correo = CorreoJText.getText();
                    String codigo = CodigoJText.getText();

                    DatosEstudiante formato = new DatosEstudiante(nombre,apellido,correo,codigo); //creo un objeto de la clase DatosEstudiante
                    listaEstudiantes.add(formato); //lo agrego al array de objetos que instancié llamado listaEstudiantes
                    JOptionPane.showMessageDialog(GuiRegistrar.this,"Registrado con exito");
                    dispose();
                }


            }
        });

        RegistroPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegistroPanel.requestFocusInWindow();

            }
        });


    }

    public static void mostrarEstudiantes(){
        for (DatosEstudiante estudiante : listaEstudiantes){
            JOptionPane.showMessageDialog(null,estudiante.getNombre() + " " + estudiante.getApellido() + " " + estudiante.getCorreo() + " " + estudiante.getCodigo());
        }
    }



}
