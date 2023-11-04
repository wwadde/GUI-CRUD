package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Inicio extends JFrame{

    private JPanel PanelPrincipal;
    private JButton VerRegistros;
    private JButton Registrar;
    private JLabel BienvenidaLabel;



    public Inicio(){

        setContentPane(PanelPrincipal);
        PanelPrincipal.setBackground(new Color(60,63,65));
        setTitle("Registro de Estudiantes");
        setBounds(500,300,500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        BienvenidaLabel.setForeground(Color.WHITE);




        Registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                GuiRegistrar RegistroFrame = new GuiRegistrar(); //crea el frame de registro Y lo superpone al frame de inicio
                RegistroFrame.setVisible(true);
                RegistroFrame.requestFocusInWindow();

            }
        });


        VerRegistros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiRegistrar importararraylist = new GuiRegistrar(); //importa el arraylist de GuiRegistrar
                GuiRegistrar.mostrarEstudiantes(); //muestra los estudiantes del arraylist
            }
        });


    }

    public static void main(String[] args) throws SQLException {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (Exception e) {
            e.printStackTrace();
            try{
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }

        SqlConnection sql = new SqlConnection();
        sql.SelectAzureSQL();

        Inicio frame = new Inicio();
        frame.setVisible(true);
    }
}